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

test.describe('explorer - drag and drop', () => {
  let projectId: string;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Playwright - explorer-dnd', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit/`);
    await new PlaywrightExplorer(page).createNewModel('robot', 'robot_flow');
    await expect(page.getByTestId('create-new-model')).not.toBeAttached();
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an object is dropped onto another tree item, then it moves into that item', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('robot');
    await explorer.expand('System');
    await explorer.expand('Central_Unit');
    const radar = await explorer.getTreeItemLabel('Radar');
    await expect(radar).not.toBeAttached();

    await explorer.expand('Capture_Subsystem');
    await expect(radar).toBeVisible();
    await explorer.dragTo('Radar', explorer.explorerLocator.getByTestId('Central_Unit-fullrow'));
    const captureSubsystem = await explorer.getTreeItemLabel('Capture_Subsystem');
    await captureSubsystem.dblclick();
    await expect(captureSubsystem).toHaveAttribute('data-expanded', 'false');
    await expect(radar).toBeVisible();

    const centralUnit = await explorer.getTreeItemLabel('Central_Unit');
    await centralUnit.dblclick();
    await expect(centralUnit).toHaveAttribute('data-expanded', 'false');
    await expect(radar).not.toBeAttached();
    await explorer.expand('Central_Unit');
    await expect(radar).toBeVisible();
  });
});
