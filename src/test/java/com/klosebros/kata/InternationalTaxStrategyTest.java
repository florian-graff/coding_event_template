package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class InternationalTaxStrategyTest {

    private final InternationalTaxStrategy strategy = new InternationalTaxStrategy();

    @Test
    void should_returnZeroTax_when_applied() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateTax(order, 100.0)).isEqualTo(0.0);
    }
}

