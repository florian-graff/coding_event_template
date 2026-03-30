package com.klosebros.kata.shipping;

import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingStrategy;

import java.util.List;

/**
 * Composite shipping strategy that additively combines multiple {@link ShippingStrategy} instances.
 *
 * <p>Use this to layer a base shipping cost with optional surcharges:</p>
 * <pre>{@code
 * ShippingStrategy shipping = new CompositeShippingStrategy(
 *     List.of(new DroneShipping(), new ChristmasShipping()));
 * }</pre>
 *
 * <p>Each contained strategy is evaluated independently; their results are summed.
 * If any strategy throws (e.g. {@link DroneShipping} for overweight orders),
 * the exception propagates immediately.</p>
 */
public class CompositeShippingStrategy implements ShippingStrategy {

    private final List<ShippingStrategy> strategies;

    /**
     * Creates a composite strategy from the given list.
     *
     * @param strategies the shipping strategies to combine; must not be {@code null} or empty
     */
    public CompositeShippingStrategy(List<ShippingStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public double calculateShippingCost(Order order) {
        return strategies.stream()
                .mapToDouble(strategy -> strategy.calculateShippingCost(order))
                .sum();
    }
}

