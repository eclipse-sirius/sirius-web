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

test.describe('diagram - palette', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
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

  test('when align tool is triggered on two elements, then they share same position', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    const playwrightNode2 = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode2.controlClick();
    await playwrightNode2.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('tool-Align bottom').click();
    //Check the last tool used is the one proposed as quick tool (disabled until the layout section is using the reworked palette extension point)
    //await page.getByTestId('Palette').getByTestId('Align bottom').click();
    const playwrightNodeXYPosition = await playwrightNode.getReactFlowXYPosition('undefined', false);
    const playwrightNode2XYPosition = await playwrightNode2.getReactFlowXYPosition('CompositeProcessor1', false);
    const playwrightNodeSize = await playwrightNode.getReactFlowSize('undefined', false);
    const playwrightNode2Size = await playwrightNode2.getReactFlowSize('CompositeProcessor1', false);
    expect(playwrightNodeXYPosition.y + playwrightNodeSize.height).toBe(
      playwrightNode2XYPosition.y + playwrightNode2Size.height
    );
  });

  test('when a node then an edge is selected, we can open the group palette and fade both elements', async ({
    page,
  }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightNode.click();
    await playwrightEdge.controlClick();
    await playwrightEdge.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('Palette').getByTestId('Fade - Tool').click();
    const edgeStyle = await playwrightEdge.getEdgeStyle();
    await expect(edgeStyle).toHaveCSS('opacity', '0.4');
    await expect(playwrightNode.nodeStyleLocator).toHaveCSS('opacity', '0.4');
  });

  test('when several elements are selected with the rectangular selection, we can open the group palette and hide both elements', async ({
    page,
  }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    const playwrightNode2 = new PlaywrightNode(page, 'CompositeProcessor1');
    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightNode.click();
    await playwrightNode2.controlClick();
    await playwrightNode2.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('Palette').getByTestId('Hide - Tool').click();
    await expect(playwrightNode.nodeLocator).not.toBeAttached();
    await expect(playwrightNode2.nodeLocator).not.toBeAttached();
    await expect(playwrightEdge.edgeLocator).not.toBeAttached();
  });
});

test.describe('diagram - palette tool section', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('diagram-palette', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramPalette.xml');
    await playwrightExplorer.expand('diagramPalette.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramPalette - palette', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a diagram palette is opened, then only not empty tool sections are displayed', async ({ page }) => {
    await page.getByTestId('rf__wrapper').click({ button: 'right', position: { x: 250, y: 250 } });
    await expect(page.getByTestId('Palette')).toBeAttached();
    await expect(page.getByTestId('toolSection-DiagramToolSection')).toBeAttached();
    await expect(page.getByTestId('toolSection-EmptyToolSection')).not.toBeAttached();
  });

  test('when a palette contains an empty tool section, then this tool section is not displayed', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'Entity1');
    await playwrightNode.openPalette();
    await expect(page.getByTestId('toolSection-NodeToolSection')).toBeAttached();
    await playwrightNode.closePalette();
    await new PlaywrightDetails(page).setText('Name', 'Empty');
    await playwrightNode.openPalette();
    await expect(page.getByTestId('toolSection-NodeToolSection')).not.toBeAttached();
  });
});
