package com.klosebros.kata.discount;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PremiumDiscount")
class PremiumDiscountTest {

    private final PremiumDiscount strategy = new PremiumDiscount();
    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2026, 3, 27, 10, 0);

    @Test
    @DisplayName("returns 5% of base price")
    void calculateDiscount_returnsFivePercent() {
        Order order = new Order(100.0, 1.0, CustomerType.PREMIUM, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(5.0);
    }

    @Test
    @DisplayName("scales correctly with a different base price")
    void calculateDiscount_scalesWithBasePrice() {
        Order order = new Order(200.0, 1.0, CustomerType.PREMIUM, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(10.0);
    }
}

