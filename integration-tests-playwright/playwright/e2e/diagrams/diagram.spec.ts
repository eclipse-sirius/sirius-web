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

test.describe('diagram', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });

    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);

    const explorer = await new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    const representationItem = await explorer.getTreeItemLabel('Topography');
    representationItem.click();
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('moving a node by click', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');

    const reactFlowXYPositionBefore = await playwrightNode.getReactFlowXYPosition();

    await playwrightNode.move({ x: 450, y: 450 });

    const reactFlowXYPositionAfter = await playwrightNode.getReactFlowXYPosition();

    expect(reactFlowXYPositionAfter.x).toBeGreaterThan(reactFlowXYPositionBefore.x);
    expect(reactFlowXYPositionAfter.y).toBeGreaterThan(reactFlowXYPositionBefore.y);
  });

  test('when the mini map is shown or hidden, then mini map is available or not', async ({ page }) => {
    // by default, the mini map is shown
    await expect(page.getByTestId('hide-mini-map')).toBeAttached();
    await expect(page.getByTestId('show-mini-map')).not.toBeAttached();
    await expect(page.locator('.react-flow__minimap')).toBeAttached();

    await page.getByTestId('hide-mini-map').click();
    await expect(page.locator('.react-flow__minimap')).not.toBeAttached();
    await expect(page.getByTestId('show-mini-map')).toBeAttached();
    await expect(page.getByTestId('hide-mini-map')).not.toBeAttached();

    await page.getByTestId('show-mini-map').click();
    await expect(page.locator('.react-flow__minimap')).toBeAttached();
  });

  test('when a mini map is hidden on a diagram, then another diagram still uses its default mini map visibility', async ({
    page,
  }) => {
    await page.getByTestId('hide-mini-map').click();
    await expect(page.locator('.react-flow__minimap')).not.toBeAttached();

    await page.goto(`/projects/${projectId}/edit`);
    const explorer = await new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.createRepresentation('NewSystem', 'Topography unsynchronized', 'Topography2');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await expect(page.getByTestId('hide-mini-map')).toBeAttached();
    await expect(page.getByTestId('show-mini-map')).not.toBeAttached();
    await expect(page.locator('.react-flow__minimap')).toBeAttached();

    await explorer.select('Topography');
    await expect(page.getByTestId('show-mini-map')).toBeAttached();
    await expect(page.getByTestId('hide-mini-map')).not.toBeAttached();
    await expect(page.locator('.react-flow__minimap')).not.toBeAttached();

    await page.getByTestId('show-mini-map').click();
    await expect(page.locator('.react-flow__minimap')).toBeAttached();
  });
});

test.describe('diagram', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-list', 'papaya-empty');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramPapayaClassNode.xml');
    await playwrightExplorer.expand('diagramPapayaClassNode.xml');
    await playwrightExplorer.expand('Project');
    await playwrightExplorer.expand('Component');

    await playwrightExplorer.createRepresentation('Component', 'Class Diagram', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a diagram has a conditional style, then the style is changed after triggering the condition', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Package');
    await page.waitForFunction(
      () => {
        const div = document.querySelector('[data-testid="rf__background"]');
        if (!div) {
          return false;
        }

        const background = window.getComputedStyle(div).getPropertyValue('background');
        return background.includes('Drag and drop elements to get started');
      },
      { timeout: 2000 }
    );

    await (await playwrightExplorer.getTreeItemLabel('NewClass')).dragTo(page.getByTestId('rf__wrapper'));

    await page.waitForFunction(
      () => {
        const div = document.querySelector('[data-testid="rf__background"]');
        if (!div) {
          return false;
        }

        const background = window.getComputedStyle(div).getPropertyValue('background');
        return !background.includes('Drag and drop elements to get started');
      },
      { timeout: 2000 }
    );
  });
});
