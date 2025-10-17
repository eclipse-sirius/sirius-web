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
import { test, expect } from '@playwright/test';
import { PlaywrightProject } from '../helpers/PlaywrightProject';
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../helpers/PlaywrightNode';

test.describe('diagram - make same size', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
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

    await c1Node.click();
    await c2Node.controlClick();

    const c1PositionBefore = await c1Node.getReactFlowXYPosition('C1', false);
    const c1SizeBefore = await c1Node.getReactFlowSize('C1', false);
    const c2PositionBefore = await c2Node.getReactFlowXYPosition('C2', false);

    await c1Node.openPalette();
    await expect(page.getByTestId('GroupPalette')).toBeAttached();
    await page.getByTestId('GroupPalette').getByTestId('expand').click();
    await page.getByTestId('GroupPalette').getByTestId('Make same size').click();

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
