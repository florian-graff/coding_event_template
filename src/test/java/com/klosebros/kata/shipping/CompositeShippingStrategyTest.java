package com.klosebros.kata.shipping;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("CompositeShippingStrategy")
class CompositeShippingStrategyTest {

    private static final LocalDateTime DECEMBER_DATE = LocalDateTime.of(2026, 12, 15, 10, 0);
    private static final LocalDateTime MARCH_DATE    = LocalDateTime.of(2026, 3, 27, 10, 0);

    // ── basic composition ────────────────────────────────────────────────────

    @Test
    @DisplayName("sums all contained shipping strategies")
    void calculateShippingCost_returnsSumOfAllStrategies() {
        // StandardShipping(1 kg) = 6€ + ChristmasShipping(December) = 3€ → 9€
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new StandardShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, DECEMBER_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(9.0);
    }

    @Test
    @DisplayName("works correctly with a single strategy")
    void calculateShippingCost_singleStrategy_delegatesDirectly() {
        CompositeShippingStrategy composite = new CompositeShippingStrategy(List.of(new PickupShipping()));
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, MARCH_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("ChristmasShipping surcharge is inactive outside December")
    void calculateShippingCost_standardPlusChristmas_outsideDecember_noSurcharge() {
        // StandardShipping(1 kg) = 6€ + ChristmasShipping(March) = 0€ → 6€
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new StandardShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, MARCH_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(6.0);
    }

    // ── Express + Christmas ──────────────────────────────────────────────────

    @Test
    @DisplayName("Express + ChristmasShipping in December adds surcharge")
    void calculateShippingCost_expressPlusChristmas_inDecember() {
        // ExpressShipping(2 kg) = 14€ + ChristmasShipping = 3€ → 17€
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new ExpressShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.EXPRESS, DECEMBER_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(17.0);
    }

    // ── Drone + Christmas: weight within limit ───────────────────────────────

    @Test
    @DisplayName("Drone + ChristmasShipping in December with valid weight adds surcharge")
    void calculateShippingCost_dronePlusChristmas_inDecember_withinWeightLimit() {
        // DroneShipping(1.5 kg) = 27.50€ + ChristmasShipping = 3€ → 30.50€
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new DroneShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 1.5, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, DECEMBER_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(30.5);
    }

    @Test
    @DisplayName("Drone + ChristmasShipping at exactly 2 kg still works")
    void calculateShippingCost_dronePlusChristmas_atMaxWeight() {
        // DroneShipping(2 kg) = 30€ + ChristmasShipping = 3€ → 33€
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new DroneShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, DECEMBER_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(33.0);
    }

    // ── Drone + Christmas: weight exceeds limit ──────────────────────────────

    @Test
    @DisplayName("Drone + ChristmasShipping over 2 kg throws IllegalArgumentException")
    void calculateShippingCost_dronePlusChristmas_overMaxWeight_throwsException() {
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new DroneShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 2.1, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, DECEMBER_DATE);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> composite.calculateShippingCost(order))
                .withMessageContaining("2.0");
    }

    // ── Drone + Christmas outside December ──────────────────────────────────

    @Test
    @DisplayName("Drone + ChristmasShipping outside December adds no surcharge")
    void calculateShippingCost_dronePlusChristmas_outsideDecember() {
        // DroneShipping(1 kg) = 25€ + ChristmasShipping(March) = 0€ → 25€
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new DroneShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, MARCH_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(25.0);
    }

    // ── Pickup + Christmas (edge case: free base + surcharge) ────────────────

    @Test
    @DisplayName("Pickup + ChristmasShipping in December: only the 3€ surcharge applies")
    void calculateShippingCost_pickupPlusChristmas_inDecember() {
        // PickupShipping = 0€ + ChristmasShipping = 3€ → 3€
        CompositeShippingStrategy composite = new CompositeShippingStrategy(
                List.of(new PickupShipping(), new ChristmasShipping()));
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, DECEMBER_DATE);

        assertThat(composite.calculateShippingCost(order)).isEqualTo(3.0);
    }
}

