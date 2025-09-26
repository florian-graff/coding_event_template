package com.klosebros.kata;

public class GameOfLife {
    private final boolean[][] grid;
    private final int rows;
    private final int cols;

    public GameOfLife(boolean[][] initialGrid) {
        this.rows = initialGrid.length;
        this.cols = initialGrid[0].length;
        this.grid = new boolean[rows][cols];

        // Deep copy des initial grids
        for (int i = 0; i < rows; i++) {
            System.arraycopy(initialGrid[i], 0, this.grid[i], 0, cols);
        }
    }

    public boolean[][] getGrid() {
        boolean[][] copy = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(this.grid[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    public GameOfLife nextGeneration() {
        boolean[][] nextGrid = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);
                boolean currentCell = grid[row][col];

                if (currentCell) {
                    // Lebende Zelle
                    nextGrid[row][col] = (liveNeighbors == 2 || liveNeighbors == 3);
                } else {
                    // Tote Zelle
                    nextGrid[row][col] = (liveNeighbors == 3);
                }
            }
        }

        return new GameOfLife(nextGrid);
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;

        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                if (deltaRow == 0 && deltaCol == 0) {
                    continue; // Skip die aktuelle Zelle
                }

                int neighborRow = row + deltaRow;
                int neighborCol = col + deltaCol;

                if (isValidPosition(neighborRow, neighborCol) &&
                    grid[neighborRow][neighborCol]) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void printGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(grid[row][col] ? "█" : "·");
            }
            System.out.println();
        }
        System.out.println();
    }
}
