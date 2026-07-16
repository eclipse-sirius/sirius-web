import react from '@vitejs/plugin-react';
import path from 'node:path';
import { defineConfig, loadEnv } from 'vite';

export default defineConfig(({ mode }) => {
  const isPlaywrightCoverageEnabled = process.env.SIRIUS_PLAYWRIGHT_COVERAGE === 'true';

  return {
    plugins: [react()],
    resolve: {
      alias: isPlaywrightCoverageEnabled
        ? {
            '@eclipse-sirius/sirius-components-diagrams': path.resolve(
              __dirname,
              '../../../diagrams/frontend/sirius-components-diagrams/src/index.ts'
            ),
          }
        : undefined,
    },
    build: {
      minify: isPlaywrightCoverageEnabled ? false : mode !== 'development',
      sourcemap: isPlaywrightCoverageEnabled,
    },
    test: {
      environment: 'jsdom',
      coverage: {
        reporter: ['text', 'html'],
      },
    },
    //We define the process.env to avoid 'Uncaught ReferenceError: process is not defined'.
    //Dependencies (such as react-trello) might expect environment variables to be defined (REDUX_LOGGING in this case).
    define: {
      'process.env': { ...process.env, ...loadEnv(mode, process.cwd()) },
    },
  };
});
