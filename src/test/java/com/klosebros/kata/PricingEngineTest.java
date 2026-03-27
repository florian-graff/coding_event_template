package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.klosebros.kata.PricingEngine.CustomerType.*;
import static com.klosebros.kata.PricingEngine.Destination.*;
import static com.klosebros.kata.PricingEngine.ShippingType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PricingEngineTest {

    private static final LocalDate REGULAR_DATE    = LocalDate.of(2026, 1, 15);
    private static final LocalDate BLACK_FRIDAY     = LocalDate.of(2026, 11, 15);

    private final PricingEngine pricingEngine = new PricingEngine();

    @Test
    void should_calculateExampleScenarioPrice_when_vipCustomerOrdersDroneToEu() {
        PricingEngine.Order order = new PricingEngine.Order(300.0, 1.5, VIP, EU, DRONE, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(324.5);
    }

    @Test
    void should_applyNoDiscount_when_customerTypeIsRegular() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 1.0, REGULAR, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(100.0);
    }

    @Test
    void should_applyPremiumDiscount_when_customerTypeIsPremium() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 1.0, PREMIUM, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(95.0);
    }

    @Test
    void should_applyVipBlackFridayAndBulkDiscountAdditively_when_allDiscountConditionsMatch() {
        PricingEngine.Order order = new PricingEngine.Order(300.0, 1.0, VIP, INTERNATIONAL, PICKUP, BLACK_FRIDAY);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(165.0);
    }

    @Test
    void should_notApplyBulkDiscount_when_basePriceIsExactly200() {
        PricingEngine.Order order = new PricingEngine.Order(200.0, 1.0, REGULAR, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(200.0);
    }

    @Test
    void should_applyBulkDiscount_when_basePriceIsAbove200() {
        PricingEngine.Order order = new PricingEngine.Order(201.0, 1.0, REGULAR, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(170.85);
    }

    @Test
    void should_applyStandardShippingCostFormula_when_shippingTypeIsStandard() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 2.0, REGULAR, INTERNATIONAL, STANDARD, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(107.0);
    }

    @Test
    void should_applyExpressShippingCostFormula_when_shippingTypeIsExpress() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 2.0, REGULAR, INTERNATIONAL, EXPRESS, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(114.0);
    }

    @Test
    void should_applyNoShippingCost_when_shippingTypeIsPickup() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 2.0, REGULAR, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(100.0);
    }

    @Test
    void should_throwException_when_droneShippingWeightIsAbove2Kg() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 2.1, REGULAR, INTERNATIONAL, DRONE, REGULAR_DATE);

        assertThatThrownBy(() -> pricingEngine.calculatePrice(order))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_applyLocalTaxRate_when_destinationIsLocal() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 1.0, REGULAR, LOCAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(119.0);
    }

    @Test
    void should_applyEuTaxRate_when_destinationIsEu() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 1.0, REGULAR, EU, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(110.0);
    }

    @Test
    void should_applyNoBaseTax_when_destinationIsInternational() {
        PricingEngine.Order order = new PricingEngine.Order(100.0, 1.0, REGULAR, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(100.0);
    }

    @Test
    void should_applyLuxuryTax_when_basePriceIsAbove500() {
        PricingEngine.Order order = new PricingEngine.Order(600.0, 1.0, REGULAR, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(540.0);
    }

    @Test
    void should_notApplyLuxuryTax_when_basePriceIsExactly500() {
        PricingEngine.Order order = new PricingEngine.Order(500.0, 1.0, REGULAR, INTERNATIONAL, PICKUP, REGULAR_DATE);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(425.0);
    }
}
