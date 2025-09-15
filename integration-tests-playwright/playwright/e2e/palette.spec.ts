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
import { PlaywrightNode } from '../helpers/PlaywrightNode';

test.describe('diagram - palette', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProjectFromTemplate('flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('When align tool is triggered on two elements, then they share same position', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    const playwrightNode2 = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode2.controlClick();
    await playwrightNode2.openPalette();
    await expect(page.getByTestId('GroupPalette')).toBeAttached();
    await expect(page.getByTestId('GroupPalette').getByTestId('Align left')).toBeAttached();
    await expect(page.getByTestId('GroupPalette').getByTestId('Align bottom')).not.toBeAttached();
    await page.getByTestId('GroupPalette').getByTestId('expand').click();
    await expect(page.getByTestId('GroupPalette').getByTestId('Align bottom')).toBeAttached();
    await page.getByTestId('GroupPalette').getByTestId('Align bottom').click();
    const playwrightNodeXYPosition = await playwrightNode.getReactFlowXYPosition(0, false);
    const playwrightNode2XYPosition = await playwrightNode2.getReactFlowXYPosition(1, false);
    const playwrightNodeSize = await playwrightNode.getReactFlowSize(0, false);
    const playwrightNode2Size = await playwrightNode2.getReactFlowSize(1, false);
    expect(playwrightNodeXYPosition.y + playwrightNodeSize.height).toBe(
      playwrightNode2XYPosition.y + playwrightNode2Size.height
    );
    //Check the last tool used is the one proposed as quick tool
    await playwrightNode2.openPalette();
    await expect(page.getByTestId('GroupPalette')).toBeAttached();
    await expect(page.getByTestId('GroupPalette').getByTestId('Align left')).not.toBeAttached();
    await expect(page.getByTestId('GroupPalette').getByTestId('Align bottom')).toBeAttached();
  });
});
