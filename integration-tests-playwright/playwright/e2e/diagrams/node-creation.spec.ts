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

test.describe('diagram - node creation', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-nodeCreation', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramNodeCreationEmpty.xml');
    await playwrightExplorer.expand('diagramNodeCreationEmpty.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramNodeCreation - node creation', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when trigger a multi node creation tool, then all the nodes are properly placed', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 250, y: 250 } });
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('tool-create nodes').click();
    const entity1Node = new PlaywrightNode(page, 'E1');
    const entity2Node = new PlaywrightNode(page, 'E2');

    const reactFlowXYPositionEntity1 = await entity1Node.getReactFlowXYPosition('E1');
    const reactFlowXYPositionEntity2 = await entity2Node.getReactFlowXYPosition('E2');

    const entityNode1Width = 150;
    const nodeGap = 25;
    expect(reactFlowXYPositionEntity2.y).toBe(reactFlowXYPositionEntity1.y);
    expect(reactFlowXYPositionEntity2.x).toBe(reactFlowXYPositionEntity1.x + entityNode1Width + nodeGap);
  });

  test('when trigger a node creation tool on a compartment, then all the nodes are properly placed', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.createNewObject('Root', 'entity3s-Entity3');
    await page.getByTestId('Label content - Parent').click({ button: 'right', position: { x: 1, y: 1 } }); // we use the label to click on the parent
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('tool-createEntity4').click();
    const entity4FirstNode = new PlaywrightNode(page, 'Entity4');

    const reactFlowXYPositionEntity4First = await entity4FirstNode.getReactFlowXYPosition();
    const reactFlowSizeEntity4First = await entity4FirstNode.getReactFlowSize();

    const nodePadding = 8;
    const headerHeight = 24;
    expect(reactFlowXYPositionEntity4First.y).toBeGreaterThanOrEqual(nodePadding + headerHeight);
    expect(reactFlowXYPositionEntity4First.y).toBeLessThanOrEqual(nodePadding + headerHeight + 1);
    expect(reactFlowXYPositionEntity4First.x).toBe(nodePadding);

    // When creating a second one, it should place next to the first one
    await page.getByTestId('Label content - Parent').click({ button: 'right', position: { x: 1, y: 1 } }); // we use the label to click on the parent
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('tool-createEntity4').first().click();
    const entity4SecondNode = new PlaywrightNode(page, 'Entity4', 'FreeForm', 1);
    const reactFlowXYPositionEntity4Second = await entity4SecondNode.getReactFlowXYPosition();
    const nodeGap = 25;
    expect(reactFlowXYPositionEntity4Second.y).toBe(reactFlowXYPositionEntity4First.y);
    expect(reactFlowXYPositionEntity4Second.x).toBe(
      reactFlowXYPositionEntity4First.x + reactFlowSizeEntity4First.width + nodeGap
    );
  });
});

test.describe('diagram - node creation', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('diagram-nodeCreation', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramNodeCreationWithEntity1.xml');
    await playwrightExplorer.expand('diagramNodeCreationWithEntity1.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramNodeCreation - node creation', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when trigger a sibling creation tool multi times, then all the new nodes are properly placed', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const entity1Node = new PlaywrightNode(page, 'Entity1');
    const reactFlowXYPositionEntity1 = await entity1Node.getReactFlowXYPosition();
    await entity1Node.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('tool-creationNode').click();

    const nodeWidth = 150;
    const nodeGap = 25;

    const firstCreatedNode = new PlaywrightNode(page, 'New1');
    const reactFlowXYPositionFirstCreatedNode = await firstCreatedNode.getReactFlowXYPosition('New1');

    expect(reactFlowXYPositionFirstCreatedNode.y).toBe(reactFlowXYPositionEntity1.y);
    expect(reactFlowXYPositionFirstCreatedNode.x).toBe(reactFlowXYPositionEntity1.x + nodeWidth + nodeGap);

    await page.keyboard.press('Escape'); // remove selection

    await entity1Node.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('tool-creationNode').first().click();

    const secondCreatedNode = new PlaywrightNode(page, 'New2');
    const reactFlowXYPositionSecondCreatedNode = await secondCreatedNode.getReactFlowXYPosition('New2');

    expect(reactFlowXYPositionSecondCreatedNode.y).toBe(reactFlowXYPositionEntity1.y);
    expect(reactFlowXYPositionSecondCreatedNode.x).toBe(reactFlowXYPositionFirstCreatedNode.x + nodeWidth + nodeGap);
  });
});
