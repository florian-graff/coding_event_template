package com.klosebros.kata;

public class BlackFridayDiscountStrategy implements DiscountStrategy {

    private static final int    BLACK_FRIDAY_MONTH = 11;
    private static final double DISCOUNT_RATE      = 0.20;

    @Override
    public double calculateDiscount(Order order) {
        if (order.orderDate().getMonthValue() == BLACK_FRIDAY_MONTH) {
            return order.basePrice() * DISCOUNT_RATE;
        }
        return 0.0;
    }
}

