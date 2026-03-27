package com.klosebros.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingEngineTest {

    private static final LocalDate ANY_DATE = LocalDate.of(2026, 1, 15);

    @Mock private DiscountStrategy firstDiscountStrategy;
    @Mock private DiscountStrategy secondDiscountStrategy;
    @Mock private ShippingStrategy shippingStrategy;
    @Mock private TaxStrategy firstTaxStrategy;
    @Mock private TaxStrategy secondTaxStrategy;

    private PricingEngine pricingEngine;

    @BeforeEach
    void setUp() {
        pricingEngine = new PricingEngine(
                List.of(firstDiscountStrategy, secondDiscountStrategy),
                shippingStrategy,
                List.of(firstTaxStrategy, secondTaxStrategy)
        );
    }

    @Test
    void should_sumDiscountsAdditively_when_multipleDiscountStrategiesApply() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.PICKUP, ANY_DATE);
        when(firstDiscountStrategy.calculateDiscount(order)).thenReturn(10.0);
        when(secondDiscountStrategy.calculateDiscount(order)).thenReturn(5.0);
        when(shippingStrategy.calculateShipping(order)).thenReturn(0.0);
        when(firstTaxStrategy.calculateTax(order, 85.0)).thenReturn(0.0);
        when(secondTaxStrategy.calculateTax(order, 85.0)).thenReturn(0.0);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(85.0);
    }

    @Test
    void should_applyShippingFromStrategy_when_calculatingPrice() {
        Order order = new Order(100.0, 2.0, CustomerType.REGULAR, Destination.INTERNATIONAL, ShippingType.STANDARD, ANY_DATE);
        when(firstDiscountStrategy.calculateDiscount(order)).thenReturn(0.0);
        when(secondDiscountStrategy.calculateDiscount(order)).thenReturn(0.0);
        when(shippingStrategy.calculateShipping(order)).thenReturn(7.0);
        when(firstTaxStrategy.calculateTax(order, 100.0)).thenReturn(0.0);
        when(secondTaxStrategy.calculateTax(order, 100.0)).thenReturn(0.0);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(107.0);
    }

    @Test
    void should_sumTaxesAdditively_when_multipleTaxStrategiesApply() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.LOCAL, ShippingType.PICKUP, ANY_DATE);
        when(firstDiscountStrategy.calculateDiscount(order)).thenReturn(0.0);
        when(secondDiscountStrategy.calculateDiscount(order)).thenReturn(0.0);
        when(shippingStrategy.calculateShipping(order)).thenReturn(0.0);
        when(firstTaxStrategy.calculateTax(order, 100.0)).thenReturn(19.0);
        when(secondTaxStrategy.calculateTax(order, 100.0)).thenReturn(5.0);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(124.0);
    }

    @Test
    void should_passDiscountedBasePriceToTaxStrategies_when_calculatingPrice() {
        Order order = new Order(100.0, 1.0, CustomerType.REGULAR, Destination.EU, ShippingType.PICKUP, ANY_DATE);
        when(firstDiscountStrategy.calculateDiscount(order)).thenReturn(20.0);
        when(secondDiscountStrategy.calculateDiscount(order)).thenReturn(0.0);
        when(shippingStrategy.calculateShipping(order)).thenReturn(0.0);
        when(firstTaxStrategy.calculateTax(order, 80.0)).thenReturn(8.0);
        when(secondTaxStrategy.calculateTax(order, 80.0)).thenReturn(0.0);

        double finalPrice = pricingEngine.calculatePrice(order);

        assertThat(finalPrice).isEqualTo(88.0);
    }
}
