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

//eine testmethode um zu testen, dass eine zelle mit mehr als 3 lebenden nachbarn stirbt
    @Test
    void testZelleMitMehrAlsDreiLebendenNachbarnStirbt() {
        // Arrange
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {false, false, false, false, false},
                {false, true,  true,  true,  false},
                {false, true,  true,  false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };

        // Act
        boolean[][] nextGeneration = game.nextGeneration(grid);

        // Assert
        assertThat(nextGeneration[1][2]).isFalse(); // Die Zelle (1,2) sollte sterben
    }

    // Test: Tote Zelle mit genau 3 lebenden Nachbarn wird lebendig (Reproduktion)
    @Test
    void testToteZelleMitDreiLebendenNachbarnWirdLebendig() {
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {false, false, false},
                {true,  false, true},
                {false, true,  false}
        };
        boolean[][] nextGeneration = game.nextGeneration(grid);
        assertThat(nextGeneration[1][1]).isTrue(); // Die tote Zelle (1,1) sollte lebendig werden
    }

    // Test: Lebende Zelle mit weniger als 2 lebenden Nachbarn stirbt (Einsamkeit)
    @Test
    void testLebendeZelleMitEinemNachbarnStirbt() {
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {false, false, false},
                {false, true,  false},
                {false, true,  false}
        };
        boolean[][] nextGeneration = game.nextGeneration(grid);
        assertThat(nextGeneration[1][1]).isFalse(); // Die Zelle (1,1) sollte sterben
    }

    // Test: Tote Zelle mit weniger als 3 lebenden Nachbarn bleibt tot
    @Test
    void testToteZelleMitWenigerAlsDreiNachbarnBleibtTot() {
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {false, false, false},
                {false, false, true},
                {false, false, false}
        };
        boolean[][] nextGeneration = game.nextGeneration(grid);
        assertThat(nextGeneration[1][1]).isFalse(); // Die tote Zelle (1,1) bleibt tot
    }

    // Test: Tote Zelle mit mehr als 3 lebenden Nachbarn bleibt tot
    @Test
    void testToteZelleMitMehrAlsDreiNachbarnBleibtTot() {
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {true,  true,  true},
                {true,  false, false},
                {false, false, false}
        };
        boolean[][] nextGeneration = game.nextGeneration(grid);
        assertThat(nextGeneration[1][1]).isFalse(); // Die tote Zelle (1,1) bleibt tot
    }

    // Test: Zelle am Rand stirbt nicht durch Indexfehler und verhaelt sich korrekt
    @Test
    void testRandzelleVerhaeltSichKorrekt() {
        GameOfLife game = new GameOfLife();
        boolean[][] grid = {
                {true, true, false},
                {false, false, false},
                {false, false, false}
        };
        boolean[][] nextGeneration = game.nextGeneration(grid);
        assertThat(nextGeneration[0][0]).isFalse(); // Zelle (0,0) hat nur einen Nachbarn, sollte sterben
        assertThat(nextGeneration[0][1]).isFalse(); // Zelle (0,1) hat nur einen Nachbarn, sollte sterben
    }


}