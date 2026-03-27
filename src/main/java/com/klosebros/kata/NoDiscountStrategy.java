package com.klosebros.kata;

public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(Order order) {
        return 0.0;
    }
}

