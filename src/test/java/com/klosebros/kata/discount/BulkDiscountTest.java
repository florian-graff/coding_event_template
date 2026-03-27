package com.klosebros.kata.discount;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BulkDiscount")
class BulkDiscountTest {

    private final BulkDiscount strategy = new BulkDiscount();
    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

    @Test
    @DisplayName("returns 15% discount when basePrice exceeds 200€")
    void calculateDiscount_aboveThreshold_returnsFifteenPercent() {
        Order order = new Order(201.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(30.15);
    }

    @Test
    @DisplayName("returns 0€ when basePrice is exactly 200€ (threshold is exclusive)")
    void calculateDiscount_atThreshold_returnsZero() {
        Order order = new Order(200.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ when basePrice is below 200€")
    void calculateDiscount_belowThreshold_returnsZero() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("scales correctly with a higher base price")
    void calculateDiscount_scalesWithBasePrice() {
        Order order = new Order(500.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(75.0);
    }
}

