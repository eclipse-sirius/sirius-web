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
/* eslint-env node */
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const integrationTestsDirectory = path.resolve(__dirname, '..');
const coverageDirectory = path.resolve(integrationTestsDirectory, 'coverage/diagram');
const coverageSummaryPath = path.resolve(coverageDirectory, 'coverage-summary.json');
const suggestedBaselinePath = path.resolve(coverageDirectory, 'diagram-coverage-baseline.suggested.json');
const baselinePath = path.resolve(integrationTestsDirectory, 'diagram-coverage-baseline.json');
const coverageMetrics = ['lines', 'branches', 'functions', 'statements'];
const percentageTolerance = 0.01;

const readJson = (filePath) => JSON.parse(fs.readFileSync(filePath, 'utf8'));

if (!fs.existsSync(coverageSummaryPath)) {
  throw new Error(`Coverage summary not found at ${coverageSummaryPath}. Run npm run start:coverage first.`);
}

const coverageSummary = readJson(coverageSummaryPath);
const baseline = readJson(baselinePath);
const currentCoverage = Object.fromEntries(
  coverageMetrics.map((metric) => {
    const percentage = coverageSummary.total?.[metric]?.pct;
    if (typeof percentage !== 'number') {
      throw new Error(`Missing ${metric} percentage in ${coverageSummaryPath}.`);
    }
    return [metric, percentage];
  })
);

const createBaseline = () => ({
  bootstrap: false,
  ...currentCoverage,
});

if (process.argv.includes('--update-baseline')) {
  fs.writeFileSync(baselinePath, `${JSON.stringify(createBaseline(), null, 2)}\n`);
  console.log(`Updated diagram coverage baseline at ${baselinePath}.`);
  process.exit(0);
}

console.log('Global diagram coverage:');
for (const metric of coverageMetrics) {
  console.log(`  ${metric}: ${currentCoverage[metric].toFixed(2)}%`);
}

if (baseline.bootstrap === true) {
  fs.mkdirSync(coverageDirectory, { recursive: true });
  fs.writeFileSync(suggestedBaselinePath, `${JSON.stringify(createBaseline(), null, 2)}\n`);
  console.warn(`Diagram coverage baseline is in bootstrap mode.`);
  console.warn(`Initialize it with npm run coverage:update-baseline or use ${suggestedBaselinePath}.`);
  console.log('Diagram coverage checks passed.');
  process.exit(0);
}

const errors = [];
for (const metric of coverageMetrics) {
  const expected = baseline[metric];
  if (typeof expected !== 'number') {
    errors.push(`Missing ${metric} value in ${baselinePath}.`);
    continue;
  }

  const actual = currentCoverage[metric];
  if (actual + percentageTolerance < expected) {
    errors.push(`${metric} coverage decreased from ${expected.toFixed(2)}% to ${actual.toFixed(2)}%.`);
  } else if (actual > expected + percentageTolerance) {
    console.warn(
      `${metric} coverage increased from ${expected.toFixed(2)}% to ${actual.toFixed(2)}%; update the baseline.`
    );
  }
}

if (errors.length > 0) {
  console.error('Diagram coverage checks failed:');
  for (const error of errors) {
    console.error(`  - ${error}`);
  }
  process.exitCode = 1;
} else {
  console.log('Diagram coverage checks passed.');
}
