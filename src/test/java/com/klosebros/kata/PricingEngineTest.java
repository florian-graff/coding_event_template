package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PricingEngineTest {
    @Test
    void calculateEmptyOrder() {
        var pricingEngine = new PricingEngine();
        var price = pricingEngine.calculatePrice(new Order());
        assertThat(price).isEqualTo(0.0);
    }
}
