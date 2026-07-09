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
import { PlaywrightNodeLabel } from '../../helpers/PlaywrightNodeLabel';
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
    const borderWidth = 1;

    expect(applicationLayerSize.height).toBe(controllerPosition.y + controllerSize.height + nodePadding + borderWidth);
    expect(controllerPosition.x).toBe(nodePadding + borderWidth);
  });
});

test.describe('diagram - freeform layout', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramFreeFormWithOneChild.xml');
    await playwrightExplorer.expand('diagramFreeFormWithOneChild.xml');
    await playwrightExplorer.createRepresentation('System', 'Topography', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a freeform node has a child node, then the layout has correct margin and placed the child correctly', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const freeformNode = new PlaywrightNode(page, 'CP1');
    const reactFlowParentSize = await freeformNode.getReactFlowSize('CP1');
    const childNode = new PlaywrightNode(page, 'Proc1');
    const childNodeSize = await childNode.getReactFlowSize('Proc1');
    const childNodePosition = await childNode.getReactFlowXYPosition('Proc1', false);
    const nodePadding = 8;
    const borderWidth = 1;
    const parentLabel = new PlaywrightNodeLabel(page, 'CP1');
    const childLabel = new PlaywrightNodeLabel(page, 'Proc1');
    const parentLabelBox = await parentLabel.labelLocator.boundingBox();
    const childLabelBox = await childLabel.labelLocator.boundingBox();
    expect(reactFlowParentSize.width).toBe(borderWidth + nodePadding + childNodeSize.width + nodePadding + borderWidth);
    expect(reactFlowParentSize.height).toBe(
      borderWidth +
        nodePadding +
        (parentLabelBox?.height ?? 0) +
        childNodeSize.height +
        (childLabelBox?.height ?? 0) +
        nodePadding +
        borderWidth
    );
    expect(childNodePosition.x).toBe(borderWidth + nodePadding);
    expect(childNodePosition.y).toBe(borderWidth + nodePadding + (parentLabelBox?.height ?? 0));
  });
});
