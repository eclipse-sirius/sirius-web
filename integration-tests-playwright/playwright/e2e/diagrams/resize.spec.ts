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

test.describe('diagram - resize', () => {
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

  test('when resizing a node, then its dimension changes', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');

    const reactFlowSizeBefore = await playwrightNode.getReactFlowSize();

    await playwrightNode.resize({ height: 20, width: 50 });

    const reactFlowSizeAfter = await playwrightNode.getReactFlowSize();

    expect(reactFlowSizeAfter.height).toBeGreaterThan(reactFlowSizeBefore.height);
    expect(reactFlowSizeAfter.width).toBeGreaterThan(reactFlowSizeBefore.width);
  });
});

test.describe('diagram - multi-resize', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramMultiResize.xml');
    await playwrightExplorer.expand('diagramMultiResize.xml');
    await playwrightExplorer.createRepresentation('NewSystem', 'Topography', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when resizing a node during a multi selection, then dimension changes for both node', async ({ page }) => {
    const compositeProcessor1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const compositeProcessor2 = new PlaywrightNode(page, 'CompositeProcessor2');

    await compositeProcessor1.click();
    await compositeProcessor2.controlClick();

    const cp1Before = await compositeProcessor1.getReactFlowSize('CompositeProcessor1', false);

    // Hide Node Panel Info to avoid overlap in diagram
    const panel = page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });

    await compositeProcessor1.resize({ height: 50, width: 50 });

    const cp1After = await compositeProcessor1.getReactFlowSize('CompositeProcessor1', false);
    const cp2After = await compositeProcessor1.getReactFlowSize('CompositeProcessor2', false);

    expect(cp1After.height).toBeGreaterThan(cp1Before.height);
    expect(cp1After.width).toBeGreaterThan(cp1Before.width);
    expect(cp2After.height).toBe(cp1After.height);
    expect(cp2After.width).toBe(cp1After.width);
  });

  test('when resizing a node during a multi selection, then dimension changes only for resizable node', async ({
    page,
  }) => {
    const compositeProcessor1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const dataSource1 = new PlaywrightNode(page, 'DataSource1');

    await compositeProcessor1.click();
    await dataSource1.controlClick();

    const cp1Before = await compositeProcessor1.getReactFlowSize('CompositeProcessor1', false);
    const ds1Before = await compositeProcessor1.getReactFlowSize('DataSource1', false);

    // Hide Node Panel Info to avoid overlap in diagram
    const panel = page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });

    await compositeProcessor1.resize({ height: 50, width: 50 });

    const cp1After = await compositeProcessor1.getReactFlowSize('CompositeProcessor1', false);
    const ds1After = await compositeProcessor1.getReactFlowSize('DataSource1', false);

    expect(cp1After.height).toBeGreaterThan(cp1Before.height);
    expect(cp1After.width).toBeGreaterThan(cp1Before.width);
    expect(ds1After.height).toBe(ds1Before.height);
    expect(ds1After.width).toBe(ds1Before.width);
  });
});
