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

const scriptDirectory = path.dirname(fileURLToPath(import.meta.url));
const integrationTestsDirectory = path.resolve(scriptDirectory, '..');
const workspaceRoot = path.resolve(integrationTestsDirectory, '..');
const diagramSourceDirectory = path.resolve(workspaceRoot, 'packages/diagrams/frontend/sirius-components-diagrams/src');
const coverageDirectory = path.resolve(integrationTestsDirectory, 'coverage/diagram');
const coverageSummaryPath = path.resolve(coverageDirectory, 'coverage-summary.json');
const coverageDetailsPath = path.resolve(coverageDirectory, 'coverage-final.json');
const baselinePath = path.resolve(integrationTestsDirectory, 'diagram-coverage-baseline.json');
const percentageTolerance = 0.01;

const readJson = (filePath) => JSON.parse(fs.readFileSync(filePath, 'utf8'));

const validateCoverageFile = (filePath) => {
  const relativePath = path.relative(diagramSourceDirectory, path.resolve(filePath));
  return relativePath !== '' && !relativePath.startsWith('..') && !path.isAbsolute(relativePath);
};

if (!fs.existsSync(coverageSummaryPath)) {
  throw new Error(`Coverage summary not found at ${coverageSummaryPath}. Run npm run start:coverage first.`);
}
if (!fs.existsSync(coverageDetailsPath)) {
  throw new Error(`Detailed coverage not found at ${coverageDetailsPath}. Run npm run start:coverage first.`);
}
if (!fs.existsSync(baselinePath)) {
  throw new Error(`Diagram coverage baseline not found at ${baselinePath}.`);
}

const coverageSummary = readJson(coverageSummaryPath);
const coverageDetails = readJson(coverageDetailsPath);
const baseline = readJson(baselinePath);
const lineSummary = coverageSummary.total?.lines;

if (
  typeof lineSummary?.pct !== 'number' ||
  typeof lineSummary?.total !== 'number' ||
  lineSummary.total <= 0 ||
  lineSummary.pct <= 0
) {
  throw new Error(`The diagram line coverage in ${coverageSummaryPath} is missing or empty.`);
}

const coveredFiles = Object.keys(coverageDetails);
if (coveredFiles.length === 0) {
  throw new Error(`No diagram source was found in ${coverageDetailsPath}.`);
}

const unexpectedFiles = coveredFiles.filter((filePath) => !validateCoverageFile(filePath));
if (unexpectedFiles.length > 0) {
  throw new Error(`Coverage contains sources outside sirius-components-diagrams:\n${unexpectedFiles.join('\n')}`);
}

const currentLineCoverage = Number(lineSummary.pct.toFixed(2));

if (process.argv.includes('--update-baseline')) {
  fs.writeFileSync(baselinePath, `${JSON.stringify({ lines: currentLineCoverage }, null, 2)}\n`);
  console.log(`Updated diagram line coverage baseline to ${currentLineCoverage.toFixed(2)}%.`);
  process.exit(0);
}

if (typeof baseline.lines !== 'number' || baseline.lines <= 0) {
  throw new Error(`The lines value in ${baselinePath} must be a positive number.`);
}

console.log(`Diagram line coverage: ${currentLineCoverage.toFixed(2)}% (expected ${baseline.lines.toFixed(2)}%).`);

if (currentLineCoverage + percentageTolerance < baseline.lines) {
  console.error(
    `Diagram line coverage decreased from ${baseline.lines.toFixed(2)}% to ${currentLineCoverage.toFixed(2)}%.`
  );
  process.exitCode = 1;
} else if (currentLineCoverage > baseline.lines + percentageTolerance) {
  console.warn(
    `Diagram line coverage increased from ${baseline.lines.toFixed(2)}% to ${currentLineCoverage.toFixed(
      2
    )}%; update the baseline with npm run coverage:update-baseline.`
  );
} else {
  console.log('Diagram line coverage check passed.');
}
