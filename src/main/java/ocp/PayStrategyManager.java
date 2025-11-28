package ocp;

public class PayStrategyManager implements PayStrategy {
    @Override
    public int calculatePay(int salary, int bonus) {
        return salary + bonus;
    }
}

