/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';

test.describe('diagram - layout tool', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectNodeLayoutTool.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.expand('NewSystem');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an align left tool is trigger, then all nodes shared the same left coordinate', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('Topography');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const node1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const node2 = new PlaywrightNode(page, 'CompositeProcessor2');
    const node3 = new PlaywrightNode(page, 'CompositeProcessor3');

    await node1.click();
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });
    await node2.controlClick();
    await node3.controlClick();

    const node1XYPositionInitial = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node2XYPositionInitial = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node3XYPositionInitial = await node3.getReactFlowXYPosition('CompositeProcessor3', false);

    await node3.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Align left').click();
    await node1.waitForAnimationToFinish();

    const node1XYPosition = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node2XYPosition = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node3XYPosition = await node3.getReactFlowXYPosition('CompositeProcessor3', false);

    expect(node1XYPosition.x).toBe(node2XYPosition.x);
    expect(node1XYPosition.x).toBe(node3XYPosition.x);

    expect(node1XYPosition.y).toBe(node1XYPositionInitial.y);
    expect(node2XYPosition.y).toBe(node2XYPositionInitial.y);
    expect(node3XYPosition.y).toBe(node3XYPositionInitial.y);
  });

  test('when an align right tool is trigger, then all nodes shared the same right coordinate', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('Topography');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const node1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const node2 = new PlaywrightNode(page, 'CompositeProcessor2');
    const node3 = new PlaywrightNode(page, 'CompositeProcessor3');

    await node1.click();
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });
    await node2.controlClick();
    await node3.controlClick();

    const node1XYPositionInitial = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node2XYPositionInitial = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node3XYPositionInitial = await node3.getReactFlowXYPosition('CompositeProcessor3', false);

    await node3.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Align right').click();
    await node1.waitForAnimationToFinish();

    const node1XYPosition = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node1Size = await node1.getReactFlowSize('CompositeProcessor1', false);
    const node2XYPosition = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node2Size = await node2.getReactFlowSize('CompositeProcessor2', false);
    const node3XYPosition = await node3.getReactFlowXYPosition('CompositeProcessor3', false);
    const node3Size = await node3.getReactFlowSize('CompositeProcessor3', false);

    expect(node1XYPosition.x + node1Size.width).toBe(node2XYPosition.x + node2Size.width);
    expect(node1XYPosition.x + node1Size.width).toBe(node3XYPosition.x + node3Size.width);

    expect(node1XYPosition.y).toBe(node1XYPositionInitial.y);
    expect(node2XYPosition.y).toBe(node2XYPositionInitial.y);
    expect(node3XYPosition.y).toBe(node3XYPositionInitial.y);
  });

  test('when an align top tool is trigger, then all nodes shared the same top coordinate', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('Topography');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const node1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const node2 = new PlaywrightNode(page, 'CompositeProcessor2');
    const node3 = new PlaywrightNode(page, 'CompositeProcessor3');

    await node1.click();
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });
    await node2.controlClick();
    await node3.controlClick();

    const node1XYPositionInitial = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node2XYPositionInitial = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node3XYPositionInitial = await node3.getReactFlowXYPosition('CompositeProcessor3', false);

    await node3.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Align top').click();
    await node1.waitForAnimationToFinish();

    const node1XYPosition = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node2XYPosition = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node3XYPosition = await node3.getReactFlowXYPosition('CompositeProcessor3', false);

    expect(node1XYPosition.y).toBe(node2XYPosition.y);
    expect(node1XYPosition.y).toBe(node3XYPosition.y);

    expect(node1XYPosition.x).toBe(node1XYPositionInitial.x);
    expect(node2XYPosition.x).toBe(node2XYPositionInitial.x);
    expect(node3XYPosition.x).toBe(node3XYPositionInitial.x);
  });

  test('when an align bottom tool is trigger, then all nodes shared the same bottom coordinate', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('Topography');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const node1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const node2 = new PlaywrightNode(page, 'CompositeProcessor2');
    const node3 = new PlaywrightNode(page, 'CompositeProcessor3');

    await node1.click();
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });
    await node2.controlClick();
    await node3.controlClick();

    const node1XYPositionInitial = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node2XYPositionInitial = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node3XYPositionInitial = await node3.getReactFlowXYPosition('CompositeProcessor3', false);

    await node3.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Align bottom').click();
    await node1.waitForAnimationToFinish();

    const node1XYPosition = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node1Size = await node1.getReactFlowSize('CompositeProcessor1', false);
    const node2XYPosition = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node2Size = await node2.getReactFlowSize('CompositeProcessor2', false);
    const node3XYPosition = await node3.getReactFlowXYPosition('CompositeProcessor3', false);
    const node3Size = await node3.getReactFlowSize('CompositeProcessor3', false);

    expect(node1XYPosition.y + node1Size.height).toBe(node2XYPosition.y + node2Size.height);
    expect(node1XYPosition.y + node1Size.height).toBe(node3XYPosition.y + node3Size.height);

    expect(node1XYPosition.x).toBe(node1XYPositionInitial.x);
    expect(node2XYPosition.x).toBe(node2XYPositionInitial.x);
    expect(node3XYPosition.x).toBe(node3XYPositionInitial.x);
  });

  test('when an arrange in column tool is trigger, then all nodes are placed in the same column', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('Topography');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const node1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const node2 = new PlaywrightNode(page, 'CompositeProcessor2');
    const node3 = new PlaywrightNode(page, 'CompositeProcessor3');

    await node1.click();
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });
    await node2.controlClick();
    await node3.controlClick();

    await node3.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Arrange in column').click();
    await node1.waitForAnimationToFinish();

    const node1XYPosition = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node1Size = await node1.getReactFlowSize('CompositeProcessor1', false);
    const node2XYPosition = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node2Size = await node2.getReactFlowSize('CompositeProcessor2', false);
    const node3XYPosition = await node3.getReactFlowXYPosition('CompositeProcessor3', false);

    const arrangeGapBetweenElements: number = 32;

    expect(node1XYPosition.x).toBe(node2XYPosition.x);
    expect(node1XYPosition.x).toBe(node3XYPosition.x);

    expect(node2XYPosition.y).toBe(node1XYPosition.y + node1Size.height + arrangeGapBetweenElements);
    expect(node3XYPosition.y).toBe(node2XYPosition.y + node2Size.height + arrangeGapBetweenElements);
  });

  test('when an arrange in row tool is trigger, then all nodes are placed in the same row', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('Topography');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const node1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const node2 = new PlaywrightNode(page, 'CompositeProcessor2');
    const node3 = new PlaywrightNode(page, 'CompositeProcessor3');

    await node1.click();
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });
    await node2.controlClick();
    await node3.controlClick();

    await node3.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Arrange in row').click();
    await node1.waitForAnimationToFinish();

    const node1XYPosition = await node1.getReactFlowXYPosition('CompositeProcessor1', false);
    const node1Size = await node1.getReactFlowSize('CompositeProcessor1', false);
    const node2XYPosition = await node2.getReactFlowXYPosition('CompositeProcessor2', false);
    const node3XYPosition = await node3.getReactFlowXYPosition('CompositeProcessor3', false);
    const node3Size = await node2.getReactFlowSize('CompositeProcessor3', false);

    const arrangeGapBetweenElements: number = 32;

    expect(node1XYPosition.y).toBe(node2XYPosition.y);
    expect(node1XYPosition.y).toBe(node3XYPosition.y);

    expect(node3XYPosition.x).toBe(node1XYPosition.x + node1Size.width + arrangeGapBetweenElements);
    expect(node2XYPosition.x).toBe(node3XYPosition.x + node3Size.width + arrangeGapBetweenElements);
  });
});
