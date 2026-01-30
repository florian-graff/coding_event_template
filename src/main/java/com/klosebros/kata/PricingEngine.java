package com.klosebros.kata;

public class PricingEngine {

    public double calculatePrice(Order order) {
        var price = order.basePrice();

        var tax = calculateTax(order);
        price *= tax;

        var shippingCost = calculateShippingCost(order);
        price += shippingCost;

        return price;
    }

    private double calculateShippingCost(Order order) {
        if (order.shippingType() == ShippingType.STANDARD) {
            return 5.0;
        }
        return 0.0;
    }

    private Double calculateTax(Order order) {
        // calculate tax
        if (order.destination() == Destination.LOCAL) {
            return 1.19;
        } else if (order.destination() == Destination.EU) {
            return 1.10;
        }
        return 1.0;
    }
}
