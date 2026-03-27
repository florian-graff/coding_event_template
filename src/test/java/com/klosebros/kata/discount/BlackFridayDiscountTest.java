package com.klosebros.kata.discount;

import com.klosebros.kata.CustomerType;
import com.klosebros.kata.Destination;
import com.klosebros.kata.Order;
import com.klosebros.kata.ShippingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BlackFridayDiscount")
class BlackFridayDiscountTest {

    private final BlackFridayDiscount strategy = new BlackFridayDiscount();

    private static final LocalDateTime NOVEMBER_DATE     = LocalDateTime.of(2026, 11, 15, 10, 0);
    private static final LocalDateTime NON_NOVEMBER_DATE = LocalDateTime.of(2026,  3, 27, 10, 0);

    @Test
    @DisplayName("returns 20% discount for orders placed in November")
    void calculateDiscount_inNovember_returnsTwentyPercent() {
        Order order = new Order(100.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.STANDARD, NOVEMBER_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(20.0);
    }

    @Test
    @DisplayName("returns 0€ for orders placed outside November")
    void calculateDiscount_outsideNovember_returnsZero() {
        Order order = new Order(100.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.STANDARD, NON_NOVEMBER_DATE);

        assertThat(strategy.calculateDiscount(order)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("applies regardless of customer type")
    void calculateDiscount_inNovember_ignoresCustomerType() {
        Order regularOrder = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, NOVEMBER_DATE);
        Order vipOrder = new Order(100.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.STANDARD, NOVEMBER_DATE);

        assertThat(strategy.calculateDiscount(regularOrder)).isEqualTo(strategy.calculateDiscount(vipOrder));
    }
}

