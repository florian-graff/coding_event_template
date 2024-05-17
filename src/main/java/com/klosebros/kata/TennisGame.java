package com.klosebros.kata;

import static java.lang.String.format;

public class TennisGame {

    private String scorePlayerOne = "Love";
    private String scorePlayerTwo = "Love";

    public String getScore() {
        return format("%s %s", scorePlayerOne, scorePlayerTwo);
    }

    public void pointToPlayerOne() {
        scorePlayerOne = "15";
    }
}
