package com.klosebros.kata;

/**
 * Strategy for calculating the discount amount for an order.
 *
 * <p>Implementations encapsulate a single discount rule (e.g. VIP rate,
 * Black Friday, bulk pricing). Multiple strategies can be composed to
 * apply several discounts simultaneously.</p>
 */
@FunctionalInterface
public interface DiscountStrategy {

    /**
     * Calculates the discount amount for the given order.
     *
     * @param order the order to evaluate; must not be {@code null}
     * @return the discount amount in EUR (≥ 0)
     */
    double calculateDiscount(Order order);
}

