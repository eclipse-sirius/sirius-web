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

import { test, expect } from '@playwright/test';
import { PlaywrightProject } from '../helpers/PlaywrightProject';
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightLabel } from '../helpers/PlaywrightLabel';

test.describe('appearance', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProjectFromTemplate('flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('change outside label appearance', async ({ page }) => {
    const playwrightLabel = new PlaywrightLabel(page, 'DataSource1');
    const fontSizeBefore = await playwrightLabel.getFontSize();
    expect(fontSizeBefore).toBe('14px');
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    await playwrightNode.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Font Size"] input').fill('16');
    await playwrightNode.closePalette();
    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label - DataSource1"]`);
        if (!label) {
          return false;
        }
        const style = window.getComputedStyle(label);
        return style.fontSize === '16px';
      },
      { timeout: 2000 }
    );
  });
});
