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
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightWorkbench } from '../../helpers/PlaywrightWorkbench';

test.describe('diagram - arrange all', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);
    await new PlaywrightWorkbench(page).performAction('Robot Flow');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Robot Flow');
    await playwrightExplorer.createRepresentation('System', 'Topography', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a arrange all is triggered, then a fit to screen is applied', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await expect(page.getByTestId('FreeForm - Wifi')).toBeInViewport();
    //move view port to hide some nodes
    await page.getByTestId('rf__wrapper').hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(10, 10, { steps: 2 });
    await page.mouse.up();

    await expect(page.getByTestId('FreeForm - Wifi')).not.toBeInViewport();

    await page.getByTestId('arrange-all-menu').click();
    await page.getByTestId('arrange-all-elk-layered').click();

    await expect(page.getByTestId('FreeForm - Wifi')).toBeInViewport();
  });
});

test.describe('diagram - arrange all', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectArrangeAllWithBorderNode.zip');
    await expect(page.locator('[data-testid^="explorer://"]')).toBeAttached();
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a arrange all is triggered on a digram with edge on border node, then the layout applied takes into account edge direction', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('arrange-direction');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await page.getByTestId('arrange-all-menu').click();
    await page.getByTestId('arrange-all-elk-layered').click();

    const sourceNode = new PlaywrightNode(page, 'source');
    const targetNode = new PlaywrightNode(page, 'target');
    const sourcePosition = await sourceNode.getReactFlowXYPosition();
    const sourceSize = await sourceNode.getReactFlowSize(null, false);
    const targetPosition = await targetNode.getReactFlowXYPosition();
    expect(targetPosition.x).toBeGreaterThan(sourcePosition.x + sourceSize.width);
  });

  test('when a arrange all is triggered on a diagram with edge between child on parent node, then the layout applied without error', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('arrange-fail');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await page.getByTestId('arrange-all-menu').click();
    await page.getByTestId('arrange-all-elk-layered').click();

    await expect(page.locator('#notistack-snackbar')).not.toBeAttached({ timeout: 2000 }); // no error
  });

  test('when a rect packing arrange all is triggered, then the layout is applied without error', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('arrange-fail');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await page.getByTestId('arrange-all-menu').click();
    await page.getByTestId('arrange-all-elk-rect-packing').click();

    await expect(page.locator('#notistack-snackbar')).not.toBeAttached({ timeout: 2000 }); // no error
  });
});
