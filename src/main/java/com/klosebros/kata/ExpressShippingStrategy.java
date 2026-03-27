package com.klosebros.kata;

public class ExpressShippingStrategy implements ShippingStrategy {

    @Override
    public double calculate(Order order) {
        return 10.0 + 2.0 * order.weightKg();
    }
}

