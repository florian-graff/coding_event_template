package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EuTaxStrategyTest {

    private final EuTaxStrategy strategy = new EuTaxStrategy();

    @Test
    void should_return10PercentOfDiscountedBasePrice_when_applied() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.EU, ShippingType.PICKUP, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateTax(order, 100.0)).isEqualTo(10.0);
    }
}

