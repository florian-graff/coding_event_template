package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KataTemplateTest {

    @Test
    void shouldCreateEmptyGrid() {
        boolean[][] initialGrid = {
            {false, false, false},
            {false, false, false},
            {false, false, false}
        };

        GameOfLife game = new GameOfLife(initialGrid);
        boolean[][] result = game.getGrid();

        assertThat(result).isEqualTo(initialGrid);
    }

    @Test
    void shouldKillLonelyCellWithoutNeighbors() {
        boolean[][] initialGrid = {
            {false, false, false},
            {false, true, false},
            {false, false, false}
        };

        boolean[][] expectedGrid = {
            {false, false, false},
            {false, false, false},
            {false, false, false}
        };

        GameOfLife game = new GameOfLife(initialGrid);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(expectedGrid);
    }

    @Test
    void shouldKillCellWithOnlyOneNeighbor() {
        boolean[][] initialGrid = {
            {true, true, false},
            {false, false, false},
            {false, false, false}
        };

        boolean[][] expectedGrid = {
            {false, false, false},
            {false, false, false},
            {false, false, false}
        };

        GameOfLife game = new GameOfLife(initialGrid);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(expectedGrid);
    }

    @Test
    void shouldKeepCellAliveWithTwoNeighbors() {
        boolean[][] initialGrid = {
            {true, true, true},
            {false, false, false},
            {false, false, false}
        };

        boolean[][] expectedGrid = {
            {false, true, false},
            {false, true, false},
            {false, false, false}
        };

        GameOfLife game = new GameOfLife(initialGrid);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(expectedGrid);
    }

    @Test
    void shouldKeepCellAliveWithThreeNeighbors() {
        boolean[][] initialGrid = {
            {true, true, false},
            {true, true, false},
            {false, false, false}
        };

        boolean[][] expectedGrid = {
            {true, true, false},
            {true, true, false},
            {false, false, false}
        };

        GameOfLife game = new GameOfLife(initialGrid);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(expectedGrid);
    }

    @Test
    void shouldKillCellWithMoreThanThreeNeighbors() {
        boolean[][] initialGrid = {
            {true, true, true},
            {true, true, false},
            {false, false, false}
        };

        boolean[][] expectedGrid = {
            {true, false, true},
            {true, false, false},
            {false, false, false}
        };

        GameOfLife game = new GameOfLife(initialGrid);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(expectedGrid);
    }

    @Test
    void shouldBringDeadCellToLifeWithExactlyThreeNeighbors() {
        boolean[][] initialGrid = {
            {true, true, false},
            {true, false, false},
            {false, false, false}
        };

        boolean[][] expectedGrid = {
            {true, true, false},
            {true, true, false},
            {false, false, false}
        };

        GameOfLife game = new GameOfLife(initialGrid);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(expectedGrid);
    }

    @Test
    void shouldHandleBlinkerPattern() {
        // Blinker pattern - oszilliert zwischen horizontal und vertikal
        boolean[][] horizontalBlinker = {
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, true, true, true, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
        };

        boolean[][] verticalBlinker = {
            {false, false, false, false, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, false, false, false, false}
        };

        GameOfLife game = new GameOfLife(horizontalBlinker);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(verticalBlinker);

        // Und zurück
        GameOfLife backToHorizontal = nextGen.nextGeneration();
        assertThat(backToHorizontal.getGrid()).isEqualTo(horizontalBlinker);
    }

    @Test
    void shouldHandleBlockPattern() {
        // Block pattern - bleibt stabil
        boolean[][] block = {
            {false, false, false, false},
            {false, true, true, false},
            {false, true, true, false},
            {false, false, false, false}
        };

        GameOfLife game = new GameOfLife(block);
        GameOfLife nextGen = game.nextGeneration();

        assertThat(nextGen.getGrid()).isEqualTo(block);
    }
}
