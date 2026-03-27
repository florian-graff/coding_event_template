package com.klosebros.kata;

public class CustomerTypeDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculate(Order order) {
        return switch (order.customerType()) {
            case VIP -> order.basePrice() * 0.10;
            case PREMIUM -> order.basePrice() * 0.05;
            case REGULAR -> 0.0;
        };
    }
}

