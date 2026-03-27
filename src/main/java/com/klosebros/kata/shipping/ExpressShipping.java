package com.klosebros.kata.shipping;

import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingStrategy;

/**
 * Express shipping: 10€ base fee plus 2€ per kilogram.
 */
public class ExpressShipping implements ShippingStrategy {

    private static final double BASE_COST = 10.0;
    private static final double COST_PER_KG = 2.0;

    @Override
    public double calculateShippingCost(Order order) {
        return BASE_COST + COST_PER_KG * order.weightKg();
    }
}

