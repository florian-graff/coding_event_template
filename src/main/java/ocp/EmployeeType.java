package ocp;

public enum EmployeeType {
    MANAGER {
        @Override
        public int pay(int salary, int bonus) {
            return salary + bonus;
        }
    },
    ENGINEER {
        @Override
        public int pay(int salary, int bonus) {
            return salary;
        }
    };

    public abstract int pay(int salary, int bonus);
}
