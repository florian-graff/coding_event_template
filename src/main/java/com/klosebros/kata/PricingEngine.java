package com.klosebros.kata;

import java.time.LocalDate;

public class PricingEngine {

    private static final double PREMIUM_DISCOUNT_RATE      = 0.05;
    private static final double VIP_DISCOUNT_RATE          = 0.10;
    private static final double BLACK_FRIDAY_DISCOUNT_RATE = 0.20;
    private static final double BULK_DISCOUNT_RATE         = 0.15;
    private static final double BULK_DISCOUNT_THRESHOLD    = 200.0;
    private static final int    BLACK_FRIDAY_MONTH         = 11;

    private static final double STANDARD_SHIPPING_BASE  = 5.0;
    private static final double EXPRESS_SHIPPING_BASE   = 10.0;
    private static final double EXPRESS_SHIPPING_PER_KG = 2.0;
    private static final double DRONE_SHIPPING_BASE     = 20.0;
    private static final double DRONE_SHIPPING_PER_KG   = 5.0;
    private static final double DRONE_MAX_WEIGHT_KG     = 2.0;

    private static final double LOCAL_TAX_RATE      = 0.19;
    private static final double EU_TAX_RATE         = 0.10;
    private static final double LUXURY_TAX_RATE     = 0.05;
    private static final double LUXURY_TAX_THRESHOLD = 500.0;

    public enum CustomerType { REGULAR, PREMIUM, VIP }
    public enum Destination  { LOCAL, EU, INTERNATIONAL }
    public enum ShippingType { STANDARD, EXPRESS, DRONE, PICKUP }

    public record Order(
            double basePrice,
            double weightKg,
            CustomerType customerType,
            Destination destination,
            ShippingType shippingType,
            LocalDate orderDate
    ) {}

    public double calculatePrice(Order order) {
        double discount = switch (order.customerType()) {
            case PREMIUM -> order.basePrice() * PREMIUM_DISCOUNT_RATE;
            case VIP     -> order.basePrice() * VIP_DISCOUNT_RATE;
            case REGULAR -> 0.0;
        };
        if (order.orderDate().getMonthValue() == BLACK_FRIDAY_MONTH) {
            discount += order.basePrice() * BLACK_FRIDAY_DISCOUNT_RATE;
        }
        if (order.basePrice() > BULK_DISCOUNT_THRESHOLD && order.shippingType() != ShippingType.DRONE) {
            discount += order.basePrice() * BULK_DISCOUNT_RATE;
        }

        double shipping = switch (order.shippingType()) {
            case STANDARD -> STANDARD_SHIPPING_BASE + order.weightKg();
            case EXPRESS  -> EXPRESS_SHIPPING_BASE + EXPRESS_SHIPPING_PER_KG * order.weightKg();
            case DRONE    -> {
                if (order.weightKg() > DRONE_MAX_WEIGHT_KG) {
                    throw new IllegalArgumentException("Drone shipping supports max 2kg");
                }
                yield DRONE_SHIPPING_BASE + DRONE_SHIPPING_PER_KG * order.weightKg();
            }
            case PICKUP   -> 0.0;
        };

        double destinationTaxRate = switch (order.destination()) {
            case LOCAL         -> LOCAL_TAX_RATE;
            case EU            -> EU_TAX_RATE;
            case INTERNATIONAL -> 0.0;
        };
        double tax = (order.basePrice() - discount) * destinationTaxRate;
        if (order.basePrice() > LUXURY_TAX_THRESHOLD) {
            tax += order.basePrice() * LUXURY_TAX_RATE;
        }

        return order.basePrice() - discount + shipping + tax;
    }
}
