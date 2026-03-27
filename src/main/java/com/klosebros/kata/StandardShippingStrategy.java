package com.klosebros.kata;

public class StandardShippingStrategy implements ShippingStrategy {

    private static final double BASE_COST   = 5.0;
    private static final double COST_PER_KG = 1.0;

    @Override
    public double calculateShipping(Order order) {
        return BASE_COST + COST_PER_KG * order.weightKg();
    }
}

