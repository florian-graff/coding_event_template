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
        assertThat(score).isEqualTo("Love");
    }
    
}
