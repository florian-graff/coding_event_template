   package com.klosebros.kata.discount;

import com.klosebros.kata.DiscountStrategy;
import com.klosebros.kata.Order;

/**
 * Grants an 8% discount for orders placed during happy hour (18:00–19:59).
 *
 * <p>TODO Phase 3 – implement the business rule.</p>
 */
public class HappyHourDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(Order order) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

