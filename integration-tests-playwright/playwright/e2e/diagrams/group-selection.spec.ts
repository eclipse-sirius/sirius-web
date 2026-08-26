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

test.describe('diagram - group selection', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectGroupResize.zip');
    await expect(page.locator('[data-testid^="explorer://"]')).toBeAttached();
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Others...');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagramResize diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when selecting several nodes, the last one is always highlighted', async ({ page }) => {
    const playwrightNode1 = new PlaywrightNode(page, 'Entity1 - Resize Both');
    const playwrightNode2 = new PlaywrightNode(page, 'Entity2 - Resize None');
    const playwrightNode3 = new PlaywrightNode(page, 'Entity3 - Resize HORIZONTAL');
    const playwrightNode4 = new PlaywrightNode(page, 'Entity4 - Resize VERTICAL');

    await page.keyboard.down('ControlOrMeta');
    await playwrightNode1.click();
    await new PlaywrightDiagram(page).hideDebugPanel();
    expect(await playwrightNode1.isLastOneSelected());

    await playwrightNode2.click();
    expect(await playwrightNode2.isLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());

    await playwrightNode3.click();
    expect(await playwrightNode3.isLastOneSelected());
    expect(await playwrightNode2.isNotLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());

    await playwrightNode4.click();
    expect(await playwrightNode4.isLastOneSelected());
    expect(await playwrightNode3.isNotLastOneSelected());
    expect(await playwrightNode2.isNotLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());

    await playwrightNode2.click();
    await playwrightNode4.click();
    expect(await playwrightNode3.isLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());
  });

  test('when selecting several nodes with a rectangle, only the node closest to the cursor is highlighted', async ({
    page,
  }) => {
    const playwrightNode1 = new PlaywrightNode(page, 'Entity1 - Resize Both');
    const playwrightNode2 = new PlaywrightNode(page, 'Entity2 - Resize None');
    const playwrightNode3 = new PlaywrightNode(page, 'Entity3 - Resize HORIZONTAL');
    const playwrightNode4 = new PlaywrightNode(page, 'Entity4 - Resize VERTICAL');

    await new PlaywrightDiagram(page).hideDebugPanel();

    await playwrightNode1.waitForAnimationToFinish();
    await playwrightNode3.waitForAnimationToFinish();

    const playwrightNode1BoundingBox = await playwrightNode1.getDOMBoundingBox();
    const playwrightNode3BoundingBox = await playwrightNode3.getDOMBoundingBox();

    const rectangleStart = {
      x: playwrightNode1BoundingBox.x - 10,
      y: playwrightNode1BoundingBox.y - 10,
    };
    const rectangleEnd = {
      x: playwrightNode3BoundingBox.x + playwrightNode3BoundingBox.width / 2,
      y: playwrightNode3BoundingBox.y + playwrightNode3BoundingBox.height + 10,
    };
    const rectangleEndWithOnlyNode1 = {
      x: playwrightNode1BoundingBox.x + playwrightNode1BoundingBox.width + 10,
      y: playwrightNode1BoundingBox.y + playwrightNode1BoundingBox.height + 10,
    };

    await page.keyboard.down('Shift');
    await page.mouse.move(rectangleStart.x, rectangleStart.y);
    await page.mouse.down();
    await page.mouse.move(rectangleEnd.x, rectangleEnd.y, { steps: 10 });

    await expect(playwrightNode1.nodeLocator).toContainClass('selected');
    await expect(playwrightNode2.nodeLocator).toContainClass('selected');
    await expect(playwrightNode3.nodeLocator).toContainClass('selected');
    await expect(playwrightNode4.nodeLocator).not.toContainClass('selected');

    expect(await playwrightNode1.isNotLastOneSelected());
    expect(await playwrightNode2.isNotLastOneSelected());
    expect(await playwrightNode3.isLastOneSelected());

    await page.mouse.move(rectangleEndWithOnlyNode1.x, rectangleEndWithOnlyNode1.y);

    await expect(playwrightNode1.nodeLocator).toContainClass('selected');
    await expect(playwrightNode2.nodeLocator).not.toContainClass('selected');
    await expect(playwrightNode3.nodeLocator).not.toContainClass('selected');
    expect(await playwrightNode1.isLastOneSelected());

    await page.mouse.up();
    await page.keyboard.up('Shift');
  });
});
