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

test.describe('diagram - borderNode', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('border-node');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramWithBorderNode.xml');
    await playwrightExplorer.expand('diagramWithBorderNode.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramBorderNode - simple border', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('When an initial position is given for the border nodes, then this position is respected when the diagram is initialized', async ({
    page,
  }) => {
    const playwrightBorderNode = new PlaywrightNode(page, 'Border');
    await playwrightBorderNode.click();

    const reactFlowXYPosition = await playwrightBorderNode.getReactFlowXYPosition();
    expect(reactFlowXYPosition.x).toBe(8);
    expect(reactFlowXYPosition.y).toBe(-15);
  });

  test('When a border node is move, then its position stay attached to its parent', async ({ page }) => {
    const playwrightBorderNode = new PlaywrightNode(page, 'Border');
    await playwrightBorderNode.click();

    await playwrightBorderNode.move({ x: 100, y: 100 });

    const reactFlowXYPosition = await playwrightBorderNode.getReactFlowXYPosition();
    expect(reactFlowXYPosition.x).toBeGreaterThan(8);
    expect(reactFlowXYPosition.y).toBe(-15);
  });

  test('When a border node is selected, then it must not be resizable', async ({ page }) => {
    const playwrightBorderNode = new PlaywrightNode(page, 'Border');
    await playwrightBorderNode.click();
    await expect(page.locator('react-flow__resize-control nodrag top left handle')).not.toBeAttached();
    await expect(page.locator('react-flow__resize-control nodrag top right handle')).not.toBeAttached();
    await expect(page.locator('react-flow__resize-control nodrag bottom left handle')).not.toBeAttached();
    await expect(page.locator('react-flow__resize-control nodrag bottom right handle')).not.toBeAttached();
  });
});
