package com.klosebros.kata.shipping;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingStrategy;
/**
 * Adds a 3EUR Christmas surcharge for orders placed in December.
 * Returns 0EUR in all other months.
 *
 * <p>Compose with a base {@link ShippingStrategy} via {@code CompositeShippingStrategy}
 * to layer this surcharge on top of the chosen shipping method.</p>
 *
 * <p>TODO Phase 3 - implement the business rule.</p>
 */
public class ChristmasShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(Order order) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
