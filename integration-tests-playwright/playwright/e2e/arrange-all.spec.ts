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
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightWorkbench } from '../helpers/PlaywrightWorkbench';
import { PlaywrightNode } from '../helpers/PlaywrightNode';

test.describe('diagram - arrange all', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProjectFromTemplate('flow-template');
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

    await page.getByTestId('arrange-all').click();

    await expect(page.getByTestId('FreeForm - Wifi')).toBeInViewport();
  });
});

test.describe('diagram - arrange all', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectDiagramUnarrange.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Unarrange-project');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a arrange all is triggered, then node are arranged depending on edge direction even if edge are on border node', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await page.getByTestId('arrange-all').click();

    const sourceNode = new PlaywrightNode(page, 'Entity1-source');
    const targetNode = new PlaywrightNode(page, 'Entity1-target');
    const sourcePosition = await sourceNode.getReactFlowXYPosition();
    const targetPosition = await targetNode.getReactFlowXYPosition();
    expect(targetPosition.x).toBeGreaterThan(sourcePosition.x);
  });
});
