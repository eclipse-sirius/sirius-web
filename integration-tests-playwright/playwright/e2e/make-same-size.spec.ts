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

test.describe('diagram - make same size', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    await new PlaywrightProject(request).uploadProject(page, 'projectMakeSameSize.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Papaya');
    await playwrightExplorer.expand('Project');
    await playwrightExplorer.expand('Component');
    await playwrightExplorer.select('Component class diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a make same size is triggered, then all nodes preserve their layout', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const c1Node = new PlaywrightNode(page, 'C1', 'List');
    const c2Node = new PlaywrightNode(page, 'C2', 'List');

    await c2Node.click();
    await c1Node.controlClick();

    const c1PositionBefore = await c1Node.getReactFlowXYPosition('C1', false);
    const c1SizeBefore = await c1Node.getReactFlowSize('C1', false);
    const c2PositionBefore = await c2Node.getReactFlowXYPosition('C2', false);

    await c2Node.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Make same size').click();

    const c1PositionAfter = await c1Node.getReactFlowXYPosition('C1', false);
    const c1SizeAfter = await c1Node.getReactFlowSize('C1', false);
    const c2PositionAfter = await c2Node.getReactFlowXYPosition('C2', false);
    const c2SizeAfter = await c2Node.getReactFlowSize('C2', false);

    expect(c1PositionBefore.x).toBe(c1PositionAfter.x);
    expect(c1PositionBefore.y).toBe(c1PositionAfter.y);
    expect(c1SizeBefore.width).toBe(c1SizeAfter.width);
    expect(c1SizeBefore.height).toBe(c1SizeAfter.height);
    expect(c2PositionBefore.x).toBe(c2PositionAfter.x);
    expect(c2PositionBefore.y).toBe(c2PositionAfter.y);
    expect(c2SizeAfter.width).toBe(c1SizeBefore.width);
    expect(c2SizeAfter.height).toBe(c1SizeBefore.height);

    await page.getByTestId('fit-to-screen').click();

    const c2AttributesNode = page.locator(`[data-testid="List - Attributes"]`).nth(1).locator('..');
    await c2AttributesNode.click({ position: { x: 10, y: 10 } });
    const c2AttributesPosition = await c2Node.getReactFlowXYPosition('Attributes', false);

    expect(c2AttributesPosition.x).toBe(1);
  });
});
