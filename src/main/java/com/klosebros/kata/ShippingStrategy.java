package com.klosebros.kata;

/**
 * Strategy for calculating the shipping cost for an order.
 *
 * <p>Implementations encapsulate a single shipping rule (e.g. Standard,
 * Express, Drone, Pickup). Multiple strategies can be composed to layer
 * additional surcharges on top of a base shipping cost.</p>
 */
@FunctionalInterface
public interface ShippingStrategy {

    /**
     * Calculates the shipping cost for the given order.
     *
     * @param order the order to evaluate; must not be {@code null}
     * @return the shipping cost in EUR (≥ 0)
     * @throws IllegalArgumentException if the order violates a shipping constraint
     *                                  (e.g. drone shipping over 2 kg)
     */
    double calculateShippingCost(Order order);
}

