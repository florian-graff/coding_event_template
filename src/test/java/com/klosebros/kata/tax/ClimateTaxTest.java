package com.klosebros.kata.tax;

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
 * Unit tests for {@link ClimateTax}.
 *
 * <p>ClimateTax is a flat 2€ surcharge applied only when the order uses
 * EXPRESS shipping. It returns 0€ for all other shipping types.
 * It is composed with a destination-based tax via {@link CompositeTaxStrategy}.</p>
 */
@DisplayName("ClimateTax")
class ClimateTaxTest {

    private final ClimateTax strategy = new ClimateTax();

    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2026, 3, 27, 10, 0);
    private static final double ANY_DISCOUNTED_PRICE = 100.0;

    private Order orderWith(ShippingType shippingType) {
        return new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, shippingType, ANY_DATE);
    }

    // ── EXPRESS: surcharge applies ────────────────────────────────────────────

    @Test
    @DisplayName("returns 2€ flat surcharge for EXPRESS shipping")
    void calculateTax_expressShipping_returnsTwoEuro() {
        Order order = orderWith(ShippingType.EXPRESS);

        assertThat(strategy.calculateTax(order, ANY_DISCOUNTED_PRICE)).isEqualTo(2.0);
    }

    @Test
    @DisplayName("surcharge is flat — does not scale with discounted price")
    void calculateTax_expressShipping_doesNotScaleWithDiscountedPrice() {
        Order order = orderWith(ShippingType.EXPRESS);

        assertThat(strategy.calculateTax(order, 500.0)).isEqualTo(2.0);
    }

    @Test
    @DisplayName("surcharge is flat — does not scale with base price")
    void calculateTax_expressShipping_doesNotScaleWithBasePrice() {
        Order expensive = new Order(1000.0, 1.0, CustomerType.VIP, Destination.EU, ShippingType.EXPRESS, ANY_DATE);

        assertThat(strategy.calculateTax(expensive, 900.0)).isEqualTo(2.0);
    }

    // ── non-EXPRESS: no surcharge ─────────────────────────────────────────────

    @Test
    @DisplayName("returns 0€ for STANDARD shipping")
    void calculateTax_standardShipping_returnsZero() {
        assertThat(strategy.calculateTax(orderWith(ShippingType.STANDARD), ANY_DISCOUNTED_PRICE)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ for DRONE shipping")
    void calculateTax_droneShipping_returnsZero() {
        assertThat(strategy.calculateTax(orderWith(ShippingType.DRONE), ANY_DISCOUNTED_PRICE)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ for PICKUP shipping")
    void calculateTax_pickupShipping_returnsZero() {
        assertThat(strategy.calculateTax(orderWith(ShippingType.PICKUP), ANY_DISCOUNTED_PRICE)).isEqualTo(0.0);
    }

    @ParameterizedTest(name = "shippingType={0} → 0€ climate surcharge")
    @EnumSource(value = ShippingType.class, names = {"STANDARD", "DRONE", "PICKUP"})
    @DisplayName("returns 0€ for all non-EXPRESS shipping types")
    void calculateTax_nonExpressShipping_returnsZero(ShippingType shippingType) {
        assertThat(strategy.calculateTax(orderWith(shippingType), ANY_DISCOUNTED_PRICE)).isEqualTo(0.0);
    }

    // ── composition: ClimateTax + destination tax via CompositeTaxStrategy ────

    @Test
    @DisplayName("adds 2€ on top of EU tax when composed via CompositeTaxStrategy for EXPRESS orders")
    void climateTax_composedWithEuTax_addsCorrectly() {
        // EU tax = 10% × 100 = 10€ ; ClimateTax = 2€ → total = 12€
        CompositeTaxStrategy composite = new CompositeTaxStrategy(
                java.util.List.of(new EuTax(), new ClimateTax()));
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.EU, ShippingType.EXPRESS, ANY_DATE);

        assertThat(composite.calculateTax(order, 100.0)).isEqualTo(12.0);
    }

    @Test
    @DisplayName("CompositeTaxStrategy with ClimateTax returns only base tax for non-EXPRESS orders")
    void climateTax_composedWithEuTax_noSurchargeForNonExpress() {
        // EU tax = 10% × 100 = 10€ ; ClimateTax = 0€ → total = 10€
        CompositeTaxStrategy composite = new CompositeTaxStrategy(
                java.util.List.of(new EuTax(), new ClimateTax()));
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.EU, ShippingType.STANDARD, ANY_DATE);

        assertThat(composite.calculateTax(order, 100.0)).isEqualTo(10.0);
    }
}

