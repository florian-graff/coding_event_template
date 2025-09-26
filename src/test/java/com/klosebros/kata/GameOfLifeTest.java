package com.klosebros.kata;

import org.junit.jupiter.api.Test;

// Tests for Conways Game of Life
// Using JUnit 5
// https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
// Using Test Driven Development (TDD)
// https://en.wikipedia.org/wiki/Test-driven_development
// Write tests first, then write the code to make the tests pass
// The code should be clean and simple
public class GameOfLifeTest {

    /*
    Jede lebende Zelle mit weniger als zwei lebenden Nachbarn stirbt, wie bei Unterbevölkerung.
     */
    @Test
    void testUnderpopulation() {
        GameOfLife game = new GameOfLife();
        boolean[][] board = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };
        boolean[][] expected = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
        boolean[][] result = game.nextGeneration(board);
        assert java.util.Arrays.deepEquals(expected, result);

    }

}
