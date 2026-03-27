package com.klosebros.kata;

public class StandardShippingStrategy implements ShippingStrategy {

    @Override
    public double calculate(Order order) {
        return 5.0 + 1.0 * order.weightKg();
    }
}

