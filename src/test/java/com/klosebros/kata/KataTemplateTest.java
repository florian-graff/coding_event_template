package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KataTemplateTest {
//schreibe eine testmethode um zu testen, dass eine zelle mit 3 lebenden nachbarn nicht stirbt
    @Test
    void testZelleMitDreiLebendenNachbarnBleibtLebendig() {
        // Arrange
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {false, false, false, false, false},
                {false, true,  true,  true,  false},
                {false, false, true,  false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };

        // Act
        boolean[][] nextGeneration = game.nextGeneration(grid);

        // Assert
        assertThat(nextGeneration[1][2]).isTrue(); // Die Zelle (1,2) sollte leben bleiben
    }


//eine testmethode um zu testen dass eine zelle mit 2 lebenden nachbarn lebt
    @Test
    void testZelleMitZweiLebendenNachbarnBleibtLebendig() {
        // Arrange
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {false, false, false, false, false},
                {false, true,  true,  false, false},
                {false, false, true,  false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };

        // Act
        boolean[][] nextGeneration = game.nextGeneration(grid);

        // Assert
        assertThat(nextGeneration[1][2]).isTrue(); // Die Zelle (1,2) sollte leben bleiben
    }



}