package com.klosebros.kata;

/**
 * Strategy for calculating the tax amount for an order.
 *
 * <p>Tax is applied on the <em>discounted</em> price
 * ({@code basePrice − discount}), not the base price.
 * Implementations encapsulate a single tax rule (e.g. local VAT, EU VAT,
 * luxury surcharge). Multiple strategies can be composed via
 * {@code CompositeTaxStrategy} to accumulate several tax rules.</p>
 */
@FunctionalInterface
public interface TaxStrategy {

    /**
     * Calculates the tax amount for the given order.
     *
     * @param order           the order to evaluate; must not be {@code null}
     * @param discountedPrice the price after discount (basePrice − discount)
     * @return the tax amount in EUR (≥ 0)
     */
    double calculateTax(Order order, double discountedPrice);
}

