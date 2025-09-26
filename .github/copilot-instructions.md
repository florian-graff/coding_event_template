# GitHub Copilot Instructions

## Project Overview
This is a Java coding kata project focused on breadcrumb generation functionality. The project follows Maven structure and uses modern Java features.

## Code Style Guidelines
- Use modern Java features (records, var keyword, streams, Optional)
- Follow clean code principles with descriptive method names
- Prefer functional programming style where appropriate
- Use proper Java naming conventions (camelCase for variables/methods, PascalCase for classes)

## Project Structure
- Main source code in `src/main/java/com/klosebros/kata/`
- Test code in `src/test/java/com/klosebros/kata/`
- Maven-based project with standard directory layout

## Specific Patterns Used in This Project
- Record classes for data structures (e.g., `Url` record)
- Stream API for data processing and filtering
- Optional for null-safe operations
- Template strings with `String.formatted()`
- Method references and lambda expressions

## Testing
- Use JUnit for unit tests
- Follow test naming conventions: `shouldDoSomething_WhenCondition`
- Test both positive and negative cases
- Focus on edge cases and boundary conditions

## Dependencies
- Java (modern version with records support)
- Maven for build management
- JUnit for testing

## Code Quality
- Keep methods small and focused on single responsibility
- Use descriptive variable names
- Avoid magic numbers and strings - use constants or configuration
- Handle edge cases appropriately
- Write self-documenting code with minimal but meaningful comments

## Kata-Specific Context
This project implements breadcrumb navigation generation with:
- URL parsing and manipulation
- String processing and cleaning
- HTML generation for navigation elements
- Path shortening algorithms
