/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

test.describe('diagram - tool', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Papaya - Blank', 'papaya-empty');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);

    const playwrightExplorer = new PlaywrightExplorer(page);
    playwrightExplorer.expand('Papaya');
    playwrightExplorer.expand('Project');
    playwrightExplorer.expand('Component');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });
  test('When clicking on a tool with selection dialog, then the dialog is displayed', async ({ page }) => {
    new PlaywrightExplorer(page).createRepresentation('Component', 'Class Diagram', 'diagram');
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 100, y: 100 } });
    await page.getByTestId('Import existing types - Tool').click();
    await expect(page.getByTestId('selection-dialog')).toBeAttached();
  });

  test('When a custom tool is contribute to a diagram, then it should be available in the palette', async ({
    page,
  }) => {
    new PlaywrightExplorer(page).createRepresentation('Component', 'Component Diagram', 'diagram');
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 100, y: 100 } });
    await expect(page.getByTestId('Palette')).toBeAttached();
    await expect(page.getByTestId('coordinates-tool')).toBeAttached();
  });
});

test.describe('diagram - tool key bindings', () => {
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

  test('When opening a diagram palette containing a tool with a key binding, then the key binding is visible', async ({
    page,
  }) => {
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 100, y: 100 } });
    await expect(page.getByTestId('tool-New entity')).toBeAttached();
    const keyBinding = await page.getByTestId('key-binding-New entity');
    await expect(keyBinding).toHaveText('Ctrl+e');
  });

  test('When opening a node palette containing a tool with a key binding, then the key binding is visible', async ({
    page,
  }) => {
    const playwrightNode = new PlaywrightNode(page, 'Entity1', 'List');
    playwrightNode.openPalette();
    await expect(page.getByTestId('toolSection-Attributes')).toBeAttached();
    await page.getByTestId('toolSection-Attributes').click();
    await expect(page.getByTestId('tool-Text')).toBeAttached();
    const keyBinding = await page.getByTestId('key-binding-Text');
    await expect(keyBinding).toHaveText('Ctrl+s');
  });
});
