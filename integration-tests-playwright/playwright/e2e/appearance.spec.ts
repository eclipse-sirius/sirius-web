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
import { PlaywrightLabel } from '../helpers/PlaywrightLabel';
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightProject } from '../helpers/PlaywrightProject';

test.describe('appearance', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProjectFromTemplate('Flow', 'flow-template', [PlaywrightProject.FLOW_NATURE]);
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('change outside label appearance of an image node', async ({ page }) => {
    const playwrightLabel = new PlaywrightLabel(page, 'DataSource1');
    const fontSizeBefore = await playwrightLabel.getFontSize();
    expect(fontSizeBefore).toBe('14px');
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    await playwrightNode.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Font Size"] input').fill('16');
    await playwrightNode.closePalette();
    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label - DataSource1"]`);
        if (!label) {
          return false;
        }
        const style = window.getComputedStyle(label);
        return style.fontSize === '16px';
      },
      { timeout: 2000 }
    );
  });

  test('change edge appearance', async ({ page }) => {
    //Move the node so it's easier to select the edge
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode.move({ x: 250, y: 0 });

    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();
    const edgeStyle = await playwrightEdge.getEdgeStyle();
    await expect(edgeStyle).toHaveCSS('stroke-width', '1px');
    await expect(edgeStyle).toHaveCSS('stroke', 'rgb(190, 26, 120)');

    await playwrightEdge.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Color"] input').fill('red');
    await page.locator('[data-testid="toolSection-Appearance-Size"] input').fill('5');
    await page.locator('[data-testid="toolSection-Appearance-Size"] input').press('Enter');
    await playwrightEdge.closePalette();

    await playwrightNode.click();
    await expect(edgeStyle).toHaveCSS('stroke-width', '5px');
    await expect(edgeStyle).toHaveCSS('stroke', 'rgb(255, 0, 0)');

    //Reset the appearance
    await playwrightEdge.click();
    await playwrightEdge.isSelected();
    await playwrightEdge.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Color-Reset"]').click();
    await page.locator('[data-testid="toolSection-Appearance-Size-Reset"]').click();
    await page.waitForFunction(
      () => {
        const colorInput: HTMLInputElement | null = document.querySelector(
          '[data-testid="toolSection-Appearance-Color"] input'
        );
        const sizeInput: HTMLInputElement | null = document.querySelector(
          '[data-testid="toolSection-Appearance-Size"] input'
        );
        return colorInput && sizeInput && colorInput.value === '#B1BCBE' && sizeInput && sizeInput.value === '1';
      },
      { timeout: 2000 }
    );
  });

  test('change edge type appearance', async ({ page }) => {
    //Move the node so it's easier to select the edge
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode.move({ x: 250, y: 0 });

    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();
    const edgePathBefore = await playwrightEdge.getEdgePath();

    await playwrightEdge.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Edge Type"]').click();
    await page.waitForSelector('.MuiMenu-paper');
    await page.locator('[data-value="SmartManhattan"]').click();

    await page.waitForFunction(
      ({ initialPath }) => {
        const path = document.querySelector('path.react-flow__edge-path');
        if (!path) {
          return false;
        }
        return initialPath !== path.getAttribute('d');
      },
      { initialPath: edgePathBefore },
      { timeout: 2000 }
    );
  });

  test('change edge center label appearance', async ({ page }) => {
    //Move the node so it's easier to select the edge
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode.move({ x: 250, y: 0 });

    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.click();
    await playwrightEdge.isSelected();
    await playwrightEdge.openPalette();

    const playwrightLabel = new PlaywrightLabel(page, '6');
    const fontSizeBefore = await playwrightLabel.getFontSize();
    expect(fontSizeBefore).toBe('14px');

    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Font Size"] input').fill('16');

    //Unselect the edge by selecting the node in order to test the validation on blur
    await playwrightNode.click();
    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label - 6"]`);
        if (!label) {
          return false;
        }
        const style = window.getComputedStyle(label);
        return style.fontSize === '16px';
      },
      { timeout: 2000 }
    );
  });

  test('change label visibility', async ({ page }) => {
    const label = page.locator(`[data-testid="Label - DataSource1"]`);
    await expect(label).toBeVisible();

    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    await playwrightNode.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Hide"]').click();
    await playwrightNode.closePalette();
    await expect(label).not.toBeVisible();
  });
});
