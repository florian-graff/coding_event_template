package com.klosebros.kata.distancecalculator;

public class ThreeDimensionalPoint extends TwoDimensionalPoint {
    private final int alt;

    public ThreeDimensionalPoint(int lat, int lon, int alt) {
        super(lat, lon);
        this.alt = alt;
    }

    public int getAlt() {
        return alt;
    }

    public double calculateDistance(ThreeDimensionalPoint other) {
        var dx = this.getLat() - other.getLat();
        var dy = this.getLon() - other.getLon();
        var dz = this.getAlt() - other.getAlt();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
}
