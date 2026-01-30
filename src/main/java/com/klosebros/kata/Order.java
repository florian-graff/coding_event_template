package com.klosebros.kata;


import java.util.Date;

public record Order(double basePrice, double weightKg, CustomerType customerType, Destination destination,
                    ShippingType shippingType, Date orderDate) {
}
