package com.klosebros.kata;

public class EuTaxStrategy implements TaxStrategy {

    @Override
    public double calculate(Order order, double netPrice) {
        return netPrice * 0.10;
    }
}

