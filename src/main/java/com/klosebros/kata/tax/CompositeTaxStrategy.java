package com.klosebros.kata.tax;

import com.klosebros.kata.Order;
import com.klosebros.kata.TaxStrategy;

import java.util.List;

/**
 * Composite tax strategy that additively combines multiple {@link TaxStrategy} instances.
 *
 * <p>Use this to layer a destination-based tax with optional surcharges:</p>
 * <pre>{@code
 * TaxStrategy tax = new CompositeTaxStrategy(List.of(new LocalTax(), new LuxuryTax()));
 * }</pre>
 */
public class CompositeTaxStrategy implements TaxStrategy {

    private final List<TaxStrategy> strategies;

    /**
     * Creates a composite strategy from the given list.
     *
     * @param strategies the tax strategies to combine; must not be {@code null} or empty
     */
    public CompositeTaxStrategy(List<TaxStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public double calculateTax(Order order, double discountedPrice) {
        return strategies.stream()
                .mapToDouble(strategy -> strategy.calculateTax(order, discountedPrice))
                .sum();
    }
}

