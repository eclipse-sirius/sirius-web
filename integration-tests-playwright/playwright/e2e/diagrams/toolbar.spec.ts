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
import { PlaywrightDiagram } from '../../helpers/PlaywrightDiagram';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - toolbar', () => {
  let projectId;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-toolbar', 'blank-project');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    const explorer = new PlaywrightExplorer(page);
    await explorer.uploadDocument('diagramToolbar.xml');
    await explorer.expand('diagramToolbar.xml');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when toolbar is defined, then the toolbar is visible', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await new PlaywrightDiagram(page).waitForInitialLayout(() =>
      explorer.createRepresentation('Root', 'diagramToolbar - with toolbar', 'diagram')
    );
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await expect(page.getByTestId('toolbar-collapse')).toBeAttached();
    await expect(page.getByTestId('fit-to-screen')).toBeAttached();
    await expect(page.getByTestId('zoom-out')).toBeAttached();
  });

  test('when toolbar is defined as collapsed, then the toolbar is visible and only the expand button is visible', async ({
    page,
  }) => {
    const explorer = new PlaywrightExplorer(page);
    await new PlaywrightDiagram(page).waitForInitialLayout(() =>
      explorer.createRepresentation('Root', 'diagramToolbar - with collapsed toolbar', 'diagram')
    );
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await expect(page.getByTestId('toolbar-expand')).toBeAttached();
    await expect(page.getByTestId('fit-to-screen')).not.toBeVisible();
    await expect(page.getByTestId('zoom-out')).not.toBeVisible();
  });

  test('when toolbar is not defined, then the toolbar is not visible', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await new PlaywrightDiagram(page).waitForInitialLayout(() =>
      explorer.createRepresentation('Root', 'diagramToolbar - without toolbar', 'diagram')
    );
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await expect(page.getByTestId('fit-to-screen')).not.toBeAttached();
    await expect(page.getByTestId('zoom-out')).not.toBeAttached();
  });
});

test.describe('diagram - toolbar - zoom level', () => {
  let projectId;

  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });

    const project = await new PlaywrightProject(request).createProject('diagram-toolbar', 'blank-project');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    const explorer = new PlaywrightExplorer(page);
    await explorer.uploadDocument('diagramToolbar.xml');
    await explorer.expand('diagramToolbar.xml');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a zoom level is selected in the toolbar, then the diagram is zoomed to this level', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await new PlaywrightDiagram(page).waitForInitialLayout(() =>
      explorer.createRepresentation('Root', 'diagramToolbar - with toolbar', 'diagram')
    );
    await expect(page.getByTestId('zoom-level')).toHaveText('100%');

    await page.getByTestId('zoom-level').click();
    await expect(page.getByTestId('zoom-level-menu')).toBeVisible();
    await page.getByTestId('zoom-level-200').click();
    await expect(page.getByTestId('zoom-level-menu')).not.toBeAttached();
    await expect(page.getByTestId('zoom-level')).toHaveText('200%');

    await page.getByTestId('zoom-out').click();
    await expect(page.getByTestId('zoom-level')).toHaveText('167%');

    await page.getByTestId('zoom-level').click();
    await page.getByTestId('zoom-level-100').click();
    await expect(page.getByTestId('zoom-level')).toHaveText('100%');
  });
});
