package com.klosebros.kata.distancecalculator;

public class TwoDimensionalPoint {
    protected int lat;
    protected int lon;

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

    public void setLon(int lon) {
        this.lon = lon;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
}
