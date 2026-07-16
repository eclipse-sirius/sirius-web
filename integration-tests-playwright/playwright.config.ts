/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { transform, transformSync, type Options } from '@swc/core';
import { defineConfig, devices, type ReporterDescription } from '@playwright/test';
import type { CoverageReportOptions } from 'monocart-reporter';
import { readFileSync } from 'node:fs';
import { resolve } from 'node:path';

const isPlaywrightCoverageEnabled = process.env.SIRIUS_PLAYWRIGHT_COVERAGE === 'true';
const workspaceRoot = resolve(__dirname, '..');
const diagramSourceDirectory = resolve(workspaceRoot, 'packages/diagrams/frontend/sirius-components-diagrams/src');
const diagramSourceMarker = 'sirius-components-diagrams/src/';
const transformedSourceFiles = new Map<string, { code: string; map?: string }>();

const normalizePath = (filePath: string): string => filePath.replaceAll('\\', '/');

const getSwcOptions = (filename: string): Options => ({
  filename,
  sourceMaps: true,
  jsc: {
    parser: {
      syntax: 'typescript',
      tsx: filename.endsWith('.tsx'),
    },
    target: 'es2022',
  },
  module: {
    type: 'es6',
  },
});

const hasRuntimeCode = (code: string): boolean => {
  const codeWithoutComments = code
    .replace(/\/\*[\s\S]*?\*\//g, '')
    .replace(/\/\/.*$/gm, '')
    .trim();
  return codeWithoutComments !== '' && !/^export\s*\{\s*\};?$/.test(codeWithoutComments);
};

const coverageOptions: CoverageReportOptions = {
  name: 'Sirius Components Diagrams - Playwright coverage',
  baseDir: workspaceRoot,
  outputDir: resolve(__dirname, 'coverage/diagram'),
  reports: ['v8', ['html', { subdir: 'istanbul' }], 'lcovonly', 'json-summary', 'console-summary', 'markdown-summary'],
  entryFilter: (entry) =>
    entry.url.startsWith('http://localhost:8080/') || entry.url.startsWith('http://localhost:5173/'),
  sourceFilter: (sourcePath) => normalizePath(sourcePath).includes(diagramSourceMarker),
  sourcePath: (sourcePath) => {
    const normalizedSourcePath = normalizePath(sourcePath);
    const markerIndex = normalizedSourcePath.lastIndexOf(diagramSourceMarker);

    if (markerIndex === -1) {
      return normalizedSourcePath;
    }

    return normalizePath(
      resolve(diagramSourceDirectory, normalizedSourcePath.slice(markerIndex + diagramSourceMarker.length))
    );
  },
  all: {
    dir: diagramSourceDirectory,
    filter: (filePath) => {
      if (!/\.tsx?$/.test(filePath)) {
        return false;
      }

      const normalizedFilePath = normalizePath(filePath);
      const result = transformSync(readFileSync(filePath, 'utf8'), getSwcOptions(filePath));
      transformedSourceFiles.set(normalizedFilePath, result);
      return hasRuntimeCode(result.code);
    },
    transformer: async (entry) => {
      const filename = entry.url as string;
      const normalizedFilename = normalizePath(filename);
      const result =
        transformedSourceFiles.get(normalizedFilename) ??
        (await transform(entry.source as string, getSwcOptions(filename)));

      entry.source = result.code;
      if (result.map) {
        entry.sourceMap = JSON.parse(result.map);
      }
    },
  },
};

const reporters: ReporterDescription[] = isPlaywrightCoverageEnabled
  ? [
      ['html'],
      [
        'monocart-reporter',
        {
          name: 'Sirius Web Playwright tests',
          outputFile: 'playwright-report/monocart/index.html',
          coverage: coverageOptions,
        },
      ],
    ]
  : [['html']];

/**
 * See https://playwright.dev/docs/test-configuration.
 */
export default defineConfig({
  testDir: './playwright/e2e',
  /* Run tests in files in parallel */
  fullyParallel: true,
  /* Fail the build on CI if you accidentally left test.only in the source code. */
  forbidOnly: !!process.env.CI,
  /* Retry on CI only */
  retries: process.env.CI ? 2 : 0,
  /* Opt out of parallel tests on CI. */
  workers: process.env.CI ? 1 : 1,
  /* Reporter to use. See https://playwright.dev/docs/test-reporters */
  reporter: reporters,
  timeout: process.env.CI ? 10_000 : 60_000,
  /* Shared settings for all the projects below. See https://playwright.dev/docs/api/class-testoptions. */
  use: {
    /* Base URL to use in actions like `await page.goto('/')`. */
    baseURL: process.env.CI ? 'http://localhost:8080' : 'http://localhost:5173',

    /* Collect trace when retrying the failed test. See https://playwright.dev/docs/trace-viewer */
    trace: 'on-all-retries',
    viewport: { width: 1920, height: 1080 },
  },

  /* Configure projects for major browsers */
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },

    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
  ],
});
