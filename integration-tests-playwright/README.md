# Sirius Web Playwright integration tests

## Diagram frontend code coverage

The diagram tests collect JavaScript coverage from Chromium with Playwright's native V8 coverage API.
The report is remapped to the TypeScript sources in `packages/diagrams/frontend/sirius-components-diagrams/src`; Firefox still runs the functional tests but does not contribute coverage data.

The tested frontend must be started or built with `SIRIUS_PLAYWRIGHT_COVERAGE=true` so that it contains source maps and resolves the diagrams package directly to its sources.
Once the frontend and backend are available at the URLs configured in `playwright.config.ts`, run:

```sh
npm run start:coverage
npm run coverage:check
```

Reports are written to `coverage/diagram`.
The CI compares the global line coverage with `diagram-coverage-baseline.json` and fails if it decreases or if the report is missing, empty, or contains sources outside the diagrams package.
When coverage increases, update the baseline explicitly after a complete, stable run:

```sh
npm run coverage:update-baseline
```

New files in `playwright/e2e/diagrams` must import `test`, `expect`, and Playwright's `Page` type from `../../fixtures/coverage`.
