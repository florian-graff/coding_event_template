package com.klosebros.kata;

import static java.lang.String.format;

public class TennisGame {

    private int scorePlayerOne = 0;
    private int scorePlayerTwo = 0;

    public String getScore() {
        String scoreValueOne = ""; 
        String scoreValueTwo = "";

        if (scorePlayerOne == 0) {
            scoreValueOne = "Love";
        } else if (scorePlayerOne == 1) {
            scoreValueOne = "15";
        }

        if (scorePlayerTwo == 0) {
            scoreValueTwo = "Love";
        }

        if (scorePlayerOne == scorePlayerTwo) {
            scoreValueTwo = "All";
        }

        return format("%s %s", scoreValueOne, scoreValueTwo);
    }

    public void pointToPlayerOne() {
        scorePlayerOne++;
    }

    public void pointToPlayerTwo() {
        scorePlayerTwo++;
    }
}
