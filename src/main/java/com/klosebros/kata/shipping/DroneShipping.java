package com.klosebros.kata.shipping;

import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingStrategy;

/**
 * Drone shipping: 20€ base fee plus 5€ per kilogram.
 *
 * <p>Maximum supported weight is {@value MAX_WEIGHT_KG} kg.
 * Orders exceeding this limit are rejected with an {@link IllegalArgumentException}.</p>
 */
public class DroneShipping implements ShippingStrategy {

    private static final double BASE_COST = 20.0;
    private static final double COST_PER_KG = 5.0;
    static final double MAX_WEIGHT_KG = 2.0;

    @Override
    public double calculateShippingCost(Order order) {
        if (order.weightKg() > MAX_WEIGHT_KG) {
            throw new IllegalArgumentException(
                    "Drone shipping is not available for orders heavier than "
                    + MAX_WEIGHT_KG + " kg, but got " + order.weightKg() + " kg");
        }
        return BASE_COST + COST_PER_KG * order.weightKg();
    }
}

