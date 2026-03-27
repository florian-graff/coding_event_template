package com.klosebros.kata;

public class DroneShippingStrategy implements ShippingStrategy {

    private static final double MAX_WEIGHT_KG = 2.0;

    @Override
    public double calculate(Order order) {
        if (order.weightKg() > MAX_WEIGHT_KG) {
            throw new IllegalArgumentException(
                    "Drone-Versand ist auf %.1f kg begrenzt, Bestellung wiegt %.1f kg."
                            .formatted(MAX_WEIGHT_KG, order.weightKg())
            );
        }
        return 20.0 + 5.0 * order.weightKg();
    }
}

