package com.klosebros.kata;

/**
 * Pricing engine that calculates the final price of an order by delegating
 * to injected strategy implementations.
 *
 * <p>Formula: {@code finalPrice = basePrice − discount + shippingCost + tax}</p>
 *
 * <p>Tax is applied on the discounted price ({@code basePrice − discount}).
 * This class contains no business logic — all rules live in the strategies.</p>
 */
public class PricingEngine {

    private final DiscountStrategy discountStrategy;
    private final ShippingStrategy shippingStrategy;
    private final TaxStrategy taxStrategy;

    /**
     * Creates a pricing engine with the given strategies.
     *
     * @param discountStrategy strategy that determines the discount amount
     * @param shippingStrategy strategy that determines the shipping cost
     * @param taxStrategy      strategy that determines the tax amount
     */
    public PricingEngine(DiscountStrategy discountStrategy,
                         ShippingStrategy shippingStrategy,
                         TaxStrategy taxStrategy) {
        this.discountStrategy = discountStrategy;
        this.shippingStrategy = shippingStrategy;
        this.taxStrategy = taxStrategy;
    }

    /**
     * Calculates the final price for the given order.
     *
     * @param order the order to price; must not be {@code null}
     * @return the final price in EUR
     */
    public double calculatePrice(Order order) {
        double discount = discountStrategy.calculateDiscount(order);
        double shippingCost = shippingStrategy.calculateShippingCost(order);
        double discountedPrice = order.basePrice() - discount;
        double tax = taxStrategy.calculateTax(order, discountedPrice);
        return discountedPrice + shippingCost + tax;
    }
}
