# Copilot Instructions

## Project Overview
This is a **Java Code Kata** implementing a pricing engine for an online shop using the **Strategy Design Pattern**. The kata progresses through phases: naive implementation → strategy refactoring → extension → combined strategies. The domain involves calculating a final price from base price, discounts, shipping costs, and taxes.

## Goals & Objectives
- Practice **Test-Driven Development (TDD)** with the red-green-refactor cycle.
- Apply the **Strategy Pattern** to separate discount, shipping, and tax logic.
- Design for the **Open/Closed Principle** — new rules must be addable without modifying existing code.
- Achieve high test coverage via JaCoCo.

## Tech Stack
- **Language:** Java 21
- **Build Tool:** Maven (with Maven Wrapper `mvnw`)
- **Testing:** JUnit 5 (`junit-jupiter`), AssertJ, Mockito
- **Libraries:** Vavr (functional programming constructs)
- **Code Coverage:** JaCoCo

## Architecture
- Follow the **Strategy Design Pattern**: define interfaces for discount, shipping, and tax strategies.
- The `PricingEngine` should **delegate** to strategy implementations — it must not contain business logic itself.
- Strategies should be composable (e.g., multiple discounts can apply simultaneously).
- Use **dependency injection** (constructor injection) to supply strategies to the engine.

## Coding Standards
- Write **clean, readable code** — favour clarity over cleverness.
- Keep methods short and focused on a single responsibility.
- Prefer **immutable objects** and final fields where possible.
- Use Java 21 features where appropriate (records, sealed interfaces, pattern matching, switch expressions).
- Avoid raw types; always use proper generics.
- Use `double` for price calculations as defined in the domain (this is a kata, not production financial code).
- Keep in mind clean code principles: meaningful names, small methods, and clear intent.
- All methosds should have a clear contract and be well-documented with Javadoc where necessary.

## Naming Conventions
- **Classes:** `PascalCase` (e.g., `PricingEngine`, `VipDiscount`, `DroneShipping`).
- **Interfaces:** `PascalCase`, named after the concept (e.g., `DiscountStrategy`, `ShippingStrategy`, `TaxStrategy`).
- **Methods:** `camelCase`, use verb phrases (e.g., `calculateDiscount`, `calculateShippingCost`).
- **Constants:** `UPPER_SNAKE_CASE` (e.g., `MAX_DRONE_WEIGHT_KG`).
- **Enums:** `PascalCase` type, `UPPER_SNAKE_CASE` values (e.g., `CustomerType.VIP`).
- **Test classes:** mirror the class under test with a `Test` suffix (e.g., `PricingEngineTest`).
- **Test methods:** use descriptive names with underscores or camelCase explaining the scenario (e.g., `shouldApplyVipDiscount`, `vipCustomer_withDroneShipping_returnsCorrectPrice`).
- **Packages:** all lowercase, dot-separated (e.g., `com.klosebros.kata`).

## File & Folder Structure
```
src/
  main/java/com/klosebros/kata/    # Production code
  test/java/com/klosebros/kata/    # Test code (mirrors main structure)
```
- Place strategy interfaces and the `PricingEngine` in the base package.
- Group strategy implementations in sub-packages if the number of classes grows (e.g., `discount/`, `shipping/`, `tax/`).

## Common Patterns
- **Strategy Pattern:** Core pattern of the kata — extract interchangeable algorithms behind interfaces.
- **Composite Pattern:** Use for combining multiple strategies (e.g., additive discounts).
- **Records:** Use Java records for immutable data carriers like `Order`.
- **Enums:** Use for `CustomerType`, `Destination`, `ShippingType`.
- **Factory or Builder:** Consider for constructing `Order` objects in tests.

## API Guidelines
- Strategy interfaces should define a single method (functional interface).
- Methods should accept an `Order` object and return a `double` result.
- Throw `IllegalArgumentException` for invalid inputs (e.g., drone shipping over 2 kg).

## Error Handling
- Use **unchecked exceptions** (`IllegalArgumentException`, `UnsupportedOperationException`) for domain rule violations.
- Fail fast — validate inputs at the boundary.
- Do **not** swallow exceptions silently.
- Include meaningful error messages in exceptions.

## Testing Strategy
- Follow **TDD strictly**: write a failing test first, make it pass, then refactor.
- Use **AssertJ** for fluent, readable assertions (`assertThat(...).isEqualTo(...)`).
- Use **Mockito** for mocking strategy dependencies when unit-testing the `PricingEngine`.
- Write **one assertion per test** where practical; each test should verify one behaviour.
- Cover edge cases: zero price, maximum weight, boundary values for discounts, date-dependent rules.
- Use `@ParameterizedTest` for testing multiple input/output combinations.
- Aim for **high code coverage** — JaCoCo is configured and runs on `mvn test`.

## Performance Considerations
- Not a concern for this kata — focus on correctness and clean design.

## Security Considerations
- Not applicable for this kata.

## Dependencies & Libraries
| Dependency       | Purpose                          | Scope  |
|------------------|----------------------------------|--------|
| JUnit 5          | Test framework                   | test   |
| AssertJ          | Fluent test assertions           | test   |
| Mockito          | Mocking framework                | test   |
| Vavr             | Functional data types & patterns | compile|
| JaCoCo           | Code coverage reporting          | plugin |

- Do **not** add unnecessary dependencies — keep it minimal.

## Environment & Configuration
- **Java version:** 21
- **Build:** `./mvnw clean test` (Unix) or `mvnw.cmd clean test` (Windows)
- No external configuration files or environment variables required.

## CI/CD & Deployment
- Not applicable — this is a local kata exercise.

## Documentation Guidelines
- Add **Javadoc** to public interfaces and strategy contracts.
- Keep inline comments minimal — code should be self-explanatory.
- Update `README.md` if new phases or rules are added.

## Code Coverage
- Aim for **100% code coverage** on the `PricingEngine` and all strategy implementations.
- Use JaCoCo to measure and report coverage.
- Ensure that all business rules are covered by tests.

## Do's
- ✅ Write tests **before** implementation (TDD).
- ✅ Keep the `PricingEngine` free of business logic — delegate to strategies.
- ✅ Use Java 21 features (records, sealed interfaces, pattern matching, switch expressions).
- ✅ Use AssertJ for assertions, Mockito for mocks.
- ✅ Make strategies composable and independently testable.
- ✅ Use Vavr collections and functional types where they simplify the code.
- ✅ Favour composition over inheritance.
- ✅ Keep tests fast, isolated, and deterministic.
- ✅ Use `@DisplayName` or descriptive method names in tests for readability.

## Don'ts
- ❌ Don't put `if/else/switch` business logic inside `PricingEngine` (after Phase 1).
- ❌ Don't modify existing strategies when adding new rules — extend instead.
- ❌ Don't use `System.out.println` for debugging — use tests to verify behaviour.
- ❌ Don't use `float` or `BigDecimal` — the kata uses `double` by convention.
- ❌ Don't add Spring, Lombok, or other heavy frameworks — keep it simple.
- ❌ Don't skip writing tests — every rule must have corresponding test coverage.
- ❌ Don't catch generic `Exception` — be specific.
- ❌ Don't leave dead code or commented-out code in the codebase.
