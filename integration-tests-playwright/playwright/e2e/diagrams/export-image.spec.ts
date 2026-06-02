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

import { test, expect } from '@playwright/test';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightWorkbench } from '../../helpers/PlaywrightWorkbench';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';

test.describe('diagram - export image', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);
    await new PlaywrightWorkbench(page).performAction('Robot Flow');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Robot Flow');
    await playwrightExplorer.createRepresentation('System', 'Topography', 'topography');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when export image menu is opened, then all export actions are available', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await page.getByTestId('export-diagram-to-image').click();
    await expect(page.getByTestId('export-diagram-to-svg')).toBeAttached();
    await expect(page.getByTestId('export-diagram-to-png')).toBeAttached();
    await expect(page.getByTestId('experimental-export-diagram-to-svg')).toBeAttached();
  });

  test('when export a diagram to image, then export file is named after the representation name', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await page.getByTestId('export-diagram-to-image').click();

    const downloadPromise = page.waitForEvent('download');
    await page.getByTestId('export-diagram-to-png').click();

    const download = await downloadPromise;
    expect(download.suggestedFilename()).toBe('topography.png');
    await download.cancel();
  });
});
