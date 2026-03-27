package com.klosebros.kata;

import java.time.LocalDate;

public record Order(
        double basePrice,
        double weightKg,
        CustomerType customerType,
        Destination destination,
        ShippingType shippingType,
        LocalDate orderDate
) {}

