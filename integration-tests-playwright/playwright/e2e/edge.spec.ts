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
import { PlaywrightProject } from '../helpers/PlaywrightProject';
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });

    const project = await new PlaywrightProject(request).createProjectFromTemplate('flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a bend point is moved, then the edge path is changed', async ({ page }) => {
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const edgePathBefore = playwrightEdge.getEdgePath();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-3"]`).first();
    const box = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(box.x - 40, box.y + 40, { steps: 2 });
    await page.mouse.up();

    const edgePathAfter = playwrightEdge.getEdgePath();

    expect(edgePathAfter).not.toBe(edgePathBefore);
  });

  test('when last edge segment is moved, then a new bend point is added at the middle of node border', async ({
    page,
  }) => {
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const edgePathBefore = playwrightEdge.getEdgePath();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-3"]`).first();
    const box = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(box.x - 40, box.y + 150, { steps: 2 });
    await page.mouse.up();

    const edgePathAfter = playwrightEdge.getEdgePath();
    expect(edgePathAfter).not.toBe(edgePathBefore);

    const newBendingPoint = page.locator(`[data-testid="bend-point-4"]`).first();
    expect(newBendingPoint).toBeAttached();
    const playwrightTargetNode = new PlaywrightNode(page, 'Processor1');
    const newBendingPointBox = (await newBendingPoint.boundingBox())!;
    const targetNodeBox = await playwrightTargetNode.getDOMBoundingBox();
    expect(newBendingPointBox.x + newBendingPointBox.width / 2).toBe(targetNodeBox.x + targetNodeBox.width / 2);
  });

  test('when moving a node, then custom handle are preserved', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode.move({ x: 200, y: 50 });

    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-1"]`).first();
    const box = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(box.x - 40, box.y - 40, { steps: 2 });
    await page.mouse.up();

    await page.waitForFunction(
      () => {
        const parent = document.querySelector('[data-testid="FreeForm - Processor1"]');
        if (!parent) return false;

        const divHandle = parent.querySelector('.react-flow__handle-left') as HTMLElement;
        return divHandle && divHandle.style.top !== 'auto';
      },
      { timeout: 2000 }
    );

    const divBefore = await page.getByTestId('FreeForm - Processor1').locator('.react-flow__handle-left').first();
    const topValueBefore = await divBefore.evaluate((el) => el.style.top);

    const playwrightProcessorNode = new PlaywrightNode(page, 'Processor1');
    await playwrightProcessorNode.move({ x: 25, y: 25 });

    await page.waitForFunction(
      ({ expectedTopValue }) => {
        const parent = document.querySelector('[data-testid="FreeForm - Processor1"]');
        if (!parent) return false;

        const divHandle = parent.querySelector(
          '.react-flow__handle-left:not([data-handleid^="creationhandle"])'
        ) as HTMLElement;
        return divHandle && divHandle.style.top === expectedTopValue;
      },
      { expectedTopValue: topValueBefore },
      { timeout: 2000 }
    );
  });
});

test.describe('edge', () => {
  let projectId: string;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeOnBorderNode.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Others...');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagramEdgeOnBorderNode diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when moving the source node, then border nodes moved to simplify the edge path', async ({ page }) => {
    const sourceNode = new PlaywrightNode(page, 'Entity1-source');
    const sourceBorderNode = new PlaywrightNode(page, 'bordernode-source');
    const targetNode = new PlaywrightNode(page, 'Entity1-target');
    const targetBorderNode = new PlaywrightNode(page, 'bordernode-target');
    const sourceSizeStep1 = await sourceNode.getReactFlowSize();
    const sourceBorderPositionStep1 = await sourceBorderNode.getReactFlowXYPosition();
    const targetBorderPositionStep1 = await targetBorderNode.getReactFlowXYPosition();
    const targetBorderNodeSizeStep1 = await targetBorderNode.getReactFlowSize();

    const borderNodeGap = -5;
    // First position source node left of target node
    expect(sourceBorderPositionStep1.x).toBe(sourceSizeStep1.width + borderNodeGap);
    expect(targetBorderPositionStep1.x).toBe(-targetBorderNodeSizeStep1.width - borderNodeGap);

    // Second position source node top of target node
    await sourceNode.move({ x: 300, y: -125 });
    await targetNode.move({ x: -150, y: 50 });
    const sourceSizeStep2 = await sourceNode.getReactFlowSize();
    const sourceBorderPositionStep2 = await sourceBorderNode.getReactFlowXYPosition();
    const targetBorderPositionStep2 = await targetBorderNode.getReactFlowXYPosition();
    const targetBorderNodeSizeStep2 = await targetBorderNode.getReactFlowSize();
    expect(sourceBorderPositionStep2.y).toBe(sourceSizeStep2.height + borderNodeGap);
    expect(targetBorderPositionStep2.y).toBe(-targetBorderNodeSizeStep2.height - borderNodeGap);

    // third position source node right of target node
    await targetNode.move({ x: 0, y: -50 });
    await sourceNode.move({ x: 300, y: 10 });
    const targetSizeStep3 = await targetNode.getReactFlowSize();
    const sourceBorderPositionStep3 = await sourceBorderNode.getReactFlowXYPosition();
    const targetBorderPositionStep3 = await targetBorderNode.getReactFlowXYPosition();
    const sourceBorderNodeSizeStep3 = await sourceBorderNode.getReactFlowSize();
    expect(sourceBorderPositionStep3.x).toBe(-sourceBorderNodeSizeStep3.width - borderNodeGap);
    expect(targetBorderPositionStep3.x).toBe(targetSizeStep3.width + borderNodeGap);
  });
});
