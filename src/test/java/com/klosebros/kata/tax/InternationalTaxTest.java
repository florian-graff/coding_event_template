package com.klosebros.kata.tax;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InternationalTax")
class InternationalTaxTest {

    private final InternationalTax strategy = new InternationalTax();
    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2026, 3, 27, 10, 0);

    @ParameterizedTest(name = "discountedPrice = {0}€ → tax = 0€")
    @ValueSource(doubles = {0.0, 100.0, 500.0, 1000.0})
    @DisplayName("always returns 0€ (international orders are tax-exempt)")
    void calculateTax_alwaysReturnsZero(double discountedPrice) {
        Order order = new Order(discountedPrice, 0.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, discountedPrice)).isEqualTo(0.0);
    }
}

