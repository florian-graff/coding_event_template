package com.klosebros.kata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.klosebros.kata.GameOfLife.DEADCELL;


class GameOfLifeTest {

    @Test
    void nothingIsBornOutOfNothing() {
        String[][] gameMap = createEmptyMap(3,3);

        GameOfLife gameOfLife = new GameOfLife();
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        Assertions.assertThat(nextGeneration).isEqualTo(gameMap);
    }

    @Test
    void nothingIsBornOutOfNothingForAnySizeMap() {
        String[][] gameMap  = createEmptyMap(4,4);

        GameOfLife gameOfLife = new GameOfLife();
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        Assertions.assertThat(nextGeneration).isEqualTo(gameMap);
    }

    @Test
    void lonelyCellDies() {
        //Given
        String[][] gameMap = createEmptyMap(3,3);
        gameMap[1][1]="X";

        GameOfLife gameOfLife = new GameOfLife();

        //WHEN
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        //THEN
        var emptyMap = createEmptyMap(3,3);
        Assertions.assertThat(nextGeneration).isEqualTo(emptyMap);

    }

    private static String[][] createEmptyMap(final int rows, final int columns) {
        var emptyMap = new String[rows][columns];
        for (int y = 0; y< rows; y++) {
            for (int x = 0; x < columns; x++) {
                emptyMap[x][y]= DEADCELL;
            }
        }
        return emptyMap;
    }
}