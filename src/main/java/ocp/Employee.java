package ocp;

public class Employee {

    private final int salary;
    private final int bonus;
    private final EmployeeType type;
    private final PayStrategy payStrategy;

    Employee(int salary, int bonus, EmployeeType type, PayStrategy payStrategy) {
        this.salary = salary;
        this.bonus = bonus;
        this.type = type;
        this.payStrategy = payStrategy;
    }

    public int payAmount() {
        return payStrategy.calculatePay(salary, bonus);
    }

}
