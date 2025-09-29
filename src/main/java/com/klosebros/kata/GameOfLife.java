package com.klosebros.kata;

/**
 * 🎮🐱 Conway's Game of Life meets Schrödinger's Cat! 📦⚛️
 *
 * Ein zellulärer Automat, in dem Katzen geboren werden, leben und sterben -
 * genau wie in der Quantenmechanik, nur deterministischer! 😸🔬
 */
public class GameOfLife {
    private final int rows;
    private final int cols;
    private CellState[][] grid; // 📦 Eine Matrix voller Schrödinger-Boxen!

    /**
     * 🏗️ Erschafft ein neues Universum für unsere Katzen-Simulation!
     * @param rows Anzahl Zeilen (Stockwerke im Katzen-Hotel) 🏨
     * @param cols Anzahl Spalten (Zimmer pro Stockwerk) 🚪
     */
    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new CellState[rows][cols]; // 📦📦📦 Viele Boxen für viele Katzen!

        // 💀 Standard: Alle Katzen sind zunächst tot (pessimistische Quantenmechanik)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = CellState.DEAD; // 😿 RIP kitties (vorerst)
            }
        }
    }

    /**
     * 🎯 Setzt den Zustand einer spezifischen Katze in ihrer Box!
     * @param row Zeilen-Koordinate der Katzen-Box 📍
     * @param col Spalten-Koordinate der Katzen-Box 📍
     * @param value String-Darstellung des gewünschten Zustands ("0" = lebendig, alles andere = tot)
     * @throws IndexOutOfBoundsException wenn die Koordinaten außerhalb des Katzen-Universums liegen 🚫🌌
     */
    public void setCell(int row, int col, String value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("🚨 Zellenposition außerhalb des Katzen-Universums! 🌌❌");
        }

        // 🔍 Schrödinger's Detektiv-Arbeit: String in CellState umwandeln
        grid[row][col] = CellState.fromString(value);
        // 📝 Alte Logik: ALIVE.equals(value) ? ALIVE : DEAD;
        // 🆕 Neue Logik: Lass das Enum entscheiden! Viel eleganter! ✨
    }

    /**
     * ⏭️🎬 Berechnet die nächste Generation im Katzen-Drama!
     * Conway's Regeln + Schrödinger's Philosophie = Quantenchaos! ⚛️🎭
     * @return 2D String-Array der nächsten Generation (für die Außenwelt sichtbar)
     */
    public String[][] nextGeneration() {
        CellState[][] next = new CellState[rows][cols]; // 🆕 Neue Katzen-Matrix!

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(i, j); // 👥 Zähle lebende Katzen-Nachbarn
                boolean currentlyLive = grid[i][j] == CellState.ALIVE; // 😸 Ist die aktuelle Katze am Leben?

                if (currentlyLive) {
                    // 😸➡️😿 Lebende Katze: stirbt bei Einsamkeit (<2) oder Überbevölkerung (>3)
                    // 😸➡️😸 Lebende Katze: überlebt bei perfekter Gesellschaft (2 oder 3)
                    next[i][j] = (liveNeighbors < 2 || liveNeighbors > 3) ? CellState.DEAD : CellState.ALIVE;
                } else {
                    // 😿➡️😸 Tote Katze: Wiedergeburt bei genau 3 Nachbarn (Katzen-Magie!) ✨
                    // 😿➡️😿 Tote Katze: bleibt tot bei allen anderen Nachbarzahlen
                    next[i][j] = (liveNeighbors == 3) ? CellState.ALIVE : CellState.DEAD;
                }
            }
        }

        // 🔄 Zeitreise: Aktuelle Generation wird zur Vergangenheit
        this.grid = next;

        // 🎭 Konvertierung für die String-basierte Außenwelt
        return convertToStringArray(next);
    }

    /**
     * 🔢 Zählt die lebenden Nachbarn einer Katze (Moore-Nachbarschaft)
     * Jede Katze hat bis zu 8 Nachbarn - wie in einem Katzen-Apartmentcomplex! 🏢😸
     *
     * @param row Zeilen-Index der zu prüfenden Katzen-Box 📍
     * @param col Spalten-Index der zu prüfenden Katzen-Box 📍
     * @return Anzahl lebender Nachbar-Katzen (0-8) 🔢
     */
    private int countLiveNeighbors(int row, int col) {
        int count = 0;

        // 🔍 Durchsuche alle 8 Nachbar-Positionen (3x3 Gitter minus Zentrum)
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // 🚫 Ignoriere die Katze selbst (Narzissmus verboten!)

                int r = row + dr;
                int c = col + dc;

                // 🌌 Grenze des Universums: Prüfe ob Nachbar existiert
                if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] == CellState.ALIVE) {
                    count++; // 😸 Eine weitere lebende Nachbar-Katze gefunden!
                }
            }
        }
        return count; // 📊 Endergebnis des Katzen-Zensus
    }

    /**
     * 🔄 Hilfsmethode: Konvertiert CellState-Matrix zu String-Matrix
     * Brücke zwischen unserer schönen Enum-Welt und der String-basierten Außenwelt 🌉
     * @param cellStates Die interne CellState-Matrix 📦📦📦
     * @return String-Matrix für externe APIs 📝
     */
    private String[][] convertToStringArray(CellState[][] cellStates) {
        String[][] result = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = cellStates[i][j].getRepresentation(); // ✨ Enum-Magie!
            }
        }

        return result; // 🎁 Geschenk für die Außenwelt verpackt!
    }
}
