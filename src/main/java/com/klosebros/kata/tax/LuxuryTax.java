package com.klosebros.kata.tax;

import com.klosebros.kata.Order;
import com.klosebros.kata.TaxStrategy;

/**
 * Applies an additional 5% luxury surcharge when the order base price exceeds 500€.
 * Returns 0€ for orders at or below the threshold.
 *
 * <p>Intended to be composed with a destination-based tax strategy via
 * {@link CompositeTaxStrategy}.</p>
 */
public class LuxuryTax implements TaxStrategy {

    private static final double LUXURY_THRESHOLD = 500.0;
    private static final double LUXURY_TAX_RATE = 0.05;

    @Override
    public double calculateTax(Order order, double discountedPrice) {
        if (order.basePrice() <= LUXURY_THRESHOLD) {
            return 0.0;
        }
        return discountedPrice * LUXURY_TAX_RATE;
    }
}

