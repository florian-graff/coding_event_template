package com.klosebros.kata;

/**
 * 🐱📦 Schrödinger's Game of Life - Cell States! 📦🐱
 *
 * In Conway's Game of Life, jede Zelle kann nur in zwei Zuständen existieren:
 * - ALIVE: Die Katze ist lebendig! 😸✨
 * - DEAD: Die Katze ist... nun ja... 💀😿
 *
 * Aber genau wie Schrödinger's berühmte Katze, die gleichzeitig tot UND lebendig war,
 * bis jemand in die Kiste schaute, existieren unsere Zellen in einem Quantenzustand
 * der Superposition - bis wir die nächste Generation berechnen! 🔬⚛️
 */
public enum CellState {

    /**
     * 💀😿 Die Katze ist tot!
     * Quantum state collapsed: |dead⟩
     * Auch bekannt als "die Kiste ist leer" oder "der Geigerzähler hat geklickt" ☢️
     * Darstellung: "." (ein Punkt für die Träne der Trauer) 😢
     */
    DEAD(".") {
        @Override
        public String toString() {
            return "💀 Dead Cat State 😿"; // RIP Kitty
        }

        @Override
        public boolean isQuantumSuperposition() {
            return false; // Definitiv tot, keine Unschärfe mehr 📐
        }
    },

    /**
     * 😸✨ Die Katze lebt!
     * Quantum state collapsed: |alive⟩
     * Auch bekannt als "die Kiste schnurrt" oder "der Geigerzähler schweigt" 🔇
     * Darstellung: "0" (ein rundes Kätzchen zusammengerollt) 🐱
     */
    ALIVE("0") {
        @Override
        public String toString() {
            return "😸 Living Cat State ✨"; // Purr purr!
        }

        @Override
        public boolean isQuantumSuperposition() {
            return false; // Definitiv lebendig, Wellenfunktion kollabiert 🌊➡️📍
        }
    };

    // 🎭 Die theatralische String-Darstellung für das Conway'sche Drama
    private final String representation;

    /**
     * 🏗️ Konstruktor für unsere Katzen-Zustände
     * @param representation Die visuelle Darstellung im Universum des Game of Life 🌌
     */
    CellState(String representation) {
        this.representation = representation;
    }

    /**
     * 🎨 Gibt die String-Darstellung zurück (für die Außenwelt sichtbar)
     * @return "." für tot 💀 oder "0" für lebendig 😸
     */
    public String getRepresentation() {
        return representation;
    }

    /**
     * 🔄 Schrödinger's Zustandsumkehr!
     * Tot wird lebendig, lebendig wird tot - die Quantenmechanik des Lebens! ⚛️🔄
     * @return Der entgegengesetzte Zustand (Katze springt zwischen den Welten!)
     */
    public CellState flip() {
        return this == ALIVE ? DEAD : ALIVE; // 🎲 Quantensprung!
    }

    /**
     * 🤔 Philosophische Frage: Ist die Katze in Superposition?
     * Spoiler Alert: In unserem deterministischen Game of Life nicht! 🚫📦
     * @return false, weil wir bereits in die Kiste geschaut haben 👁️
     */
    public abstract boolean isQuantumSuperposition();

    /**
     * 🕵️ Schrödinger's Detektiv-Methode: Finde den Zustand basierend auf String!
     * @param representation Die String-Darstellung ("." oder "0")
     * @return Den entsprechenden CellState oder DEAD als Fallback (Safety first! 🦺)
     */
    public static CellState fromString(String representation) {
        // 🔍 Durchsuche alle Zustände wie ein Quantendetektor
        for (CellState state : values()) {
            if (state.representation.equals(representation)) {
                return state; // 🎯 Treffer! Wellenfunktion kollabiert zum gewünschten Zustand
            }
        }
        // 🤷‍♂️ Unbekannter Zustand? Die Katze ist wohl durch die Wand getunnelt...
        // Default: DEAD (pessimistische Quantenmechanik) 😿
        return DEAD;
    }

    /**
     * 🎭 Überschreibt die Standard-toString für mehr Dramatik!
     * Jeder Zustand erzählt seine eigene Geschichte 📖✨
     */
    @Override
    public abstract String toString();
}
