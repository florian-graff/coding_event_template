package ocp;

public class PayStrategyEngineer implements PayStrategy{
    @Override
    public int calculatePay(int salary, int bonus) {
        return salary;
    }
}
