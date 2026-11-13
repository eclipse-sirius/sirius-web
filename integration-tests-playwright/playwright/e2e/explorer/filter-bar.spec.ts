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

import { expect, test } from '@playwright/test';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('explorer - filter bar', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Studio', 'studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when the filter bar is used, the tree is filtered without displaying extra characters in tree items', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);

    await page.getByTestId('filterbar-textfield').isHidden();

    await page.getByTestId('tree-descriptions-menu-icon').click();
    await page.getByTestId('tree-descriptions-menu-item-Domain explorer by DSL').click();

    await playwrightExplorer.expandAll('DomainNewModel');
    await page.getByTestId('[Entity] Root {}').isVisible();

    await playwrightExplorer.select('DomainNewModel');
    await page.keyboard.press('Control+f');
    await page.getByTestId('filterbar-textfield').isVisible();
    await page.getByTestId('filterbar-textfield').click();
    await page.keyboard.type('Root');

    // the label of the tree item has not been modified (with extra characters) by the filter bar research
    await page.getByTestId('[Entity] Root {}').isVisible();
  });
});

test.describe('explorer - filter bar', () => {
  let projectId: string;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Playwright - search Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit/`);

    const explorer = new PlaywrightExplorer(page);
    await explorer.createNewModel('robot', 'robot_flow');
    await expect(page.getByTestId('create-new-model')).not.toBeAttached();
    await expect(await explorer.getTreeItemLabel('robot')).toBeVisible();
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when searching with backend search activated, then all matching elements and their ancestors are displayed', async ({
    page,
  }) => {
    const explorer = new PlaywrightExplorer(page);
    await expect(await explorer.getTreeItemLabel('robot')).toHaveAttribute('data-expanded', 'false');
    await expect(await explorer.getTreeItemLabel('Motion_Engine')).not.toBeAttached();
    await expect(await explorer.getTreeItemLabel('Engine')).not.toBeAttached();

    await explorer.select('robot');
    await page.keyboard.press('Control+f');
    await page.getByTestId('filterbar-filter-button').click();
    await page.getByTestId('filterbar-textfield').getByRole('textbox').fill('Engine');

    const expectedLabels = ['robot', 'System', 'Central_Unit', 'Motion_Engine', 'Capture_Subsystem', 'Engine'];
    await expect(explorer.explorerLocator.locator('[data-treeitemlabel]')).toHaveCount(expectedLabels.length);
    for (const label of expectedLabels) {
      await expect(await explorer.getTreeItemLabel(label)).toBeVisible();
    }
    await expect(await explorer.getTreeItemLabel('Wifi')).not.toBeAttached();
  });
});
