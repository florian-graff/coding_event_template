package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ExpressShippingStrategyTest {

    private final ExpressShippingStrategy strategy = new ExpressShippingStrategy();

    @Test
    void should_return10Plus2TimesWeightInEuros_when_applied() {
        Order order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.EXPRESS, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateShipping(order)).isEqualTo(14.0);
    }
}

