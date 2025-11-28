package ocp;

public class EngineerEmployee extends Employee {

    EngineerEmployee(int salary) {
        super(salary);
    }

    @Override
    int payAmount() {
        return salary;
    }
}
