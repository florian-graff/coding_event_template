package com.klosebros.kata.leaderboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RaceTest {

    @Test
    public void isShouldCalculateDriverPoints() {
        // setup

        // act

        // verify
        assertEquals(25, TestData.race1.getPoints(TestData.driver1));
        assertEquals(18, TestData.race1.getPoints(TestData.driver2));
        assertEquals(15, TestData.race1.getPoints(TestData.driver3));
    }

}
