   package com.klosebros.kata.discount;

import com.klosebros.kata.DiscountStrategy;
import com.klosebros.kata.Order;

/**
 * Grants an 8% discount for orders placed during happy hour (18:00–19:59).
 * Returns 0€ outside that window, making it safe to compose with other strategies.
 */
public class HappyHourDiscount implements DiscountStrategy {

    private static final double HAPPY_HOUR_DISCOUNT_RATE = 0.08;
    private static final int HAPPY_HOUR_START = 18;
    private static final int HAPPY_HOUR_END   = 20; // exclusive

    @Override
    public double calculateDiscount(Order order) {
        int hour = order.orderDate().getHour();
        if (hour < HAPPY_HOUR_START || hour >= HAPPY_HOUR_END) {
            return 0.0;
        }
        return order.basePrice() * HAPPY_HOUR_DISCOUNT_RATE;
    }
}

