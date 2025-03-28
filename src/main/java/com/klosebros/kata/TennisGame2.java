package com.klosebros.kata;

public class TennisGame2 implements TennisGame {
    public int P1point = 0;
    public int P2point = 0;

    public String P1res = "";
    public String P2res = "";
    private String player1Name;
    private String player2Name;

    public TennisGame2(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public String getScore() {
        String score = "";
        var p1point = P1point;
        var p2point = P2point;
        if (p1point == p2point && p1point < 4) {
            if (p1point == 0)
                score = "Love";
            if (p1point == 1)
                score = "Fifteen";
            if (p1point == 2)
                score = "Thirty";
            score += "-All";
        }
        if (p1point == p2point && p1point >= 3)
            score = "Deuce";

        if (p1point > 0 && p2point == 0) {
            score = calculatePointsUntilThree();
        }
        if (p2point > 0 && p1point == 0) {
            if (p2point == 1)
                P2res = "Fifteen";
            if (p2point == 2)
                P2res = "Thirty";
            if (p2point == 3)
                P2res = "Forty";

            P1res = "Love";
            score = P1res + "-" + P2res;
        }

        var p1res = P1res;
        var p2res = P2res;
        score = getString(p1point, p2point, p1res, p2res, score);
        if (p2point > p1point && p2point < 4) {
            if (p2point == 2)
                P2res = "Thirty";
            if (p2point == 3)
                P2res = "Forty";
            if (p1point == 1)
                P1res = "Fifteen";
            if (p1point == 2)
                P1res = "Thirty";
            score = P1res + "-" + P2res;
        }

        if (p1point > p2point && p2point >= 3) {
            score = "Advantage player1";
        }

        if (p2point > p1point && p1point >= 3) {
            score = "Advantage player2";
        }

        if (p1point >= 4 && p2point >= 0 && (p1point - p2point) >= 2) {
            score = "Win for player1";
        }
        if (p2point >= 4 && p1point >= 0 && (p2point - p1point) >= 2) {
            score = "Win for player2";
        }
        return score;
    }

    private String getString(int p1point, int p2point, String p1res, String p2res, String score) {
        if (p1point > p2point && p1point < 4) {
            if (p1point == 2) {
                p1res = "Thirty";
            }
            if (p1point == 3) {
                p1res = "Forty";
            }
            if (p2point == 1) {
                p2res = "Fifteen";
            }
            if (p2point == 2)
                p2res = "Thirty";
            score = p1res + "-" + p2res;
        }
        return score;
    }

    private String calculatePointsUntilThree() {
        String score;
        if (P1point == 1)
            P1res = "Fifteen";
        if (P1point == 2)
            P1res = "Thirty";
        if (P1point == 3)
            P1res = "Forty";

        P2res = "Love";
        score = P1res + "-" + P2res;
        return score;
    }

    public void SetP1Score(int number) {

        for (int i = 0; i < number; i++) {
            P1Score();
        }

    }

    public void SetP2Score(int number) {

        for (int i = 0; i < number; i++) {
            P2Score();
        }

    }

    public void P1Score() {
        P1point++;
    }

    public void P2Score() {
        P2point++;
    }

    public void wonPoint(String player) {
        if (player == "player1")
            P1Score();
        else
            P2Score();
    }
}
