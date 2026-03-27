package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PricingEngineScenarioTest {

    @Test
    void should_calculate324Point50_when_vipCustomerOrdersDroneToEuWith300EuroBasePrice() {
        // VIP-Kunde | 300€ | 1.5kg | EU | Drone
        // Rabatt    = 10% von 300                 = 30€
        // Versand   = 20 + (5 × 1.5)             = 27.5€
        // Steuer    = 10% von (300 - 30)          = 27€
        // Endpreis  = 300 - 30 + 27.5 + 27        = 324.50€
        PricingEngine pricingEngine = new PricingEngine(
                List.of(new VipDiscountStrategy()),
                new DroneShippingStrategy(),
                List.of(new EuTaxStrategy())
        );
        Order order = new Order(300.0, 1.5, CustomerType.VIP, Destination.EU, ShippingType.DRONE, LocalDate.of(2026, 1, 15));

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(324.5);
    }
}

