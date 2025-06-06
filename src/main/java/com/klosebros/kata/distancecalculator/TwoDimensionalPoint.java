package com.klosebros.kata.distancecalculator;

public class TwoDimensionalPoint extends MultiDimensionalPoint {

    public TwoDimensionalPoint(int lat, int lon) {
        super(lat, lon);
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }

    @Override
    public double calculateDistance(MultiDimensionalPoint other) {
        var dx = this.lat - other.lat;
        var dy = this.lon - other.lon;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
