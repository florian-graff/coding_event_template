package com.klosebros.kata;

public class InternationalTaxStrategy implements TaxStrategy {

    @Override
    public double calculate(Order order, double netPrice) {
        return 0.0;
    }
}

