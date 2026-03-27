package com.klosebros.kata.discount;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("VipDiscount")
class VipDiscountTest {

    private final VipDiscount strategy = new VipDiscount();
    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

    @Test
    @DisplayName("returns 10% of base price")
    void calculateDiscount_returnsTenPercent() {
        Order order = new Order(100.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(10.0);
    }

    @Test
    @DisplayName("scales correctly with a different base price")
    void calculateDiscount_scalesWithBasePrice() {
        Order order = new Order(300.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(30.0);
    }
}

