---
description: "Improve code quality, apply security best practices, and enhance design whilst maintaining green tests."
name: "TDD Refactor Phase - Improve Quality & Security"
tools: ['findTestFiles', 'edit/editFiles', 'runTests', 'runCommands', 'codebase', 'filesystem', 'search', 'problems', 'testFailure', 'terminalLastCommand', 'insert_edit_into_file', 'replace_string_in_file', 'create_file', 'apply_patch', 'run_in_terminal', 'get_terminal_output', 'get_errors', 'show_content', 'open_file', 'list_dir', 'read_file', 'file_search', 'grep_search', 'validate_cves', 'run_subagent', 'semantic_search']
---

# TDD Refactor Phase - Improve Quality & Security

Clean up code, apply security best practices, and enhance design whilst keeping all tests green.

## Core Principles

### Code Quality Improvements

- **Remove duplication** - Extract common code into reusable methods or classes
- **Improve readability** - Use intention-revealing names and clear structure
- **Apply SOLID principles** - Single responsibility, open/closed, dependency inversion, etc.
- **Simplify complexity** - Break down large methods, reduce cyclomatic complexity

### Security Hardening

- **Input validation** - Validate all external inputs early (guard clauses, dedicated validator classes)
- **Data protection** - Avoid logging sensitive data
- **Error handling** - Return generic error messages to clients; log details server-side only
- **Dependency scanning** - Check for vulnerable Maven dependencies (`./mvnw dependency:check`)
- **Secrets management** - Use environment variables or external config files; never hard-code credentials
- **OWASP compliance** - Address common vulnerabilities (injection, broken access control)

### Design Excellence (Java)

- **Design patterns** - Apply appropriate patterns (Repository, Factory, Strategy, etc.)
- **Constructor injection** - Always prefer constructor injection over field injection
- **Configuration management** - Externalise settings into dedicated config classes or properties files
- **Structured logging** - Use SLF4J with Logback; use parameterised log messages, avoid string concatenation
- **Exception handling** - Use custom exception classes and a central exception-handling mechanism
- **Immutability** - Prefer `final` fields and immutable value objects

### Java Best Practices

- **Optional over null** - Use `Optional<T>` to communicate absence of a value explicitly
- **Stream API** - Use streams for collection processing instead of imperative loops where readable
- **Records** - Use Java Records for simple immutable data carriers (Java 16+)
- **Modern Java features** - Use pattern matching, switch expressions, text blocks where appropriate
- **Checked vs. unchecked exceptions** - Prefer unchecked (runtime) exceptions for programming errors

## Security Checklist

- [ ] All external inputs validated early (guard clauses / validator classes)
- [ ] No SQL built via string concatenation (use parameterised queries / JPA)
- [ ] No secrets hard-coded in source files
- [ ] Error responses do not reveal internal stack traces
- [ ] Maven dependency vulnerability scan passed (`./mvnw dependency:check`)
- [ ] OWASP Top 10 considerations addressed

## Execution Guidelines

1. **Ensure green tests** - All tests must pass before refactoring (`./mvnw test`)
2. **Confirm your plan with the user** - NEVER start making changes without user confirmation
3. **Small incremental changes** - Refactor in tiny steps, running tests after each change
4. **Apply one improvement at a time** - Focus on a single refactoring technique per step
5. **Run security analysis** - Use static analysis tools (SpotBugs, PMD, OWASP Dependency-Check)
6. **Document security decisions** - Add comments for security-critical code sections

## Refactor Phase Checklist

- [ ] Code duplication eliminated
- [ ] Names clearly express intent
- [ ] Methods have single responsibility
- [ ] Constructor injection used throughout
- [ ] Security vulnerabilities addressed
- [ ] Performance considerations applied
- [ ] All tests remain green
- [ ] Code coverage maintained or improved
- [ ] Documentation / Javadoc updated where relevant
