# Sirius Web Frontend Developer Guide

This guide explains how to work on the Sirius Web frontend packages with the least possible friction. It walks through environment preparation, backend startup, frontend development, formatting, testing, and recommended Visual Studio Code settings.

## 1. Repository Layout

- The frontend projects live under `./packages/*/frontend/*`.
- TurboRepo orchestrates shared scripts (`build`, `start`, `test`, `coverage`) across every package.
- The sample application you run during development is `@eclipse-sirius/sirius-web`. It consumes the shared component packages while you iterate.

## 2. Prerequisites

1. **Node.js and npm**
   - Required versions: Node `22.16.0`, npm `10.9.2`.
   - Install with a version manager (examples below) and select the version for this repo:
     ```sh
     # macOS/Linux using nvm
     nvm install 22.16.0
     nvm use 22.16.0

     # any platform using Volta
     volta install node@22.16.0
     volta pin node@22.16.0
     volta pin npm@10.9.2
     ```
   - Confirm the versions:
     ```sh
     node -v
     npm -v
     ```

2. **GitHub Packages access**
   - Create a personal access token with the `read:packages` scope at https://github.com/settings/tokens.
   - Add the token to your global npm configuration so npm can download private dependencies:
     ```sh
     echo "//npm.pkg.github.com/:_authToken=<YOUR_TOKEN>" >> ~/.npmrc
     ```
   - Keep the token secret; it grants read access to Eclipse Sirius packages hosted on GitHub.

3. **Command shell**
   - On Windows, make sure npm scripts run through Git Bash (they rely on commands such as `mkdir`):
     ```sh
     npm config set script-shell "C:\\Program Files\\Git\\bin\\bash.exe"
     ```
   - On macOS/Linux, the default shell already works.

4. **Docker (optional but recommended)**
   - Docker simplifies running PostgreSQL for the backend.
   - Install it from https://www.docker.com/products/docker-desktop/.

## 3. Initial Repository Setup

1. Clone the repository and move into the `sirius-components` workspace:
   ```sh
   git clone https://github.com/eclipse-sirius/sirius-web.git
   cd sirius-web/sirius-components
   ```

2. Install dependencies exactly as declared in `package-lock.json`:
   ```sh
   npm ci
   ```

3. Verify TurboRepo is reachable from `devDependencies`:
   ```sh
   npx turbo --version
   ```
   You should see the version number without installing Turbo globally.

## 4. Start Supporting Services

You need the Sirius Web backend up so the frontend can communicate with GraphQL and REST endpoints.

### 4.1 PostgreSQL via Docker

```sh
docker run -p 5433:5432 --rm --name sirius-web-postgres \
  -e POSTGRES_USER=dbuser \
  -e POSTGRES_PASSWORD=dbpwd \
  -e POSTGRES_DB=sirius-web-db \
  -d postgres:15
```

- Port `5433` on your machine forwards to the container’s default port `5432`.
- The database persists only while the container runs because of the `--rm` flag.

Stop the container when you are done:
```sh
docker kill sirius-web-postgres
```

### 4.2 Sirius Web backend

You can either download the released `sirius-web.jar` from GitHub or build it yourself from this repository. To produce the jar locally (and include your latest frontend changes), run the backend build pipeline from the repo root:

```sh
npm ci
npx turbo run build
mkdir -p packages/sirius-web/backend/sirius-web-frontend/src/main/resources/static
cp -R packages/sirius-web/frontend/sirius-web/dist/* packages/sirius-web/backend/sirius-web-frontend/src/main/resources/static
mvn clean package -f packages/pom.xml -DskipTests
```

The `package` phase is enough to produce the fat jar at `packages/sirius-web/backend/sirius-web/target/sirius-web-<version>.jar`, and `-DskipTests` avoids a full backend test run while you iterate. Copy or symlink the archive as `sirius-web.jar` if you prefer a stable file name.

Once you have the jar file, start it from the repository root. The following command picks the newest versioned archive automatically, so you can copy/paste it without editing a version number:

```sh
java -jar "$(ls -t packages/sirius-web/backend/sirius-web/target/sirius-web-*.jar \
  | grep -E 'sirius-web-[0-9]+\.[0-9]+\.[0-9]+\.jar$' \
  | head -n 1)" \
  --spring.datasource.url=jdbc:postgresql://localhost:5433/sirius-web-db \
  --spring.datasource.username=dbuser \
  --spring.datasource.password=dbpwd
```

- The backend listens on `http://localhost:8080` by default.
- The frontend queries `http://localhost:8080/api/graphql` and `ws://localhost:8080/subscriptions` through Apollo Client.
- Leave the backend running while you code so the frontend has active endpoints.

## 5. Configure Frontend Environment Variables

Create a workspace-local environment file for the app so you can customize ports without modifying tracked files.

```sh
cp packages/sirius-web/frontend/sirius-web/.env.development \
   packages/sirius-web/frontend/sirius-web/.env.local
```

`packages/sirius-web/frontend/sirius-web/.env.local` should contain:
```
VITE_HTTP_SERVER_PORT=8080
VITE_WS_SERVER_PORT=8080
```

- Adjust the port values if you change backend bindings.
- The React app reads these variables and constructs URLs in `src/core/URL.ts`.

## 6. Launch the Frontend Dev Server

Run the Vite development server for the sample application from the repository root:

```sh
npm run start --workspace @eclipse-sirius/sirius-web
```

Here is what happens under the hood:
- TurboRepo runs the `format-lint` task first. It executes `prettier --list-different` to ensure your files already match the style guide. If formatting is off, the command fails and tells you which files need attention.
- TypeScript compiles the project (`tsc`). Compilation catches static typing errors before Vite starts.
- Vite boots with hot-module reloading at `http://localhost:5173`. Any file save immediately updates the browser without full reloads.

When the server starts successfully, open `http://localhost:5173` in a browser. The app proxies GraphQL and WebSocket requests to the backend using the origins you defined in `.env.local`.

Stop the dev server at any time with `Ctrl+C` in the terminal.

## 7. Editing in VS Code

1. Open the workspace in VS Code:
   ```sh
   code .
   ```

2. Install recommended extensions:
   - **Prettier** (`esbenp.prettier-vscode`) – already configured as the default formatter.
   - **GraphQL** – optional but useful for `.graphql` snippets and schema awareness.
   - **TypeScript ESLint** – optional; helps with lint feedback inside the editor.

3. Ensure format on save is active. The workspace settings enforce Prettier globally, so every save respects the project style.

4. Update the default TypeScript watch task. The repo ships with `.vscode/tasks.json`, but the referenced `tsconfig-noEmit.json` no longer exists. Replace it with the app’s `tsconfig` once:
   - Open `.vscode/tasks.json`.
   - Change the `args` array to:
     ```json
     "args": ["tsc", "--project", "packages/sirius-web/frontend/sirius-web/tsconfig.json", "--watch"]
     ```
   - Save the file.
   - Run the task from VS Code via **Terminal → Run Task → typescript compile watch mode**.

TypeScript diagnostics now flow into VS Code’s Problems panel while you type, without blocking the dev server.


## 8. Trivial Test To Check Everything Works

Follow these steps to prove the frontend toolchain is live-reloading correctly:

1. In VS Code, open `packages/sirius-web/frontend/sirius-web/src/index.tsx`.
2. Just above the existing `root.render(` call, add:
   ```ts
   document.title = 'Sirius Web – Dev Playground';
   ```
3. Save the file. Vite’s Hot Module Replacement updates the browser automatically.
4. Switch to `http://localhost:5173/` and confirm the tab now reads “Sirius Web – Dev Playground”.
5. Keep or remove the line once you finish testing—either choice validates that edits propagate instantly.


## 9. Working on Component Packages

Every frontend package exposes the same script names. Open a new terminal for the package you are editing and use npm workspace commands.

### Example: sirius-components-core

Run build and tests directly:
```sh
npm run build --workspace @eclipse-sirius/sirius-components-core
npm run test --workspace @eclipse-sirius/sirius-components-core -- --watch
```

- `build` runs `vite build && tsc`, producing distributable assets under `dist/` and checking the package compiles on its own.
- `test` uses Vitest in watch mode. It reruns relevant tests each time you save a file.

### Turbo filtering

TurboRepo understands package dependency graphs. Use `--filter` to run commands only where needed. Example:
```sh
npx turbo run build --filter=@eclipse-sirius/sirius-components-core
```
The command builds the target package and any dependent packages that need to stay in sync.

## 10. When Editing Shared Packages

Frontend packages are bundled before the demo app consumes them. Vite only picks up changes from a package’s `dist/` folder, so edits under `src/` stay invisible until you rebuild the package.

Example for `@eclipse-sirius/sirius-components-diagrams`:

```sh
npm run build --workspace @eclipse-sirius/sirius-components-diagrams
```

- Run the build whenever you touch code in `packages/diagrams/frontend/sirius-components-diagrams/src`. It regenerates `dist/` so the dev server serves your updates.
- After the build finishes, restart the Vite dev server (`Ctrl+C` then `npm run start --workspace @eclipse-sirius/sirius-web`) or let it auto-reload if it is resilient enough; finally refresh the browser to see the change.
- Other shared packages follow the same pattern: adjust the workspace name and rerun their `build` script.

## 11. Formatting and Linting

Prettier handles formatting across the workspace. Key settings: 2 spaces, 120-character line width, single quotes, semicolons.

### Fix formatting in the package you touched
```sh
npx turbo run format --filter=@eclipse-sirius/sirius-web
```

### Check formatting everywhere (mirrors CI)
```sh
npx turbo run format-lint
```

Turbo halts if any file deviates from the Prettier rules, so you can fix issues before pushing.

## 12. Testing Strategy

1. **Package unit tests**
   ```sh
   npm run test --workspace @eclipse-sirius/sirius-web -- --watch
   ```
   Run this in the packages you modify. Watch mode immediately reruns tests on save.

2. **Coverage**
   ```sh
   npm run coverage --workspace @eclipse-sirius/sirius-components-core
   ```
   Each package exposes a `coverage` script that collects Vitest coverage reports.

3. **Workspace-wide testing**
   ```sh
   npx turbo run test --filter=@eclipse-sirius/sirius-web...
   ```
   The `...` suffix includes dependent packages. Use it to validate that the app and its shared libraries still pass tests together.

4. **End-to-end suites**
   - Cypress scenarios live in `integration-tests-cypress`.
   - Playwright scenarios live in `integration-tests-playwright`.
   Run them when you need browser-level confidence, especially for regressions or major UI changes.

## 13. Daily Workflow Checklist

1. Start Docker and the backend jar.
2. Launch the frontend dev server with `npm run start --workspace @eclipse-sirius/sirius-web`.
3. Edit components or views. Vite hot reload keeps the browser in sync.
4. Run targeted unit tests (`npm run test --workspace … -- --watch`).
5. Format files (`npx turbo run format --filter=…`).
6. Before committing, run:
   ```sh
   npx turbo run format-lint
   npx turbo run build --filter=@eclipse-sirius/sirius-web
   ```
   These commands replicate the checks you expect CI to perform.

## 14. Troubleshooting Tips

- **Dev server won’t start** – Run `npx turbo run format` to auto-format files, then retry. Turbo blocks the `start` command if Prettier differences exist.
- **GraphQL errors in the browser** – Confirm the backend jar is running on `http://localhost:8080` and that `.env.local` points to the correct ports.
- **TypeScript errors missing from VS Code** – Make sure the updated watch task is running (see section 7). Alternatively, run `npx tsc --project packages/sirius-web/frontend/sirius-web/tsconfig.json --watch` in a terminal.
- **Backend authentication error** – Verify Docker published port `5433` is free. If not, change both the Docker run command and the backend `--spring.datasource.url` to another open port.

---

Following these steps keeps your frontend workflow aligned with the project’s tooling so you can focus on building features instead of fighting your environment.
