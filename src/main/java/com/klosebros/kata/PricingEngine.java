package com.klosebros.kata;

import java.time.Month;

public class PricingEngine {

    private static final double BULK_DISCOUNT_THRESHOLD = 200.0;
    private static final double MAX_DRONE_WEIGHT_KG = 2.0;
    private static final double LUXURY_TAX_THRESHOLD = 500.0;

    public double calculatePrice(Order order) {
        double discount = calculateDiscount(order);
        double shippingCost = calculateShippingCost(order);
        double netPrice = order.basePrice() - discount;
        double tax = calculateTax(order, netPrice);
        return netPrice + shippingCost + tax;
    }

    // Priorität: BlackFriday > CustomerType (PREMIUM/VIP) > BulkDiscount (nur REGULAR)
    private double calculateDiscount(Order order) {
        if (isBlackFriday(order)) {
            return order.basePrice() * 0.20;
        }
        return switch (order.customerType()) {
            case VIP -> order.basePrice() * 0.10;
            case PREMIUM -> order.basePrice() * 0.05;
            case REGULAR -> isBulkOrder(order) ? order.basePrice() * 0.15 : 0.0;
        };
    }

    private boolean isBlackFriday(Order order) {
        return order.orderDate().getMonth() == Month.NOVEMBER;
    }

    private boolean isBulkOrder(Order order) {
        return order.basePrice() > BULK_DISCOUNT_THRESHOLD;
    }

    private double calculateShippingCost(Order order) {
        return switch (order.shippingType()) {
            case STANDARD -> 5.0 + 1.0 * order.weightKg();
            case EXPRESS -> 10.0 + 2.0 * order.weightKg();
            case PICKUP -> 0.0;
            case DRONE -> calculateDroneShipping(order);
        };
    }

    private double calculateDroneShipping(Order order) {
        if (order.weightKg() > MAX_DRONE_WEIGHT_KG) {
            throw new IllegalArgumentException(
                    "Drone-Versand ist auf %.1f kg begrenzt, Bestellung wiegt %.1f kg."
                            .formatted(MAX_DRONE_WEIGHT_KG, order.weightKg())
            );
        }
        return 20.0 + 5.0 * order.weightKg();
    }

    private double calculateTax(Order order, double netPrice) {
        double standardTax = netPrice * standardTaxRate(order);
        double luxuryTax = order.basePrice() > LUXURY_TAX_THRESHOLD ? order.basePrice() * 0.05 : 0.0;
        return standardTax + luxuryTax;
    }

    private double standardTaxRate(Order order) {
        return switch (order.destination()) {
            case LOCAL -> 0.19;
            case EU -> 0.10;
            case INTERNATIONAL -> 0.0;
        };
    }
}
