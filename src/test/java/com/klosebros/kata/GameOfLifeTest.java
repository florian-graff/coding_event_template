package com.klosebros.kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameOfLifeTest {
    /**
     * Testet, dass eine lebende Zelle mit weniger als 2 lebenden Nachbarn stirbt (Regel 1).
     */
    @Test
    void lebendeZelleMitWenigerAlsZweiNachbarnStirbt() {
        boolean[][] start = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };
        boolean[][] expected = {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
        boolean[][] result = GameOfLife.nextGeneration(start);
        assertThat(result).isEqualTo(expected);
    }

    /**
     * Testet, dass eine lebende Zelle mit mehr als 3 lebenden Nachbarn stirbt (Regel 2).
     */
    @Test
    void lebendeZelleMitMehrAlsDreiNachbarnStirbt() {
        boolean[][] start = {
                {true, true, true},
                {true, true, true},
                {false, false, false}
        };
        boolean[][] expected = {
                {true, false, true},
                {false, false, false},
                {true, true, true}
        };
        boolean[][] result = GameOfLife.nextGeneration(start);
        assertThat(result[1][1]).isFalse(); // Zentrale Zelle stirbt
    }

    /**
     * Testet, dass eine lebende Zelle mit 2 oder 3 lebenden Nachbarn weiterlebt (Regel 3).
     */
    @Test
    void lebendeZelleMitZweiOderDreiNachbarnLebtWeiter() {
        boolean[][] start = {
                {false, true, false},
                {true, true, false},
                {false, false, false}
        };
        boolean[][] result = GameOfLife.nextGeneration(start);
        assertThat(result[1][1]).isTrue(); // Zentrale Zelle lebt weiter
    }

    /**
     * Testet, dass eine tote Zelle mit genau drei lebenden Nachbarn lebendig wird (Regel 4).
     */
    @Test
    void toteZelleMitDreiNachbarnWirdLebendig() {
        boolean[][] start = {
                {false, true, false},
                {true, false, false},
                {false, true, false}
        };
        boolean[][] result = GameOfLife.nextGeneration(start);
        assertThat(result[1][1]).isTrue(); // Zentrale Zelle wird lebendig
    }

    /**
     * Testet das toroidale Grid: Zelle am Rand zählt Nachbarn auf der gegenüberliegenden Seite.
     */
    @Test
    void wrapAroundLebendeZelleAmRandHatNachbarnAufGegenueberliegenderSeite() {
        boolean[][] start = {
                {false, false, true},
                {false, false, false},
                {true, false, false}
        };
        // Zelle oben rechts (0,2) hat Nachbarn unten rechts (2,2), oben links (0,0) und unten links (2,0) im Wrap-around
        // Wir setzen die Zelle oben rechts lebendig und die Zelle unten links lebendig
        // Nach Regel 1: Weniger als 2 lebende Nachbarn -> stirbt
        boolean[][] result = GameOfLife.nextGeneration(start);
        assertThat(result[0][2]).isFalse(); // Zelle stirbt
    }

    /**
     * Testet das toroidale Grid: Tote Zelle am Rand wird durch Nachbarn auf der gegenüberliegenden Seite lebendig.
     */
    @Test
    void wrapAroundToteZelleAmRandWirdDurchGegenueberliegendeNachbarnLebendig() {
        boolean[][] start = {
                {false, false, true},
                {false, false, false},
                {true, false, true}
        };
        // Zelle oben links (0,0) hat drei lebende Nachbarn: oben rechts (0,2), unten links (2,0), unten rechts (2,2)
        boolean[][] result = GameOfLife.nextGeneration(start);
        assertThat(result[0][0]).isTrue(); // Zelle wird lebendig
    }
}
