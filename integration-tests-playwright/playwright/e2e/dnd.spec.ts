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

test.describe('diagram - drag and drop', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-dnd', 'blank-project');
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

  test('when dropping a node in a compatible target (diagram background), then a drop is triggered and the move is accepted', async ({
    page,
  }) => {
    let requestTriggered = false;
    page.on('request', (request) => {
      if (
        request.url().includes('api/graphql') &&
        request.method() === 'POST' &&
        JSON.parse(request.postData()).operationName === 'dropNodes'
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

  test('when dropping a node inside is current parent, then no drop is triggered and the move is accepted', async ({
    page,
  }) => {
    let requestTriggered = false;
    page.on('request', (request) => {
      if (
        request.url().includes('api/graphql') &&
        request.method() === 'POST' &&
        JSON.parse(request.postData()).operationName === 'dropNodes'
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

  test('when dragging a node, then non-compatible node style is not changed', async ({ page }) => {
    const entity1Locator = page.locator('[data-testid="FreeForm - Entity1"]');

    const boxShadowBefore = await entity1Locator.evaluate((node) => {
      return window.getComputedStyle(node).getPropertyValue('box-shadow');
    });
    expect(boxShadowBefore).toBe('none');

    // Move Entity2 elsewhere on the diagram background
    const entity2PlaywrightNode = new PlaywrightNode(page, 'Entity2-outside');
    const xyPosition = await entity2PlaywrightNode.getDOMXYPosition();

    await entity2PlaywrightNode.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition.x + 25, xyPosition.y + 25, { steps: 10 });

    // Entity one is NOT a compatible drop target, its border/shadow should not change
    await page.waitForFunction(
      () => {
        const node = document.querySelector(`[data-testid="FreeForm - Entity1"]`);
        return node && window.getComputedStyle(node).getPropertyValue('box-shadow') === 'none';
      },
      { timeout: 2000 }
    );

    await page.mouse.up();
  });

  test('when dragging a node, then compatible nodes style indicates it', async ({ page }) => {
    const entity1Locator = page.locator('[data-testid="FreeForm - Entity1"]');

    const boxShadowBefore = await entity1Locator.evaluate((node) => {
      return window.getComputedStyle(node).getPropertyValue('box-shadow');
    });
    expect(boxShadowBefore).toBe('none');

    // Move Entity3 elsewhere on the diagram background
    const entity3PlaywrightNode = new PlaywrightNode(page, 'Entity3');
    const xyPosition = await entity3PlaywrightNode.getDOMXYPosition();

    await entity3PlaywrightNode.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move(xyPosition.x + 25, xyPosition.y + 25, { steps: 10 });

    // Entity1 IS a compatible drop target, its border/shadow should change to indicate t
    await page.waitForFunction(
      () => {
        const node = document.querySelector(`[data-testid="FreeForm - Entity1"]`);
        // We need to check for the normalized box-shadow value, which is different from what we set in the code, but equivalent
        return (
          node && window.getComputedStyle(node).getPropertyValue('box-shadow') === 'rgb(67, 160, 71) 0px 0px 2px 2px'
        );
      },
      { timeout: 2000 }
    );

    await page.mouse.up();

    // After the mode, the target's border/shadow should be back to its original state
    const boxShadowAfter = await entity1Locator.evaluate((node) => {
      return window.getComputedStyle(node).getPropertyValue('box-shadow');
    });
    expect(boxShadowAfter).toBe('none');
  });

  test('when dropping a node in a compatible target (other node), then a drop is triggered and the move is accepted', async ({
    page,
  }) => {
    let requestTriggered = false;
    page.on('request', (request) => {
      if (
        request.url().includes('api/graphql') &&
        request.method() === 'POST' &&
        JSON.parse(request.postData()).operationName === 'dropNodes'
      ) {
        requestTriggered = true;
      }
    });

    const playwrightNode = new PlaywrightNode(page, 'Entity3');
    const targetNodeLocator = new PlaywrightNode(page, 'Entity1');

    await targetNodeLocator.waitForAnimationToFinish();
    const xyTargetPosition = await targetNodeLocator.getDOMXYPosition();
    await playwrightNode.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await playwrightNode.page.mouse.down();
    await playwrightNode.page.mouse.move(xyTargetPosition.x + 100, xyTargetPosition.y + 50, { steps: 5 });
    await playwrightNode.page.mouse.up();

    await page.waitForFunction(
      () => {
        return !!document.querySelector(`[data-testid="FreeForm - dropped"]`);
      },
      { timeout: 2000 }
    );
    expect(requestTriggered).toBe(true);

    const playwrightNodeAfterDrop = new PlaywrightNode(page, 'dropped');
    const reactFlowXYPositionAfter = await playwrightNodeAfterDrop.getReactFlowXYPosition();
    expect(reactFlowXYPositionAfter.y).toBeGreaterThan(15);
    expect(reactFlowXYPositionAfter.x).toBeGreaterThan(50);
  });
});
