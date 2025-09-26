package com.klosebros.kata;

public class ToroidalGameOfLife extends GameOfLife {

    public ToroidalGameOfLife(boolean[][] initialGrid) {
        super(initialGrid);
    }

    @Override
    public GameOfLife nextGeneration() {
        boolean[][] currentGrid = getGrid();
        int rows = currentGrid.length;
        int cols = currentGrid[0].length;
        boolean[][] nextGrid = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighborsToroidal(currentGrid, row, col);
                boolean currentCell = currentGrid[row][col];

                if (currentCell) {
                    // Lebende Zelle
                    nextGrid[row][col] = (liveNeighbors == 2 || liveNeighbors == 3);
                } else {
                    // Tote Zelle
                    nextGrid[row][col] = (liveNeighbors == 3);
                }
            }
        }

        return new ToroidalGameOfLife(nextGrid);
    }

    private int countLiveNeighborsToroidal(boolean[][] grid, int row, int col) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                if (deltaRow == 0 && deltaCol == 0) {
                    continue; // Skip die aktuelle Zelle
                }

                // Wrap-around Logik für toroidale Topologie
                int neighborRow = (row + deltaRow + rows) % rows;
                int neighborCol = (col + deltaCol + cols) % cols;

                if (grid[neighborRow][neighborCol]) {
                    count++;
                }
            }
        }

        return count;
    }
}
