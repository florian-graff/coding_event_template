package com.klosebros.kata.discount;

import com.klosebros.kata.DiscountStrategy;
import com.klosebros.kata.Order;

/**
 * Grants a 10% discount to VIP customers.
 */
public class VipDiscount implements DiscountStrategy {

    private static final double VIP_DISCOUNT_RATE = 0.10;

    @Override
    public double calculateDiscount(Order order) {
        return order.basePrice() * VIP_DISCOUNT_RATE;
    }
}

