package com.klosebros.kata.distancecalculator;

public class TwoDimensionalPoint {
    private int lat;
    private int lon;

    public TwoDimensionalPoint(int lat, int lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }

    public double calculateDistance(TwoDimensionalPoint other) {
        var dx = this.lat - other.lat;
        var dy = this.lon - other.lon;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
