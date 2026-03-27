package com.klosebros.kata.discount;

import com.klosebros.kata.DiscountStrategy;
import com.klosebros.kata.Order;

/**
 * Grants a 15% bulk discount when the order base price exceeds 200€.
 * Returns 0€ for orders at or below the threshold.
 */
public class BulkDiscount implements DiscountStrategy {

    private static final double BULK_THRESHOLD = 200.0;
    private static final double BULK_DISCOUNT_RATE = 0.15;

    @Override
    public double calculateDiscount(Order order) {
        if (order.basePrice() <= BULK_THRESHOLD) {
            return 0.0;
        }
        return order.basePrice() * BULK_DISCOUNT_RATE;
    }
}

