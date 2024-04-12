package com.klosebros.kata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameOfLifeTest {

    @Test
    void nothingIsBornOutOfNothing() {
        String[][] gameMap = {
                {"0", "0", "0"},
                {"0", "0", "0"},
                {"0", "0", "0"}
        };

        GameOfLife gameOfLife = new GameOfLife();
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        Assertions.assertThat(nextGeneration).isEqualTo(gameMap);
    }

    @Test
    void nothingIsBornOutOfNothingForAnySizeMap() {
        String[][] gameMap = {
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"}
        };

        GameOfLife gameOfLife = new GameOfLife();
        String[][] nextGeneration = gameOfLife.nextGeneration(gameMap);

        Assertions.assertThat(nextGeneration).isEqualTo(gameMap);
    }
}