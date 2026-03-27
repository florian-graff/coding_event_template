package com.klosebros.kata;

public class ExpressShippingStrategy implements ShippingStrategy {

    private static final double BASE_COST   = 10.0;
    private static final double COST_PER_KG = 2.0;

    @Override
    public double calculateShipping(Order order) {
        return BASE_COST + COST_PER_KG * order.weightKg();
    }
}

