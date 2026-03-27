package com.klosebros.kata.shipping;

import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingStrategy;

/**
 * Standard shipping: 5€ base fee plus 1€ per kilogram.
 */
public class StandardShipping implements ShippingStrategy {

    private static final double BASE_COST = 5.0;
    private static final double COST_PER_KG = 1.0;

    @Override
    public double calculateShippingCost(Order order) {
        return BASE_COST + COST_PER_KG * order.weightKg();
    }
}

