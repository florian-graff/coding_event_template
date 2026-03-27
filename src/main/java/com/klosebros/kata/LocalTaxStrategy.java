package com.klosebros.kata;

public class LocalTaxStrategy implements TaxStrategy {

    private static final double TAX_RATE = 0.19;

    @Override
    public double calculateTax(Order order, double discountedBasePrice) {
        return discountedBasePrice * TAX_RATE;
    }
}

