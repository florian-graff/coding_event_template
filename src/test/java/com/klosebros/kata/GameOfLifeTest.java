package com.klosebros.kata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.klosebros.kata.GameOfLife.DEAD_CELL;

class GameOfLifeTest {

    @Test
    void nothingIsBornOutOfNothing() {
        String[][] gameMap = createEmptyMap(3, 3);

        GameOfLife gameOfLife = new GameOfLife();
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        Assertions.assertThat(nextGeneration).isEqualTo(gameMap);
    }

    @Test
    void nothingIsBornOutOfNothingForAnySizeMap() {
        String[][] gameMap = createEmptyMap(4, 4);

        GameOfLife gameOfLife = new GameOfLife();
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        Assertions.assertThat(nextGeneration).isEqualTo(gameMap);
    }

    @Test
    void lonelyCellDies() {
        //Given
        String[][] gameMap = createEmptyMap(3, 3);
        gameMap[1][1] = "X";

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        //THEN
        var emptyMap = createEmptyMap(3, 3);
        Assertions.assertThat(nextGeneration).isEqualTo(emptyMap);

    }

    @Test
    void cellWithThreeNeighborsComesToLife() {
        //Given
        String[][] gameMap = {
                {"0", "X", "0", "0"},
                {"X", "0", "X", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"}
        };

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        //THEN
        Assertions.assertThat(nextGeneration[1][1]).isEqualTo("X");

    }

    @Test
    void livingCellWithThreeNeighborsLivesOn() {
        //Given
        String[][] gameMap = {
                {"0", "X", "X", "0"},
                {"0", "X", "X", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"}
        };

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        //THEN
        Assertions.assertThat(nextGeneration[1][1]).isEqualTo("X");

    }

    @Test
    void livingCellWithTwoNeighborsLivesOn() {
        //Given
        String[][] gameMap = {
                {"0", "X", "X", "0"},
                {"0", "X", "0", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"}
        };

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);
        //THEN
        Assertions.assertThat(nextGeneration[0][1]).isEqualTo("X");

    }

    @Test
    void livingCellWithMoreThanThreeNeighborsDies() {
        //Given
        String[][] gameMap = {
                {"0", "X", "X", "0"},
                {"0", "X", "X", "0"},
                {"0", "X", "0", "0"},
                {"0", "0", "0", "0"}
        };

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);
        //THEN
        Assertions.assertThat(nextGeneration[1][1]).isEqualTo(DEAD_CELL);

    }

    @Test
    void multiCellTest() {
        //Given
        String[][] gameMap = {
                {"0", "X", "0"},
                {"X", "X", "0"},
                {"0", "0", "X"}
        };

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);
        //THEN
        String[][] expected = {
                {"X", "X", "0"},
                {"X", "X", "X"},
                {"0", "X", "0"}
        };
        Assertions.assertThat(nextGeneration).isEqualTo(expected);
    }

    @Test
    void multiCellTestWithOverPopulation() {
        //Given
        String[][] gameMap = {
                {"X", "X", "0"},
                {"X", "X", "0"},
                {"0", "0", "X"}
        };

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);
        //THEN
        String[][] expected = {
                {"X", "X", "0"},
                {"X", "0", "X"},
                {"0", "X", "0"}
        };
        Assertions.assertThat(nextGeneration).isEqualTo(expected);
    }



    private static String[][] createEmptyMap(final int rows, final int columns) {
        var emptyMap = new String[rows][columns];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                emptyMap[x][y] = DEAD_CELL;
            }
        }
        return emptyMap;
    }
}