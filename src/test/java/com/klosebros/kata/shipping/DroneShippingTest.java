package com.klosebros.kata.shipping;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("DroneShipping")
class DroneShippingTest {

    private final DroneShipping strategy = new DroneShipping();
    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

    @ParameterizedTest(name = "{0} kg → {1}€")
    @CsvSource({"0.0, 20.0", "1.0, 25.0", "2.0, 30.0"})
    @DisplayName("costs 20€ base fee plus 5€ per kilogram (up to 2 kg)")
    void calculateShippingCost_twentyPlusFivePerKg(double weightKg, double expectedCost) {
        Order order = new Order(100.0, weightKg, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, ANY_DATE);

        assertThat(strategy.calculateShippingCost(order)).isEqualTo(expectedCost);
    }

    @Test
    @DisplayName("accepts orders at exactly the 2 kg weight limit")
    void calculateShippingCost_atMaxWeight_succeeds() {
        Order order = new Order(100.0, DroneShipping.MAX_WEIGHT_KG, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, ANY_DATE);

        assertThat(strategy.calculateShippingCost(order)).isEqualTo(30.0);
    }

    @Test
    @DisplayName("throws IllegalArgumentException for orders over 2 kg")
    void calculateShippingCost_overMaxWeight_throwsException() {
        Order order = new Order(100.0, 2.1, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, ANY_DATE);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> strategy.calculateShippingCost(order))
                .withMessageContaining("2.0");
    }
}

