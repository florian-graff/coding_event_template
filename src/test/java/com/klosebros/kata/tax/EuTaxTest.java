package com.klosebros.kata.tax;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EuTax")
class EuTaxTest {

    private final EuTax strategy = new EuTax();
    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2026, 3, 27, 10, 0);

    @Test
    @DisplayName("returns 10% of the discounted price")
    void calculateTax_returnsTenPercent() {
        Order order = new Order(100.0, 0.0, CustomerType.REGULAR, Destination.EU, ShippingType.PICKUP, ANY_DATE);

        assertThat(strategy.calculateTax(order, 100.0)).isEqualTo(10.0);
    }

    @Test
    @DisplayName("applies 10% to the discounted price, not the base price")
    void calculateTax_appliedOnDiscountedPrice() {
        Order order = new Order(300.0, 0.0, CustomerType.VIP, Destination.EU, ShippingType.PICKUP, ANY_DATE);

        // discountedPrice = 270 (after 10% VIP discount applied upstream)
        assertThat(strategy.calculateTax(order, 270.0)).isEqualTo(27.0);
    }
}

