package com.klosebros.kata.distancecalculator;

public class TwoDimensionalPoint extends ThreeDimensionalPoint {

    public TwoDimensionalPoint(int lat, int lon) {
        super(lat, lon, 0); // Altitude is set to 0 for 2D points
    }
}
