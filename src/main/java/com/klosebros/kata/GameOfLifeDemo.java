package com.klosebros.kata;

public class GameOfLifeDemo {

    public static void main(String[] args) {
        System.out.println("=== Conway's Game of Life Demo ===\n");

        // Demo 1: Blinker Pattern
        System.out.println("Demo 1: Blinker Pattern (oszilliert zwischen horizontal und vertikal)");
        runBlinkerDemo();

        // Demo 2: Block Pattern
        System.out.println("\nDemo 2: Block Pattern (stabil)");
        runBlockDemo();

        // Demo 3: Glider Pattern
        System.out.println("\nDemo 3: Glider Pattern (bewegt sich diagonal)");
        runGliderDemo();
    }

    private static void runBlinkerDemo() {
        boolean[][] blinker = {
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, true, true, true, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
        };

        GameOfLife game = new GameOfLife(blinker);
        animatePattern(game, 4, 800);
    }

    private static void runBlockDemo() {
        boolean[][] block = {
            {false, false, false, false},
            {false, true, true, false},
            {false, true, true, false},
            {false, false, false, false}
        };

        GameOfLife game = new GameOfLife(block);
        animatePattern(game, 3, 1000);
    }

    private static void runGliderDemo() {
        boolean[][] glider = {
            {false, false, false, false, false, false, false},
            {false, false, true, false, false, false, false},
            {false, false, false, true, false, false, false},
            {false, true, true, true, false, false, false},
            {false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false}
        };

        GameOfLife game = new GameOfLife(glider);
        animatePattern(game, 6, 1000);
    }

    private static void animatePattern(GameOfLife game, int generations, int delayMs) {
        GameOfLife currentGame = game;

        for (int gen = 0; gen <= generations; gen++) {
            clearScreen();
            System.out.println("Generation " + gen + ":");
            currentGame.printGrid();

            if (gen < generations) {
                try {
                    Thread.sleep(delayMs);
                    currentGame = currentGame.nextGeneration();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        System.out.println("Drücken Sie Enter für die nächste Demo...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignoriere Eingabefehler
        }
    }

    private static void clearScreen() {
        // Für Windows/Unix Konsolen - drucke mehrere leere Zeilen
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
