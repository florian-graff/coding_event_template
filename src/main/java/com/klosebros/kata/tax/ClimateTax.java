package com.klosebros.kata.tax;

import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import com.klosebros.kata.TaxStrategy;

/**
 * Adds a flat 2€ climate surcharge for orders shipped via Express.
 * Returns 0€ for all other shipping types.
 *
 * <p>Compose with a destination-based tax via {@link CompositeTaxStrategy}
 * to layer this surcharge on top of the standard tax.</p>
 */
public class ClimateTax implements TaxStrategy {

    private static final double CLIMATE_SURCHARGE = 2.0;

    @Override
    public double calculateTax(Order order, double discountedPrice) {
        if (order.shippingType() != ShippingType.EXPRESS) {
            return 0.0;
        }
        return CLIMATE_SURCHARGE;
    }
}

