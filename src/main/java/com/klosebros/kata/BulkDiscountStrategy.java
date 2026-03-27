package com.klosebros.kata;

public class BulkDiscountStrategy implements DiscountStrategy {

    private static final double BULK_DISCOUNT_THRESHOLD = 200.0;

    @Override
    public double calculate(Order order) {
        return order.basePrice() * 0.15;
    }

    public boolean appliesTo(Order order) {
        return order.customerType() == CustomerType.REGULAR
                && order.basePrice() > BULK_DISCOUNT_THRESHOLD;
    }
}

