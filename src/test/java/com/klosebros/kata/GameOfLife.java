package com.klosebros.kata;

public class GameOfLife {
    public boolean[][] nextGeneration(boolean[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] newGrid = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int liveNeighbors = countLiveNeighbors(grid, r, c);

                if (grid[r][c]) { // Zelle ist lebendig
                    newGrid[r][c] = (liveNeighbors == 2 || liveNeighbors == 3);
                } else { // Zelle ist tot
                    newGrid[r][c] = (liveNeighbors == 3);
                }
            }
        }

        return newGrid;
    }

    private int countLiveNeighbors(boolean[][] grid, int row, int col) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r == row && c == col) continue; // Überspringe die Zelle selbst
                if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c]) {
                    count++;
                }
            }
        }

        return count;
    }
}
