package com.klosebros.kata;

public class LuxuryTaxStrategy implements TaxStrategy {

    private static final double LUXURY_THRESHOLD = 500.0;
    private static final double TAX_RATE         = 0.05;

    @Override
    public double calculateTax(Order order, double discountedBasePrice) {
        if (order.basePrice() > LUXURY_THRESHOLD) {
            return order.basePrice() * TAX_RATE;
        }
        return 0.0;
    }
}

