package com.klosebros.kata.tax;

import com.klosebros.kata.Order;
import com.klosebros.kata.TaxStrategy;

/**
 * Adds a flat 2€ climate surcharge for orders shipped via Express.
 * Returns 0€ for all other shipping types.
 *
 * <p>Compose with a destination-based tax via {@link CompositeTaxStrategy}
 * to layer this surcharge on top of the standard tax.</p>
 *
 * <p>TODO Phase 3 – implement the business rule.</p>
 */
public class ClimateTax implements TaxStrategy {

    @Override
    public double calculateTax(Order order, double discountedPrice) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

