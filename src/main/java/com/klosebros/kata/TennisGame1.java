package com.klosebros.kata;

public class TennisGame1 implements TennisGame {

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private String playerOneName;
    private String playerTwoName;

    public TennisGame1(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(this.playerOneName))
            playerOneScore += 1;
        else
            playerTwoScore += 1;
    }

    public String getScore() {
        String score_output = "";

        if (playerOneScore == playerTwoScore) {
            score_output = determineScoreOutputForDraw(playerOneScore);
        } else if (playerOneScore >= 4 || playerTwoScore >= 4) {
            score_output = determineScoreOutputForFinalPhase(playerOneScore, playerTwoScore);
        } else {
            score_output = determineScoreOutputOtherwise(playerOneScore, playerTwoScore);
        }
        return score_output;
    }

    private String determineScoreOutputOtherwise(int playerOneScore, int playerTwoScore) {
        String score_output = "";

        int tempScore = 0;
        for (int i = 1; i < 3; i++) {
            if (i == 1) tempScore = playerOneScore;
            else {
                score_output += "-";
                tempScore = playerTwoScore;
            }
            switch (tempScore) {
                case 0:
                    score_output += "Love";
                    break;
                case 1:
                    score_output += "Fifteen";
                    break;
                case 2:
                    score_output += "Thirty";
                    break;
                case 3:
                    score_output += "Forty";
                    break;
            }
        }
        return score_output;
    }

    private String determineScoreOutputForFinalPhase(int playerOneScore, int playerTwoScore) {
        String score_output;
        int minusResult = playerOneScore - playerTwoScore;
        if (minusResult == 1) score_output = "Advantage " + this.playerOneName;
        else if (minusResult == -1) score_output = "Advantage " + this.playerTwoName;
        else if (minusResult >= 2) score_output = "Win for " + this.playerOneName;
        else score_output = "Win for " + this.playerTwoName;
        return score_output;
    }

    private String determineScoreOutputForDraw(int score) {
        String score_output;
        switch (score) {
            case 0:
                score_output = "Love-All";
                break;
            case 1:
                score_output = "Fifteen-All";
                break;
            case 2:
                score_output = "Thirty-All";
                break;
            default:
                score_output = "Deuce";
                break;

        }
        return score_output;
    }
}
