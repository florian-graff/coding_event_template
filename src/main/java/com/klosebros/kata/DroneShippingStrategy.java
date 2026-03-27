package com.klosebros.kata;

public class DroneShippingStrategy implements ShippingStrategy {

    private static final double MAX_WEIGHT_KG = 2.0;
    private static final double BASE_COST     = 20.0;
    private static final double COST_PER_KG   = 5.0;

    @Override
    public double calculateShipping(Order order) {
        if (order.weightKg() > MAX_WEIGHT_KG) {
            throw new IllegalArgumentException("Drone shipping supports max 2kg");
        }
        return BASE_COST + COST_PER_KG * order.weightKg();
    }
}

