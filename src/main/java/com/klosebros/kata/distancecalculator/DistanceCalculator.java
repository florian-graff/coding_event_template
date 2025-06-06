package com.klosebros.kata.distancecalculator;

public class DistanceCalculator {

    public double calculate(TwoDimensionalPoint p1, TwoDimensionalPoint p2) {
        var dx = p1.lat - p2.lat;
        var dy = p1.lon - p2.lon;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calculate(ThreeDimensionalPoint p1, ThreeDimensionalPoint p2) {
        var dx = p1.getLat() - p2.getLat();
        var dy = p1.getLon() - p2.getLon();
        var dz = p1.getAlt() - p2.getAlt();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
