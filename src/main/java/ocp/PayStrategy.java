package ocp;

public class PayStrategy {
    public int calculatePay(int salary, int bonus, EmployeeType type) {
        return switch (type) {
            case ENGINEER -> salary;
            case MANAGER -> salary + bonus;
            default -> 0;
        };
    }
}
