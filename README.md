# 🥋 Strategy Pattern kata

## 🎯 Ziel des Katas
In diesem Kata entwickelt ihr eine Preisberechnungs-Engine für einen Online-Shop.  
Der Fokus liegt auf dem **Strategy Design Pattern**.

---

## 🧠 Domäne

Ein Shop berechnet den **Endpreis** einer Bestellung aus:

1. Grundpreis (basePrice)
2. Rabatt (Discount)
3. Versandkosten (Shipping)
4. Steuer (Tax)

Formel:
finalPrice = basePrice - discount + shippingCost + tax


---

## 🧩 Fachliche Daten

Eine Bestellung (`Order`) besitzt:

- `basePrice` (double)
- `weightKg` (double)
- `customerType` (REGULAR, PREMIUM, VIP)
- `destination` (LOCAL, EU, INTERNATIONAL)
- `shippingType` (STANDARD, EXPRESS, DRONE, PICKUP)
- `orderDate`

---

## 💸 Rabattregeln

| Typ            | Regel |
|----------------|------|
| Regular        | kein Rabatt |
| Premium        | 5% Rabatt |
| VIP            | 10% Rabatt |
| BlackFriday    | 20% Rabatt im November |
| BulkDiscount   | 15% Rabatt bei basePrice > 200€ |

---

## 🚚 Versandregeln

| Typ        | Regel |
|------------|------|
| Standard   | 5€ + 1€/kg |
| Express    | 10€ + 2€/kg |
| Pickup     | 0€ |
| Drone      | 20€ + 5€/kg (max 2kg, sonst Exception) |

---

## 🧾 Steuerregeln

| Ziel          | Regel |
|---------------|------|
| LOCAL         | 19% MwSt |
| EU            | 10% |
| INTERNATIONAL | 0% |

Zusatzregel:
- **LuxuryTax**: +5% wenn basePrice > 500€

---

## 🧪 Beispiel-Szenario

VIP-Kunde  
BasePrice: 300€  
Gewicht: 1.5kg  
Destination: EU  
Shipping: Drone

Rabatt = 30€
Versand = 20 + 7.5 = 27.5  
Steuer = 10% von (300 - 30) = 27

➡️ Finalpreis = 300 - 30 + 27.5 + 27 = 324.50 €


---

## 🕐 Aufgaben

### 🥇 Phase 1 – Naive Lösung
- Implementiert eine Preisberechnung erstmal naiv in einer einzigen Klasse mit `if/else` oder `switch`
- Implementiert die Aufgabenstellung strikt testgetrieben (TDD, red-green-refactor)
- Sorgt für eine gute Testabdeckung 

---

### 🥈 Phase 2 – Refactoring zu Strategy
- Lagert Rabatt-, Versand- und Steuerlogik in Strategien aus
- Die `PricingEngine` darf keine Business-Logik mehr enthalten, sondern nur delegieren.
- implementiert **ohne** `if/else/switch`-Logik im Core 
- designed so, dass neue Regeln ohne Änderung bestehender Logik ergänzt werden können
- Denkt daran, auch die neu angelegten Klassen zu testen

---

### 🥉 Phase 3 – Erweiterung
Neue Regeln:

- **HappyHourDiscount**: 8% Rabatt zwischen 18–20 Uhr
- **ChristmasShipping**: +3€ Versand im Dezember
- **ClimateTax**: +2€ bei Versandart EXPRESS

➡️ Diese Regeln müssen ohne Änderung bestehender Strategien ergänzt werden können

---

### 🏆 Phase 4 – Kombinierte Strategien
Mehrere Rabatte können gleichzeitig gelten:

Beispiele:
- VIP + BlackFriday
- Bulk + HappyHour

Designentscheidung:
- additiv?
- max?
- gedeckelt?

Implementiert eine Lösung für kombinierte Discount-Strategien.
