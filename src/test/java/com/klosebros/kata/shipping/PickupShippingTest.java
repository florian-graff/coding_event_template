package com.klosebros.kata.shipping;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PickupShipping")
class PickupShippingTest {

    private final PickupShipping strategy = new PickupShipping();
    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

    @Test
    @DisplayName("is always free (0€) regardless of weight")
    void calculateShippingCost_alwaysReturnsZero() {
        Order order = new Order(100.0, 50.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateShippingCost(order)).isEqualTo(0.0);
    }
}

