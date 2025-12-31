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

import { test } from '@playwright/test';
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
