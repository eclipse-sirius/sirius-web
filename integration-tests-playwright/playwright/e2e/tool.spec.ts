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
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';

test.describe('diagram - tool', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProjectFromTemplate('Papaya - Blank', 'papaya-empty', [
      PlaywrightProject.PAPAYA_NATURE,
    ]);
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);

    const playwrightExplorer = new PlaywrightExplorer(page);
    playwrightExplorer.expand('Papaya');
    playwrightExplorer.expand('Project');
    playwrightExplorer.expand('Component');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });
  test('When clicking on a tool with selection dialog, then the dialog is displayed', async ({ page }) => {
    new PlaywrightExplorer(page).createRepresentation('Component', 'Class Diagram', 'diagram');
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 100, y: 100 } });
    await page.getByTestId('Import existing types - Tool').click();
    await expect(page.getByTestId('selection-dialog')).toBeAttached();
  });

  test('When a custom tool is contribute to a diagram, then it should be available in the palette', async ({
    page,
  }) => {
    new PlaywrightExplorer(page).createRepresentation('Component', 'Component Diagram', 'diagram');
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 100, y: 100 } });
    await expect(page.getByTestId('Palette')).toBeAttached();
    await expect(page.getByTestId('coordinates-tool')).toBeAttached();
  });
});
