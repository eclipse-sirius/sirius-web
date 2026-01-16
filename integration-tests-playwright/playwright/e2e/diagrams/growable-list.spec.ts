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

test.describe('diagram - growable list', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('diagram-growable-list', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramGrowableList.xml');
    await playwrightExplorer.expand('diagramGrowableList.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramList - multiple growable list nodes', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a node with growable children is displayed, then growable nodes are properly layout', async ({ page }) => {
    const list1Node = new PlaywrightNode(page, 'List 1', 'List');
    const list2Node = new PlaywrightNode(page, 'Growable', 'FreeForm', 2);
    const list3Node = new PlaywrightNode(page, 'List 3', 'List');

    const list1NodeSize = await list1Node.getReactFlowSize();
    const list2NodeSize = await list2Node.getReactFlowSize('List 2');
    const list3NodeSize = await list3Node.getReactFlowSize();

    expect(list1NodeSize.height).toBeGreaterThanOrEqual(173);
    expect(list1NodeSize.height).toBeLessThanOrEqual(174);
    expect(list2NodeSize.height).toBe(70);
    expect(list3NodeSize.height).toBeGreaterThanOrEqual(146);
    expect(list3NodeSize.height).toBeLessThanOrEqual(147);

    expect(list1NodeSize.width).toBe(150);
    expect(list2NodeSize.width).toBe(150);
    expect(list3NodeSize.width).toBe(150);
  });

  test('when a sub node is hide, then the parent node is resized', async ({ page }) => {
    const growableListNode = new PlaywrightNode(page, 'Growable', 'List');
    const growableListNodeSize = await growableListNode.getReactFlowSize();
    expect(growableListNodeSize.height).toBeGreaterThanOrEqual(425);
    expect(growableListNodeSize.height).toBeLessThanOrEqual(426.5);
    expect(growableListNodeSize.width).toBe(152);

    const list2Node = new PlaywrightNode(page, 'Growable', 'FreeForm', 2);
    await list2Node.openPalette();
    await page.getByTestId('tool-Hide').click();
    await expect(list2Node.nodeLocator).not.toBeAttached();

    const growableListNodeSizeAfterHide = await growableListNode.getReactFlowSize();
    expect(growableListNodeSizeAfterHide.height).toBe(400);
    expect(growableListNodeSizeAfterHide.width).toBe(152);
  });
});
test.describe('diagram - growable list', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('diagram-subnode-list', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramSubNodeList.xml');
    await playwrightExplorer.expand('diagramSubNodeList.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramList - list with subnode', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a sub node is hide, then next sub nodes are correctly placed', async ({ page }) => {
    const listNode = new PlaywrightNode(page, 'ListWithSubNodes', 'List');

    await listNode.click();

    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });

    //issue tested only appear after resize
    await listNode.resize({ height: 20, width: 50 });

    const comp3Node = new PlaywrightNode(page, 'ListWithSubNodes', 'FreeForm', 2);

    await comp3Node.openPalette();
    await page.getByTestId('tool-Hide').click();
    await expect(page.locator(`[data-testid="FreeForm - ListWithSubNodes"]`)).toHaveCount(3);

    const comp1Node = new PlaywrightNode(page, 'ListWithSubNodes', 'FreeForm', 0);
    const comp2Node = new PlaywrightNode(page, 'ListWithSubNodes', 'FreeForm', 1);
    const comp4Node = new PlaywrightNode(page, 'ListWithSubNodes', 'FreeForm', 2);

    const { height: list1NodeHeight } = await listNode.getReactFlowSize('ListWithSubNodes');
    const { height: comp1NodeHeight } = await comp1Node.getReactFlowSize('Compartment 1');
    const { height: comp2NodeHeight } = await comp2Node.getReactFlowSize('Compartment 2');
    const { height: comp4NodeHeight } = await comp4Node.getReactFlowSize('Compartment 4');

    expect(list1NodeHeight).toBeGreaterThanOrEqual(comp1NodeHeight + comp2NodeHeight + comp4NodeHeight);
  });
});
