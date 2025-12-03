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

test.describe('diagram - borderNode', () => {
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

    const reactFlowXYPositionEast = await playwrightBorderNodeEast.getReactFlowXYPosition();
    expect(reactFlowXYPositionEast.x).toBe(145);
    expect(reactFlowXYPositionEast.y).toBe(8);

    const playwrightParentNode = new PlaywrightNode(page, 'Entity1');
    await playwrightParentNode.click();
    await playwrightParentNode.resize({ height: 20, width: 50 }, 'bottom.right');
    const parentSizeAfterBottomRight = await playwrightParentNode.getReactFlowSize();

    const reactFlowXYPositionEastAfterBottomRight = await playwrightBorderNodeEast.getReactFlowXYPosition();
    expect(reactFlowXYPositionEastAfterBottomRight.x).toBe(parentSizeAfterBottomRight.width - borderNodeGap);
    expect(reactFlowXYPositionEastAfterBottomRight.y).toBe(8);

    await page.getByTestId('zoom-out').click();

    await playwrightParentNode.click();
    await playwrightParentNode.resize({ height: -20, width: -50 }, 'top.left');
    const parentSizeAfterTopLeft = await playwrightParentNode.getReactFlowSize();

    const reactFlowXYPositionEastAfterTopLeft = await playwrightBorderNodeEast.getReactFlowXYPosition();
    expect(reactFlowXYPositionEastAfterTopLeft.x).toBe(parentSizeAfterTopLeft.width - borderNodeGap);
  });
});
