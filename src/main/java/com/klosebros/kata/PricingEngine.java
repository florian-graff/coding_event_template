package com.klosebros.kata;

public class PricingEngine {

    private static final double LUXURY_TAX_THRESHOLD = 500.0;

    private final BlackFridayDiscountStrategy blackFridayDiscount = new BlackFridayDiscountStrategy();
    private final BulkDiscountStrategy bulkDiscount = new BulkDiscountStrategy();
    private final CustomerTypeDiscountStrategy customerTypeDiscount = new CustomerTypeDiscountStrategy();

    private final ShippingStrategy standardShipping = new StandardShippingStrategy();
    private final ShippingStrategy expressShipping = new ExpressShippingStrategy();
    private final ShippingStrategy pickupShipping = new PickupShippingStrategy();
    private final ShippingStrategy droneShipping = new DroneShippingStrategy();

    public double calculatePrice(Order order) {
        double discount = resolveDiscountStrategy(order).calculate(order);
        double shippingCost = resolveShippingStrategy(order).calculate(order);
        double netPrice = order.basePrice() - discount;
        double tax = calculateTax(order, netPrice);
        return netPrice + shippingCost + tax;
    }

    // Priorität: BlackFriday > CustomerType (PREMIUM/VIP) > BulkDiscount (nur REGULAR)
    private DiscountStrategy resolveDiscountStrategy(Order order) {
        if (blackFridayDiscount.appliesTo(order)) {
            return blackFridayDiscount;
        }
        if (bulkDiscount.appliesTo(order)) {
            return bulkDiscount;
        }
        return customerTypeDiscount;
    }

    private ShippingStrategy resolveShippingStrategy(Order order) {
        return switch (order.shippingType()) {
            case STANDARD -> standardShipping;
            case EXPRESS -> expressShipping;
            case PICKUP -> pickupShipping;
            case DRONE -> droneShipping;
        };
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
