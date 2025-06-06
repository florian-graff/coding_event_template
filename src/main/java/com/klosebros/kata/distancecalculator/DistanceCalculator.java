package com.klosebros.kata.distancecalculator;

public class DistanceCalculator {
    public double calculate(MultiDimensionalPoint p1, MultiDimensionalPoint p2) {
        var dx = p1.getLat() - p2.getLat();
        var dy = p1.getLon() - p2.getLon();
        if (p1 instanceof ThreeDimensionalPoint && p2 instanceof ThreeDimensionalPoint) {
            var dz = ((ThreeDimensionalPoint) p1).getAlt() - ((ThreeDimensionalPoint) p2).getAlt();
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
