package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LuxuryTaxStrategyTest {

    private static final LocalDate ANY_DATE = LocalDate.of(2026, 1, 15);

    private final LuxuryTaxStrategy strategy = new LuxuryTaxStrategy();

    @Test
    void should_return5PercentOfBasePrice_when_basePriceIsAbove500() {
        Order order = new Order(600.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 600.0)).isEqualTo(30.0);
    }

    @Test
    void should_returnZeroTax_when_basePriceIsExactly500() {
        Order order = new Order(500.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 500.0)).isEqualTo(0.0);
    }

    @Test
    void should_returnZeroTax_when_basePriceIsBelow500() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 100.0)).isEqualTo(0.0);
    }
}

