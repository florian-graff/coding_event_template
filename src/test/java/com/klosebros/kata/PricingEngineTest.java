package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class PricingEngineTest {
    @Test
    void calculateEmptyOrder() {
        var pricingEngine = new PricingEngine();
        var order = new Order(0.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, null);
        var price = pricingEngine.calculatePrice(order);
        assertThat(price).isEqualTo(0.0);
    }

    @Test
    void calculateRegularInternationalOrderByPickup() {
        var pricingEngine = new PricingEngine();
        var order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, new Date());
        var price = pricingEngine.calculatePrice(order);
        assertThat(price).isEqualTo(100.0);
    }

    @Test
    void calculateRegularLocalOrderByPickup() {
        var pricingEngine = new PricingEngine();
        var order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, new Date());
        var price = pricingEngine.calculatePrice(order);
        assertThat(price).isEqualTo(100.0 * 1.19);
    }

    @Test
    void calculateRegularEuOrderByPickup() {
        var pricingEngine = new PricingEngine();
        var order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.EU, ShippingType.PICKUP, new Date());
        var price = pricingEngine.calculatePrice(order);
        assertThat(price).isEqualTo(100.0 * 1.10);
    }

    @Test
    void calculateRegularInternationalOderByStandard() {
        var pricingEngine = new PricingEngine();
        var order = new Order(0.0, 0.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.STANDARD, new Date());
        var price = pricingEngine.calculatePrice(order);
        assertThat(price).isEqualTo(5);
    }
}
