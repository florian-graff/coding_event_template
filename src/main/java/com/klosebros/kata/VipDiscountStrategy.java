package com.klosebros.kata;

public class VipDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(Order order) {
        return order.basePrice() * 0.10;
    }
}

