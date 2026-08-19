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
import { expect, test as base } from '@playwright/test';
import { addCoverageReport } from 'monocart-reporter';

type CoverageFixtures = {
  collectDiagramCoverage: void;
};

const isPlaywrightCoverageEnabled = process.env.SIRIUS_PLAYWRIGHT_COVERAGE === 'true';

export const test = base.extend<CoverageFixtures>({
  collectDiagramCoverage: [
    async ({ browserName, page }, use, testInfo) => {
      const shouldCollectCoverage = isPlaywrightCoverageEnabled && browserName === 'chromium';

      if (!shouldCollectCoverage) {
        await use();
        return;
      }

      await page.coverage.startJSCoverage({ resetOnNavigation: false });

      try {
        await use();
      } finally {
        if (!page.isClosed()) {
          const coverage = await page.coverage.stopJSCoverage();
          if (testInfo.status === 'passed') {
            await addCoverageReport(coverage, testInfo);
          }
        }
      }
    },
    { auto: true },
  ],
});

export { expect };
export type { Page } from '@playwright/test';
