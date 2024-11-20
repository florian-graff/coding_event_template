# Parrot Refactoring kata

Wir starten mit der Klasse Parrot, die ausreichend durch Unit Tests abgedeckt ist.
Ziel ist es die Code Smells zu entfernen (Switch statement auf Typ).
Geht in kleinen Schritten vor, und versucht durch kleine Refactorings den Code Stück für Stück zu verbessern.
Achtet darauf, dass die Tests nach jedem Zwischenschritt wieder komplett grün sind.

Constraints:
- No Mouse (Nur die Tastatur verwenden)
- Pair Programming (Driver + Navigator, wechselt alle 5 bis 10 Minuten die Rollen)

Tips:
- Polymorphismus (Vererbung oder Komposition)
- Factory Methods
- IDE Refactorings nutzen
    - Push Members down
    - create subclass
- oft commiten und in Zwischenschritten immer wieder die Tests ausführen und grün (funktionierend) halten

Hilfreiche IntelliJ Shortcuts:
- Alt + Enter (Context Action)
- F2 - Next highlighted error
- Ctrl + W - Expand Selection
- Alt + 3 - Find menu
