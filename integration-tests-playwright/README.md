# Sirius Web Playwright integration tests

## Diagram code coverage

Diagram tests collect JavaScript coverage from Chromium with Playwright's native V8 coverage API.
The report is remapped to the TypeScript sources in
`packages/diagrams/frontend/sirius-components-diagrams/src`.

The tested frontend must be built with `SIRIUS_PLAYWRIGHT_COVERAGE=true` so both the diagram package and the final application contain source maps.
Once that application is available on the URL configured in `playwright.config.ts`, run:

```sh
npm run start:coverage
npm run coverage:check
```

Reports are written to `coverage/diagram`.
The CI checks the global line, branch, function, and statement coverage against the values stored in `diagram-coverage-baseline.json`.

The baseline starts in bootstrap mode because it must be measured against the complete Sirius Web backend and all diagram tests.
After the first complete coverage run, initialize it with:

```sh
npm run coverage:update-baseline
```

Commit the resulting `diagram-coverage-baseline.json`.
After initialization, a global coverage decrease fails the check, while an increase asks for the baseline to be updated.
The first CI artifact also contains `diagram-coverage-baseline.suggested.json`, which can be copied directly over the repository baseline file.
