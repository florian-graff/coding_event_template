package com.klosebros.kata.distancecalculator;

public class DistanceCalculator {
    public double calculate(TwoDimensionalPoint p1, TwoDimensionalPoint p2) {
        int dx = p1.getLat() - p2.getLat();
        int dy = p1.getLon() - p2.getLon();
        if (p1 instanceof ThreeDimensionalPoint && p2 instanceof ThreeDimensionalPoint) {
            int dz = ((ThreeDimensionalPoint) p1).getAlt() - ((ThreeDimensionalPoint) p2).getAlt();
            return Math.sqrt(
                    dx * dx +
                    dy * dy +
                    dz * dz
            );
        }
        return Math.sqrt(
            dx * dx +
            dy * dy
        );
    }
}
