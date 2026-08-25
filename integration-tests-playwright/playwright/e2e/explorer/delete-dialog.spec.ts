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

const isDeletionConfirmationDialogDisabled = async (page: Page, projectId: string): Promise<boolean> => {
  return await page.evaluate((id: string): boolean => {
    const disabledIds: string[] = JSON.parse(localStorage.getItem('sirius-confirmation-dialog-disabled') ?? '[]');
    return disabledIds.includes(id);
  }, projectId);
};

const requestDeletion = async (page: Page): Promise<void> => {
  const explorer = new PlaywrightExplorer(page);
  await explorer.expand('robot');
  await explorer.expand('System');
  await explorer.openPalette('Central_Unit');
  await page.getByTestId('delete').click();
};

const createRobotDocument = async (page: Page, projectId: string): Promise<void> => {
  await page.goto(`/projects/${projectId}/edit/`);
  const explorer = new PlaywrightExplorer(page);
  await explorer.createNewModel('robot', 'robot_flow');
  await expect(page.getByTestId('create-new-model')).not.toBeAttached();
  await expect(await explorer.getTreeItemLabel('robot')).toBeVisible();
};

const disableDeletionConfirmationDialog = async (page: Page, projectId: string): Promise<void> => {
  await page.evaluate((id: string): void => {
    localStorage.setItem('sirius-confirmation-dialog-disabled', JSON.stringify([id]));
  }, projectId);
  await page.reload();
  await expect(await new PlaywrightExplorer(page).getTreeItemLabel('robot')).toBeVisible();
};

test.describe('explorer - deletion confirmation dialog', () => {
  let projectId: string;
  let projectId2: string;

  test.beforeEach(async ({ page, request }) => {
    const project = new PlaywrightProject(request);
    projectId = (await project.createProject('Playwright - explorer-delete-dialog-1', 'flow-template')).projectId;
    projectId2 = (await project.createProject('Playwright - explorer-delete-dialog-2', 'flow-template')).projectId;
    await createRobotDocument(page, projectId2);
    await createRobotDocument(page, projectId);
  });

  test.afterEach(async ({ request }) => {
    const project = new PlaywrightProject(request);
    await Promise.all([project.deleteProject(projectId), project.deleteProject(projectId2)]);
  });

  test('when a project is created, then the confirmation dialog is enabled by default', async ({ page }) => {
    expect(await isDeletionConfirmationDialogDisabled(page, projectId)).toBe(false);
  });

  test('when a model element is deleted, then the confirmation dialog is displayed', async ({ page }) => {
    await requestDeletion(page);
    await expect(page.getByTestId('confirmation-dialog')).toBeVisible();
    await expect(await new PlaywrightExplorer(page).getTreeItemLabel('Central_Unit')).toBeVisible();
  });

  test('when confirmation is disabled, then the element is deleted without displaying the dialog', async ({ page }) => {
    await disableDeletionConfirmationDialog(page, projectId);
    await requestDeletion(page);
    await expect(await new PlaywrightExplorer(page).getTreeItemLabel('Central_Unit')).not.toBeAttached();
    await expect(page.getByTestId('confirmation-dialog')).not.toBeAttached();
  });

  test('when confirmation is disabled for one project, then it remains enabled for another project', async ({
    page,
  }) => {
    await disableDeletionConfirmationDialog(page, projectId);
    expect(await isDeletionConfirmationDialogDisabled(page, projectId2)).toBe(false);
  });

  test('when an element is deleted in another project, then the confirmation dialog is still displayed', async ({
    page,
  }) => {
    await disableDeletionConfirmationDialog(page, projectId);
    await page.goto(`/projects/${projectId2}/edit/`);
    await requestDeletion(page);
    await expect(page.getByTestId('confirmation-dialog')).toBeVisible();
    await expect(await new PlaywrightExplorer(page).getTreeItemLabel('Central_Unit')).toBeVisible();
  });
});
