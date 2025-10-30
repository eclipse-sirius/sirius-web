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
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';

test.describe('diagram - delete node', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProjectFromTemplate('flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);

    await page.evaluate(() => {
      window.localStorage.setItem('sirius-confirmation-dialog-disabled', JSON.stringify(true));
    });
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a new node is created, then it can be deleted with del shortcut', async ({ page }) => {
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 250, y: 250 } });
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Creation Tools').click();
    await page.getByTestId('tool-Composite Processor').click();
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor2');
    await expect(playwrightNode.nodeLocator).toBeAttached();
    await playwrightNode.click();
    await page.keyboard.press('Delete');
    await expect(playwrightNode.nodeLocator).not.toBeAttached();
  });
});
