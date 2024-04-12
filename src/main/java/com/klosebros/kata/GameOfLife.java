package com.klosebros.kata;

import java.util.stream.IntStream;

public class GameOfLife {

    public static final String DEADCELL = "0";

    public String[][] nextGeneration(String[][] gameMap) {
        int rows = gameMap.length;
        int columns = gameMap[0].length;

        var nextGen = new String[rows][columns];
        for (int y = 0; y< rows; y++) {
            for (int x = 0; x < columns; x++) {
                nextGen[x][y]= DEADCELL;
            }
        }

        return nextGen;
    }
}
