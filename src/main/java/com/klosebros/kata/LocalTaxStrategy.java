package com.klosebros.kata;

public class LocalTaxStrategy implements TaxStrategy {

    @Override
    public double calculate(Order order, double netPrice) {
        return netPrice * 0.19;
    }
}

