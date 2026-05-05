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
import { test, expect } from '@playwright/test';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightWorkbench } from '../../helpers/PlaywrightWorkbench';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightDiagram } from '../../helpers/PlaywrightDiagram';

test.describe('diagram - auto-layout', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);
    await new PlaywrightWorkbench(page).performAction('Robot Flow');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Robot Flow');
    await playwrightExplorer.createRepresentation('System', 'Topography with auto layout', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a diagram with auto-layout is opened, then all nodes are positioned according to the elk layout', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await new PlaywrightDiagram(page).collapseToolbar();
    await expect(page.getByTestId('FreeForm - Wifi')).toBeInViewport();

    const captureSubsystemNode = new PlaywrightNode(page, 'Wifi');
    const captureSubsystemPosition = await captureSubsystemNode.getReactFlowXYPosition();
    expect(captureSubsystemPosition.x).toBe(12);
    expect(captureSubsystemPosition.y).toBe(12);

    const dspNode = new PlaywrightNode(page, 'DSP');
    const dspPosition = await dspNode.getReactFlowXYPosition();
    expect(dspPosition.x).toBeLessThanOrEqual(124);
    expect(dspPosition.x).toBeGreaterThanOrEqual(120);
    expect(dspPosition.y).toBeLessThanOrEqual(36);
    expect(dspPosition.y).toBeGreaterThanOrEqual(32);
  });

  test('when moving a node on an auto-layout diagram, then move is reset to its default position', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await new PlaywrightDiagram(page).collapseToolbar();
    await expect(page.getByTestId('FreeForm - Wifi')).toBeInViewport();

    const wifiNode = new PlaywrightNode(page, 'Wifi');
    const wifiPositionBefore = await wifiNode.getReactFlowXYPosition();
    await wifiNode.move({ x: -50, y: 50 });

    await page.waitForFunction(
      ({ expectedX, expectedY }) => {
        const nodePanelInfo = document.querySelector('div[data-testid="nodePanelInfos"]');
        if (!nodePanelInfo) return false;

        const spans = nodePanelInfo.querySelectorAll('span');
        let xValue, yValue;

        spans.forEach((span) => {
          const text = span.textContent || '';
          if (text.includes('x :')) {
            xValue = Number(text.split('x :')[1]?.trim());
          } else if (text.includes('y :')) {
            yValue = Number(text.split('y :')[1]?.trim());
          }
        });

        return xValue === expectedX && yValue === expectedY;
      },
      {
        expectedX: wifiPositionBefore.x,
        expectedY: wifiPositionBefore.y,
      },
      { timeout: 2000 }
    );
  });
});

test.describe('diagram - auto-until-manual', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Studio', 'studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a diagram with auto-until-manual is opened, then all nodes are positioned according to the elk layout', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const node = new PlaywrightNode(page, 'Entity1', 'List');
    const position = await node.getReactFlowXYPosition();
    expect(position.x).toBe(12);
    expect(position.y).toBe(242);
  });

  test('when moving a node on an auto-until-manual diagram, then move is not reset', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const rootNode = new PlaywrightNode(page, 'Entity1', 'List');
    const rootPositionBefore = await rootNode.getDOMXYPosition();
    await rootNode.move({ x: 100, y: 100 });

    const rootPositionAfter = await rootNode.getDOMXYPosition();
    expect(rootPositionAfter.x).not.toBe(rootPositionBefore.x);
    expect(rootPositionAfter.y).not.toBe(rootPositionBefore.y);
  });
});
