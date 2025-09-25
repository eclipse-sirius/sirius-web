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

test.describe('selection', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProjectFromTemplate('flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('selecting a node on a diagram does not select/reveal it in the explorer', async ({ page }) => {
    await new PlaywrightNode(page, 'CompositeProcessor1').click();

    const explorer = new PlaywrightExplorer(page);
    const treeItem = await explorer.getTreeItemLabel('CompositeProcessor1');
    await expect(treeItem).not.toBeVisible();
  });

  test('the explorer can update its local selection with an explicit button click', async ({ page }) => {
    await new PlaywrightNode(page, 'CompositeProcessor1').click();

    const explorer = new PlaywrightExplorer(page);
    let treeItem = await explorer.getTreeItemLabel('CompositeProcessor1');
    await expect(treeItem).not.toBeVisible();

    await explorer.revealGlobalSelection();
    treeItem = await explorer.getTreeItemLabel('CompositeProcessor1');
    await expect(treeItem).toBeVisible();
    const selectedItem = await page.getByTestId('selected');
    await expect(selectedItem).toHaveText('CompositeProcessor1');
  });

  test('the diagram can push its local selection to the explorer', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();

    const explorer = new PlaywrightExplorer(page);
    let treeItem = await explorer.getTreeItemLabel('CompositeProcessor1');
    await expect(treeItem).not.toBeVisible();

    await playwrightNode.openPalette();
    await page.getByTestId('toolSection-Show in').click();
    await page.getByTestId('push-diagram-selection-to-explorer').click();

    treeItem = await explorer.getTreeItemLabel('CompositeProcessor1');
    await expect(treeItem).toBeVisible();
    const selectedItem = await page.getByTestId('selected');
    await expect(selectedItem).toHaveText('CompositeProcessor1');
  });

  test('selecting an element in the explorer does not select it in the diagram', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.select('CompositeProcessor1');

    const node = await new PlaywrightNode(page, 'CompositeProcessor1');
    await expect(node.nodeLocator).not.toContainClass('selected');
  });

  test('the diagram can update its local selection with an explicit button click', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.select('CompositeProcessor1');

    let node = await new PlaywrightNode(page, 'CompositeProcessor1');
    await expect(node.nodeLocator).not.toContainClass('selected');

    await page.getByTestId('diagram-reveal-selection').click();

    node = await new PlaywrightNode(page, 'CompositeProcessor1');
    await expect(node.nodeLocator).toContainClass('selected');
  });

  test('the explorer can push its local selection to the diagram', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.select('CompositeProcessor1');

    let node = await new PlaywrightNode(page, 'CompositeProcessor1');
    await expect(node.nodeLocator).not.toContainClass('selected');

    await explorer.explorerLocator.getByTestId(`CompositeProcessor1-more`).click();
    await page.getByTestId(`push-selection-to-Topography`).click();

    node = await new PlaywrightNode(page, 'CompositeProcessor1');
    await expect(node.nodeLocator).toContainClass('selected');
  });
});
