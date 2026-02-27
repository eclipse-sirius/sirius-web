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
        // The color used for the highlight is computed, and the normalized value we get from the browser does
        // not match the string we use to set it, so we only check that a box-shadow is set without trying to match
        // an exact value.
        return node && window.getComputedStyle(node).getPropertyValue('box-shadow')?.length > 0;
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
        return !!document.querySelector(`[data-testid="FreeForm - Entity3-dropped"]`);
      },
      { timeout: 2000 }
    );
    expect(requestTriggered).toBe(true);

    const playwrightNodeAfterDrop = new PlaywrightNode(page, 'Entity3-dropped');
    const reactFlowXYPositionAfter = await playwrightNodeAfterDrop.getReactFlowXYPosition();
    expect(reactFlowXYPositionAfter.y).toBeGreaterThan(15);
    expect(reactFlowXYPositionAfter.x).toBeGreaterThan(50);
  });
});

test.describe('diagram - drag and drop', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectNodeMultiDrops.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('multiDrops');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when dropping multi nodes, then a drop is triggered and the relative position of the nodes is preserved', async ({
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

    // Hide Node Panel Info to avoid overlap in diagram
    const panel = await page.locator('.react-flow__panel.bottom.left');
    await panel.evaluate((node) => {
      node.style.visibility = 'hidden';
    });

    const targetNodeLocator = new PlaywrightNode(page, 'Target');
    const entity31Node = new PlaywrightNode(page, 'E3-1');
    const entity32Node = new PlaywrightNode(page, 'E3-2');
    const entity33Node = new PlaywrightNode(page, 'E3-3');
    await targetNodeLocator.waitForAnimationToFinish();
    const entity31Position = await entity31Node.getReactFlowXYPosition('E3-1');
    const entity32Position = await entity32Node.getReactFlowXYPosition('E3-2');
    const entity33Position = await entity33Node.getReactFlowXYPosition('E3-3');

    const entity32RelativePositionX = entity32Position.x - entity31Position.x;
    const entity33RelativePositionX = entity33Position.x - entity31Position.x;
    const entity32RelativePositionY = entity32Position.y - entity31Position.y;
    const entity33RelativePositionY = entity33Position.y - entity31Position.y;

    await entity31Node.click();
    await entity32Node.controlClick();
    await entity33Node.controlClick();

    const xyTargetPosition = await targetNodeLocator.getDOMXYPosition();
    await entity31Node.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await entity31Node.page.mouse.down();
    await entity31Node.page.mouse.move(xyTargetPosition.x + 100, xyTargetPosition.y + 50, { steps: 5 });
    await entity31Node.page.mouse.up();

    await page.waitForFunction(
      () => {
        return !!document.querySelector(`[data-testid="FreeForm - E3-1-dropped"]`);
      },
      { timeout: 2000 }
    );
    expect(requestTriggered).toBe(true);

    const entity31DroppedNode = new PlaywrightNode(page, 'E3-1-dropped');
    const entity32DroppedNode = new PlaywrightNode(page, 'E3-2-dropped');
    const entity33DroppedNode = new PlaywrightNode(page, 'E3-3-dropped');

    const entity31PositionAfter = await entity31DroppedNode.getReactFlowXYPosition('E3-1-dropped');
    const entity32PositionAfter = await entity32DroppedNode.getReactFlowXYPosition('E3-2-dropped');
    const entity33PositionAfter = await entity33DroppedNode.getReactFlowXYPosition('E3-3-dropped');

    expect(entity32PositionAfter.x - entity31PositionAfter.x).toBe(entity32RelativePositionX);
    expect(entity32PositionAfter.y - entity31PositionAfter.y).toBe(entity32RelativePositionY);
    expect(entity33PositionAfter.x - entity31PositionAfter.x).toBe(entity33RelativePositionX);
    expect(entity33PositionAfter.y - entity31PositionAfter.y).toBe(entity33RelativePositionY);
  });
});

test.describe('diagram - drag and drop', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);

    const explorer = await new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.createRepresentation('NewSystem', 'Topography with auto layout', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a list node is dragged from its child, then wrong drop location reset the node position', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await page.getByTestId('arrange-all-menu').click();
    await page.getByTestId('arrange-all-elk-layered').click();

    const parentNode = new PlaywrightNode(page, 'Description', 'List');
    const parentNodePositionInitial = await parentNode.getReactFlowXYPosition('Description');

    const childNode = new PlaywrightNode(page, 'Weight: 0', 'IconLabel');
    await childNode.move({ x: -300, y: 0 });
    await childNode.waitForAnimationToFinish();

    const parentNodePosition = await parentNode.getReactFlowXYPosition('Description', true);
    expect(parentNodePosition.x).toBe(parentNodePositionInitial.x);
    expect(parentNodePosition.y).toBe(parentNodePositionInitial.y);
  });
});
