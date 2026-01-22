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

test.describe('diagram - freeform layout', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Papaya - Blank', 'papaya-empty');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('papayaApplicationConcern.xml');
    await playwrightExplorer.expand('papayaApplicationConcern.xml');
    await playwrightExplorer.expand('Project');
    await playwrightExplorer.createRepresentation('Application Concern', 'Lifecycle Diagram', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a freeform node has children, then there is padding on the left and bottom', async ({ page }) => {
    const applicationLayerNode = new PlaywrightNode(page, 'Application Concern', 'FreeForm', 1);
    const controllerNode = new PlaywrightNode(page, 'Controller');

    await applicationLayerNode.click();
    // Hide Node Panel Info to avoid overlap in diagram
    const panel = page.locator('.react-flow__panel.bottom.left').first();
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });

    const applicationLayerSize = await applicationLayerNode.getReactFlowSize('Application Layer', false);
    const controllerSize = await controllerNode.getReactFlowSize('Controller');
    const controllerPosition = await controllerNode.getReactFlowXYPosition('Controller');
    const nodePadding = 8;

    expect(applicationLayerSize.height).toBe(controllerPosition.y + controllerSize.height + nodePadding);
    expect(controllerPosition.x).toBe(nodePadding);
  });
});
