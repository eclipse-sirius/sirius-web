/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import react from '@vitejs/plugin-react';
import { createRequire } from 'node:module';
import path from 'node:path';
import { defineConfig } from 'vite';

const require = createRequire(import.meta.url);
const { peerDependencies = {} } = require('./package.json');
const isExternal = (id) =>
  Object.keys(peerDependencies).some((dependency) => id === dependency || id.startsWith(`${dependency}/`));

export default defineConfig(() => {
  const isPlaywrightCoverageEnabled = process.env.SIRIUS_PLAYWRIGHT_COVERAGE === 'true';
  const configuration = {
    plugins: [react()],
    build: {
      minify: false,
      sourcemap: isPlaywrightCoverageEnabled,
      lib: {
        name: 'sirius-components-diagrams',
        entry: path.resolve(__dirname, 'src/index.ts'),
        formats: ['es', 'cjs'],
        fileName: (format) => `sirius-components-diagrams.${format}.js`,
      },
      rollupOptions: {
        external: isExternal,
      },
    },
    test: {
      environment: 'jsdom',
      coverage: {
        reporter: ['text', 'html'],
      },
    },
  };
  return configuration;
});
