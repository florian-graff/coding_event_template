package com.klosebros.kata.shipping;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ExpressShipping")
class ExpressShippingTest {

    private final ExpressShipping strategy = new ExpressShipping();
    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2026, 3, 27, 10, 0);

    @ParameterizedTest(name = "{0} kg → {1}€")
    @CsvSource({"0.0, 10.0", "1.0, 12.0", "2.0, 14.0", "5.0, 20.0"})
    @DisplayName("costs 10€ base fee plus 2€ per kilogram")
    void calculateShippingCost_tenPlusTwoPerKg(double weightKg, double expectedCost) {
        Order order = new Order(100.0, weightKg, CustomerType.REGULAR, Destination.LOCAL, ShippingType.EXPRESS, ANY_DATE);

        assertThat(strategy.calculateShippingCost(order)).isEqualTo(expectedCost);
    }
}

