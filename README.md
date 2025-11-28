# QA-Final_Assigment

# OpenCart UI Automation

## Project overview
Automation suite for the OpenCart UI implemented in Java using Selenium WebDriver and Maven. Tests follow the Page Object Model (POM) and are designed to be readable, maintainable and easily configurable for local and CI runs.

## Tech stack
- Java 17+ 
- Maven (project build & dependency management) 
- Selenium WebDriver 
- Test framework: TestNG
- Reporting: Use of logs

## Design & strategy
- Page Object Model: UI pages are represented by classes under `src/main/java` (e.g. `BasePage`, `SearchPage`). 
- Tests live under `src/test/java` and invoke page objects to perform flows. 
- Use explicit waits (WebDriverWait) inside `BasePage` for reliable interactions. 
- Keep locators centralized in page classes and use descriptive names. Prefer stable selectors (id, data-attributes) over brittle XPaths. 
- Tests should be data-driven where applicable and avoid hard-coded waits.

## Project structure
- `src/main/java` - page objects and helpers (logging, drivers, utils) 
- `src/test/java` - test cases 
- `pom.xml` - dependencies, plugins and profiles 
- `README.md` - this document

## Configuration
Use environment variables or Maven properties to configure runs:
- `BASE_URL` - application base URL 

## Running tests
- Run full suite:
  - `mvn test`
- Run a single test class:
  - `mvn -Dtest=SearchTest test`

## Reporting & logs
- Use of log file to save the executions.
- Keep WebDriver logs and screenshots on failures.

## Contributing
- Add new page objects under `src/main/java` and tests under `src/test/java`. 
- Follow existing naming conventions and POM structure. Run the test suite locally.

