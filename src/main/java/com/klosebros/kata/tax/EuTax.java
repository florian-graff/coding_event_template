package com.klosebros.kata.tax;

import com.klosebros.kata.Order;
import com.klosebros.kata.TaxStrategy;

/**
 * Applies 10% VAT for orders shipped to EU destinations.
 */
public class EuTax implements TaxStrategy {

    private static final double EU_TAX_RATE = 0.10;

    @Override
    public double calculateTax(Order order, double discountedPrice) {
        return discountedPrice * EU_TAX_RATE;
    }
}

