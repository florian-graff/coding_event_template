package dip;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.MonthDay;
import java.util.List;

import static dip.EmployeeBuilder.anEmployee;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BirthdayGreeterShould {
    private static final int CURRENT_MONTH = 7;
    private static final int CURRENT_DAY_OF_MONTH = 9;
    private static final MonthDay TODAY = MonthDay.of(CURRENT_MONTH, CURRENT_DAY_OF_MONTH);

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private Clock clock;
    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private BirthdayGreeter birthdayGreeter;

    private final ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();

    @Test
    void should_send_greeting_email_to_employee() {
        //System.setOut(new PrintStream(consoleContent));
        given(clock.monthDay()).willReturn(TODAY);
        Employee employee = anEmployee().build();
        given(employeeRepository.findEmployeesBornOn(MonthDay.of(CURRENT_MONTH, CURRENT_DAY_OF_MONTH))).willReturn(List.of(employee));

        birthdayGreeter.sendGreetings();

//        String actual = consoleContent.toString();
//        assertThat(actual)
//                .isEqualTo("To:" + employee.getEmail() + ", Subject: Happy birthday!, Message: Happy birthday, dear " + employee.getFirstName()+"!");

        ArgumentCaptor<Email> captor = ArgumentCaptor.forClass(Email.class);
        verify(emailSender, times(1)).send(captor.capture());

        Email sent = captor.getValue();
        assertThat(sent.getTo()).isEqualTo(employee.getEmail());
        assertThat(sent.getSubject()).isEqualTo("Happy birthday!");
        assertThat(sent.getMessage()).isEqualTo("Happy birthday, dear " + employee.getFirstName() + "!");
    }
}