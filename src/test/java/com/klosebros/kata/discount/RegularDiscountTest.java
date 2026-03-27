package com.klosebros.kata.discount;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RegularDiscount")
class RegularDiscountTest {

    private final RegularDiscount strategy = new RegularDiscount();
    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2026, 3, 27, 10, 0);

    @ParameterizedTest(name = "basePrice = {0}€ → discount = 0€")
    @ValueSource(doubles = {0.0, 50.0, 100.0, 300.0})
    @DisplayName("always returns 0€ regardless of base price")
    void calculateDiscount_alwaysReturnsZero(double basePrice) {
        Order order = new Order(basePrice, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(0.0);
    }
}

