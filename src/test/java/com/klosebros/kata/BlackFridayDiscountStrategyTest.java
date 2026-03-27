package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BlackFridayDiscountStrategyTest {

    private final BlackFridayDiscountStrategy strategy = new BlackFridayDiscountStrategy();

    @Test
    void should_returnTwentyPercentDiscount_when_orderDateIsInNovember() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, LocalDate.of(2026, 11, 15));

        assertThat(strategy.calculateDiscount(order)).isEqualTo(20.0);
    }

    @Test
    void should_returnZeroDiscount_when_orderDateIsNotInNovember() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateDiscount(order)).isEqualTo(0.0);
    }
}

