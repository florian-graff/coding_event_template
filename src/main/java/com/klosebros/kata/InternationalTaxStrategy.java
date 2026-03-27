package com.klosebros.kata;

public class InternationalTaxStrategy implements TaxStrategy {

    @Override
    public double calculateTax(Order order, double discountedBasePrice) {
        return 0.0;
    }
}

