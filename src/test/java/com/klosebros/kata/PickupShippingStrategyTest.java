package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PickupShippingStrategyTest {

    private final PickupShippingStrategy strategy = new PickupShippingStrategy();

    @Test
    void should_returnZeroShippingCost_when_applied() {
        Order order = new Order(100.0, 5.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateShipping(order)).isEqualTo(0.0);
    }
}

