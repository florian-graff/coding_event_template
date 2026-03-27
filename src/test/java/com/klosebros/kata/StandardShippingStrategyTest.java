package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StandardShippingStrategyTest {

    private final StandardShippingStrategy strategy = new StandardShippingStrategy();

    @Test
    void should_return5PlusWeightInEuros_when_applied() {
        Order order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.STANDARD, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateShipping(order)).isEqualTo(7.0);
    }
}

