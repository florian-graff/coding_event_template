package com.klosebros.kata.shipping;

import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingStrategy;

import java.time.Month;

/**
 * Adds a flat 3EUR Christmas surcharge for orders placed in December.
 * Returns 0EUR in all other months.
 *
 * <p>Compose with a base {@link ShippingStrategy} via {@code CompositeShippingStrategy}
 * to layer this surcharge on top of the chosen shipping method.</p>
 */
public class ChristmasShipping implements ShippingStrategy {

    private static final double CHRISTMAS_SURCHARGE = 3.0;

    @Override
    public double calculateShippingCost(Order order) {
        if (order.orderDate().getMonth() != Month.DECEMBER) {
            return 0.0;
        }
        return CHRISTMAS_SURCHARGE;
    }
}

