package ocp;

public abstract class Employee {

    protected final int salary;

    Employee(int salary) {
        this.salary = salary;
    }

    abstract int payAmount();

}