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

test.describe('diagram - list', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('list-node', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramList.xml');
    await playwrightExplorer.expand('diagramList.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramList - simple list node', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a list node is display, then the proper separator are present', async ({ page }) => {
    // Compartment list
    const borderWidthListCompartment = await page.getByTestId('List - undefined').evaluate((el) => {
      return el.style.borderWidth;
    });
    expect(borderWidthListCompartment).toBe('0px 0px 1px');

    // Compartment freeform
    const borderWidthFreeFormCompartment = await page
      .getByTestId('FreeForm - List')
      .first()
      .evaluate((el) => {
        return el.style.borderWidth;
      });
    expect(borderWidthFreeFormCompartment).toBe('0px');
  });

  test('when a list node is resized, then child node width are properly updated', async ({ page }) => {
    const parentNode = new PlaywrightNode(page, 'List', 'List');
    const childNode = new PlaywrightNode(page, 'undefined', 'List');

    const parentNodeSizeBefore = await parentNode.getReactFlowSize();

    await childNode.nodeLocator.click({ position: { x: 25, y: 25 } });
    const childNodeSizeBefore = await childNode.getReactFlowSize(0, false);

    await parentNode.click();
    await parentNode.resize({ height: 0, width: 50 });

    const parentNodeSizeAfter = await parentNode.getReactFlowSize();
    await childNode.nodeLocator.click({ position: { x: 25, y: 25 } });
    const childNodeSizeAfter = await childNode.getReactFlowSize(0, false);

    const borderWidth: number = 2;
    expect(childNodeSizeBefore.width).toBe(parentNodeSizeBefore.width - borderWidth);
    expect(parentNodeSizeAfter.width).toBeGreaterThan(parentNodeSizeBefore.width);
    expect(childNodeSizeAfter.width).toBe(parentNodeSizeAfter.width - borderWidth);
  });

  test('when a list node is moved, then the size of the node is not updated', async ({ page }) => {
    const parentNode = new PlaywrightNode(page, 'List', 'List');

    const parentNodeSizeBefore = await parentNode.getReactFlowSize();

    await parentNode.move({ x: 50, y: 50 });

    const parentNodeSizeAfter = await parentNode.getReactFlowSize();

    expect(parentNodeSizeAfter.width).toBe(parentNodeSizeBefore.width);
    expect(parentNodeSizeAfter.height).toBe(parentNodeSizeBefore.height);
  });
});
