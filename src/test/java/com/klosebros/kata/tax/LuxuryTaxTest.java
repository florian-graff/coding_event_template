package com.klosebros.kata.tax;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LuxuryTax")
class LuxuryTaxTest {

    private final LuxuryTax strategy = new LuxuryTax();
    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

    @Test
    @DisplayName("returns 5% of discounted price when base price exceeds 500€")
    void calculateTax_aboveThreshold_returnsFivePercent() {
        Order order = new Order(501.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 501.0)).isEqualTo(25.05);
    }

    @Test
    @DisplayName("returns 0€ when base price is exactly 500€ (threshold is exclusive)")
    void calculateTax_atThreshold_returnsZero() {
        Order order = new Order(500.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 500.0)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("returns 0€ when base price is below 500€")
    void calculateTax_belowThreshold_returnsZero() {
        Order order = new Order(100.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 100.0)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("applies 5% to the discounted price, not the base price")
    void calculateTax_appliedOnDiscountedPrice() {
        // basePrice=600 > 500 → luxury applies; discountedPrice=510 (after bulk discount)
        Order order = new Order(600.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 510.0)).isEqualTo(25.5);
    }
}

