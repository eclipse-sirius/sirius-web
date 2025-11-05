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
import { PlaywrightProject } from '../helpers/PlaywrightProject';

test.describe('delete node without delete tool', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'papayaBasicClassDiagram.zip');

    await page.evaluate(() => {
      window.localStorage.removeItem('sirius-confirmation-dialog-disabled');
    });
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test("when hitting 'Del' on a node with no configured delete tool, no confirmation popup is shown ", async ({
    page,
  }) => {
    let requestTriggered = false;
    page.on('request', (request) => {
      if (
        request.url().includes('api/graphql') &&
        request.method() === 'POST' &&
        JSON.parse(request.postData()).operationName === 'deleteFromDiagram'
      ) {
        requestTriggered = true;
      }
    });

    // Open the diagram
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Papaya');
    await playwrightExplorer.expand('Project');
    await playwrightExplorer.expand('Component');
    await playwrightExplorer.select('Component class diagram');

    await expect(page.getByTestId('rf__wrapper')).toBeVisible();

    // Select the semantic in the explorer
    await playwrightExplorer.expand('package');
    await playwrightExplorer.select('Class');
    // And the corresponding node on the diagram
    await page.getByTestId('diagram-reveal-selection').click();

    // Try do delete from the keyboard
    await page.keyboard.press('Delete');
    // No dialog should open
    await expect(page.getByTestId('confirmation-dialog')).not.toBeVisible();
    // And no mutation invoked
    expect(requestTriggered).toBe(false);
  });
});
