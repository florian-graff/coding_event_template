package com.klosebros.kata;

@FunctionalInterface
public interface DiscountStrategy {
    double calculate(Order order);
}

