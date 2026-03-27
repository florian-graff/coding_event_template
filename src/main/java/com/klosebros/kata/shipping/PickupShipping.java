package com.klosebros.kata.shipping;

import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingStrategy;

/**
 * Pickup shipping: the customer collects the order themselves — no shipping cost.
 */
public class PickupShipping implements ShippingStrategy {

    @Override
    public double calculateShippingCost(Order order) {
        return 0.0;
    }
}

