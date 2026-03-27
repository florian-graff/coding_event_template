package com.klosebros.kata.discount;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HappyHourDiscount")
class HappyHourDiscountTest {

    private final HappyHourDiscount strategy = new HappyHourDiscount();

    // ── helpers ──────────────────────────────────────────────────────────────
    private static final LocalDateTime HAPPY_HOUR_START   = LocalDateTime.of(2026, 3, 27, 18,  0);
    private static final LocalDateTime HAPPY_HOUR_MID     = LocalDateTime.of(2026, 3, 27, 19, 30);
    private static final LocalDateTime HAPPY_HOUR_LAST    = LocalDateTime.of(2026, 3, 27, 19, 59);
    private static final LocalDateTime HAPPY_HOUR_END     = LocalDateTime.of(2026, 3, 27, 20,  0); // exclusive
    private static final LocalDateTime BEFORE_HAPPY_HOUR  = LocalDateTime.of(2026, 3, 27, 17, 59);
    private static final LocalDateTime MORNING            = LocalDateTime.of(2026, 3, 27, 10,  0);

    private Order orderAt(LocalDateTime dateTime) {
        return new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, dateTime);
    }

    // ── happy-hour window ────────────────────────────────────────────────────

    @Test
    @DisplayName("returns 8% discount at 18:00 (start of happy hour, inclusive)")
    void calculateDiscount_atEighteen_returnsEightPercent() {
        assertThat(strategy.calculateDiscount(orderAt(HAPPY_HOUR_START))).isEqualTo(8.0);
    }

    @Test
    @DisplayName("returns 8% discount at 19:30 (middle of happy hour)")
    void calculateDiscount_duringHappyHour_returnsEightPercent() {
        assertThat(strategy.calculateDiscount(orderAt(HAPPY_HOUR_MID))).isEqualTo(8.0);
    }

    @Test
    @DisplayName("returns 8% discount at 19:59 (last minute of happy hour)")
    void calculateDiscount_atLastMinuteOfHappyHour_returnsEightPercent() {
        assertThat(strategy.calculateDiscount(orderAt(HAPPY_HOUR_LAST))).isEqualTo(8.0);
    }

    @Test
    @DisplayName("scales correctly with a different base price during happy hour")
    void calculateDiscount_duringHappyHour_scalesWithBasePrice() {
        Order order = new Order(200.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, HAPPY_HOUR_MID);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(16.0);
    }

    // ── outside happy-hour window ─────────────────────────────────────────────

    @Test
    @DisplayName("returns 0€ at 20:00 (end of happy hour, exclusive)")
    void calculateDiscount_atTwenty_returnsZero() {
        assertThat(strategy.calculateDiscount(orderAt(HAPPY_HOUR_END))).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ at 17:59 (one minute before happy hour)")
    void calculateDiscount_oneMinuteBeforeHappyHour_returnsZero() {
        assertThat(strategy.calculateDiscount(orderAt(BEFORE_HAPPY_HOUR))).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ during regular business hours")
    void calculateDiscount_inMorning_returnsZero() {
        assertThat(strategy.calculateDiscount(orderAt(MORNING))).isEqualTo(0.0);
    }

    // ── boundary: exact hour values ───────────────────────────────────────────

    @ParameterizedTest(name = "hour={0} → discount={1}€ (basePrice=100)")
    @CsvSource({
            "17, 0.0",
            "18, 8.0",
            "19, 8.0",
            "20, 0.0",
            "21, 0.0"
    })
    @DisplayName("applies discount only between hour 18 (inclusive) and 20 (exclusive)")
    void calculateDiscount_byHour(int hour, double expectedDiscount) {
        LocalDateTime dateTime = LocalDateTime.of(2026, 3, 27, hour, 0);
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, dateTime);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(expectedDiscount);
    }
}

