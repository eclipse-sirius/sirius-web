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
import { PlaywrightProject } from '../helpers/PlaywrightProject';
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightEdge } from '../helpers/PlaywrightEdge';
import { PlaywrightNode } from '../helpers/PlaywrightNode';

test.describe('edge-handle', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('edge-handle');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramWithTwoEdges.xml');
    await playwrightExplorer.expand('diagramWithTwoEdges.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramEdges - simple edges', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when two handles are on the same node, then they stay sticky to the node side', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'Entity2');
    await playwrightNode.click();
    await playwrightNode.move({ x: 200, y: 50 });

    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.click();

    const sourceEdgeUpdaterBefore = page.locator('.react-flow__edgeupdater-source');
    const csBefore = await sourceEdgeUpdaterBefore.evaluate((el) => {
      return window.getComputedStyle(el);
    });

    const handleCXBefore = csBefore.cx;

    const firstLine = page.getByTestId(`temporary-moving-line-0`);
    const box = (await firstLine.boundingBox())!;
    await firstLine.hover();
    await page.mouse.down();
    await page.mouse.move(box.x, box.y + 30, { steps: 2 });
    await page.mouse.up();

    const sourceEdgeUpdaterAfter = await page.locator('.react-flow__edgeupdater-source');
    const csAfter = await sourceEdgeUpdaterAfter.evaluate((el) => {
      return window.getComputedStyle(el);
    });

    expect(csAfter.cx).toBe(handleCXBefore);
  });
});
