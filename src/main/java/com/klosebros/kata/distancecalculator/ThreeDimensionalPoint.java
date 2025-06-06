package com.klosebros.kata.distancecalculator;

public class ThreeDimensionalPoint extends MultiDimensionalPoint{
    private int alt;

    public ThreeDimensionalPoint(int lat, int lon, int alt) {
        super(lat, lon);
        this.alt = alt;
    }

    public int getAlt() {
        return alt;
    }

    @Override
    public double calculateDistance(MultiDimensionalPoint other) {
        if (!(other instanceof ThreeDimensionalPoint)) {
            throw new IllegalArgumentException("Cannot calculate distance to a non-3D point");
        }
        var dx = this.getLat() - other.getLat();
        var dy = this.getLon() - other.getLon();
        var dz = this.alt - ((ThreeDimensionalPoint) other).getAlt();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
