package com.klosebros.kata.shipping;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ChristmasShipping}.
 *
 * <p>ChristmasShipping is a surcharge-only strategy: it returns +3€ in December
 * and 0€ in every other month. It is designed to be composed with a base
 * {@link ShippingStrategy} via {@code CompositeShippingStrategy}.</p>
 */
@DisplayName("ChristmasShipping")
class ChristmasShippingTest {

    private final ChristmasShipping strategy = new ChristmasShipping();

    // ── reference instants ────────────────────────────────────────────────────
    private static final LocalDateTime DECEMBER_MID   = LocalDateTime.of(2026, 12, 15, 10, 0);
    private static final LocalDateTime DECEMBER_FIRST = LocalDateTime.of(2026, 12,  1, 10, 0);
    private static final LocalDateTime DECEMBER_LAST  = LocalDateTime.of(2026, 12, 31, 10, 0);
    private static final LocalDateTime NOVEMBER_LAST  = LocalDateTime.of(2026, 11, 30, 10, 0);
    private static final LocalDateTime JANUARY_FIRST  = LocalDateTime.of(2027,  1,  1, 10, 0);
    private static final LocalDateTime MARCH          = LocalDateTime.of(2026,  3, 27, 10, 0);

    private Order orderOn(LocalDateTime dateTime, ShippingType shippingType) {
        return new Order(100.0, 2.0, CustomerType.REGULAR, Destination.LOCAL, shippingType, dateTime);
    }

    // ── December: surcharge applies ───────────────────────────────────────────

    @Test
    @DisplayName("returns 3€ surcharge for an order placed in December")
    void calculateShippingCost_inDecember_returnsThreeEuro() {
        assertThat(strategy.calculateShippingCost(orderOn(DECEMBER_MID, ShippingType.STANDARD))).isEqualTo(3.0);
    }

    @Test
    @DisplayName("returns 3€ on December 1st (first day of month)")
    void calculateShippingCost_onDecemberFirst_returnsThreeEuro() {
        assertThat(strategy.calculateShippingCost(orderOn(DECEMBER_FIRST, ShippingType.STANDARD))).isEqualTo(3.0);
    }

    @Test
    @DisplayName("returns 3€ on December 31st (last day of month)")
    void calculateShippingCost_onDecemberLast_returnsThreeEuro() {
        assertThat(strategy.calculateShippingCost(orderOn(DECEMBER_LAST, ShippingType.STANDARD))).isEqualTo(3.0);
    }

    // ── outside December: no surcharge ───────────────────────────────────────

    @Test
    @DisplayName("returns 0€ on November 30th (day before December)")
    void calculateShippingCost_onNovemberLast_returnsZero() {
        assertThat(strategy.calculateShippingCost(orderOn(NOVEMBER_LAST, ShippingType.STANDARD))).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ on January 1st (day after December)")
    void calculateShippingCost_onJanuaryFirst_returnsZero() {
        assertThat(strategy.calculateShippingCost(orderOn(JANUARY_FIRST, ShippingType.STANDARD))).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ in any regular month")
    void calculateShippingCost_inRegularMonth_returnsZero() {
        assertThat(strategy.calculateShippingCost(orderOn(MARCH, ShippingType.STANDARD))).isEqualTo(0.0);
    }

    // ── surcharge is independent of shipping type ─────────────────────────────

    @ParameterizedTest(name = "shippingType={0} → surcharge=3€ in December")
    @EnumSource(ShippingType.class)
    @DisplayName("surcharge applies regardless of the chosen base shipping type")
    void calculateShippingCost_inDecember_appliesForAnyShippingType(ShippingType shippingType) {
        assertThat(strategy.calculateShippingCost(orderOn(DECEMBER_MID, shippingType))).isEqualTo(3.0);
    }

    // ── surcharge is independent of weight and base price ─────────────────────

    @Test
    @DisplayName("surcharge is a flat fee — independent of order weight")
    void calculateShippingCost_inDecember_doesNotScaleWithWeight() {
        Order heavyOrder = new Order(100.0, 50.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, DECEMBER_MID);

        assertThat(strategy.calculateShippingCost(heavyOrder)).isEqualTo(3.0);
    }
}

