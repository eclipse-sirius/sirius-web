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
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('edge');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a node overlap a bend point, then the edge path is reset', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramSimpleEdge.xml');
    await playwrightExplorer.expand('diagramSimpleEdge.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramEdges - simple edges', 'diagram');

    const playwrightNode = new PlaywrightNode(page, 'Entity2');
    await playwrightNode.click();
    await playwrightNode.move({ x: 250, y: 200 });

    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.click();

    const firstBendingPoint = page.locator(`[data-testid="bend-point-0"]`).first();
    const firstBendingPointBox = (await firstBendingPoint.boundingBox())!;
    await firstBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(firstBendingPointBox.x + 50, firstBendingPointBox.y + 50, { steps: 2 });
    await page.mouse.up();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-1"]`).first();
    const lastBendingPointBox = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(lastBendingPointBox.x + 75, lastBendingPointBox.y + 50, { steps: 2 });
    await page.mouse.up();

    playwrightEdge.openPalette();
    expect(page.getByTestId('Reset-path')).toBeAttached();
    expect(page.getByTestId('Reset-handles')).toBeAttached();
    playwrightEdge.closePalette();

    await playwrightNode.click();
    await playwrightNode.move({ x: -75, y: 25 });

    playwrightEdge.openPalette();
    expect(page.getByTestId('Reset-path')).not.toBeAttached();
    expect(page.getByTestId('Reset-handles')).not.toBeAttached();
    playwrightEdge.closePalette();
  });
});
