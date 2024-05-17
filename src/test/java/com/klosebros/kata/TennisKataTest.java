package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TennisKataTest {

    @Test
    void testStartMatch() {
        // Arrange
        var tennisGame = new TennisGame();
        // Act
        var score = tennisGame.getScore();
        // Assert
        assertThat(score).isEqualTo("Love All");
    }

    @Test
    void testFirstPointToPlayerOne() {
        // Arrange
        var tennisGame = new TennisGame();
        // Act
        tennisGame.pointToPlayerOne();
        var score = tennisGame.getScore();
        // Assert
        assertThat(score).isEqualTo("15 Love");
    }

    @Test
    void testBothPlayerHave15Points() {
        // Arrange
        var tennisGame = new TennisGame();
        // Act
        tennisGame.pointToPlayerOne();
        tennisGame.pointToPlayerTwo();
        var score = tennisGame.getScore();
        // Assert
        assertThat(score).isEqualTo("15 All");
    }

    @Test
    void testPlayer1Have30AndPlayer2Have0Points() {
        // Arrange
        var tennisGame = new TennisGame();
        // Act
        tennisGame.pointToPlayerOne();
        tennisGame.pointToPlayerOne();
        var score = tennisGame.getScore();
        // Assert
        assertThat(score).isEqualTo("30 Love");
    }
}
