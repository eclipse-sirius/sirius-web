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

  test('when a node is resizable both directions with a default width, then it can be resized smaller than its default width', async ({ page }) => {
    const resizableNode = new PlaywrightNode(page, "Resizable");
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

  test('when a node is resizable both directions with a default width and an overflow strategy set to "NONE", then it can be resized smaller than its default width but not than the minimum width of its inside label', async ({ page }) => {
    const resizableNode = new PlaywrightNode(page, "Resizable");
    const resizableNodeSizeBefore = await resizableNode.getReactFlowSize();
    expect(resizableNodeSizeBefore.width).toBe(200);
    expect(resizableNodeSizeBefore.height).toBe(100);

    await resizableNode.resize({ width: -198, height: -98 });

    const resizableNodeSizeAfter = await resizableNode.getReactFlowSize();
    expect(resizableNodeSizeAfter.width).toBeGreaterThanOrEqual(100);
    expect(resizableNodeSizeAfter.width).toBeLessThanOrEqual(150);
    expect(resizableNodeSizeAfter.height).toBeGreaterThanOrEqual(20);
    expect(resizableNodeSizeAfter.height).toBeLessThanOrEqual(70);
  });
});