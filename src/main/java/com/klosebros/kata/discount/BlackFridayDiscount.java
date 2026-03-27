package com.klosebros.kata.discount;

import com.klosebros.kata.DiscountStrategy;
import com.klosebros.kata.Order;

import java.time.Month;

/**
 * Grants a 20% Black Friday discount for orders placed in November.
 * Returns 0€ for orders placed in any other month, making it safe to compose
 * with other discount strategies in Phase 4.
 */
public class BlackFridayDiscount implements DiscountStrategy {

    private static final double BLACK_FRIDAY_DISCOUNT_RATE = 0.20;

    @Override
    public double calculateDiscount(Order order) {
        if (order.orderDate().getMonth() != Month.NOVEMBER) {
            return 0.0;
        }
        return order.basePrice() * BLACK_FRIDAY_DISCOUNT_RATE;
    }
}

