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
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - key bindings', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('Studio', 'blank-studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);

    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('studioKeyBindings.xml');
    await playwrightExplorer.expand('studioKeyBindings.xml');
    await playwrightExplorer.createRepresentation('domain', 'Domain', 'Domain Diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('When clicking on the diagram and entering a valid key binding, then the corresponding tool is executed', async ({
    page,
  }) => {
    await page.getByTestId('rf__wrapper').press('ControlOrMeta+e');
    await expect(page.getByTestId('List - NewEntity')).toBeAttached();
  });

  test('When clicking on a node and entering a valid key binding, then the corresponding tool is executed', async ({
    page,
  }) => {
    const playwrightNode = new PlaywrightNode(page, 'Entity1', 'List');
    await playwrightNode.click();
    await playwrightNode.nodeLocator.press('ControlOrMeta+b');
    await expect(page.getByTestId('IconLabel - newBoolean')).toBeAttached();
  });
});
