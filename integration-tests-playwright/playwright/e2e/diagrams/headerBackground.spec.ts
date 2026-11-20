/******************************************************************************
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
import { chromium, expect, test } from '@playwright/test';
import path from 'node:path';

test('header-only FreeForm and List nodes fill their interior at 150% display scale', async () => {

  const { build } = await import('vite');
  const bundle = await build({
    configFile: false,
    define: { 'process.env.NODE_ENV': JSON.stringify('production') },
    logLevel: 'silent',
    oxc: { jsx: { runtime: 'automatic' } },
    build: {
      write: false,
      lib: {
        entry: path.resolve(__dirname, '../../fixtures/diagrams/headerBackground.tsx'),
        name: 'headerBackgroundFixture',
        formats: ['iife'],
      },
    },
  });
  const output = Array.isArray(bundle) ? bundle[0] : bundle;
  if (!output || !('output' in output)) {
    throw new Error('Expected one browser bundle for the header fixture');
  }
  const script = output.output.find((item) => item.type === 'chunk');
  if (!script || script.type !== 'chunk') {
    throw new Error('The header fixture browser bundle is missing');
  }

  // Set the browser scale so Chromium snaps borders to physical pixels.
  const browser = await chromium.launch({ args: ['--force-device-scale-factor=1.5'] });
  try {
    const page = await browser.newPage({ viewport: null, deviceScaleFactor: undefined });
    await page.addScriptTag({ content: script.code });
    expect(await page.evaluate(() => window.devicePixelRatio)).toBe(1.5);

    for (const type of ['freeFormNode', 'listNode']) {
      await test.step(type, async () => {
        const result = await page.evaluate<{ borderWidth: number; backgroundColor: string; gap: number }>(
          `headerBackgroundFixture.measureHeaderGap(${JSON.stringify(type)})`
        );
        expect(result.borderWidth).toBeCloseTo(2 / 3, 4);
        expect(result.backgroundColor).toBe('rgb(255, 0, 0)');
        expect
          .soft(result.gap, 'Gap in CSS pixels between the header background and the inner bottom edge')
          .toBeCloseTo(0, 2);
      });
    }
  } finally {
    await browser.close();
  }
});
