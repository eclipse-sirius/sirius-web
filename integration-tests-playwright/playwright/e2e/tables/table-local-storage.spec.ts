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
import { expect, test, type Page } from '@playwright/test';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightTable } from '../../helpers/PlaywrightTable';

const reopenTable = async (page: Page, label: string): Promise<void> => {
  const explorer = new PlaywrightExplorer(page);
  const item = await explorer.getTreeItemLabel(label);
  if (!(await item.isVisible())) {
    await explorer.expand('Package');
  }
  await explorer.select(label);
  await expect(new PlaywrightTable(page).rowHeader('Class 1')).toBeVisible();
};

const getStorageKey = async (page: Page): Promise<string> => {
  await expect
    .poll(() =>
      page.evaluate(
        () => Object.keys(localStorage).filter((key) => key.startsWith('sirius-table-interaction-state-')).length
      )
    )
    .toBe(1);
  return page.evaluate(() => {
    const key = Object.keys(localStorage).find((candidate) => candidate.startsWith('sirius-table-interaction-state-'));
    if (!key) {
      throw new Error('Missing table interaction state');
    }
    return key;
  });
};

test.describe('table local storage', () => {
  let projectId: string | null = null;

  test.beforeEach(async ({ page, request }) => {
    projectId = null;
    const project = await new PlaywrightProject(request).createProject('Papaya', 'papaya-empty');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    const explorer = new PlaywrightExplorer(page);
    await explorer.uploadDocument('tablePapayaPackage.xml');
    await explorer.expand('tablePapayaPackage.xml');
    await explorer.expand('Project');
    await explorer.expand('Component');
    await explorer.createRepresentation('Package', 'Papaya package table', 'table');
    await expect(new PlaywrightTable(page).rowHeader('Class 1')).toBeVisible();
  });

  test.afterEach(async ({ request }) => {
    if (projectId !== null) {
      await new PlaywrightProject(request).deleteProject(projectId);
    }
  });

  test('restores expanded rows after closing and reopening the table', async ({ page }) => {
    const table = new PlaywrightTable(page);
    await expect(table.rowHeader('Operation 1()')).not.toBeVisible();
    await table.toggleRow(page, 'Class 1');
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
    await page.getByTestId('close-representation-tab-table').click();
    await expect(table.tableLocator).not.toBeVisible();
    await reopenTable(page, 'table');
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
  });

  test('keeps expanded rows from both pages when navigating back and forth', async ({ page }) => {
    const table = new PlaywrightTable(page);
    await table.changeRowPerPage(5);
    await expect(table.tableLocator.getByTestId('table-row-header')).toHaveCount(5);
    await table.toggleRow(page, 'Class 1');
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
    await table.navigateNextPage();
    await expect(table.rowHeader('Record Record 1')).toBeVisible();
    await table.toggleRow(page, 'Record Record 1');
    await expect(table.rowHeader('Operation1()')).toBeVisible();
    await table.navigatePrevPage();
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
    await expect(table.rowHeader('Record Record 1')).not.toBeVisible();
    await table.navigateNextPage();
    await expect(table.rowHeader('Operation1()')).toBeVisible();
  });

  test('restores expand all and collapse all after a reload', async ({ page }) => {
    const table = new PlaywrightTable(page);
    await table.tableLocator.getByTestId('KeyboardDoubleArrowRightIcon').locator('..').click();
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
    await expect(table.rowHeader('Operation1()')).toBeVisible();
    await page.reload();
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
    await expect(table.rowHeader('Operation1()')).toBeVisible();
    await table.tableLocator.getByTestId('KeyboardDoubleArrowDownIcon').locator('..').click();
    await expect(table.rowHeader('Operation 1()')).not.toBeVisible();
    await page.reload();
    await expect(table.rowHeader('Class 1')).toBeVisible();
    await expect(table.rowHeader('Operation 1()')).not.toBeVisible();
    await expect(table.rowHeader('Operation1()')).not.toBeVisible();
    await expect(table.tableLocator.getByTestId('KeyboardDoubleArrowRightIcon')).toBeVisible();
  });

  test(`ignores invalid stored state: {"expanded":[42],"expandAll":false}`, async ({ page }) => {
    const table = new PlaywrightTable(page);
    const key = await getStorageKey(page);
    const invalid = '{"expanded":[42],"expandAll":false}';
    await page.evaluate(({ key, invalid }) => localStorage.setItem(key, invalid), { key, invalid });
    await page.reload();
    await expect(table.rowHeader('Class 1')).toBeVisible();
    await expect(table.rowHeader('Operation 1()')).not.toBeVisible();
    await table.toggleRow(page, 'Class 1');
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
    await page.reload();
    await expect(table.rowHeader('Operation 1()')).toBeVisible();
  });
});
