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
import { PlaywrightNode } from '../helpers/PlaywrightNode';

test.describe('diagram - freeform layout', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProjectFromTemplate('papaya-empty');
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
    const controlerNode = new PlaywrightNode(page, 'Controller');

    const applicationLayerSize = await applicationLayerNode.getReactFlowSize();
    const controlerSize = await controlerNode.getReactFlowSize();
    const controlerPosition = await controlerNode.getReactFlowXYPosition();
    const nodePadding = 8;

    expect(applicationLayerSize.height).toBe(controlerPosition.y + controlerSize.height + nodePadding);
    expect(controlerPosition.x).toBe(nodePadding);
  });
});
