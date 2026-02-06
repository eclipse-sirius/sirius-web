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
    await explorer.createRepresentation('Root', 'diagramToolbar - with toolbar', 'diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await expect(page.getByTestId('fit-to-screen')).toBeAttached();
    await expect(page.getByTestId('zoom-out')).toBeAttached();
  });

  test('when toolbar is not defined, then the toolbar is not visible', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.createRepresentation('Root', 'diagramToolbar - without toolbar', 'diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await expect(page.getByTestId('fit-to-screen')).not.toBeAttached();
    await expect(page.getByTestId('zoom-out')).not.toBeAttached();
  });
});
