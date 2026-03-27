package com.klosebros.kata;

import java.time.Month;

/**
 * Naive Phase-1 implementation of the pricing engine.
 * Calculates the final price of an order using inline if/else logic.
 *
 * <p>Formula: finalPrice = basePrice − discount + shippingCost + tax</p>
 *
 * <p>Tax is applied on the discounted price (basePrice − discount).</p>
 */
public class PricingEngine {

    /**
     * Calculates the final price for the given order.
     *
     * @param order the order to price; must not be {@code null}
     * @return the final price in EUR
     * @throws IllegalArgumentException if drone shipping is chosen for an order heavier than 2 kg
     */
    public double calculatePrice(Order order) {
        double discount = calculateDiscount(order);
        double shippingCost = calculateShippingCost(order);
        double tax = calculateTax(order, discount);
        return order.basePrice() - discount + shippingCost + tax;
    }

    private double calculateDiscount(Order order) {
        double basePrice = order.basePrice();

        if (order.orderDate().getMonth() == Month.NOVEMBER) {
            return basePrice * 0.20;
        }

        return switch (order.customerType()) {
            case REGULAR -> basePrice > 200 ? basePrice * 0.15 : 0.0;
            case PREMIUM -> basePrice * 0.05;
            case VIP    -> basePrice * 0.10;
        };
    }

    private double calculateShippingCost(Order order) {
        double weight = order.weightKg();
        return switch (order.shippingType()) {
            case STANDARD -> 5.0 + 1.0 * weight;
            case EXPRESS -> 10.0 + 2.0 * weight;
            case PICKUP -> 0.0;
            case DRONE -> {
                if (weight > 2.0) {
                    throw new IllegalArgumentException(
                            "Drone shipping is not available for orders heavier than 2 kg, but got " + weight + " kg");
                }
                yield 20.0 + 5.0 * weight;
            }
        };
    }

    private double calculateTax(Order order, double discount) {
        double discountedPrice = order.basePrice() - discount;

        double taxRate = switch (order.destination()) {
            case LOCAL -> 0.19;
            case EU -> 0.10;
            case INTERNATIONAL -> 0.0;
        };

        double tax = discountedPrice * taxRate;

        if (order.basePrice() > 500) {
            tax += discountedPrice * 0.05;
        }

        return tax;
    }
}

