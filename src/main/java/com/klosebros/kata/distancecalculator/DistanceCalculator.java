package com.klosebros.kata.distancecalculator;

public class DistanceCalculator {
    public double calculate(TwoDimensionalPoint p1, TwoDimensionalPoint p2) {
        return p1.calculateDistance(p2);
    }

    public double calculate(ThreeDimensionalPoint p1, ThreeDimensionalPoint p2) {
        return p1.calculateDistance(p2);
    }
}
