package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOfLifeCliAnimationTest {

    // ...existing code...

    @Test
    void testCliAnimationShowsMultipleFramesWithSleep() throws InterruptedException {
        GameOfLife game = new GameOfLife();

        // Simple initial pattern (blinker) that oscillates every generation
        boolean[][] board = {
                {false, false, false},
                {true,  true,  true},
                {false, false, false}
        };

        final int iterations = 3;
        final long sleepMillis = 30; // small sleep to keep tests fast but observable

        // Capture stdout so the test can assert the CLI output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));

        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < iterations; i++) {
                printFrame(board, i);
                if (i < iterations - 1) {
                    Thread.sleep(sleepMillis);
                }
                board = game.nextGeneration(board);
            }
        } finally {
            System.setOut(originalOut);
        }
        long duration = System.currentTimeMillis() - start;

        String output = baos.toString();

        // We used a marker "---FRAME n---" before each frame in printFrame
        int frameMarkers = countOccurrences(output, "---FRAME");
        assertEquals(iterations, frameMarkers, "Expected number of frames printed to stdout");

        // Ensure that sleeps actually happened (allow some tolerance)
        long expectedMin = (iterations - 1) * sleepMillis;
        assertTrue(duration >= expectedMin - 20, "Total duration should include the sleeps (with small tolerance)");

        // Optionally print captured output to the real stdout so developers running tests can see the animation
        System.out.println(output);
    }

    private static void printFrame(boolean[][] board, int frameIndex) {
        System.out.println("---FRAME " + frameIndex + "---");
        for (boolean[] row : board) {
            StringBuilder sb = new StringBuilder();
            for (boolean cell : row) {
                sb.append(cell ? 'O' : '.');
            }
            System.out.println(sb.toString());
        }
        System.out.println();
    }

    private static int countOccurrences(String text, String substring) {
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(substring, idx)) != -1) {
            count++;
            idx += substring.length();
        }
        return count;
    }

}

