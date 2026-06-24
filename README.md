# Automaten-Kata (Vending Machine Kata)

In dieser Übung entwickelt ihr die Logik eines Verkaufsautomaten. Der Automat nimmt Geld an, verwaltet seinen Bestand, gibt Produkte aus und gibt Wechselgeld zurück – genau wie ein echter Snackautomat.

Ziel dieses Katas ist es, an einer etwas größeren Aufgabe Test-Driven Development (TDD) sowie das State Design Pattern zu üben. Ein wesentlicher Teil der Herausforderung besteht darin, zu entscheiden, welche Tests als Nächstes geschrieben werden sollten.

Nehmt euch zu Beginn ruhig etwas Zeit, über sinnvolle States des Verkaufautomaten nachzudenken.

# Constraints
- striktes TDD (Red, Green, Refactor)
- So wenig KI wie möglich verwenden (insbesondere keine one-shot prompts "implementier das kata..." 😉)
---

# Funktionen / Akzeptanzkriterien

## 1. Münzen akzeptieren

### Als Betreiber

möchte ich, dass der Automat Münzen akzeptiert,

### damit

Kunden Produkte kaufen können.

Der Automat akzeptiert nur gültige Euro-Münzen:

* 10 Cent
* 20 Cent
* 50 Cent
* 1 Euro
* 2 Euro

Ungültige Münzen oder Fremdwährungen werden zurückgegeben.

Wenn eine gültige Münze eingeworfen wird, erhöht sich das aktuelle Guthaben und die Anzeige wird aktualisiert.

Wenn kein Geld eingeworfen wurde, zeigt der Automat:

```text
GELD EINWERFEN
```

Ungültige Münzen landen im Münzrückgabefach.

### Hinweis

Ein echter Automat erkennt Münzen nicht anhand ihres Wertes, sondern anhand von Größe und Gewicht. Für diese Kata kann dies durch Strings, Enums oder Konstanten simuliert werden.

---

## 2. Produkt auswählen

### Als Betreiber

möchte ich, dass Kunden Produkte auswählen können,

### damit

sie einen Anreiz haben, Geld einzuwerfen.

Der Automat bietet drei Produkte an:

| Produkt      | Preis  |
| ------------ | ------ |
| Cola         | 2,00 € |
| Chips        | 1,50 € |
| Schokoriegel | 1,80 € |

Wird ein Produkt ausgewählt und genügend Geld eingeworfen, dann:

* wird das Produkt ausgegeben,
* die Anzeige zeigt

```text
VIELEN DANK
```

Beim nächsten Blick auf die Anzeige erscheint wieder:

```text
GELD EINWERFEN
```

und das Guthaben wird auf 0,00 € zurückgesetzt.

Falls nicht genügend Geld eingeworfen wurde, zeigt der Automat:

```text
PREIS: x,xx €
```

Anschließend zeigt die Anzeige wieder entweder:

* das aktuelle Guthaben oder
* „GELD EINWERFEN“.

---

## 3. Wechselgeld zurückgeben

### Als Betreiber

möchte ich, dass Kunden korrektes Wechselgeld erhalten,

### damit

sie den Automaten erneut benutzen.

Wenn mehr Geld eingeworfen wurde als für das gewählte Produkt nötig ist, wird die Differenz automatisch in das Münzrückgabefach gelegt.

Beispiel:

```text
Guthaben: 2,00 €
Produkt: Chips (1,50 €)

=> Rückgeld: 0,50 €
```

---

## 4. Geld zurückgeben

### Als Kunde

möchte ich mein Geld zurückbekommen,

### damit

ich meine Meinung ändern kann.

Wenn die Taste „Geld zurückgeben“ gedrückt wird:

* wird das komplette eingeworfene Geld zurückgegeben,
* das Guthaben wird auf 0,00 € gesetzt,
* die Anzeige zeigt:

```text
GELD EINWERFEN
```

---

## 5. Ausverkauft

### Als Kunde

möchte ich erfahren, wenn ein Produkt nicht mehr verfügbar ist,

### damit

ich ein anderes Produkt auswählen kann.

Ist das gewählte Produkt ausverkauft, zeigt der Automat:

```text
AUSVERKAUFT
```

Beim nächsten Blick auf die Anzeige erscheint wieder:

* das aktuelle Guthaben oder
* „GELD EINWERFEN“, falls kein Guthaben vorhanden ist.

---

## 6. Nur passend bezahlen

### Als Kunde

möchte ich wissen, ob der Automat Wechselgeld herausgeben kann,

### damit

ich schon vor dem Einwerfen meines Geldes weiß, ob ich passend bezahlen muss.

Kann der Automat für keines seiner Produkte korrekt Wechselgeld herausgeben, zeigt die Anzeige:

```text
NUR PASSEND
```

anstelle von

```text
GELD EINWERFEN
```

Der Kunde muss dann den exakten Betrag einwerfen.

---

# Startbestand

## Produkte

| Produkt      |  Preis | Bestand |
| ------------ | -----: | ------: |
| Cola         | 2,00 € |      10 |
| Chips        | 1,50 € |      10 |
| Schokoriegel | 1,80 € |      10 |

## Münzbestand

| Münze   | Anzahl |
| ------- | -----: |
| 10 Cent |     10 |
| 20 Cent |     10 |
| 50 Cent |     10 |
| 1 Euro  |     10 |
| 2 Euro  |     10 |

---

# Erweiterungen (optional)

Falls ihr schneller fertig seid:

### Wartungsmodus

Neue Zustände:

* Außer Betrieb
* Betriebsbereit

Im Wartungsmodus:

* können keine Produkte gekauft werden,
* können Produkte nachgefüllt werden,
* können Münzen nachgefüllt werden.

---

### Historie

Jeder Kauf soll protokolliert werden:

```text
14:32 - Cola verkauft
14:37 - Chips verkauft
14:45 - Schokoriegel verkauft
```

---

### Kontaktlos bezahlen

Zusätzlich zu Münzen kann bezahlt werden mit:

* Girokarte
* Kreditkarte
* Apple Pay
* Google Pay

---

### Rabattaktionen

Beispiele:

* Happy Hour: Chips 20 % günstiger
* Jeder 5. Schokoriegel kostenlos
* Cola + Chips als Menüpreis

---

# Ziel des Katas

Beginnt bewusst mit einer einfachen Lösung.

Wenn immer mehr Zustände und Sonderfälle hinzukommen, wird deutlich, warum das State Pattern eine elegante Lösung für die Zustandslogik eines Automaten darstellt.

