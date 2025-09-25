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
import { PlaywrightEdge } from '../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightProject } from '../helpers/PlaywrightProject';

test.describe('edgeOnEdge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('edge-on-edge');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);

    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramEdgesOnEdges.xml');
    await playwrightExplorer.expand('diagramEdgesOnEdges.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramEdges - simple edges', 'diagram');

    //Hide the panels
    await page.locator(`[data-testid="sidebar-left"] button`).first().click();
    await page.locator(`[data-testid="sidebar-right"] button`).first().click();

    //Place the nodes
    const playwrightNode1a = new PlaywrightNode(page, 'Entity1a');
    await playwrightNode1a.click();
    await playwrightNode1a.move({ x: 0, y: -150 });

    const playwrightNode2a = new PlaywrightNode(page, 'Entity2a');
    await playwrightNode2a.click();
    await playwrightNode2a.move({ x: 250, y: -150 });

    const playwrightNode2b = new PlaywrightNode(page, 'Entity2b');
    await playwrightNode2b.click();
    await playwrightNode2b.move({ x: 0, y: 150 });
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when we create several type of edges, they are all rendered correctly', async ({ page }) => {
    const edges = page.locator('[data-testid^="rf__edge-"]');
    await expect(edges).toHaveCount(0);
    //Create node to node
    const playwrightNode1a = new PlaywrightNode(page, 'Entity1a');
    await playwrightNode1a.click();
    await page.getByTestId('creationhandle-right').hover();
    await page.mouse.down();
    const playwrightNode2a = new PlaywrightNode(page, 'Entity2a');
    await playwrightNode2a.nodeLocator.hover();
    await page.mouse.up();
    await page.getByTestId('connectorContextualMenu-E1toE2A').click();
    await expect(edges).toHaveCount(1);
    //Create edge to node
    const edgeAnchorNode = page.locator('[data-testid^="rf__node-edgeAnchorNodeCreationHandles"]');
    await expect(edgeAnchorNode).toHaveCount(1);
    await page.getByTestId('creationhandle-bottom').hover();
    await page.mouse.down();
    const playwrightNode2b = new PlaywrightNode(page, 'Entity2b');
    await playwrightNode2b.nodeLocator.hover();
    await page.mouse.up();
    await expect(edges).toHaveCount(2);
    //Create node to edge
    playwrightNode2b.click();
    await page.getByTestId('creationhandle-top').hover();
    await page.mouse.down();
    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.edgeLocator.hover({ force: true });
    await page.mouse.up();
    await page.getByTestId('connectorContextualMenu-E2ToEdge1A').click();
    await expect(edges).toHaveCount(3);
  });
});
