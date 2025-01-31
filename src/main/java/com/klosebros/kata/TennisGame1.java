package com.klosebros.kata;

public class TennisGame1 implements TennisGame {

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private final String playerOneName;
    private final String playerTwoName;

    public TennisGame1(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(this.playerOneName)) {
            playerOneScore++;
        } else {
            playerTwoScore++;
        }
    }

    public String getScore() {
        return playerOneScore == playerTwoScore ? determineScoreOutputForEqualPoints(playerOneScore) :
                (playerOneScore >= 4 || playerTwoScore >= 4) ? determineScoreOutputForFinalPhase(playerOneScore, playerTwoScore) :
                        determineScoreOutputOtherwise(playerOneScore, playerTwoScore);
    }

    private String determineScoreOutputOtherwise(int playerOneScore, int playerTwoScore) {
        return getScoreString(playerOneScore) + "-" + getScoreString(playerTwoScore);
    }

    private String getScoreString(int score) {
        return switch (score) {
            case 0 -> "Love";
            case 1 -> "Fifteen";
            case 2 -> "Thirty";
            case 3 -> "Forty";
            default -> throw new IllegalArgumentException("Unexpected score: " + score);
        };
    }

    private String determineScoreOutputForFinalPhase(int playerOneScore, int playerTwoScore) {
        int minusResult = playerOneScore - playerTwoScore;
        return switch (minusResult) {
            case 1 -> "Advantage " + this.playerOneName;
            case -1 -> "Advantage " + this.playerTwoName;
            case 2, 3, 4, 5 -> "Win for " + this.playerOneName;
            case -2, -3, -4, -5 -> "Win for " + this.playerTwoName;
            default -> throw new IllegalStateException("Unexpected value: " + minusResult);
        };
    }

    private String determineScoreOutputForEqualPoints(int score) {
        return switch (score) {
            case 0 -> "Love-All";
            case 1 -> "Fifteen-All";
            case 2 -> "Thirty-All";
            default -> "Deuce";
        };
    }
}
