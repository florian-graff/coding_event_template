package com.klosebros.kata.distancecalculator;

public class ThreeDimensionalPoint{

    private final int lat;
    private final int lon;
    private final int alt;

    public ThreeDimensionalPoint(int lat, int lon, int alt) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public int getLat() {
        return lat;
    }
    public int getLon() {
        return lon;
    }
    public int getAlt() {
        return alt;
    }
}
