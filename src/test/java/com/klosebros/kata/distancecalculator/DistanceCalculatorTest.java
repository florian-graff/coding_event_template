package com.klosebros.kata.distancecalculator;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DistanceCalculatorTest {

    @Test
    void itCalculatesCorrect2dDistance() {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        var p1 = new TwoDimensionalPoint(1, 1);
        var p2 = new TwoDimensionalPoint(3, 3);
        double distance = distanceCalculator.calculate(p1, p2);
        assertThat(distance).isEqualTo(2.8284271247461903);
    }

    @Test
    void itCalculatesCorrect2dDistance2() {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        var p1 = new TwoDimensionalPoint(0, 1);
        var p2 = new TwoDimensionalPoint(0, 2);
        double distance = distanceCalculator.calculate(p1, p2);
        assertThat(distance).isEqualTo(1);
    }

    @Test
    void itCalculatesCorrect3dDistance() {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        var p1 = new ThreeDimensionalPoint(0, 0, 0);
        var p2 = new ThreeDimensionalPoint(1, 1, 1);
        double distance = distanceCalculator.calculate(p1, p2);
        assertThat(distance).isEqualTo(1.7320508075688772);
    }

//    @Test
//    void itCalculatesCorrect2dAnd3dDistance() {
//        DistanceCalculator distanceCalculator = new DistanceCalculator();
//        var p1 = new TwoDimensionalPoint(0, 0);
//        var p2 = new ThreeDimensionalPoint(1, 1, 1);
//        double distance = distanceCalculator.calculate(p1, p2);
//        assertThat(distance).isEqualTo(1.4142135623730951);
//    }
//
//    @Test
//    void itCalculatesCorrect3dAnd2dDistance() {
//        DistanceCalculator distanceCalculator = new DistanceCalculator();
//        var p1 = new TwoDimensionalPoint(0, 0);
//        var p2 = new ThreeDimensionalPoint(1, 1, 1);
//        double distance = distanceCalculator.calculate(p2, p1);
//        assertThat(distance).isEqualTo(1.4142135623730951);
//    }
//
//    @Test
//    void foo() {
//        DistanceCalculator distanceCalculator = new DistanceCalculator();
//        var p1 = new TwoDimensionalPoint(1, 1);
//        var p2 = new ThreeDimensionalPoint(1, 1, 1);
//        var p3 = new ThreeDimensionalPoint(1, 1, 2);
//
//        AssertionsForClassTypes.assertThat(distanceCalculator.calculate(p1,p2)).isEqualTo(0);
//        AssertionsForClassTypes.assertThat(distanceCalculator.calculate(p2,p3)).isEqualTo(1);
//        AssertionsForClassTypes.assertThat(distanceCalculator.calculate(p1,p3)).isEqualTo(0); // This is a bit weird, but it is correct according to the current implementation
//    }
}
