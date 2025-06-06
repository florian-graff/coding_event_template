package com.klosebros.kata.distancecalculator;

public class DistanceCalculator {
    public double calculate(ThreeDimensionalPoint p1, ThreeDimensionalPoint p2) {
        int dx = p1.getLat() - p2.getLat();
        int dy = p1.getLon() - p2.getLon();
        int dz = p1.getAlt() - p2.getAlt();
        return Math.sqrt(
                dx * dx +
                        dy * dy +
                        dz * dz
        );
    }
}
