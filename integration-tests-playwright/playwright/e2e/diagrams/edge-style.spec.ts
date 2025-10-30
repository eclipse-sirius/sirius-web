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
import { PlaywrightDetails } from '../../helpers/PlaywrightDetails';
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - edgeStyle', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('edge-style', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('conditional edge style', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramEdgesWithConditionalStyle.xml');
    await playwrightExplorer.expand('diagramEdgesWithConditionalStyle.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramEdges - simple edges', 'diagram');
    const playwrightEdge = new PlaywrightEdge(page);
    const color = await playwrightEdge.getEdgeColor();
    await expect(color).toBe('rgb(255, 0, 0)');
  });

  test('oblique edge type', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramEdgesWithConditionalStyle.xml');
    await playwrightExplorer.expand('diagramEdgesWithConditionalStyle.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramEdges - simple edges', 'diagram');
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightExplorer.showIn('TestConditionalEdgeStyle', 'Details');
    await new PlaywrightDetails(page).setText('Name', 'TestObliqueEdgeType');

    //Move the node so it's easier to identify the edge type
    const playwrightNode = new PlaywrightNode(page, 'Entity1');
    await playwrightNode.click();
    await playwrightNode.move({ x: 0, y: -50 });

    const edgePath = await playwrightEdge.getEdgePath();
    expect(edgePath.trim()).toMatch(/^M\s?[\d.-]+,[\d.-]+\s?L\s?[\d.-]+,[\d.-]+$/); // check it's a straight path
  });
});
