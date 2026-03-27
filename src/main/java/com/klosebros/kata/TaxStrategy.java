package com.klosebros.kata;

public interface TaxStrategy {
    double calculateTax(Order order, double discountedBasePrice);
}

