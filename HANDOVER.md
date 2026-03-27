# 🥋 Strategy Pattern Kata – Handover

## ✅ Aktueller Stand: Phase 1 & Phase 2 abgeschlossen

---

## Was wurde implementiert

### Modell (unveränderlich – Java Records & Enums)

| Datei | Typ | Inhalt |
|---|---|---|
| `Order.java` | Record | `basePrice`, `weightKg`, `customerType`, `destination`, `shippingType`, `orderDate` |
| `CustomerType.java` | Enum | `REGULAR`, `PREMIUM`, `VIP` |
| `Destination.java` | Enum | `LOCAL`, `EU`, `INTERNATIONAL` |
| `ShippingType.java` | Enum | `STANDARD`, `EXPRESS`, `DRONE`, `PICKUP` |

---

### Strategy-Interfaces

| Interface | Methode |
|---|---|
| `DiscountStrategy` | `double calculate(Order order)` |
| `ShippingStrategy` | `double calculate(Order order)` |
| `TaxStrategy` | `double calculate(Order order, double netPrice)` |

> **Hinweis:** `TaxStrategy` erhält zusätzlich `netPrice` (= `basePrice - discount`), da z.B. `LocalTaxStrategy` auf dem Nettopreis, `LuxuryTaxStrategy` aber auf dem `basePrice` rechnet.

---

### Konkrete Strategies

**Discount**
| Klasse | Regel |
|---|---|
| `BlackFridayDiscountStrategy` | 20% – gilt im November; besitzt `appliesTo(Order)` |
| `BulkDiscountStrategy` | 15% – gilt für `REGULAR` + `basePrice > 200€`; besitzt `appliesTo(Order)` |
| `CustomerTypeDiscountStrategy` | VIP 10% / PREMIUM 5% / REGULAR 0% |

**Shipping**
| Klasse | Regel |
|---|---|
| `StandardShippingStrategy` | 5€ + 1€/kg |
| `ExpressShippingStrategy` | 10€ + 2€/kg |
| `PickupShippingStrategy` | 0€ |
| `DroneShippingStrategy` | 20€ + 5€/kg, max 2kg – wirft `IllegalArgumentException` bei Überschreitung |

**Tax**
| Klasse | Regel |
|---|---|
| `LocalTaxStrategy` | 19% auf `netPrice` |
| `EuTaxStrategy` | 10% auf `netPrice` |
| `InternationalTaxStrategy` | 0% |
| `LuxuryTaxStrategy` | +5% auf `basePrice` wenn `basePrice > 500€` |

---

### PricingEngine

Die `PricingEngine` enthält **keine Berechnungslogik** – sie delegiert ausschließlich:

```java
public double calculatePrice(Order order) {
    double discount     = resolveDiscountStrategy(order).calculate(order);
    double shippingCost = resolveShippingStrategy(order).calculate(order);
    double netPrice     = order.basePrice() - discount;
    double tax          = resolveTaxStrategy(order).calculate(order, netPrice)
                        + luxuryTax.calculate(order, netPrice);
    return netPrice + shippingCost + tax;
}
```

**Rabatt-Priorität** (in `resolveDiscountStrategy`):
```
BlackFriday (Nov.) → 20%
   ↓ sonst
VIP → 10%  |  PREMIUM → 5%
   ↓ sonst (nur REGULAR)
BulkDiscount (>200€) → 15%  |  0%
```

---

### Tests (`PricingEngineTest`) – 18 Tests, alle GREEN ✅

| Nested-Klasse | Anzahl Tests |
|---|---|
| `DiscountCalculation` | 7 |
| `ShippingCostCalculation` | 5 |
| `TaxCalculation` | 5 |
| `CombinedScenarios` | 1 (README-Beispiel: 324,50€) |

---

## ⚠️ Bekannte Design-Entscheidungen & offene Punkte

1. **Rabatt-Priorität Phase 1/2:** Aktuell gilt genau **ein** Rabatt pro Bestellung (höchste Priorität gewinnt). Phase 4 soll dieses Verhalten aufbrechen.

2. **`PricingEngine` instanziiert Strategies direkt** (kein Dependency Injection). Sobald neue Strategies ergänzt werden, muss die `PricingEngine` angefasst werden → das verletzt das Open/Closed-Prinzip. Das README sagt explizit: *"designed so, dass neue Regeln ohne Änderung bestehender Logik ergänzt werden können"* → hier liegt noch Refactoring-Bedarf.

3. **Keine Unit-Tests für die einzelnen Strategy-Klassen** – das README fordert in Phase 2 explizit: *"Denkt daran, auch die neu angelegten Klassen zu testen"*. Das steht noch aus.

---

## 🔜 Nächste Schritte

### Phase 2 – offen
- [ ] Unit-Tests für alle Strategy-Klassen schreiben

### Phase 3 – Erweiterung (ohne Änderung bestehender Klassen)
- [ ] `HappyHourDiscountStrategy`: 8% Rabatt zwischen 18–20 Uhr
- [ ] `ChristmasShippingStrategy`: +3€ Versand im Dezember
- [ ] `ClimateTaxStrategy`: +2€ bei `EXPRESS`

### Phase 4 – Kombinierte Discount-Strategien
- [ ] Mehrere Rabatte gleichzeitig anwenden (z.B. VIP + BlackFriday, Bulk + HappyHour)
- [ ] Design-Entscheidung treffen: **additiv**, **max** oder **gedeckelt**?

