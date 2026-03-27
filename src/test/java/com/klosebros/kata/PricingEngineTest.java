package com.klosebros.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class PricingEngineTest {

    private PricingEngine pricingEngine;

    private static final LocalDate IN_NOVEMBER = LocalDate.of(2024, Month.NOVEMBER, 15);
    private static final LocalDate NOT_IN_NOVEMBER = LocalDate.of(2024, Month.JUNE, 15);

    @BeforeEach
    void setUp() {
        pricingEngine = new PricingEngine();
    }

    @Nested
    class DiscountCalculation {

        @Test
        void shouldApplyNoDiscountWhenCustomerIsRegular() {
            // 100 - 0% + 0(pickup) + 0(international) = 100
            var order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(100.0, within(0.001));
        }

        @Test
        void shouldApplyFivePercentDiscountWhenCustomerIsPremium() {
            // 100 - 5% + 0 + 0 = 95
            var order = new Order(100.0, 1.0, CustomerType.PREMIUM, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(95.0, within(0.001));
        }

        @Test
        void shouldApplyTenPercentDiscountWhenCustomerIsVip() {
            // 100 - 10% + 0 + 0 = 90
            var order = new Order(100.0, 1.0, CustomerType.VIP, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(90.0, within(0.001));
        }

        @Test
        void shouldApplyTwentyPercentDiscountWhenOrderIsInNovember() {
            // 100 - 20% + 0 + 0 = 80
            var order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(80.0, within(0.001));
        }

        @Test
        void shouldApplyFifteenPercentDiscountWhenCustomerIsRegularAndBasePriceExceedsTwoHundred() {
            // 300 - 15% + 0 + 0 = 255
            var order = new Order(300.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(255.0, within(0.001));
        }

        @Test
        void shouldApplyBlackFridayDiscountWhenNovemberEvenForVipCustomer() {
            // BlackFriday (20%) hat höhere Priorität als VIP (10%)
            // 100 - 20% + 0 + 0 = 80
            var order = new Order(100.0, 1.0, CustomerType.VIP, Destination.INTERNATIONAL, ShippingType.PICKUP, IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(80.0, within(0.001));
        }

        @Test
        void shouldApplyCustomerTypeDiscountInsteadOfBulkDiscountForNonRegularCustomer() {
            // PREMIUM (5%) hat höhere Priorität als BulkDiscount (15%) – CustomerType schlägt Bulk
            // 300 - 5% + 0 + 0 = 285
            var order = new Order(300.0, 1.0, CustomerType.PREMIUM, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(285.0, within(0.001));
        }
    }

    @Nested
    class ShippingCostCalculation {

        @Test
        void shouldCalculateStandardShippingAsFlatRatePlusWeightCost() {
            // 100 - 0% + (5 + 1*2) + 0 = 107
            var order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.STANDARD, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(107.0, within(0.001));
        }

        @Test
        void shouldCalculateExpressShippingAsFlatRatePlusDoubleWeightCost() {
            // 100 - 0% + (10 + 2*2) + 0 = 114
            var order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.EXPRESS, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(114.0, within(0.001));
        }

        @Test
        void shouldReturnZeroShippingCostWhenPickup() {
            // 100 - 0% + 0 + 0 = 100
            var order = new Order(100.0, 5.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(100.0, within(0.001));
        }

        @Test
        void shouldCalculateDroneShippingWhenWeightIsWithinLimit() {
            // 100 - 0% + (20 + 5*1.5) + 0 = 127.5
            var order = new Order(100.0, 1.5, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.DRONE, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(127.5, within(0.001));
        }

        @Test
        void shouldThrowExceptionWhenDroneWeightExceedsTwoKg() {
            var order = new Order(100.0, 2.1, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.DRONE, NOT_IN_NOVEMBER);

            assertThatThrownBy(() -> pricingEngine.calculatePrice(order))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("2");
        }
    }

    @Nested
    class TaxCalculation {

        @Test
        void shouldApplyNineteenPercentTaxOnNetPriceWhenDestinationIsLocal() {
            // 100 - 0% + 0 + 19% von 100 = 119
            var order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(119.0, within(0.001));
        }

        @Test
        void shouldApplyTenPercentTaxOnNetPriceWhenDestinationIsEu() {
            // 100 - 0% + 0 + 10% von 100 = 110
            var order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.EU, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(110.0, within(0.001));
        }

        @Test
        void shouldApplyZeroTaxWhenDestinationIsInternational() {
            // 100 - 0% + 0 + 0% = 100
            var order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(100.0, within(0.001));
        }

        @Test
        void shouldApplyLuxuryTaxOnBasePriceWhenBasePriceExceedsFiveHundred() {
            // REGULAR, 600€, PICKUP, INTERNATIONAL, kein November
            // BulkDiscount: 15% von 600 = 90  → netPrice = 510
            // Reguläre Steuer: 0% (INTERNATIONAL)
            // LuxuryTax: 5% von basePrice (600) = 30
            // final = 600 - 90 + 0 + 30 = 540
            var order = new Order(600.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(540.0, within(0.001));
        }

        @Test
        void shouldApplyBothStandardAndLuxuryTaxWhenDestinationIsLocalAndBasePriceIsHigh() {
            // REGULAR, 600€, PICKUP, LOCAL, kein November
            // BulkDiscount: 15% von 600 = 90  → netPrice = 510
            // Reguläre Steuer: 19% von 510 = 96.90
            // LuxuryTax: 5% von 600 = 30
            // final = 600 - 90 + 0 + 96.90 + 30 = 636.90
            var order = new Order(600.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(636.90, within(0.001));
        }
    }

    @Nested
    class CombinedScenarios {

        @Test
        void shouldCalculateFinalPriceForVipCustomerWithDroneShippingToEu() {
            // Beispiel aus dem README:
            // VIP, 300€, 1.5kg, EU, DRONE, kein November
            // Rabatt:   VIP 10% von 300 = 30        → netPrice = 270
            // Versand:  20 + 5*1.5 = 27.5
            // Steuer:   10% von 270 = 27
            // Final:    300 - 30 + 27.5 + 27 = 324.50
            var order = new Order(300.0, 1.5, CustomerType.VIP, Destination.EU, ShippingType.DRONE, NOT_IN_NOVEMBER);

            assertThat(pricingEngine.calculatePrice(order)).isCloseTo(324.50, within(0.001));
        }
    }
}