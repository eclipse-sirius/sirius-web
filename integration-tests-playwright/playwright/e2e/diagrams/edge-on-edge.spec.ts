/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('edge on edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('edge-on-edge', 'blank-project');
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
    await playwrightNode2a.move({ x: 400, y: -150 });

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
    await page.getByTestId('creationhandle--right').hover();
    await page.mouse.down();
    const playwrightNode2a = new PlaywrightNode(page, 'Entity2a');
    await playwrightNode2a.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await playwrightNode2a.nodeLocator.hover({ position: { x: 50, y: 50 } });
    await page.mouse.up();
    await page.getByTestId('connectorContextualMenu-E1toE2A').click();
    await expect(edges).toHaveCount(1);
    //Create edge to node
    await page.getByTestId('creationhandle--bottom').hover();
    await page.mouse.down();
    const playwrightNode2b = new PlaywrightNode(page, 'Entity2b');
    await playwrightNode2b.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await playwrightNode2b.nodeLocator.hover({ position: { x: 50, y: 50 } });
    await page.mouse.up();
    await expect(edges).toHaveCount(2);
    //Create node to edge
    await playwrightNode2b.click();
    await page.getByTestId('creationhandle--bottom').hover();
    await page.mouse.down();
    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.edgeLocator
      .locator('path')
      .first()
      .hover({ position: { x: 50, y: 10 }, force: true });
    await page.mouse.up();
    await page.getByTestId('connectorContextualMenu-E2ToEdge1A').click();
    await expect(edges).toHaveCount(3);
  });

  test('when we create several type of edges from an handle that is in front on an edge, they are all rendered correctly', async ({
    page,
  }) => {
    const edges = page.locator('[data-testid^="rf__edge-"]');
    await expect(edges).toHaveCount(0);
    //Create node to node 1
    const playwrightNode1a = new PlaywrightNode(page, 'Entity1a');
    await playwrightNode1a.click();
    await page.getByTestId('creationhandle--right').hover();
    await page.mouse.down();
    const playwrightNode2a = new PlaywrightNode(page, 'Entity2a');
    await playwrightNode2a.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await playwrightNode2a.nodeLocator.hover({ position: { x: 50, y: 50 } });
    await page.mouse.up();
    await page.getByTestId('connectorContextualMenu-E1toE2A').click();
    await expect(edges).toHaveCount(1);

    //Create node to node 2
    await playwrightNode1a.click();
    await page.getByTestId('creationhandle--right').hover();
    await page.mouse.down();
    await playwrightNode2a.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await playwrightNode2a.nodeLocator.hover({ position: { x: 50, y: 50 } });
    await page.mouse.up();
    await page.getByTestId('connectorContextualMenu-E1toE2B').click();
    await expect(edges).toHaveCount(2);
  });
});

test.describe('edge on edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeToEdge.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('edgesOnEdges');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when 2 edges with 2 anchorNodes are rendered, then the handles on the anchorNodes are correctly placed', async ({
    page,
  }) => {
    const edges = page.locator('[data-testid^="rf__edge-"]');
    await expect(edges).toHaveCount(3);

    await expect(page.locator('.source_handle_right')).toHaveCount(2);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.source_handle_left')).toHaveCount(0);
    await expect(page.locator('.source_handle_top')).toHaveCount(0);

    await expect(page.locator('.target_handle_right')).toHaveCount(0);
    await expect(page.locator('.target_handle_bottom')).toHaveCount(0);
    await expect(page.locator('.target_handle_left')).toHaveCount(2);
    await expect(page.locator('.target_handle_top')).toHaveCount(1);
  });
});

test.describe('edge on edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeToEdgeHandles.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('edgesOnEdges');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an edge is between a node and an anchorNode and the node is moved, then the handles are updated', async ({
    page,
  }) => {
    const edges = page.locator('[data-testid^="rf__edge-"]');
    await expect(edges).toHaveCount(2);

    await expect(page.locator('.source_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(2);

    const playwrightNode2b = new PlaywrightNode(page, 'Entity2b');
    const xyPosition = await playwrightNode2b.getDOMXYPosition();
    await playwrightNode2b.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition.x + 400, xyPosition.y + 100, { steps: 10 });
    await expect(page.locator('.target_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(1);
    await page.mouse.up();
    await expect(page.locator('.target_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(1);

    await playwrightNode2b.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition.x, xyPosition.y, { steps: 10 });
    await expect(page.locator('.source_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(2);
    await page.mouse.up();
    await expect(page.locator('.source_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(2);
  });

  test('when an edge is between a node and an anchorNode and the anchorNode is moved, then the handles position are updated', async ({
    page,
  }) => {
    const edges = page.locator('[data-testid^="rf__edge-"]');
    await expect(edges).toHaveCount(2);

    await expect(page.locator('.source_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(2);

    const playwrightNode1a = new PlaywrightNode(page, 'Entity1a');
    const xyPosition1 = await playwrightNode1a.getDOMXYPosition();
    const playwrightNode2a = new PlaywrightNode(page, 'Entity2a');
    const xyPosition2 = await playwrightNode2a.getDOMXYPosition();
    await playwrightNode1a.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition2.x, xyPosition1.y, { steps: 10 });
    await expect(page.locator('.target_handle_top')).toHaveCount(1);
    await expect(page.locator('.target_handle_left')).toHaveCount(1);
    await page.mouse.up();
    await expect(page.locator('.target_handle_top')).toHaveCount(1);
    await expect(page.locator('.target_handle_left')).toHaveCount(1);

    await playwrightNode1a.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition1.x, xyPosition1.y, { steps: 10 });
    await expect(page.locator('.source_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(2);
    await page.mouse.up();
    await expect(page.locator('.source_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.target_handle_top')).toHaveCount(2);
  });
});
