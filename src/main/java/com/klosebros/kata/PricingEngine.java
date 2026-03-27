package com.klosebros.kata;

public class PricingEngine {

    private final BlackFridayDiscountStrategy blackFridayDiscount = new BlackFridayDiscountStrategy();
    private final BulkDiscountStrategy bulkDiscount = new BulkDiscountStrategy();
    private final CustomerTypeDiscountStrategy customerTypeDiscount = new CustomerTypeDiscountStrategy();

    private final ShippingStrategy standardShipping = new StandardShippingStrategy();
    private final ShippingStrategy expressShipping = new ExpressShippingStrategy();
    private final ShippingStrategy pickupShipping = new PickupShippingStrategy();
    private final ShippingStrategy droneShipping = new DroneShippingStrategy();

    private final TaxStrategy localTax = new LocalTaxStrategy();
    private final TaxStrategy euTax = new EuTaxStrategy();
    private final TaxStrategy internationalTax = new InternationalTaxStrategy();
    private final TaxStrategy luxuryTax = new LuxuryTaxStrategy();

    public double calculatePrice(Order order) {
        double discount = resolveDiscountStrategy(order).calculate(order);
        double shippingCost = resolveShippingStrategy(order).calculate(order);
        double netPrice = order.basePrice() - discount;
        double tax = resolveTaxStrategy(order).calculate(order, netPrice)
                + luxuryTax.calculate(order, netPrice);
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

    private TaxStrategy resolveTaxStrategy(Order order) {
        return switch (order.destination()) {
            case LOCAL -> localTax;
            case EU -> euTax;
            case INTERNATIONAL -> internationalTax;
        };
    }
}
