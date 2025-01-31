package com.klosebros.kata;

import java.util.Objects;

public class TennisGame1 implements TennisGame {
    
    private int mScore1 = 0;
    private int mScore2 = 0;

    public TennisGame1() {
    }

    public void wonPoint(String playerName) {
        if (Objects.equals(playerName, "player1"))
            mScore1 += 1;
        else
            mScore2 += 1;
    }

    public String getScore() {
        var score = new StringBuilder();
        var tempScore=0;
        if (mScore1 == mScore2)
        {
            score = new StringBuilder(switch (mScore1) {
                case 0 -> "Love-All";
                case 1 -> "Fifteen-All";
                case 2 -> "Thirty-All";
                default -> "Deuce";
            });
        }
        else if (mScore1 >=4 || mScore2 >=4)
        {
            var minusResult = mScore1 - mScore2;
            if (minusResult==1) score = new StringBuilder("Advantage player1");
            else if (minusResult ==-1) score = new StringBuilder("Advantage player2");
            else if (minusResult>=2) score = new StringBuilder("Win for player1");
            else score = new StringBuilder("Win for player2");
        }
        else
        {
            for (var i = 1; i<3; i++)
            {
                if (i==1) tempScore = mScore1;
                else { score.append("-"); tempScore = mScore2;}
                switch (tempScore) {
                    case 0 -> score.append("Love");
                    case 1 -> score.append("Fifteen");
                    case 2 -> score.append("Thirty");
                    case 3 -> score.append("Forty");
                    default -> throw new IllegalStateException("Unexpected value: " + tempScore);
                }
            }
        }
        return score.toString();
    }
}
