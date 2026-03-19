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
import { PlaywrightDiagram } from '../../helpers/PlaywrightDiagram';

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

test.describe('diagram - resize smaller than default', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('diagram-resizable', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramResizableSmallerThanDefaultWidth.xml');
    await playwrightExplorer.expand('diagramResizableSmallerThanDefaultWidth.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramResize - simple resize node', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a node is resizable both directions with a default width, then it can be resized smaller than its default width', async ({
    page,
  }) => {
    const resizableNode = new PlaywrightNode(page, 'Resizable');
    const resizableNodeSizeBefore = await resizableNode.getReactFlowSize();
    expect(resizableNodeSizeBefore.width).toBe(200);
    expect(resizableNodeSizeBefore.height).toBe(100);

    await resizableNode.resize({ width: -50, height: -25 });

    const resizableNodeSizeAfter = await resizableNode.getReactFlowSize();
    expect(resizableNodeSizeAfter.width).toBeGreaterThanOrEqual(145);
    expect(resizableNodeSizeAfter.width).toBeLessThanOrEqual(155);
    expect(resizableNodeSizeAfter.height).toBeGreaterThanOrEqual(70);
    expect(resizableNodeSizeAfter.height).toBeLessThanOrEqual(80);
  });

  test('when a node is resizable both directions with a default width and an overflow strategy set to "NONE", then it can be resized smaller than its default width but not than the minimum width of its inside label', async ({
    page,
  }) => {
    const resizableNode = new PlaywrightNode(page, 'Resizable');
    const resizableNodeSizeBefore = await resizableNode.getReactFlowSize();
    expect(resizableNodeSizeBefore.width).toBe(200);
    expect(resizableNodeSizeBefore.height).toBe(100);

    await resizableNode.resize({ width: -198, height: -98 });

    const resizableNodeSizeAfter = await resizableNode.getReactFlowSize();
    expect(resizableNodeSizeAfter.width).toBeGreaterThanOrEqual(95);
    expect(resizableNodeSizeAfter.width).toBeLessThanOrEqual(150);
    expect(resizableNodeSizeAfter.height).toBeGreaterThanOrEqual(20);
    expect(resizableNodeSizeAfter.height).toBeLessThanOrEqual(70);
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
    expect(cp2After.height).toBeCloseTo(cp1After.height, 1);
    expect(cp2After.width).toBeCloseTo(cp1After.width, 1);
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
    expect(ds1After.height).toBeCloseTo(ds1Before.height, 1);
    expect(ds1After.width).toBeCloseTo(ds1Before.width, 1);
  });

  test('when resizing a node during a multi selection from its left side, then dimension and position changes for both node', async ({
    page,
  }) => {
    const compositeProcessor1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const compositeProcessor2 = new PlaywrightNode(page, 'CompositeProcessor2');

    await compositeProcessor1.click();
    await compositeProcessor2.controlClick();

    const cp1Before = await compositeProcessor1.getReactFlowSize('CompositeProcessor1', false);
    const cp1PositionBefore = await compositeProcessor1.getReactFlowXYPosition('CompositeProcessor1', false);
    const cp2PositionBefore = await compositeProcessor2.getReactFlowXYPosition('CompositeProcessor2', false);

    // Hide Node Panel Info to avoid overlap in diagram
    const panel = page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });

    await compositeProcessor1.resize({ height: -50, width: -50 }, 'top.left');

    const cp1After = await compositeProcessor1.getReactFlowSize('CompositeProcessor1', false);
    const cp1PositionAfter = await compositeProcessor1.getReactFlowXYPosition('CompositeProcessor1', false);
    const cp2After = await compositeProcessor2.getReactFlowSize('CompositeProcessor2', false);
    const cp2PositionAfter = await compositeProcessor2.getReactFlowXYPosition('CompositeProcessor2', false);

    expect(cp1After.height).toBeGreaterThan(cp1Before.height);
    expect(cp1After.width).toBeGreaterThan(cp1Before.width);
    expect(cp2After.height).toBeCloseTo(cp1After.height, 1);
    expect(cp2After.width).toBeCloseTo(cp1After.width, 1);
    expect(cp1PositionAfter.x).not.toBe(cp1PositionBefore.x);
    expect(cp1PositionAfter.y).not.toBe(cp1PositionBefore.y);
    expect(cp2PositionAfter.x).not.toBe(cp2PositionBefore.x);
    expect(cp2PositionAfter.y).not.toBe(cp2PositionBefore.y);
    expect(cp1PositionAfter.x - cp1PositionBefore.x).toBeCloseTo(cp2PositionAfter.x - cp2PositionBefore.x, 1);
    expect(cp1PositionAfter.y - cp1PositionBefore.y).toBeCloseTo(cp2PositionAfter.y - cp2PositionBefore.y, 1);
  });
});

test.describe('diagram - resize', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-resizable', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramResizeWithSameSemanticElement.xml');
    await playwrightExplorer.expand('diagramResizeWithSameSemanticElement.xml');
    await playwrightExplorer.createRepresentation(
      'Root',
      'diagramResize - resize node with same semantic element',
      'diagram'
    );
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a multiple node represent the same element, then resizing one of these node do not resize the others', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const child = new PlaywrightNode(page, 'Parent', 'FreeForm', 1);
    const childSizeBefore = await child.getReactFlowSize('child');
    const parent = new PlaywrightNode(page, 'Parent');

    await parent.click();

    await parent.resize({ width: 50, height: 50 });

    const resizableNodeSizeAfter = await child.getReactFlowSize('child');
    expect(resizableNodeSizeAfter.width).toBe(childSizeBefore.width);
    expect(resizableNodeSizeAfter.height).toBe(childSizeBefore.height);
  });
});

test.describe('diagram - resize', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectResizeParentWithFreeFormChild.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.expand('System');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });
  [
    { resizeHandle: 'top.left', resizeOffset: { height: 50, width: 50 }, resizeDirection: 'smaller' },
    { resizeHandle: 'top.right', resizeOffset: { height: 50, width: -50 }, resizeDirection: 'smaller' },
    { resizeHandle: 'bottom.left', resizeOffset: { height: -50, width: 50 }, resizeDirection: 'smaller' },
    { resizeHandle: 'bottom.right', resizeOffset: { height: -50, width: -50 }, resizeDirection: 'smaller' },
    { resizeHandle: 'top.left', resizeOffset: { height: -50, width: -50 }, resizeDirection: 'larger' },
    { resizeHandle: 'top.right', resizeOffset: { height: -50, width: 50 }, resizeDirection: 'larger' },
    { resizeHandle: 'bottom.left', resizeOffset: { height: 50, width: -50 }, resizeDirection: 'larger' },
    { resizeHandle: 'bottom.right', resizeOffset: { height: 50, width: 50 }, resizeDirection: 'larger' },
  ].forEach(({ resizeHandle, resizeOffset, resizeDirection }) => {
    test(`when resize ${resizeDirection} a parent node from its ${resizeHandle} handle, then child preserved its relative coordinates`, async ({
      page,
    }) => {
      const playwrightExplorer = new PlaywrightExplorer(page);
      await playwrightExplorer.select('Topography');
      await expect(page.getByTestId('rf__wrapper')).toBeAttached();

      await page.getByTestId('hide-mini-map').click();

      const parentNode = new PlaywrightNode(page, 'CompositeProcessor1');
      const childNode = new PlaywrightNode(page, 'Processor1');
      const childNodePositionBefore = await childNode.getReactFlowXYPosition('Processor1');
      // Hide Node Panel Info to avoid overlap in diagram
      const panel = await page.locator('.react-flow__panel.bottom.left');
      await panel.evaluate((node) => {
        node.style.visibility = 'hidden';
      });

      await parentNode.click();
      await parentNode.resize(resizeOffset!, `${resizeHandle}`);

      const childNodePositionAfter = await childNode.getReactFlowXYPosition('Processor1');
      expect(childNodePositionAfter.x).toBeCloseTo(childNodePositionBefore.x, 1);
      expect(childNodePositionAfter.y).toBeCloseTo(childNodePositionBefore.y, 1);
    });
  });
});

test.describe('diagram - resize', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectResizeChild.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('resizeChild');
    await playwrightExplorer.expand('Root');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test(`when resizing a child node from its left side, then it moves within its parent node`, async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const childNode = new PlaywrightNode(page, 'Child');
    await childNode.click();
    await new PlaywrightDiagram(page).hideDebugPanel();
    const childNodePositionBefore = await childNode.getReactFlowXYPosition('Child', false);
    await childNode.resize({ width: 50, height: 50 }, 'top.left');

    const childNodePositionAfterTopLeft = await childNode.getReactFlowXYPosition('Child', false);
    expect(childNodePositionAfterTopLeft.x).not.toBe(childNodePositionBefore.x);
    expect(childNodePositionAfterTopLeft.y).not.toBe(childNodePositionBefore.y);

    await childNode.resize({ width: 50, height: 50 }, 'bottom.left');

    const childNodePositionAfterBottomLeft = await childNode.getReactFlowXYPosition('Child', false);
    expect(childNodePositionAfterBottomLeft.x).not.toBe(childNodePositionAfterTopLeft.x);
    expect(childNodePositionAfterBottomLeft.y).toBe(childNodePositionAfterTopLeft.y);
  });
});
