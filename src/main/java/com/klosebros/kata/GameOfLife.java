package com.klosebros.kata;

/**
 * Conway's Game of Life implementation operating on a 2D boolean board.
 *
 * <p>Cells are represented as booleans: {@code true} = alive, {@code false} = dead.
 * The board is a rectangular {@code boolean[][]} where {@code board[row][col]} is the cell
 * at the given row and column. The next generation is computed according to the standard
 * Game of Life rules.
 */
public class GameOfLife {

    /**
     * Wenn true, wird ein toroidales (wrap-around) Grid verwendet.
     */
    private final boolean toroidal;

    /**
     * Default constructor: nicht-toroidal (das bisherige Verhalten).
     */
    public GameOfLife() {
        this(false);
    }

    /**
     * Konstruktor, um toroidales Verhalten zu aktivieren.
     *
     * @param toroidal true für Wrap-Around, false für Standard (Ränder sind tot)
     */
    public GameOfLife(boolean toroidal) {
        this.toroidal = toroidal;
    }

    /**
     * Computes the next generation for a given board.
     *
     * @param board the current board state; may be {@code null} or empty
     * @return a new {@code boolean[][]} containing the next generation; never {@code null}
     */
    public boolean[][] nextGeneration(boolean[][] board) {
        if (board == null || board.length == 0) {
            return new boolean[0][];
        }

        int rowCount = board.length;
        int colCount = board[0].length;
        boolean[][] nextGenerationBoard = new boolean[rowCount][colCount];

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                int aliveNeighbors = countAliveNeighbors(board, row, col, rowCount, colCount);
                if (board[row][col]) {
                    nextGenerationBoard[row][col] = (aliveNeighbors == 2 || aliveNeighbors == 3);
                } else {
                    nextGenerationBoard[row][col] = (aliveNeighbors == 3);
                }
            }
        }
        return nextGenerationBoard;
    }

    /**
     * Counts alive neighbors for the cell at {@code (row, col)}.
     *
     * <p>Neighbors are the up to eight surrounding cells. Cells outside the board bounds
     * are considered dead (unless toroidal==true, then wrap-around is used). If {@code (row, col)} is out of bounds, 0 is returned.
     */
    private int countAliveNeighbors(boolean[][] board, int row, int col, int rows, int cols) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return 0;
        }

        int aliveNeighbors = 0;
        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                if (deltaRow == 0 && deltaCol == 0) {
                    continue;
                }
                int neighborRow = row + deltaRow;
                int neighborCol = col + deltaCol;
                if (toroidal) {
                    // Wrap-around mit Modulo (positiv halten, daher +rows/cols vor %)
                    neighborRow = (neighborRow + rows) % rows;
                    neighborCol = (neighborCol + cols) % cols;
                    if (board[neighborRow][neighborCol]) {
                        aliveNeighbors++;
                    }
                } else {
                    if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols && board[neighborRow][neighborCol]) {
                        aliveNeighbors++;
                    }
                }
            }
        }
        return aliveNeighbors;
    }
}