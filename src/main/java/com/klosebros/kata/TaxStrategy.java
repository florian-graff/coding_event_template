package com.klosebros.kata;

@FunctionalInterface
public interface TaxStrategy {
    double calculate(Order order, double netPrice);
}

