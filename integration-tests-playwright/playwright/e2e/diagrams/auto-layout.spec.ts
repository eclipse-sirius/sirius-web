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
    await expect(page.getByTestId('FreeForm - Wifi')).toBeInViewport();

    const captureSubsystemNode = new PlaywrightNode(page, 'Capture_Subsystem');
    const captureSubsystemPosition = await captureSubsystemNode.getReactFlowXYPosition();
    expect(captureSubsystemPosition.x).toBe(12);
    expect(captureSubsystemPosition.y).toBe(12);

    const radarCaptureNode = new PlaywrightNode(page, 'Radar_Capture');
    const radarCapturePosition = await radarCaptureNode.getReactFlowXYPosition();
    expect(radarCapturePosition.x).toBe(12);
    expect(radarCapturePosition.y).toBe(242);
  });

  test('when moving a node on an auto-layout diagram, then move is reset to its default position', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await expect(page.getByTestId('FreeForm - Wifi')).toBeInViewport();

    const wifiNode = new PlaywrightNode(page, 'Wifi');
    const wifiPositionBefore = await wifiNode.getDOMXYPosition();
    await wifiNode.move({ x: 100, y: 100 });

    await page.waitForFunction(
      ({ expectedX, expectedY }) => {
        const node = document.querySelector(`[data-testid="FreeForm - Wifi"]`);
        if (!node) {
          return false;
        }
        const nodeBoundingBox = node.getBoundingClientRect();
        return (
          nodeBoundingBox.x > expectedX - 5 &&
          nodeBoundingBox.x < expectedX + 5 &&
          nodeBoundingBox.y > expectedY - 5 &&
          nodeBoundingBox.y < expectedY + 5
        );
      },
      {
        expectedX: wifiPositionBefore.x,
        expectedY: wifiPositionBefore.y,
      },
      { timeout: 2000 }
    );
  });
});
