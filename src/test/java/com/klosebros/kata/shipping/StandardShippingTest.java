package com.klosebros.kata.shipping;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StandardShipping")
class StandardShippingTest {

    private final StandardShipping strategy = new StandardShipping();
    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

    @ParameterizedTest(name = "{0} kg → {1}€")
    @CsvSource({"0.0, 5.0", "1.0, 6.0", "2.5, 7.5", "10.0, 15.0"})
    @DisplayName("costs 5€ base fee plus 1€ per kilogram")
    void calculateShippingCost_fivePlusOnePerKg(double weightKg, double expectedCost) {
        Order order = new Order(100.0, weightKg, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, ANY_DATE);

        assertThat(strategy.calculateShippingCost(order)).isEqualTo(expectedCost);
    }
}

