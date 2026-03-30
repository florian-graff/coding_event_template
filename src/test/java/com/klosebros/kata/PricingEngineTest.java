package com.klosebros.kata;

import com.klosebros.kata.discount.*;
import com.klosebros.kata.shipping.*;
import com.klosebros.kata.tax.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("PricingEngine – Phase 2 (Strategy Pattern)")
@ExtendWith(MockitoExtension.class)
class PricingEngineTest {

    private static final LocalDateTime ANY_DATE = LocalDateTime.of(2026, 3, 27, 10, 0);

    @Mock private DiscountStrategy discountStrategy;
    @Mock private ShippingStrategy shippingStrategy;
    @Mock private TaxStrategy taxStrategy;

    private PricingEngine engine;

    // order used in all unit tests: basePrice = 300, the rest is irrelevant for delegation tests
    private final Order order = new Order(300.0, 1.5, CustomerType.VIP, Destination.EU, ShippingType.DRONE, ANY_DATE);

    @BeforeEach
    void setUp() {
        engine = new PricingEngine(discountStrategy, shippingStrategy, taxStrategy);
    }

    // =========================================================================
    // Delegation unit tests (via Mockito)
    // =========================================================================

    @Nested
    @DisplayName("Delegation")
    class Delegation {

        @Test
        @DisplayName("delegates discount calculation to DiscountStrategy")
        void calculatePrice_delegatesToDiscountStrategy() {
            when(discountStrategy.calculateDiscount(order)).thenReturn(30.0);
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(0.0);
            when(taxStrategy.calculateTax(order, 270.0)).thenReturn(0.0);

            engine.calculatePrice(order);

            verify(discountStrategy).calculateDiscount(order);
        }

        @Test
        @DisplayName("delegates shipping calculation to ShippingStrategy")
        void calculatePrice_delegatesToShippingStrategy() {
            when(discountStrategy.calculateDiscount(order)).thenReturn(0.0);
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(27.5);
            when(taxStrategy.calculateTax(order, 300.0)).thenReturn(0.0);

            engine.calculatePrice(order);

            verify(shippingStrategy).calculateShippingCost(order);
        }

        @Test
        @DisplayName("delegates tax calculation to TaxStrategy")
        void calculatePrice_delegatesToTaxStrategy() {
            when(discountStrategy.calculateDiscount(order)).thenReturn(0.0);
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(0.0);
            when(taxStrategy.calculateTax(order, 300.0)).thenReturn(27.0);

            engine.calculatePrice(order);

            verify(taxStrategy).calculateTax(order, 300.0);
        }

        @Test
        @DisplayName("passes discounted price (basePrice − discount) to TaxStrategy")
        void calculatePrice_taxStrategyReceivesDiscountedPrice() {
            when(discountStrategy.calculateDiscount(order)).thenReturn(30.0);  // 300 − 30 = 270
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(0.0);
            when(taxStrategy.calculateTax(order, 270.0)).thenReturn(0.0);

            engine.calculatePrice(order);

            verify(taxStrategy).calculateTax(order, 270.0);
        }
    }

    // =========================================================================
    // Formula unit tests (via Mockito)
    // =========================================================================

    @Nested
    @DisplayName("Price formula")
    class PriceFormula {

        @Test
        @DisplayName("subtracts discount from base price")
        void calculatePrice_subtractsDiscount() {
            when(discountStrategy.calculateDiscount(order)).thenReturn(30.0);
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(0.0);
            when(taxStrategy.calculateTax(order, 270.0)).thenReturn(0.0);

            assertThat(engine.calculatePrice(order)).isEqualTo(270.0);
        }

        @Test
        @DisplayName("adds shipping cost to the result")
        void calculatePrice_addsShippingCost() {
            when(discountStrategy.calculateDiscount(order)).thenReturn(0.0);
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(27.5);
            when(taxStrategy.calculateTax(order, 300.0)).thenReturn(0.0);

            assertThat(engine.calculatePrice(order)).isEqualTo(327.5);
        }

        @Test
        @DisplayName("adds tax to the result")
        void calculatePrice_addsTax() {
            when(discountStrategy.calculateDiscount(order)).thenReturn(0.0);
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(0.0);
            when(taxStrategy.calculateTax(order, 300.0)).thenReturn(27.0);

            assertThat(engine.calculatePrice(order)).isEqualTo(327.0);
        }

        @Test
        @DisplayName("combines discount, shipping and tax correctly")
        void calculatePrice_combinesAllComponents() {
            // discount=30, shipping=27.5, tax=27 → 270+27.5+27 = 324.5
            when(discountStrategy.calculateDiscount(order)).thenReturn(30.0);
            when(shippingStrategy.calculateShippingCost(order)).thenReturn(27.5);
            when(taxStrategy.calculateTax(order, 270.0)).thenReturn(27.0);

            assertThat(engine.calculatePrice(order)).isEqualTo(324.5);
        }
    }

    // =========================================================================
    // Integration end-to-end tests — real strategies wired through the engine
    // =========================================================================

    @Nested
    @DisplayName("End-to-end integration")
    class EndToEnd {

        // ── reference timestamps ─────────────────────────────────────────────
        private static final LocalDateTime MARCH_MORNING  = LocalDateTime.of(2026, 3, 27, 10, 0);
        private static final LocalDateTime NOVEMBER_DATE  = LocalDateTime.of(2026, 11, 15, 10, 0);
        private static final LocalDateTime HAPPY_HOUR     = LocalDateTime.of(2026, 3, 27, 19, 0);
        private static final LocalDateTime DECEMBER_DATE  = LocalDateTime.of(2026, 12, 15, 10, 0);

        // ── Gap 0 (original): README example ─────────────────────────────────

        @Test
        @DisplayName("README example: VIP · 300€ · 1.5 kg · EU · Drone → 324.50€")
        void readmeExample_vip_drone_eu() {
            // discount = 10% × 300 = 30 | shipping = 20 + 5×1.5 = 27.50 | tax = 10% × 270 = 27
            Order order = new Order(300.0, 1.5, CustomerType.VIP, Destination.EU, ShippingType.DRONE, MARCH_MORNING);
            PricingEngine e = new PricingEngine(new VipDiscount(), new DroneShipping(), new EuTax());

            assertThat(e.calculatePrice(order)).isEqualTo(324.50);
        }

        // ── Gap 1: RegularDiscount + StandardShipping + LocalTax ─────────────

        @Test
        @DisplayName("Gap 1: REGULAR · 100€ · 1 kg · LOCAL · Standard → 125.00€")
        void regularCustomer_standard_local() {
            // discount = 0 | shipping = 5 + 1 = 6 | tax = 19% × 100 = 19
            Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, MARCH_MORNING);
            PricingEngine e = new PricingEngine(new RegularDiscount(), new StandardShipping(), new LocalTax());

            assertThat(e.calculatePrice(order)).isEqualTo(125.0);
        }

        // ── Gap 2: PremiumDiscount + ExpressShipping + EuTax ─────────────────

        @Test
        @DisplayName("Gap 2: PREMIUM · 100€ · 1 kg · EU · Express → 121.50€")
        void premiumCustomer_express_eu() {
            // discount = 5% × 100 = 5 | shipping = 10 + 2 = 12 | tax = 10% × 95 = 9.50
            Order order = new Order(100.0, 1.0, CustomerType.PREMIUM, Destination.EU, ShippingType.EXPRESS, MARCH_MORNING);
            PricingEngine e = new PricingEngine(new PremiumDiscount(), new ExpressShipping(), new EuTax());

            assertThat(e.calculatePrice(order)).isEqualTo(116.5);
        }

        // ── Gap 3: BulkDiscount + StandardShipping + LocalTax+LuxuryTax ──────

        @Test
        @DisplayName("Gap 3: REGULAR · 501€ · 1 kg · LOCAL · Standard + LuxuryTax → ~534.05€")
        void bulkDiscount_standard_localPlusLuxury() {
            // discount = 15% × 501 = 75.15 | discountedPrice = 425.85
            // shipping = 5 + 1 = 6
            // tax = (19% + 5%) × 425.85 = 24% × 425.85 = 102.204
            // final = 425.85 + 6 + 102.204 = 534.054
            Order order = new Order(501.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, MARCH_MORNING);
            PricingEngine e = new PricingEngine(
                    new BulkDiscount(),
                    new StandardShipping(),
                    new CompositeTaxStrategy(List.of(new LocalTax(), new LuxuryTax())));

            assertThat(e.calculatePrice(order)).isCloseTo(534.054, within(0.001));
        }

        // ── Gap 4: BlackFridayDiscount + ExpressShipping + LocalTax+ClimateTax

        @Test
        @DisplayName("Gap 4: VIP · 100€ · 1 kg · LOCAL · Express · November + ClimateTax → 99.20€")
        void blackFriday_express_localPlusClimate() {
            // discount = 20% × 100 = 20 (BlackFriday) | discountedPrice = 80
            // shipping = 10 + 2 = 12
            // tax = 19% × 80 + 2€ = 15.20 + 2 = 17.20
            // final = 80 + 12 + 17.20 = 109.20
            Order order = new Order(100.0, 1.0, CustomerType.VIP, Destination.LOCAL, ShippingType.EXPRESS, NOVEMBER_DATE);
            PricingEngine e = new PricingEngine(
                    new BlackFridayDiscount(),
                    new ExpressShipping(),
                    new CompositeTaxStrategy(List.of(new LocalTax(), new ClimateTax())));

            assertThat(e.calculatePrice(order)).isEqualTo(109.2);
        }

        // ── Gap 5: HappyHourDiscount + StandardShipping + LocalTax ──────────

        @Test
        @DisplayName("Gap 5: REGULAR · 100€ · 1 kg · LOCAL · Standard · 19:00 → 120.48€")
        void happyHour_standard_local() {
            // discount = 8% × 100 = 8 | discountedPrice = 92
            // shipping = 5 + 1 = 6
            // tax = 19% × 92 = 17.48
            // final = 92 + 6 + 17.48 = 115.48
            Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.STANDARD, HAPPY_HOUR);
            PricingEngine e = new PricingEngine(new HappyHourDiscount(), new StandardShipping(), new LocalTax());

            assertThat(e.calculatePrice(order)).isEqualTo(115.48);
        }

        // ── Gap 6: any + PickupShipping + InternationalTax ──────────────────

        @Test
        @DisplayName("Gap 6: VIP · 100€ · 0 kg · INTERNATIONAL · Pickup → 90.00€")
        void vip_pickup_international() {
            // discount = 10% × 100 = 10 | shipping = 0 | tax = 0% × 90 = 0
            // final = 90 + 0 + 0 = 90
            Order order = new Order(100.0, 0.0, CustomerType.VIP, Destination.INTERNATIONAL, ShippingType.PICKUP, MARCH_MORNING);
            PricingEngine e = new PricingEngine(new VipDiscount(), new PickupShipping(), new InternationalTax());

            assertThat(e.calculatePrice(order)).isEqualTo(90.0);
        }

        // ── Gap 7: any + DroneShipping + LocalTax ───────────────────────────

        @Test
        @DisplayName("Gap 7: REGULAR · 100€ · 2 kg · LOCAL · Drone → 149.00€")
        void regular_drone_local() {
            // discount = 0 | shipping = 20 + 5×2 = 30 | tax = 19% × 100 = 19
            // final = 100 + 30 + 19 = 149
            Order order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, MARCH_MORNING);
            PricingEngine e = new PricingEngine(new RegularDiscount(), new DroneShipping(), new LocalTax());

            assertThat(e.calculatePrice(order)).isEqualTo(149.0);
        }

        // ── Gap 8: ChristmasShipping as composed surcharge through engine ───

        @Test
        @DisplayName("Gap 8: VIP · 300€ · 1.5 kg · EU · Drone+Christmas (December) → 327.50€")
        void vip_dronePlusChristmas_eu_december() {
            // discount = 10% × 300 = 30 | discountedPrice = 270
            // shipping = DroneShipping(1.5) + ChristmasShipping = 27.50 + 3 = 30.50
            // tax = 10% × 270 = 27
            // final = 270 + 30.50 + 27 = 327.50
            Order order = new Order(300.0, 1.5, CustomerType.VIP, Destination.EU, ShippingType.DRONE, DECEMBER_DATE);
            PricingEngine e = new PricingEngine(
                    new VipDiscount(),
                    new CompositeShippingStrategy(List.of(new DroneShipping(), new ChristmasShipping())),
                    new EuTax());

            assertThat(e.calculatePrice(order)).isEqualTo(327.50);
        }

        // ── Bonus: Drone+Christmas through engine with overweight → exception

        @Test
        @DisplayName("Drone+Christmas through engine: over 2 kg throws IllegalArgumentException")
        void dronePlusChristmas_overweight_throwsException() {
            Order order = new Order(100.0, 2.1, CustomerType.REGULAR, Destination.LOCAL, ShippingType.DRONE, DECEMBER_DATE);
            PricingEngine e = new PricingEngine(
                    new RegularDiscount(),
                    new CompositeShippingStrategy(List.of(new DroneShipping(), new ChristmasShipping())),
                    new LocalTax());

            assertThatIllegalArgumentException()
                    .isThrownBy(() -> e.calculatePrice(order))
                    .withMessageContaining("2.0");
        }
    }
}
