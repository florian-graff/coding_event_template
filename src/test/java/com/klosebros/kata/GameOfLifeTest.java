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


    /*
         Jede lebende Zelle mit mehr als drei lebenden Nachbarn stirbt (Überbevölkerung).
          */
    @Test
    void testOverpopulation() {
        GameOfLife game = new GameOfLife();
        boolean[][] board = {
                {true, true, true},
                {true, true, true},
                {true, true, true}
        };
        boolean[][] expected = {
                {true, false, true},
                {false, false, false},
                {true, false, true}
        };
        boolean[][] result = game.nextGeneration(board);
        assert java.util.Arrays.deepEquals(expected, result);
    }

    /*
        Jede lebende Zelle mit zwei oder drei lebenden Nachbarn lebt in der nächsten Generation weiter.
         */
    @Test
    void testSurvivalTwoOrThreeNeighbors() {
        GameOfLife game = new GameOfLife();
        // 2x2 Block ist stabil: jede lebende Zelle hat 2 oder 3 Nachbarn und bleibt lebendig
        boolean[][] board = {
                {true, true, false},
                {true, true, false},
                {false, false, false}
        };
        boolean[][] expected = {
                {true, true, false},
                {true, true, false},
                {false, false, false}
        };
        boolean[][] result = game.nextGeneration(board);
        assert java.util.Arrays.deepEquals(expected, result);
    }

    /*
        Jede tote Zelle mit genau drei lebenden Nachbarn wird zu einer lebenden Zelle.
         */
    @Test
    void testReproductionThreeNeighbors() {
        GameOfLife game = new GameOfLife();
        // die mittlere Zelle hat genau drei Nachbarn und sollte lebendig werden
        boolean[][] board = {
                {true, true, false},
                {true, false, false},
                {false, false, false}
        };
        boolean[][] expected = {
                {true, true, false},
                {true, true, false},
                {false, false, false}
        };
        boolean[][] result = game.nextGeneration(board);
        assert java.util.Arrays.deepEquals(expected, result);
    }

    /* Toroidaler (wrap-around) Test: Eine tote Zelle an (0,0) hat genau drei lebende Nachbarn über die Ränder */
    @Test
    void testToroidalReproductionThreeNeighbors() {
        GameOfLife game = new GameOfLife(true);
        // In diesem 3x3-Board sind die lebenden Zellen so platziert, dass (0,0) durch Wrap-Around
        // drei Nachbarn hat: (0,2), (2,0), (2,2)
        boolean[][] board = {
                {false, false, true},
                {false, false, false},
                {true, false, true}
        };
        // Erwartung: nach einer Generation sind alle Zellen lebendig (siehe manuelle Berechnung)
        boolean[][] expected = {
                {true, true, true},
                {true, true, true},
                {true, true, true}
        };
        boolean[][] result = game.nextGeneration(board);
        assert java.util.Arrays.deepEquals(expected, result);
    }

}
