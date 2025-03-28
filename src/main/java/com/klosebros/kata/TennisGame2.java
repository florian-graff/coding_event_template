package com.klosebros.kata;

import java.util.Objects;

public class TennisGame2 implements TennisGame {
    public static final String LOVE = "Love";
    public static final String FIFTEEN = "Fifteen";
    public static final String THIRTY = "Thirty";
    public static final String ALL = "-All";
    public static final String DEUCE = "Deuce";
    public static final String FORTY = "Forty";
    private int player1Point = 0;
    private int player2Point = 0;

    private String player1Result = "";
    private String player2Result = "";

    public String getScore() {
        var score = "";
        if (player1Point == player2Point && player1Point < 4) {
            if (player1Point == 0)
                score = LOVE;
            if (player1Point == 1)
                score = FIFTEEN;
            if (player1Point == 2)
                score = THIRTY;
            score += ALL;
        }
        if (player1Point == player2Point && player1Point >= 3)
            score = DEUCE;

        if (player1Point > 0 && player2Point == 0) {
            if (player1Point == 1)
                player1Result = FIFTEEN;
            if (player1Point == 2)
                player1Result = THIRTY;
            if (player1Point == 3)
                player1Result = FORTY;

            player2Result = LOVE;
            score = player1Result + "-" + player2Result;
        }
        if (player2Point > 0 && player1Point == 0) {
            if (player2Point == 1)
                player2Result = FIFTEEN;
            if (player2Point == 2)
                player2Result = THIRTY;
            if (player2Point == 3)
                player2Result = FORTY;

            player1Result = LOVE;
            score = player1Result + "-" + player2Result;
        }

        if (player1Point > player2Point && player1Point < 4) {
            if (player1Point == 2)
                player1Result = THIRTY;
            if (player1Point == 3)
                player1Result = FORTY;
            if (player2Point == 1)
                player2Result = FIFTEEN;
            if (player2Point == 2)
                player2Result = THIRTY;
            score = player1Result + "-" + player2Result;
        }
        if (player2Point > player1Point && player2Point < 4) {
            if (player2Point == 2)
                player2Result = THIRTY;
            if (player2Point == 3)
                player2Result = FORTY;
            if (player1Point == 1)
                player1Result = FIFTEEN;
            if (player1Point == 2)
                player1Result = THIRTY;
            score = player1Result + "-" + player2Result;
        }

        if (player1Point > player2Point && player2Point >= 3) {
            score = "Advantage player1";
        }

        if (player2Point > player1Point && player1Point >= 3) {
            score = "Advantage player2";
        }

        if (player1Point >= 4 && player2Point >= 0 && (player1Point - player2Point) >= 2) {
            score = "Win for player1";
        }
        if (player2Point >= 4 && player1Point >= 0 && (player2Point - player1Point) >= 2) {
            score = "Win for player2";
        }
        return score;
    }

    public void p1Score() {
        player1Point++;
    }

    public void p2Score() {
        player2Point++;
    }

    public void wonPoint(String player) {
        if (Objects.equals(player, "player1"))
            p1Score();
        else
            p2Score();
    }
}
