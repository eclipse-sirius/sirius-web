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
import { PlaywrightDetails } from '../../helpers/PlaywrightDetails';
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('edge - reconnection', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectFlowEdgeReconnection.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.expand('NewSystem');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an edge is reconnected, then new custom handle is preserved', async ({ page, browserName }) => {
    if (browserName === 'firefox') {
      test.skip(); //The test fails inexplicably in Firefox.
    }
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('Topography');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const newSourceNode = new PlaywrightNode(page, 'DataSource2');
    await newSourceNode.waitForAnimationToFinish();
    const newSourceNodeXYPosition = await newSourceNode.getDOMBoundingBox();

    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.click();

    const sourceReconnectHandle = page.locator(`.react-flow__edgeupdater-source`).first();
    await sourceReconnectHandle.hover();
    await page.mouse.down();
    await page.mouse.move(newSourceNodeXYPosition.x + newSourceNodeXYPosition.width / 2, newSourceNodeXYPosition.y);
    await page.mouse.up();

    await page.waitForFunction(() => !!document.querySelector('[data-testid="DataSource2-toggle"]'));

    await playwrightExplorer.expand('DataSource2');
    await playwrightExplorer.select('standard');

    const playwrightDetails = new PlaywrightDetails(page);
    const isReconnectPerformed = await playwrightDetails.isReferenceValueSet('Source', 'DataSource2');
    expect(isReconnectPerformed).toBe(true);

    await expect(page.locator('.source_handle_left')).toHaveCount(0);
    await expect(page.locator('.source_handle_right')).toHaveCount(0);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(0);
    await expect(page.locator('.source_handle_top')).toHaveCount(1);
  });
});
