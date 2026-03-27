package com.klosebros.kata;

import java.time.Month;

public class BlackFridayDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculate(Order order) {
        return order.basePrice() * 0.20;
    }

    public boolean appliesTo(Order order) {
        return order.orderDate().getMonth() == Month.NOVEMBER;
    }
}

