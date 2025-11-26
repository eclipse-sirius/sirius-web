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

import { expect, test } from '@playwright/test';
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightProject } from '../helpers/PlaywrightProject';

test.describe('diagram - animation', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.createRepresentation('NewSystem', 'Topography unsynchronized', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a new element is add to an empty diagram, then the view port keep the same zoom level', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.dragTo('DataSource1', page.locator('.react-flow__pane'));

    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    await expect(playwrightNode.nodeLocator).toBeInViewport();

    const viewport = await page.locator('.react-flow__viewport');
    const scale = await viewport.evaluate((el) => {
      const transform = el.style.transform;
      const match = transform.match(/scale\(([0-9.]+)\)/);
      return match ? parseFloat(match[1]) : 1;
    });
    expect(scale).toBe(1);
  });
});
