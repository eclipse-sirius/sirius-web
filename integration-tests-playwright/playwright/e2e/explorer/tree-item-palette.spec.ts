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

test.describe('domain explorer - tree item palette', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Studio', 'studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a tool with an impact analysis is clicked, then the impact analysis popup opens', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);

    await page.getByTestId('tree-descriptions-menu-icon').click();
    await page.getByTestId('tree-descriptions-menu-item-Domain explorer by DSL').click();

    await playwrightExplorer.expandAll('DomainNewModel');
    await playwrightExplorer.openPalette('[Entity] Entity2 {name}');

    await page.getByTestId('tool-Toggle abstract').click();
    await page.getByTestId('impact-analysis-dialog-title').isVisible();
  });

  test('when a tool that redirect the user clicked, then the tool redirects the user', async ({ page, context }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);

    await page.getByTestId('tree-descriptions-menu-icon').click();
    await page.getByTestId('tree-descriptions-menu-item-Domain explorer by DSL').click();

    await playwrightExplorer.expandAll('DomainNewModel');
    await playwrightExplorer.openPalette('[Entity] Entity2 {name}');

    const newPagePromise = context.waitForEvent('page');
    await page.getByTestId('tool-Help').click();
    const newPage = await newPagePromise;

    await newPage.waitForLoadState();
    expect(newPage.url()).toContain('https://eclipse.dev/sirius/sirius-web.html');
  });
});
