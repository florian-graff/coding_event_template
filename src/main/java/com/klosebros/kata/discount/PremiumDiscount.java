package com.klosebros.kata.discount;

import com.klosebros.kata.DiscountStrategy;
import com.klosebros.kata.Order;

/**
 * Grants a 5% discount to Premium customers.
 */
public class PremiumDiscount implements DiscountStrategy {

    private static final double PREMIUM_DISCOUNT_RATE = 0.05;

    @Override
    public double calculateDiscount(Order order) {
        return order.basePrice() * PREMIUM_DISCOUNT_RATE;
    }
}

