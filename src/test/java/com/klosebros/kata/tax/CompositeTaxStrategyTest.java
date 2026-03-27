package com.klosebros.kata.tax;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DisplayName("CompositeTaxStrategy")
class CompositeTaxStrategyTest {

    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

    @Test
    @DisplayName("returns the sum of all contained tax strategies")
    void calculateTax_returnsSumOfAllStrategies() {
        // LocalTax(19%) + EuTax(10%) on discountedPrice=100 → 19 + 10 = 29
        CompositeTaxStrategy composite = new CompositeTaxStrategy(List.of(new LocalTax(), new EuTax()));
        Order order = new Order(100.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(composite.calculateTax(order, 100.0)).isEqualTo(29.0);
    }

    @Test
    @DisplayName("combines LocalTax and LuxuryTax for high-value local orders")
    void calculateTax_localPlusLuxury_forHighValueOrder() {
        // basePrice=501 > 500 → LuxuryTax applies
        // discountedPrice=425.85 (after 15% bulk discount)
        // LocalTax = 19% × 425.85 = 80.9115
        // LuxuryTax = 5%  × 425.85 = 21.2925
        // Total = 102.204
        CompositeTaxStrategy composite = new CompositeTaxStrategy(List.of(new LocalTax(), new LuxuryTax()));
        Order order = new Order(501.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(composite.calculateTax(order, 425.85)).isCloseTo(102.204, within(0.001));
    }

    @Test
    @DisplayName("works correctly with a single strategy")
    void calculateTax_singleStrategy_delegatesDirectly() {
        CompositeTaxStrategy composite = new CompositeTaxStrategy(List.of(new EuTax()));
        Order order = new Order(300.0, 0.0, CustomerType.VIP, Destination.EU, ShippingType.PICKUP, ANY_DATE);

        assertThat(composite.calculateTax(order, 270.0)).isEqualTo(27.0);
    }

    @Test
    @DisplayName("LuxuryTax does not apply when base price is at or below 500€")
    void calculateTax_localPlusLuxury_noSurchargeUnderThreshold() {
        // basePrice=500 → LuxuryTax returns 0
        // LocalTax = 19% × 425 = 80.75
        CompositeTaxStrategy composite = new CompositeTaxStrategy(List.of(new LocalTax(), new LuxuryTax()));
        Order order = new Order(500.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);

        assertThat(composite.calculateTax(order, 425.0)).isEqualTo(80.75);
    }
}

