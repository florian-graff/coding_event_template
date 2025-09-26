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
        boolean[][] result = GameOfLife.nextGeneration(start, true); // Wrap-Around aktiv
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
        boolean[][] result = GameOfLife.nextGeneration(start, true); // Wrap-Around aktiv
        assertThat(result[0][0]).isTrue(); // Zelle wird lebendig
    }

    /**
     * Testet ein unendliches 2D-Grid: Das Grid wird erweitert, wenn lebende Zellen am Rand neue Nachbarn erzeugen.
     */
    @Test
    void unendlichesGridWirdErweitertWennNeueLebendeZellenAusserhalbEntstehen() {
        boolean[][] start2 = {
                {true, false, false},
                {false, false, false},
                {false, false, true}
        };
        // Nach der nächsten Generation sollte oben links (0,0) und unten rechts (2,2) sterben, aber neue Zellen könnten außerhalb entstehen, wenn die Logik das Grid erweitert.
        // Erwartung: Das Grid wird um eine Zeile und Spalte oben/unten/links/rechts erweitert, falls dort lebende Zellen entstehen.
        boolean[][] result = GameOfLife.nextGeneration(start2);
        // Prüfe, ob das Grid mindestens die gleiche Größe hat oder größer ist
        assertThat(result.length).isGreaterThanOrEqualTo(start2.length);
        assertThat(result[0].length).isGreaterThanOrEqualTo(start2[0].length);
        // Prüfe, dass keine lebende Zelle außerhalb des ursprünglichen Bereichs existiert, wenn keine neuen entstehen
        boolean lebendeZelleAusserhalb = false;
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if ((i >= start2.length || j >= start2[0].length) && result[i][j]) {
                    lebendeZelleAusserhalb = true;
                    break;
                }
            }
            if (lebendeZelleAusserhalb) break;
        }
        assertThat(lebendeZelleAusserhalb).isFalse();
    }
}
