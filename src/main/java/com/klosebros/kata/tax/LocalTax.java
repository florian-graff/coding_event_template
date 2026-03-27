package com.klosebros.kata.tax;

import com.klosebros.kata.Order;
import com.klosebros.kata.TaxStrategy;

/**
 * Applies 19% VAT for orders shipped to local (domestic) destinations.
 */
public class LocalTax implements TaxStrategy {

    private static final double LOCAL_TAX_RATE = 0.19;

    @Override
    public double calculateTax(Order order, double discountedPrice) {
        return discountedPrice * LOCAL_TAX_RATE;
    }
}

