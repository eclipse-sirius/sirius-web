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
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';

test.describe('diagram - pin', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);

    const explorer = await new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.select('Topography');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when using the unpin tool in the diagram toolbar, then all pinned nodes are unpin', async ({ page }) => {
    const node = new PlaywrightNode(page, 'DataSource1');
    await node.openPalette();
    await page.getByTestId('Pin - Tool').click();

    await node.openPalette();
    await expect(page.getByTestId('Unpin - Tool')).toBeAttached();

    await page.getByTestId('unpin-all-elements').click();

    await node.openPalette();
    await expect(page.getByTestId('Pin - Tool')).toBeAttached();
  });
});
