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
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightProject } from '../helpers/PlaywrightProject';

test.describe('diagram - direct edit', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });

    const project = await new PlaywrightProject(request).createProjectFromTemplate('Flow', 'flow-template', [
      PlaywrightProject.FLOW_NATURE,
    ]);
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a new node is created, then we can use direct edit', async ({ page }) => {
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 250, y: 250 } });
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Creation Tools').click();
    await page.getByTestId('tool-Composite Processor').click();
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor2');
    await expect(playwrightNode.nodeLocator).toBeAttached();
    await playwrightNode.click();
    await page.keyboard.type('Edited');
    await page.keyboard.press('Enter');
    await expect(playwrightNode.nodeLocator).not.toBeAttached();
    const editedNode = new PlaywrightNode(page, 'Edited');
    await expect(editedNode.nodeLocator).toBeAttached();
  });

  test('when opening the palette on a node, then we can use direct edit with the quicktool', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    await playwrightNode.openPalette();
    await page.getByTestId('Palette').getByTestId('Edit - Tool').click();
    await expect(page.getByTestId('Palette')).toBeHidden();
    await page.keyboard.type('Edited');
    await page.keyboard.press('Enter');
    const editedNode = new PlaywrightNode(page, 'Edited');
    await expect(editedNode.nodeLocator).toBeAttached();
  });

  test('when opening the palette on an edge, then we can use direct edit with the quicktool', async ({ page }) => {
    await expect(page.getByTestId('Label - 6')).toBeAttached();
    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.openPalette();
    await page.getByTestId('Palette').getByTestId('Edit - Tool').click();
    await expect(page.getByTestId('Palette')).toBeHidden();
    await page.keyboard.type('10');
    await page.keyboard.press('Enter');
    await expect(page.getByTestId('Label - 10')).toBeAttached();
  });
});
