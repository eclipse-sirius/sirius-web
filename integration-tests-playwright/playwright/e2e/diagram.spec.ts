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
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightProject } from '../helpers/PlaywrightProject';

test.describe('diagram', () => {
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

  test('change edge path', async ({ page }) => {
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

  test('moving a node by click', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');

    const reactFlowXYPositionBefore = await playwrightNode.getReactFlowXYPosition();

    await playwrightNode.move({ x: 450, y: 450 });

    const reactFlowXYPositionAfter = await playwrightNode.getReactFlowXYPosition();

    expect(reactFlowXYPositionAfter.x).toBeGreaterThan(reactFlowXYPositionBefore.x);
    expect(reactFlowXYPositionAfter.y).toBeGreaterThan(reactFlowXYPositionBefore.y);
  });

  test('resizing a node by click', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');

    const reactFlowSizeBefore = await playwrightNode.getReactFlowSize();

    await playwrightNode.resize({ height: 20, width: 50 });

    const reactFlowSizeAfter = await playwrightNode.getReactFlowSize();

    expect(reactFlowSizeAfter.height).toBeGreaterThan(reactFlowSizeBefore.height);
    expect(reactFlowSizeAfter.width).toBeGreaterThan(reactFlowSizeBefore.width);
  });
});
