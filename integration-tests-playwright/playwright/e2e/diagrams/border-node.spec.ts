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
import { PlaywrightDiagram } from '../../helpers/PlaywrightDiagram';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - border-node', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('border-node', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramWithBorderNode.xml');
    await playwrightExplorer.expand('diagramWithBorderNode.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramBorderNode - simple border', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('When an initial position is given for the border nodes, then this position is respected when the diagram is initialized', async ({
    page,
  }) => {
    const playwrightBorderNodeNorth = new PlaywrightNode(page, 'north');
    await playwrightBorderNodeNorth.click();

    const reactFlowXYPositionNorth = await playwrightBorderNodeNorth.getReactFlowXYPosition();
    expect(reactFlowXYPositionNorth.x).toBe(8);
    expect(reactFlowXYPositionNorth.y).toBe(-15);

    const playwrightBorderNodeEast = new PlaywrightNode(page, 'east');
    await playwrightBorderNodeEast.click();

    const reactFlowXYPositionEast = await playwrightBorderNodeEast.getReactFlowXYPosition();
    expect(reactFlowXYPositionEast.x).toBe(145);
    expect(reactFlowXYPositionEast.y).toBe(8);
  });

  test('When a border node is move, then its position stay attached to its parent', async ({ page }) => {
    const playwrightBorderNode = new PlaywrightNode(page, 'north');
    await playwrightBorderNode.click();

    await playwrightBorderNode.move({ x: 100, y: 100 });

    const reactFlowXYPosition = await playwrightBorderNode.getReactFlowXYPosition();
    expect(reactFlowXYPosition.x).toBeGreaterThan(8);
    expect(reactFlowXYPosition.y).toBe(-15);
  });

  test('When a border node is selected, then it must not be resizable', async ({ page }) => {
    const playwrightBorderNode = new PlaywrightNode(page, 'north');
    await playwrightBorderNode.click();
    await expect(page.locator('react-flow__resize-control nodrag top left handle')).not.toBeAttached();
    await expect(page.locator('react-flow__resize-control nodrag top right handle')).not.toBeAttached();
    await expect(page.locator('react-flow__resize-control nodrag bottom left handle')).not.toBeAttached();
    await expect(page.locator('react-flow__resize-control nodrag bottom right handle')).not.toBeAttached();
  });

  test('When the parent node is resize, then the border node must stay attached', async ({ page }) => {
    const borderNodeGap: number = 5;
    const playwrightBorderNodeEast = new PlaywrightNode(page, 'east');
    await playwrightBorderNodeEast.click();

    const reactFlowXYPositionEast = await playwrightBorderNodeEast.getReactFlowXYPosition('east');
    expect(reactFlowXYPositionEast.x).toBe(145);
    expect(reactFlowXYPositionEast.y).toBe(8);

    const playwrightParentNode = new PlaywrightNode(page, 'Entity1');
    await playwrightParentNode.click();
    await playwrightParentNode.resize({ height: 20, width: 50 }, 'bottom.right');
    const parentSizeAfterBottomRight = await playwrightParentNode.getReactFlowSize('Entity1');

    const reactFlowXYPositionEastAfterBottomRight = await playwrightBorderNodeEast.getReactFlowXYPosition('east');
    expect(reactFlowXYPositionEastAfterBottomRight.x).toBe(parentSizeAfterBottomRight.width - borderNodeGap);
    expect(reactFlowXYPositionEastAfterBottomRight.y).toBe(8);

    await page.getByTestId('zoom-out').click();

    await playwrightParentNode.click();
    await playwrightParentNode.resize({ height: -20, width: -50 }, 'top.left');
    const parentSizeAfterTopLeft = await playwrightParentNode.getReactFlowSize('Entity1');

    const reactFlowXYPositionEastAfterTopLeft = await playwrightBorderNodeEast.getReactFlowXYPosition('east');
    expect(reactFlowXYPositionEastAfterTopLeft.x).toBe(parentSizeAfterTopLeft.width - borderNodeGap);
  });

  test('When a border node has a child, then the child is displayed inside it after resize and reload', async ({
    page,
  }) => {
    const borderNodeGap: number = 5;
    const parentNode = new PlaywrightNode(page, 'Entity1');
    const borderNode = new PlaywrightNode(page, 'container');
    const childNode = new PlaywrightNode(page, 'child');
    await parentNode.click();
    await new PlaywrightDiagram(page).hideDebugPanel();

    await expect(borderNode.nodeLocator).toBeVisible();
    await expect(childNode.nodeLocator).toBeVisible();

    const parentNodeId = await parentNode.getReactFlowId('Entity1');
    const parentNodeSize = await parentNode.getReactFlowSize('Entity1', false);

    await borderNode.nodeLocator.click({ position: { x: 2, y: 2 } });
    const borderNodeId = await borderNode.getReactFlowId('container', false);
    const borderNodeParentId = await borderNode.getReactFlowParentId('container', false);
    const borderNodePosition = await borderNode.getReactFlowXYPosition('container', false);
    const borderNodeSize = await borderNode.getReactFlowSize('container', false);

    const childNodeParentId = await childNode.getReactFlowParentId('child');
    const childNodePosition = await childNode.getReactFlowXYPosition('child', false);
    const childNodeSize = await childNode.getReactFlowSize('child', false);

    expect(borderNodeParentId).toBe(parentNodeId);
    expect(childNodeParentId).toBe(borderNodeId);
    expect(borderNodePosition.y).toBeCloseTo(parentNodeSize.height - borderNodeGap);
    expect(borderNodeSize.width).toBeGreaterThan(20);
    expect(borderNodeSize.height).toBeGreaterThan(20);
    expect(childNodePosition.x).toBeGreaterThanOrEqual(0);
    expect(childNodePosition.y).toBeGreaterThanOrEqual(0);
    expect(childNodePosition.x + childNodeSize.width).toBeLessThanOrEqual(borderNodeSize.width);
    expect(childNodePosition.y + childNodeSize.height).toBeLessThanOrEqual(borderNodeSize.height);
  });
});

test.describe('diagram - border-node', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectBorderNodeHandlePosition.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('handlePosition');
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

  test('When an edge is on a border node, then its handle is on the opposite', async ({ page }) => {
    await expect(page.locator('.target_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_top')).toHaveCount(1);
  });
});
test.describe('diagram - border-node', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectBorderNodesPositionAfterResize.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('borderNodes');
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

  test('When resizing a node with border nodes, then each border node preserve its side', async ({ page }) => {
    const parentNode = new PlaywrightNode(page, 'ParentNode');
    await parentNode.click();
    await new PlaywrightDiagram(page).hideDebugPanel();
    await parentNode.resize({ height: -200, width: 200 }, 'bottom.left');

    const posONode = new PlaywrightNode(page, 'Pos0');
    const pos0Position = await posONode.getReactFlowXYPosition('Pos0');
    const pos1Node = new PlaywrightNode(page, 'Pos1');
    const pos1Position = await pos1Node.getReactFlowXYPosition('Pos1');
    const pos2Node = new PlaywrightNode(page, 'Pos2');
    const pos2Position = await pos2Node.getReactFlowXYPosition('Pos2');
    const pos3Node = new PlaywrightNode(page, 'Pos3');
    const pos3Position = await pos3Node.getReactFlowXYPosition('Pos3');
    const parentNodeSize = await parentNode.getReactFlowSize('ParentNode');
    const borderNodeGap: number = 5;
    const borderNodeSize: number = 20;
    expect(pos3Position.x).toBe(-borderNodeSize + borderNodeGap);
    expect(pos1Position.x).toBe(parentNodeSize.width - borderNodeGap);
    expect(pos0Position.y).toBe(-borderNodeSize + borderNodeGap);
    expect(pos2Position.y).toBe(parentNodeSize.height - borderNodeGap);
  });
});

test.describe('diagram - border-node', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeBorderNodeAsHandlePosition.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Others...');
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

  test('When an edge is hidden, then it is not considered for positioning the border node', async ({ page }) => {
    const nodeC = new PlaywrightNode(page, 'C');
    const borderNodeD = new PlaywrightNode(page, 'D');
    await nodeC.nodeLocator.click({ position: { x: 50, y: 50 } });
    const nodeCSize = await nodeC.getReactFlowSize('C', false);
    const borderNodeDPosition = await borderNodeD.getReactFlowXYPosition('D');
    const borderNodeDSize = await borderNodeD.getReactFlowSize('D', false);
    const borderNodeGap = 5;
    //Check that the border node D is placed on the right of the node C
    expect(borderNodeDPosition.x).toBe(nodeCSize.width - borderNodeGap);

    const nodeE = new PlaywrightNode(page, 'E');
    await nodeE.openPalette();
    await page.getByTestId('Hide - Tool').click();

    await expect(nodeE.nodeLocator).not.toBeVisible();
    const borderNodeDPositionAfter = await borderNodeD.getReactFlowXYPosition('D');
    //Check that the border node D is placed on the left of the node C
    expect(borderNodeDPositionAfter.x).toBe(-borderNodeDSize.width + borderNodeGap);
  });
});

test.describe('diagram - border-node', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('border-node', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramBorderNodeDnD.xml');
    await playwrightExplorer.expand('diagramBorderNodeDnD.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramDnD - simple dnd view', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('When dragging a border node, then other compatible nodes are not highlighted', async ({ page }) => {
    const borderNode = new PlaywrightNode(page, 'Border');
    await borderNode.click();

    const xyPosition = await borderNode.getDOMXYPosition();
    await borderNode.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition.x + 20, xyPosition.y + 20, { steps: 2 });

    // Entity one is a compatible drop target (but it's a border node that is dragged), its border/shadow should not change
    await page.waitForFunction(
      () => {
        const node = document.querySelector(`[data-testid="FreeForm - Target"]`);
        return node && window.getComputedStyle(node).getPropertyValue('box-shadow') === 'none';
      },
      { timeout: 2000 }
    );
  });
});
