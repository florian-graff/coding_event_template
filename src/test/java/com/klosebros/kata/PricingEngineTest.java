package com.klosebros.kata;

import com.klosebros.kata.discount.VipDiscount;
import com.klosebros.kata.shipping.DroneShipping;
import com.klosebros.kata.tax.EuTax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("PricingEngine – Phase 2 (Strategy Pattern)")
@ExtendWith(MockitoExtension.class)
class PricingEngineTest {

    private static final LocalDate ANY_DATE = LocalDate.of(2026, 3, 27);

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
    // Integration smoke test — README example with real strategies
    // =========================================================================

    @Test
    @DisplayName("README example: VIP · 300€ · 1.5 kg · EU · Drone → 324.50€")
    void readmeExample_endToEnd() {
        // discount  = 10% × 300       = 30.00
        // shipping  = 20 + 5 × 1.5   = 27.50
        // tax       = 10% × (300−30)  = 27.00
        // final     = 270 + 27.5 + 27 = 324.50
        Order order = new Order(300.0, 1.5, CustomerType.VIP, Destination.EU, ShippingType.DRONE, ANY_DATE);
        PricingEngine realEngine = new PricingEngine(new VipDiscount(), new DroneShipping(), new EuTax());

        assertThat(realEngine.calculatePrice(order)).isEqualTo(324.50);
    }
}
