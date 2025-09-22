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
import { test, expect } from '@playwright/test';
import { PlaywrightProject } from '../helpers/PlaywrightProject';
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../helpers/PlaywrightNode';

test.describe('diagram - drag and drop', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-dnd');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramDnD.xml');
    await playwrightExplorer.expand('diagramDnD.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramDnD - simple dnd view', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when dropping a node in a compatible target, then a drop is triggered and the move is accept', async ({
    page,
  }) => {
    let requestTriggered = false;
    page.on('request', (request) => {
      if (
        request.url().includes('api/graphql') &&
        request.method() === 'POST' &&
        JSON.parse(request.postData()).operationName === 'dropNode'
      ) {
        requestTriggered = true;
      }
    });

    const playwrightNode = new PlaywrightNode(page, 'Entity2-inside');

    const reactFlowXYPositionBefore = await playwrightNode.getReactFlowXYPosition();

    await playwrightNode.move({ x: 0, y: -150 });

    await page.waitForFunction(
      () => {
        return !!document.querySelector(`[data-testid="FreeForm - dropPerformed"]`);
      },
      { timeout: 2000 }
    );
    expect(requestTriggered).toBe(true);

    const playwrightNodeAfterDrop = new PlaywrightNode(page, 'dropPerformed');
    const reactFlowXYPositionAfter = await playwrightNodeAfterDrop.getReactFlowXYPosition();
    expect(reactFlowXYPositionAfter.y).not.toBe(reactFlowXYPositionBefore.y);
  });

  test('when dropping a node inside is current parent, then no drop is triggered and the move is accept', async ({
    page,
  }) => {
    let requestTriggered = false;
    page.on('request', (request) => {
      if (
        request.url().includes('api/graphql') &&
        request.method() === 'POST' &&
        JSON.parse(request.postData()).operationName === 'dropNode'
      ) {
        requestTriggered = true;
      }
    });

    const playwrightNode = new PlaywrightNode(page, 'Entity2-inside');

    const reactFlowXYPositionBefore = await playwrightNode.getReactFlowXYPosition();

    await playwrightNode.move({ x: 50, y: 50 });

    const reactFlowXYPositionAfter = await playwrightNode.getReactFlowXYPosition();

    expect(reactFlowXYPositionAfter.y).not.toBe(reactFlowXYPositionBefore.y);
    expect(reactFlowXYPositionAfter.x).not.toBe(reactFlowXYPositionBefore.x);
    expect(requestTriggered).toBe(false);
  });

  test('when dragging a node, then not compatible node style changed', async ({ page }) => {
    const entity1Locator = page.locator('[data-testid="FreeForm - Entity1"]');

    const opacityBefore = await entity1Locator.evaluate((node) => {
      return window.getComputedStyle(node).getPropertyValue('opacity');
    });
    expect(opacityBefore).toBe('1');

    const entity2PlaywrightNode = new PlaywrightNode(page, 'Entity2-outside');
    const xyPosition = await entity2PlaywrightNode.getDOMXYPosition();

    await entity2PlaywrightNode.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition.x + 25, xyPosition.y + 25, { steps: 10 });

    await page.waitForFunction(
      () => {
        const node = document.querySelector(`[data-testid="FreeForm - Entity1"]`);
        return node && window.getComputedStyle(node).getPropertyValue('opacity') === '0.4';
      },
      { timeout: 2000 }
    );

    await page.mouse.up();

    await page.waitForFunction(
      () => {
        const node = document.querySelector(`[data-testid="FreeForm - Entity1"]`);
        return node && window.getComputedStyle(node).getPropertyValue('opacity') === '1';
      },
      { timeout: 2000 }
    );
  });
});
