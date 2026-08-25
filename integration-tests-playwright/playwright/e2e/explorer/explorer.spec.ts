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

import { expect, test } from '@playwright/test';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('explorer', () => {
  let projectId: string;
  let explorer: PlaywrightExplorer;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Playwright - explorer', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit/`);

    explorer = new PlaywrightExplorer(page);
    await explorer.createNewModel('robot', 'robot_flow');
    await expect(page.getByTestId('create-new-model')).not.toBeAttached();
    await expect(await explorer.getTreeItemLabel('robot')).toBeVisible();
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an item is double-clicked, then it expands or collapses', async () => {
    await expect(await explorer.getTreeItemLabel('robot')).toHaveAttribute('data-expanded', 'false');
    await explorer.expand('robot');
    await expect(await explorer.getTreeItemLabel('System')).toBeVisible();
    await expect(await explorer.getTreeItemLabel('System')).toHaveAttribute('data-expanded', 'false');

    await explorer.expand('System');
    await expect(await explorer.getTreeItemLabel('System')).toHaveAttribute('data-expanded', 'true');
    for (const label of ['Central_Unit', 'Capture_Subsystem', 'Wifi']) {
      await expect(await explorer.getTreeItemLabel(label)).toBeVisible();
    }

    await (await explorer.getTreeItemLabel('System')).dblclick();
    await expect(await explorer.getTreeItemLabel('System')).toHaveAttribute('data-expanded', 'false');
    for (const label of ['Central_Unit', 'Capture_Subsystem', 'Wifi']) {
      await expect(await explorer.getTreeItemLabel(label)).not.toBeAttached();
    }
  });

  test('when an item is toggled, then it expands or collapses without changing the selection', async () => {
    await explorer.expand('robot');
    await expect(await explorer.getTreeItemLabel('System')).toBeVisible();
    await explorer.select('robot');
    const selectedItems = explorer.explorerLocator.getByTestId('selected');
    await expect(selectedItems).toHaveCount(1);
    await expect(selectedItems).toHaveAttribute('data-treeitemlabel', 'robot');

    await explorer.explorerLocator.getByTestId('System-toggle').click();
    for (const label of ['Central_Unit', 'Capture_Subsystem', 'Wifi']) {
      await expect(await explorer.getTreeItemLabel(label)).toBeVisible();
    }
    await expect(selectedItems).toHaveCount(1);
    await expect(selectedItems).toHaveAttribute('data-treeitemlabel', 'robot');

    await explorer.explorerLocator.getByTestId('System-toggle').click();
    for (const label of ['Central_Unit', 'Capture_Subsystem', 'Wifi']) {
      await expect(await explorer.getTreeItemLabel(label)).not.toBeAttached();
    }
    await expect(selectedItems).toHaveCount(1);
    await expect(selectedItems).toHaveAttribute('data-treeitemlabel', 'robot');
  });

  for (const side of ['right', 'left']) {
    test(`when the ${side} empty area of an item is clicked, then the item is selected`, async () => {
      await explorer.expand('robot');
      await explorer.expand('System');
      await expect(await explorer.getTreeItemLabel('Central_Unit')).toBeVisible();

      const row = explorer.explorerLocator.getByTestId('Central_Unit-fullrow');
      await expect(row).toBeVisible();
      const position = await row.evaluate((element, side) => {
        return { x: side === 'right' ? element.clientWidth - 2 : 2, y: element.clientHeight / 2 };
      }, side);
      await row.click({ position });

      const selectedItems = explorer.explorerLocator.getByTestId('selected');
      await expect(selectedItems).toHaveCount(1);
      await expect(selectedItems).toHaveAttribute('data-treeitemlabel', 'Central_Unit');
    });
  }

  test('when a top-level element is created, then it is selected', async ({ page }) => {
    await explorer.openPalette('robot');
    await page.getByTestId('new-object').click();
    await page.getByTestId('domain').click();
    await page.locator('[data-value="http://www.obeo.fr/dsl/designer/sample/flow"]').click();
    await page.getByTestId('type').click();
    await page.locator('[data-value="CompositeProcessor"]').click();
    await page.getByTestId('create-object').click();

    await expect(await explorer.getTreeItemLabel('CompositeProcessor')).toBeVisible();
    const selectedItems = explorer.explorerLocator.getByTestId('selected');
    await expect(selectedItems).toHaveCount(1);
    await expect(selectedItems).toHaveAttribute('data-treeitemlabel', 'CompositeProcessor');
  });

  test('when a child element is created, then it is selected', async () => {
    await explorer.expand('robot');
    await expect(await explorer.getTreeItemLabel('System')).toBeVisible();
    await explorer.createNewObject('System', 'elements-Processor');

    await expect(await explorer.getTreeItemLabel('Processor')).toBeVisible();
    const selectedItems = explorer.explorerLocator.getByTestId('selected');
    await expect(selectedItems).toHaveCount(1);
    await expect(selectedItems).toHaveAttribute('data-treeitemlabel', 'Processor');
  });

  test('when a project is read-only, then all editing actions are disabled', async ({ page }) => {
    // This name activates DisableEditCapabilityVoter in the backend's e2e profile.
    const readOnlyProjectName = 'Cypress - Disabled Edit Project';
    await page.getByTestId('navigation-bar').getByTestId('more').click();
    await page.getByTestId('rename').click();
    const dialog = page.getByTestId('rename-project-dialog');
    await dialog.getByTestId('inner-rename-textfield').fill(readOnlyProjectName);
    await dialog.getByTestId('rename-project').click();
    await expect(dialog).not.toBeAttached();
    await expect(page.getByTestId('navbar-title')).toHaveText(readOnlyProjectName);
    await page.reload();

    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await expect(await explorer.getTreeItemLabel('NewSystem')).toBeVisible();
    await explorer.openPalette('NewSystem');

    const palette = page.getByTestId('Palette');
    for (const action of ['new-object', 'new-representation', 'rename-tree-item', 'delete']) {
      await expect(palette.getByTestId(action)).toBeDisabled();
    }
  });
});
