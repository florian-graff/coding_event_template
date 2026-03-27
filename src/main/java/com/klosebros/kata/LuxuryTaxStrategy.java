package com.klosebros.kata;

public class LuxuryTaxStrategy implements TaxStrategy {

    private static final double LUXURY_TAX_THRESHOLD = 500.0;

    @Override
    public double calculate(Order order, double netPrice) {
        if (order.basePrice() > LUXURY_TAX_THRESHOLD) {
            return order.basePrice() * 0.05;
        }
        return 0.0;
    }
}

