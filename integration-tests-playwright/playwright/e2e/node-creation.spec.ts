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
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightProject } from '../helpers/PlaywrightProject';

test.describe('diagram - node creation', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-nodeCreation', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramNodeCreation.xml');
    await playwrightExplorer.expand('diagramNodeCreation.xml');
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
    const entity1Node = new PlaywrightNode(page, 'Entity1');
    const entity2Node = new PlaywrightNode(page, 'Entity2');

    const reactFlowXYPositionEntity1 = await entity1Node.getReactFlowXYPosition();
    const reactFlowXYPositionEntity2 = await entity2Node.getReactFlowXYPosition();

    const entityNode1Width = 150;
    const nodeGap = 25;
    expect(reactFlowXYPositionEntity2.y).toBe(reactFlowXYPositionEntity1.y);
    expect(reactFlowXYPositionEntity2.x).toBe(reactFlowXYPositionEntity1.x + entityNode1Width + nodeGap);
  });

  test('when trigger a node creation tool on a compartment, then all the nodes are properly placed', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    playwrightExplorer.createNewObject('Root', 'entity3s-Entity3');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
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
