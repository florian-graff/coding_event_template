package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LocalTaxStrategyTest {

    private final LocalTaxStrategy strategy = new LocalTaxStrategy();

    @Test
    void should_return19PercentOfDiscountedBasePrice_when_applied() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateTax(order, 100.0)).isEqualTo(19.0);
    }
}

