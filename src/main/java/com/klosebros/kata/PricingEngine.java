package com.klosebros.kata;

public class PricingEngine {

    public double calculatePrice(Order order) {

        if (order.destination() == Destination.LOCAL) {
            return order.basePrice() * 1.19;
        } else if (order.destination() == Destination.EU) {
            return order.basePrice() * 1.10;
        }

        return order.basePrice();
    }
}
