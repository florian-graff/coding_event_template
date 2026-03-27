package com.klosebros.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.within;

@DisplayName("PricingEngine – Phase 1 (naive)")
class PricingEngineTest {

    // ─── fixed reference dates ───────────────────────────────────────────────
    private static final LocalDate REGULAR_DATE    = LocalDate.of(2026, 3, 27);
    private static final LocalDate NOVEMBER_DATE   = LocalDate.of(2026, 11, 15);

    private PricingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PricingEngine();
    }

    // =========================================================================
    // README example scenario
    // =========================================================================

    @Test
    @DisplayName("README example: VIP, 300€, 1.5 kg, EU, Drone → 324.50€")
    void readmeExample_vipCustomer_droneShipping_euDestination_returnsCorrectFinalPrice() {
        // discount  = 10% × 300      = 30.00
        // shipping  = 20 + 5×1.5     = 27.50
        // tax       = 10% × (300−30) = 27.00
        // final     = 300−30+27.5+27 = 324.50
        Order order = new Order(300.0, 1.5, CustomerType.VIP, Destination.EU, ShippingType.DRONE, REGULAR_DATE);

        double finalPrice = engine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(324.50);
    }

    // =========================================================================
    // Discount rules
    // =========================================================================

    @Nested
    @DisplayName("Discount rules")
    class DiscountRules {

        @Test
        @DisplayName("REGULAR customer receives no discount")
        void regularCustomer_noDiscount() {
            // shipping = 5 + 1×1 = 6 | tax = 19% × 100 = 19
            Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(125.0);
        }

        @Test
        @DisplayName("PREMIUM customer receives 5% discount")
        void premiumCustomer_fivePercentDiscount() {
            // discount = 5% × 100 = 5 | shipping = 6 | tax = 19% × 95 = 18.05
            Order order = new Order(100.0, 1.0, CustomerType.PREMIUM, Destination.LOCAL, ShippingType.STANDARD, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(119.05);
        }

        @Test
        @DisplayName("VIP customer receives 10% discount")
        void vipCustomer_tenPercentDiscount() {
            // discount = 10% × 100 = 10 | shipping = 6 | tax = 19% × 90 = 17.1
            Order order = new Order(100.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.STANDARD, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(113.1);
        }

        @Test
        @DisplayName("BlackFriday (November) gives 20% discount regardless of customer type")
        void novemberOrder_blackFridayDiscountApplies() {
            // discount = 20% × 100 = 20 | shipping = 6 | tax = 19% × 80 = 15.2
            Order order = new Order(100.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.STANDARD, NOVEMBER_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(101.2);
        }

        @Test
        @DisplayName("BulkDiscount (basePrice > 200€) gives 15% discount for REGULAR customers")
        void bulkOrder_fifteenPercentDiscount() {
            // discount = 15% × 201 = 30.15 | shipping = 5 + 1×1 = 6 | tax = 19% × 170.85 = 32.4615
            // final = 201 - 30.15 + 6 + 32.4615 = 209.3115
            Order order = new Order(201.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(209.3115);
        }

        @Test
        @DisplayName("BulkDiscount threshold: basePrice exactly 200€ does NOT trigger bulk discount")
        void orderAtExactly200_noBulkDiscount() {
            // basePrice = 200 → not > 200, so REGULAR gets 0% discount
            // shipping = 6 | tax = 19% × 200 = 38
            Order order = new Order(200.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(244.0);
        }
    }

    // =========================================================================
    // Shipping rules
    // =========================================================================

    @Nested
    @DisplayName("Shipping rules")
    class ShippingRules {

        @ParameterizedTest(name = "Standard shipping: {0} kg → shipping cost {1}€")
        @CsvSource({"0.0, 5.0", "1.0, 6.0", "2.5, 7.5"})
        @DisplayName("Standard shipping = 5€ + 1€/kg")
        void standardShipping_fivePlusOnePerKg(double weightKg, double expectedShipping) {
            // basePrice = 100 | REGULAR → no discount | LOCAL tax = 19% × 100 = 19
            Order order = new Order(100.0, weightKg, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(100.0 + expectedShipping + 19.0);
        }

        @ParameterizedTest(name = "Express shipping: {0} kg → shipping cost {1}€")
        @CsvSource({"0.0, 10.0", "1.0, 12.0", "2.0, 14.0"})
        @DisplayName("Express shipping = 10€ + 2€/kg")
        void expressShipping_tenPlusTwoPerKg(double weightKg, double expectedShipping) {
            // basePrice = 100 | REGULAR → no discount | LOCAL tax = 19% × 100 = 19
            Order order = new Order(100.0, weightKg, CustomerType.REGULAR, Destination.LOCAL, ShippingType.EXPRESS, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(100.0 + expectedShipping + 19.0);
        }

        @Test
        @DisplayName("Pickup shipping is free (0€)")
        void pickupShipping_free() {
            // basePrice = 100 | REGULAR → no discount | LOCAL tax = 19% × 100 = 19
            Order order = new Order(100.0, 5.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(119.0);
        }

        @ParameterizedTest(name = "Drone shipping: {0} kg → shipping cost {1}€")
        @CsvSource({"0.0, 20.0", "1.0, 25.0", "2.0, 30.0"})
        @DisplayName("Drone shipping = 20€ + 5€/kg (up to 2 kg)")
        void droneShipping_twentyPlusFivePerKg(double weightKg, double expectedShipping) {
            // basePrice = 100 | REGULAR → no discount | LOCAL tax = 19% × 100 = 19
            Order order = new Order(100.0, weightKg, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(100.0 + expectedShipping + 19.0);
        }

        @Test
        @DisplayName("Drone shipping over 2 kg throws IllegalArgumentException")
        void droneShipping_overMaxWeight_throwsException() {
            Order order = new Order(100.0, 2.1, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, REGULAR_DATE);

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> engine.calculatePrice(order))
                    .withMessageContaining("2");
        }

        @Test
        @DisplayName("Drone shipping at exactly 2 kg is allowed")
        void droneShipping_atMaxWeight_doesNotThrow() {
            Order order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, REGULAR_DATE);

            assertThat(engine.calculatePrice(order)).isEqualTo(149.0);
        }
    }

    // =========================================================================
    // Tax rules
    // =========================================================================

    @Nested
    @DisplayName("Tax rules")
    class TaxRules {

        @Test
        @DisplayName("LOCAL destination: 19% tax on discounted price")
        void localDestination_nineteenPercentTax() {
            // basePrice = 100 | REGULAR → no discount | shipping = pickup (0) | tax = 19% × 100 = 19
            Order order = new Order(100.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(119.0);
        }

        @Test
        @DisplayName("EU destination: 10% tax on discounted price")
        void euDestination_tenPercentTax() {
            // basePrice = 100 | REGULAR → no discount | shipping = pickup (0) | tax = 10% × 100 = 10
            Order order = new Order(100.0, 0.0, CustomerType.REGULAR, Destination.EU, ShippingType.PICKUP, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(110.0);
        }

        @Test
        @DisplayName("INTERNATIONAL destination: 0% tax")
        void internationalDestination_noTax() {
            // basePrice = 100 | REGULAR → no discount | shipping = pickup (0) | tax = 0
            Order order = new Order(100.0, 0.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(100.0);
        }

        @Test
        @DisplayName("LuxuryTax: basePrice > 500€ adds extra 5% tax on discounted price")
        void luxuryOrder_additionalFivePercentTax() {
            // basePrice = 501 | REGULAR + bulk (501 > 200): discount = 15% × 501 = 75.15
            // discountedPrice = 501 - 75.15 = 425.85
            // tax = (19% + 5%) × 425.85 = 24% × 425.85 = 102.204
            // final = 501 - 75.15 + 0 + 102.204 = 528.054
            Order order = new Order(501.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isCloseTo(528.054, within(0.001));
        }

        @Test
        @DisplayName("LuxuryTax threshold: basePrice exactly 500€ does NOT trigger luxury tax")
        void orderAtExactly500_noLuxuryTax() {
            // basePrice = 500 | bulk discount 15% = 75 | discountedPrice = 425
            // tax = 19% × 425 = 80.75 (no luxury) | shipping = 0
            // final = 500 - 75 + 0 + 80.75 = 505.75
            Order order = new Order(500.0, 0.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, REGULAR_DATE);

            double finalPrice = engine.calculatePrice(order);

            assertThat(finalPrice).isEqualTo(505.75);
        }
    }
}

