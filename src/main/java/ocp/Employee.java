package ocp;

public class Employee {

    private final int salary;
    private final int bonus;
    private final EmployeeType type;
    private final PayStrategy payStrategy = new PayStrategy();

    Employee(int salary, int bonus, EmployeeType type) {
        this.salary = salary;
        this.bonus = bonus;
        this.type = type;
    }

    public int payAmount() {
        return payStrategy.calculatePay(salary, bonus, type);
    }

}
