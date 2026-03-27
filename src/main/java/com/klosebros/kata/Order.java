package com.klosebros.kata;

import java.time.LocalDate;

/**
 * Represents a customer order that the pricing engine calculates a final price for.
 *
 * @param basePrice    the base price of the order in EUR
 * @param weightKg     the total weight of the order in kilograms
 * @param customerType the type of the customer (REGULAR, PREMIUM, VIP)
 * @param destination  the shipping destination (LOCAL, EU, INTERNATIONAL)
 * @param shippingType the chosen shipping method (STANDARD, EXPRESS, DRONE, PICKUP)
 * @param orderDate    the date on which the order was placed
 */
public record Order(
        double basePrice,
        double weightKg,
        CustomerType customerType,
        Destination destination,
        ShippingType shippingType,
        LocalDate orderDate
) {
}

