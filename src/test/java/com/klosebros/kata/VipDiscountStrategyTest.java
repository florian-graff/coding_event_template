package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class VipDiscountStrategyTest {

    private final VipDiscountStrategy strategy = new VipDiscountStrategy();

    @Test
    void should_returnTenPercentOfBasePrice_when_applied() {
        Order order = new Order(100.0, 1.0, CustomerType.VIP, Destination.INTERNATIONAL, ShippingType.PICKUP, LocalDate.of(2026, 1, 15));

        assertThat(strategy.calculateDiscount(order)).isEqualTo(10.0);
    }
}

