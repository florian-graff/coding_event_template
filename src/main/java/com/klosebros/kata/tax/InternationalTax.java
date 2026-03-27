package com.klosebros.kata.tax;

import com.klosebros.kata.Order;
import com.klosebros.kata.TaxStrategy;

/**
 * Zero-tax strategy for orders shipped internationally.
 * International orders are exempt from VAT.
 */
public class InternationalTax implements TaxStrategy {

    @Override
    public double calculateTax(Order order, double discountedPrice) {
        return 0.0;
    }
}

