package com.klosebros.kata;

import java.util.List;

public class PricingEngine {

    private final List<DiscountStrategy> discountStrategies;
    private final ShippingStrategy shippingStrategy;
    private final List<TaxStrategy> taxStrategies;

    public PricingEngine(List<DiscountStrategy> discountStrategies,
                         ShippingStrategy shippingStrategy,
                         List<TaxStrategy> taxStrategies) {
        this.discountStrategies = discountStrategies;
        this.shippingStrategy = shippingStrategy;
        this.taxStrategies = taxStrategies;
    }

    public double calculatePrice(Order order) {
        double totalDiscount = discountStrategies.stream()
                .mapToDouble(strategy -> strategy.calculateDiscount(order))
                .sum();

        double shipping = shippingStrategy.calculateShipping(order);

        double discountedBasePrice = order.basePrice() - totalDiscount;

        double totalTax = taxStrategies.stream()
                .mapToDouble(strategy -> strategy.calculateTax(order, discountedBasePrice))
                .sum();

        return discountedBasePrice + shipping + totalTax;
    }
}
