package com.klosebros.kata;

public class EuTaxStrategy implements TaxStrategy {

    private static final double TAX_RATE = 0.10;

    @Override
    public double calculateTax(Order order, double discountedBasePrice) {
        return discountedBasePrice * TAX_RATE;
    }
}

