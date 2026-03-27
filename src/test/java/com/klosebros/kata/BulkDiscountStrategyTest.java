package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BulkDiscountStrategyTest {

    private static final LocalDate ANY_DATE = LocalDate.of(2026, 1, 15);

    private final BulkDiscountStrategy strategy = new BulkDiscountStrategy();

    @Test
    void should_returnFifteenPercentDiscount_when_basePriceIsAbove200() {
        Order order = new Order(201.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(30.15);
    }

    @Test
    void should_returnZeroDiscount_when_basePriceIsExactly200() {
        Order order = new Order(200.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(0.0);
    }

    @Test
    void should_returnZeroDiscount_when_basePriceIsBelow200() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(0.0);
    }
}

