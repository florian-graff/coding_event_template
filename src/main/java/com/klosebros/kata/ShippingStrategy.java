package com.klosebros.kata;

@FunctionalInterface
public interface ShippingStrategy {
    double calculate(Order order);
}

