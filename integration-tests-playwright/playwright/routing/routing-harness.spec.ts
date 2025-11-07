/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { expect, test } from '@playwright/test';
import { readFileSync, readdirSync } from 'node:fs';
import path from 'node:path';

type FixtureMetadata = {
  id: string;
  name: string;
  expectedEdgeShape: string;
  description?: string;
};

const fixturesDir = path.resolve(__dirname, '../../../packages/dev/frontend/routing-harness/src/fixtures');
const fixtures: FixtureMetadata[] = readdirSync(fixturesDir)
  .filter((file) => file.endsWith('.json'))
  .map((file) => {
    const raw = readFileSync(path.join(fixturesDir, file), 'utf-8');
    const parsed = JSON.parse(raw) as { id: string; name?: string; expectedEdgeShape?: string; description?: string };
    return {
      id: parsed.id,
      name: parsed.name ?? parsed.id,
      expectedEdgeShape: parsed.expectedEdgeShape ?? '',
      description: parsed.description,
    };
  });

for (const fixture of fixtures) {
  test(`renders fixture ${fixture.id}`, async ({ page }) => {
    await page.goto(`/?fixture=${fixture.id}`);
    await page.waitForSelector('.react-flow__nodes');
    await page.waitForTimeout(250); // give routing a beat to settle
    if (fixture.id.startsWith('decision-rel-')) {
      expect
        .soft(fixture.expectedEdgeShape, `Fixture ${fixture.id} must define expectedEdgeShape metadata`)
        .not.toHaveLength(0);
      expect
        .soft(fixture.description ?? '', `Fixture ${fixture.id} must describe the expected shape`)
        .toMatch(/Expected edge shape/i);
    }
    await expect(page).toHaveScreenshot(`${fixture.id}.png`, {
      animations: 'disabled',
      fullPage: false,
      maxDiffPixelRatio: 0.05,
    });
  });
}
