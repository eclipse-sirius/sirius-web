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

test.describe('edge-handle', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('edge-handle', 'blank-project');
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

  test('when we select and edge, the handle style is updated', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'Entity2');
    await playwrightNode.click();
    await playwrightNode.move({ x: 200, y: 50 });

    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.click();

    await expect(page.locator('[data-testid^="connectionHandle"]')).toHaveCount(2);
    await expect(page.locator('[data-testid^="connectionHandle"]').first()).toHaveCSS(
      'background-color',
      'rgb(0, 0, 0)'
    );
    await expect(page.locator('[data-testid^="connectionHandle"]').last()).toHaveCSS(
      'background-color',
      'rgb(0, 0, 0)'
    );

    await page.locator('[data-testid^="rf__edge-"]').last().click();

    await expect(page.locator('[data-testid^="connectionHandle"]')).toHaveCount(2);
    await expect(page.locator('[data-testid^="connectionHandle"]').first()).toHaveCSS(
      'background-color',
      'rgb(0, 0, 0)'
    );
    await expect(page.locator('[data-testid^="connectionHandle"]').last()).toHaveCSS(
      'background-color',
      'rgb(0, 0, 0)'
    );
  });
});

test.describe('edge-handle', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeHandlesPosition.zip');
    await expect(page.locator('[data-testid^="explorer://"]')).toBeAttached();
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a diagram has a layout direction to undefined, then there is no unique edge direction', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('handlesPosition');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('UNDEFINED');

    await expect(page.locator('.source_handle_left')).toHaveCount(1);
    await expect(page.locator('.source_handle_right')).toHaveCount(0);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(0);
    await expect(page.locator('.source_handle_top')).toHaveCount(1);
  });

  test('when a diagram has a layout direction to right, then all edge directions are horizontal', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('handlesPosition');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('RIGHT');

    await expect(page.locator('.source_handle_left')).toHaveCount(1);
    await expect(page.locator('.source_handle_right')).toHaveCount(1);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(0);
    await expect(page.locator('.source_handle_top')).toHaveCount(0);
  });

  test('when a diagram has a layout direction to down, then all edge directions are vertical', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('handlesPosition');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('DOWN');

    await expect(page.locator('.source_handle_left')).toHaveCount(0);
    await expect(page.locator('.source_handle_right')).toHaveCount(0);
    await expect(page.locator('.source_handle_bottom')).toHaveCount(1);
    await expect(page.locator('.source_handle_top')).toHaveCount(1);
  });
});

test.describe('edge-handle', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectDomainWithCustomEdge.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Domain');
    await playwrightExplorer.expand('merkle');
    await playwrightExplorer.select('diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a node with a custom handle is resized, then the handle is moved to stay attached to the node', async ({
    page,
  }) => {
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const lastMovingLine = page.locator(`[data-testid="temporary-moving-line-2"]`).first();
    const box = (await lastMovingLine.boundingBox())!;
    await lastMovingLine.hover();
    await page.mouse.down();
    await page.mouse.move(box.x, box.y + 50, { steps: 2 });
    await page.mouse.up();

    const targetNode = new PlaywrightNode(page, 'Target', 'List');
    await targetNode.click();
    await targetNode.resize({ width: 0, height: -75 });

    const targetNodePosition = await targetNode.getReactFlowXYPosition('Target');
    const targetNodeSize = await targetNode.getReactFlowSize('Target');

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    await page.waitForFunction(
      ({ maxCyPos }) => {
        const circle = document.querySelector('.react-flow__edgeupdater-target');
        if (!circle) return false;

        const cyValue = circle ? circle.getAttribute('cy') : null;
        return cyValue && parseFloat(cyValue) < maxCyPos;
      },
      { maxCyPos: targetNodePosition.y + targetNodeSize.height },
      { timeout: 2000 }
    );
  });

  test('when a node with a custom handle is resized and moved, then the handle is moved to stay attached to the node', async ({
    page,
  }) => {
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const lastMovingLine = page.locator(`[data-testid="temporary-moving-line-2"]`).first();
    const box = (await lastMovingLine.boundingBox())!;
    await lastMovingLine.hover();
    await page.mouse.down();
    await page.mouse.move(box.x, box.y + 50, { steps: 2 });
    await page.mouse.up();

    const targetNode = new PlaywrightNode(page, 'Target', 'List');
    await targetNode.click();
    await targetNode.resize({ width: 0, height: 75 }, 'top.left');

    const targetNodePosition = await targetNode.getReactFlowXYPosition('Target');
    const targetNodeSize = await targetNode.getReactFlowSize('Target');

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    await page.waitForFunction(
      ({ maxCyPos }) => {
        const circle = document.querySelector('.react-flow__edgeupdater-target');
        if (!circle) return false;

        const cyValue = circle ? circle.getAttribute('cy') : null;
        return cyValue && parseFloat(cyValue) < maxCyPos;
      },
      { maxCyPos: targetNodePosition.y + targetNodeSize.height },
      { timeout: 2000 }
    );
  });
});
