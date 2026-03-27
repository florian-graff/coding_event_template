package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DroneShippingStrategyTest {

    private static final LocalDate ANY_DATE = LocalDate.of(2026, 1, 15);

    private final DroneShippingStrategy strategy = new DroneShippingStrategy();

    @Test
    void should_return20Plus5TimesWeightInEuros_when_weightIsAtMost2Kg() {
        Order order = new Order(100.0, 1.5, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.DRONE, ANY_DATE);

        assertThat(strategy.calculateShipping(order)).isEqualTo(27.5);
    }

    @Test
    void should_throwIllegalArgumentException_when_weightExceeds2Kg() {
        Order order = new Order(100.0, 2.1, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.DRONE, ANY_DATE);

        assertThatThrownBy(() -> strategy.calculateShipping(order))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

