package dip;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.Assertions.assertThat;


class emailSenderShould {
    private final ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();
    @Test
    void writEmailOnStdOut() {
        //Given
        System.setOut(new PrintStream(consoleContent));
        EmailSenderImpl emailSender = new EmailSenderImpl();
        Email email = new Email("to", "subject", "body");
        //When
        emailSender.send(email);
        //Then
        String actual = consoleContent.toString();
        assertThat(actual)
                .isEqualTo("To:to, Subject: subject, Message: body");
    }
}