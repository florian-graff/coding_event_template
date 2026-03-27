package com.klosebros.kata.discount;

import com.klosebros.kata.DiscountStrategy;
import com.klosebros.kata.Order;

/**
 * No-discount strategy for regular customers.
 * Always returns 0€ discount.
 */
public class RegularDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(Order order) {
        return 0.0;
    }
}

