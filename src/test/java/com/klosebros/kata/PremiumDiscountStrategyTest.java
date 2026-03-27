package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PremiumDiscountStrategyTest {

    private final PremiumDiscountStrategy strategy = new PremiumDiscountStrategy();

    @Test
    void should_returnFivePercentOfBasePrice_when_applied() {
        Order order = new Order(100.0, 1.0, CustomerType.PREMIUM, Destination.INTERNATIONAL, ShippingType.PICKUP, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateDiscount(order)).isEqualTo(5.0);
    }
}

