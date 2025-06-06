package com.klosebros.kata.distancecalculator;

public abstract class MultiDimensionalPoint {
    protected int lat;
    protected int lon;

    public MultiDimensionalPoint(int lat, int lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public abstract double calculateDistance(MultiDimensionalPoint other);

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
