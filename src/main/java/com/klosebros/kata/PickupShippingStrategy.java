package com.klosebros.kata;

public class PickupShippingStrategy implements ShippingStrategy {

    @Override
    public double calculateShipping(Order order) {
        return 0.0;
    }
}

