package ocp;

public class ManagerEmployee extends Employee {
    final int bonus;

    ManagerEmployee(int salary, int bonus) {
        super(salary);
        this.bonus = bonus;
    }

    @Override
    int payAmount() {
        return salary + bonus;
    }
}
