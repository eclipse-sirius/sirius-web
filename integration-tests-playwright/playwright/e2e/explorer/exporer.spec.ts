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
import { expect, test } from '@playwright/test';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Studio', 'studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when expanded state changes, then it s kept after refreshing the explorer', async ({ page }) => {
    await page.goto(`/projects/${projectId}/edit`);
    const explorer = new PlaywrightExplorer(page);

    await explorer.expandAll('DomainNewModel');
    await explorer.expandAll('ViewNewModel');

    const treeItem = await explorer.getTreeItemLabel('LinkedTo Edge');
    await expect(treeItem).toBeVisible();

    const treeItem2 = await explorer.getTreeItemLabel('LinkedTo Edge');
    await expect(treeItem2).toBeVisible();

    await page.reload();
    await expect(treeItem).toBeVisible();
    await expect(treeItem2).toBeVisible();
  });

  test('when current tree description state changes, then it s kept after refreshing the explorer', async ({
    page,
  }) => {
    await page.goto(`/projects/${projectId}/edit`);
    const explorer = new PlaywrightExplorer(page);

    await page.getByTestId('tree-descriptions-menu-icon').click();
    await page.getByTestId('tree-descriptions-menu-item-Domain explorer by DSL').click();

    await explorer.expandAll('DomainNewModel');
    await page.getByTestId('[Entity] Root {}').isVisible();

    await page.reload();
    await page.getByTestId('[Entity] Root {}').isVisible();
  });
});
