package com.klosebros.kata.distancecalculator;

public class TwoDimensionalPoint extends ThreeDimensionalPoint {

    public TwoDimensionalPoint(int lat, int lon) {
        super(lat, lon, 0); // Altitude is set to 0 for 2D points
    }

    /**
     * Returns always 0 for altitude since this is a 2D point.
     * @return 0
     */
    @Override
    public int getAlt() {
        return 0; // Override to return 0 for 2D points
    }
}
