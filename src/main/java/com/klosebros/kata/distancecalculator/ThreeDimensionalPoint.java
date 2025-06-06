package com.klosebros.kata.distancecalculator;

public class ThreeDimensionalPoint {
    private final int alt;
    protected int lat;
    protected int lon;

    public ThreeDimensionalPoint(int alt, int lat, int lon) {
        this.alt = alt;
        this.lat = lat;
        this.lon = lon;
    }

    public int getAlt() {
        return alt;
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
