package com.klosebros.kata;

public class BulkDiscountStrategy implements DiscountStrategy {

    private static final double BULK_THRESHOLD   = 200.0;
    private static final double DISCOUNT_RATE    = 0.15;

    @Override
    public double calculateDiscount(Order order) {
        if (order.basePrice() > BULK_THRESHOLD) {
            return order.basePrice() * DISCOUNT_RATE;
        }
        return 0.0;
    }
}

