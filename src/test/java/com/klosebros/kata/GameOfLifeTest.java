package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class GameOfLifeTest {


    // Test unterbevölkerung
    @Test
    void testUnderpopulation() {
        GameOfLife game = new GameOfLife(3, 3);
        game.setCell(1, 1, "0"); // Lebende Zelle
        String[][] nextGen = game.nextGeneration();
        // Überprüfen, dass alle Zellen tot sind
        forEveryCell((i, j) -> assertThat(nextGen[i][j]).isEqualTo("."));
    }

    // Test überbevölkerung

    @Test
    void testOverpopulation() {
        GameOfLife game = new GameOfLife(3, 3);
//        +---+---+---+
//        | . | 0 | . |
//        +---+---+---+
//        | 0 | 0 | 0 |
//        +---+---+---+
//        | . | 0 | . |
//        +---+---+---+
        game.setCell(1, 1, "0"); // Lebende Zelle
        game.setCell(0, 1, "0"); // Nachbar
        game.setCell(1, 0, "0"); // Nachbar
        game.setCell(1, 2, "0"); // Nachbar
        game.setCell(2, 1, "0"); // Nachbar
        String[][] nextGen = game.nextGeneration();

        // Darstellung nächste Generation
        // +---+---+---+
        // | 0 | 0 | 0 |
        // +---+---+---+
        // | 0 | . | 0 |
        // +---+---+---+
        // | 0 | 0 | 0 |
        // +---+---+---+

        // Überprüfen, dass die mittlere Zelle tot ist
        assertThat(nextGen[1][1]).isEqualTo(".");
// Überprüfen, dass die Zellen mit genau 3 Nachbarn leben
        assertThat(nextGen[0][1]).isEqualTo("0");
        assertThat(nextGen[1][0]).isEqualTo("0");
        assertThat(nextGen[1][2]).isEqualTo("0");
        assertThat(nextGen[2][1]).isEqualTo("0");
// Die Ecken haben jeweils 3 Nachbarn und werden lebendig
        assertThat(nextGen[0][0]).isEqualTo("0");
        assertThat(nextGen[0][2]).isEqualTo("0");
        assertThat(nextGen[2][0]).isEqualTo("0");
        assertThat(nextGen[2][2]).isEqualTo("0");
    }

    private static void forEveryCell(BiConsumer<Integer, Integer> callback) {
        IntStream.range(0, 3).forEach(i -> IntStream.range(0, 3).forEach(j -> callback.accept(i, j)));
    }
}
