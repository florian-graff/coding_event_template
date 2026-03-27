package com.klosebros.kata;

public class PremiumDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(Order order) {
        return order.basePrice() * 0.05;
    }
}

