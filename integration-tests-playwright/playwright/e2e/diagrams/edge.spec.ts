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
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

const extractPoints = (path: string) => {
  const points: { x: number; y: number }[] = [];
  const regex = /[ML]\s*(-?\d+\.?\d*)\s*(-?\d+\.?\d*)/g;
  let match;
  while ((match = regex.exec(path)) !== null) {
    points.push({ x: parseFloat(match[1]), y: parseFloat(match[2]) });
  }
  return points;
};

const checkPathUniformOffsets = (expectedPath, receivedPath, errorMargin = 1) => {
  const expectedPoints = extractPoints(expectedPath);
  const receivedPoints = extractPoints(receivedPath);
  if (expectedPoints.length !== receivedPoints.length) {
    return false;
  }

  const deltasX: number[] = [];
  const deltasY: number[] = [];
  for (let i = 0; i < expectedPoints.length; i++) {
    const dx = receivedPoints[i]?.x ?? Number.MAX_VALUE - (expectedPoints[i]?.x ?? 0);
    const dy = receivedPoints[i]?.y ?? Number.MAX_VALUE - (expectedPoints[i]?.y ?? 0);
    deltasX.push(dx);
    deltasY.push(dy);
  }
  const dxRef = deltasX[0];
  const isDxUniform = dxRef && deltasX.every((dx) => Math.abs(dx - dxRef) <= errorMargin);
  const dyRef = deltasY[0];
  const isDyUniform = dyRef && deltasY.every((dy) => Math.abs(dy - dyRef) <= errorMargin);

  return isDxUniform && isDyUniform;
};

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });

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

  test('when a bend point is moved, then the edge path is changed', async ({ page }) => {
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const edgePathBefore = playwrightEdge.getEdgePath();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-1"]`).first();
    const box = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(box.x - 40, box.y + 40, { steps: 2 });
    await page.mouse.up();

    const edgePathAfter = playwrightEdge.getEdgePath();

    expect(edgePathAfter).not.toBe(edgePathBefore);
  });

  test('when last edge segment is moved, then a new bend point is added at the middle of node border', async ({
    page,
  }) => {
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode.move({ x: 200, y: 50 });

    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const edgePathBefore = playwrightEdge.getEdgePath();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-1"]`).first();
    const box = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(box.x - 40, box.y + 150, { steps: 2 });
    await page.mouse.up();

    const edgePathAfter = playwrightEdge.getEdgePath();
    expect(edgePathAfter).not.toBe(edgePathBefore);

    const newBendingPoint = page.locator(`[data-testid="bend-point-2"]`).first();
    expect(newBendingPoint).toBeAttached();
    const playwrightTargetNode = new PlaywrightNode(page, 'Processor1');
    const newBendingPointBox = (await newBendingPoint.boundingBox())!;
    const targetNodeBox = await playwrightTargetNode.getDOMBoundingBox();
    expect(newBendingPointBox.x + newBendingPointBox.width / 2).toBe(targetNodeBox.x + targetNodeBox.width / 2);
  });

  test('when moving a node, then custom handle are preserved', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'CompositeProcessor1');
    await playwrightNode.click();
    await playwrightNode.move({ x: 200, y: 50 });

    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-1"]`).first();
    const box = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(box.x - 40, box.y + 40, { steps: 2 });
    await page.mouse.up();

    await page.waitForFunction(
      () => {
        const parent = document.querySelector('[data-testid="FreeForm - Processor1"]');
        if (!parent) return false;

        const divHandle = parent.querySelector('.react-flow__handle-left') as HTMLElement;
        return divHandle && divHandle.style.top !== 'auto';
      },
      { timeout: 2000 }
    );

    const divBefore = await page.getByTestId('FreeForm - Processor1').locator('.react-flow__handle-left').first();
    const topValueBefore = await divBefore.evaluate((el) => el.style.top);

    const playwrightProcessorNode = new PlaywrightNode(page, 'Processor1');
    await playwrightProcessorNode.move({ x: 25, y: 25 });

    await page.waitForFunction(
      ({ expectedTopValue }) => {
        const parent = document.querySelector('[data-testid="FreeForm - Processor1"]');
        if (!parent) return false;

        const divHandle = parent.querySelector(
          '.react-flow__handle-left:not([data-handleid^="creationhandle"])'
        ) as HTMLElement;
        return divHandle && divHandle.style.top === expectedTopValue;
      },
      { expectedTopValue: topValueBefore },
      { timeout: 2000 }
    );
  });

  test('when a target node is revealed, then edge path is preserved', async ({ page }) => {
    const compositeProcessor = new PlaywrightNode(page, 'CompositeProcessor1');
    const dataSource = new PlaywrightNode(page, 'DataSource1');
    const edge = new PlaywrightEdge(page);
    await compositeProcessor.click();
    await compositeProcessor.move({ x: 150, y: 50 });

    await expect(edge.edgeLocator).toBeAttached();

    await compositeProcessor.openPalette();
    await page.getByTestId('toolSection-Show/Hide').click();
    await page.getByTestId('tool-Hide').click();

    await expect(edge.edgeLocator).not.toBeAttached();
    await dataSource.click();
    await dataSource.move({ x: 10, y: 20 });

    await page.getByTestId('reveal-hidden-elements').click();
    await expect(edge.edgeLocator).toBeAttached();

    const edgePathDBefore = await edge.getEdgePath();

    await dataSource.move({ x: 10, y: 20 });

    const edgePathAfter = await edge.getEdgePath();

    expect(edgePathDBefore).not.toBe(edgePathAfter);
  });
});

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectFlowForBendPointsDeletion.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.expand('NewSystem');
    await playwrightExplorer.select('Topography');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when source and target handles are aligned, then bend point are removed', async ({ page }) => {
    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    await expect(page.getByTestId(`bend-point-0`)).toBeAttached();

    const firstLine = page.getByTestId(`temporary-moving-line-0`);
    const box = (await firstLine.boundingBox())!;
    await firstLine.hover();
    await page.mouse.down();
    await page.mouse.move(box.x, box.y + 30, { steps: 2 });
    await page.mouse.up();

    await expect(page.getByTestId(`bend-point-0`)).not.toBeAttached();
  });
});

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('edge', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a node overlap a bend point, then the edge path is reset', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramSimpleEdge.xml');
    await playwrightExplorer.expand('diagramSimpleEdge.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramEdges - simple edges', 'diagram');

    const playwrightNode = new PlaywrightNode(page, 'Entity2');
    await playwrightNode.click();
    await playwrightNode.move({ x: 250, y: 200 });

    const playwrightEdge = new PlaywrightEdge(page);
    await playwrightEdge.click();

    const firstBendingPoint = page.locator(`[data-testid="bend-point-0"]`).first();
    const firstBendingPointBox = (await firstBendingPoint.boundingBox())!;
    await firstBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(firstBendingPointBox.x + 50, firstBendingPointBox.y + 50, { steps: 2 });
    await page.mouse.up();

    const lastBendingPoint = page.locator(`[data-testid="bend-point-1"]`).first();
    const lastBendingPointBox = (await lastBendingPoint.boundingBox())!;
    await lastBendingPoint.hover();
    await page.mouse.down();
    await page.mouse.move(lastBendingPointBox.x + 100, lastBendingPointBox.y + 50, { steps: 2 });
    await page.mouse.up();

    await playwrightNode.click();
    await playwrightNode.move({ x: -75, y: 25 });

    await playwrightEdge.openPalette();
    await expect(page.getByTestId('Reset-path')).not.toBeAttached();
    await playwrightEdge.closePalette();
  });
});

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeOnBorderNode.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Others...');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagramEdgeOnBorderNode diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when moving the source node, then border nodes moved to simplify the edge path', async ({ page }) => {
    const sourceNode = new PlaywrightNode(page, 'Entity1-source');
    const sourceBorderNode = new PlaywrightNode(page, 'bordernode-source');
    const targetNode = new PlaywrightNode(page, 'Entity1-target');
    const targetBorderNode = new PlaywrightNode(page, 'bordernode-target');
    const sourceSizeStep1 = await sourceNode.getReactFlowSize('Entity1-source');
    const sourceBorderPositionStep1 = await sourceBorderNode.getReactFlowXYPosition('bordernode-source');
    const targetBorderPositionStep1 = await targetBorderNode.getReactFlowXYPosition('bordernode-target');
    const targetBorderNodeSizeStep1 = await targetBorderNode.getReactFlowSize('bordernode-target', false);

    const borderNodeGap = 5;
    // First position source node left of target node
    expect(sourceBorderPositionStep1.x).toBe(sourceSizeStep1.width - borderNodeGap);
    expect(targetBorderPositionStep1.x).toBe(-targetBorderNodeSizeStep1.width + borderNodeGap);

    // Second position source node top of target node
    await sourceNode.move({ x: 300, y: -125 });
    await targetNode.move({ x: -150, y: 50 });
    const sourceSizeStep2 = await sourceNode.getReactFlowSize('Entity1-source');
    const sourceBorderPositionStep2 = await sourceBorderNode.getReactFlowXYPosition('bordernode-source');
    const targetBorderPositionStep2 = await targetBorderNode.getReactFlowXYPosition('bordernode-target');
    const targetBorderNodeSizeStep2 = await targetBorderNode.getReactFlowSize('bordernode-target', false);
    expect(sourceBorderPositionStep2.y).toBe(sourceSizeStep2.height - borderNodeGap);
    expect(targetBorderPositionStep2.y).toBe(-targetBorderNodeSizeStep2.height + borderNodeGap);

    // third position source node right of target node
    await targetNode.move({ x: 0, y: -50 });
    await sourceNode.move({ x: 300, y: 10 });
    const sourceBorderPositionStep3 = await sourceBorderNode.getReactFlowXYPosition('bordernode-source');
    const sourceBorderNodeSizeStep3 = await sourceBorderNode.getReactFlowSize('bordernode-source', false);
    const targetSizeStep3 = await targetNode.getReactFlowSize('Entity1-target');
    const targetBorderPositionStep3 = await targetBorderNode.getReactFlowXYPosition('bordernode-target');
    expect(sourceBorderPositionStep3.x).toBe(-sourceBorderNodeSizeStep3.width + borderNodeGap);
    expect(targetBorderPositionStep3.x).toBe(targetSizeStep3.width - borderNodeGap);
  });
});

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeCrossFadeTunnel.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('CrossFadeTunnels');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagramEdges diagram');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an edge is hide, then the cross fade tunnel is removed', async ({ page }) => {
    // Helper function to check for cross fade tunnels count
    const waitForCrossFadeTunnelCount = async (expectedCount: number) => {
      await page.waitForFunction(
        (count) => {
          let edgeWithStrokeDasharray: number = 0;
          const edgePaths = document.querySelectorAll('.react-flow__edge-path');
          if (!parent) return false;
          // Loop through edges to count the number of edges with a stroke-dasharray
          edgePaths.forEach((edgePath) => {
            const style = edgePath.getAttribute('style');
            if (style && style.includes('stroke-dasharray')) {
              edgeWithStrokeDasharray++;
            }
          });
          return edgeWithStrokeDasharray === count;
        },
        expectedCount,
        { timeout: 2000 }
      );
    };

    await waitForCrossFadeTunnelCount(1);

    const a2Node = new PlaywrightNode(page, 'A2');
    await a2Node.openPalette();
    await page.getByTestId('tool-Hide').click();
    await expect(a2Node.nodeLocator).not.toBeVisible();

    await waitForCrossFadeTunnelCount(0);

    await page.getByTestId('reveal-hidden-elements').click();
    await expect(a2Node.nodeLocator).toBeVisible();

    await waitForCrossFadeTunnelCount(1);
  });
});

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeWithBendingPoints.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.expand('NewSystem');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a manhattan edge has bending points, then changing its type reset these bending point', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('ManhattanEdgeWithBendingPoints');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.edgeLocator.locator('path').first().click({ button: 'right' });
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Edge Type"]').click();
    await page.waitForSelector('.MuiMenu-paper');
    await page.locator('[data-value="Oblique"]').click();

    await page.waitForFunction(
      () => {
        const edgePath = document.querySelector('[data-testid^="rf__edge-"] path')?.getAttribute('d');
        return edgePath?.trim().match(/^M\s?[\d.-]+\s?[\d.-]+\s?L\s?[\d.-]+\s[\d.-]+$/);
      },
      { timeout: 2000 }
    );
  });

  test('when a oblique edge has one bending point, then path is composed of two lines', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('ObliqueEdgeWithBendingPoints');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await page.waitForFunction(
      () => {
        const edgePath = document.querySelector('[data-testid^="rf__edge-"] path')?.getAttribute('d');
        return edgePath?.trim().match(/^M\s?[\d.-]+\s?[\d.-]+(\s?L\s?[\d.-]+\s[\d.-]+){2}$/);
      },
      { timeout: 2000 }
    );
  });
});

test.describe('edge', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectEdgeOnSelfWithBendingPoints.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('edgeOnSelf');
    await playwrightExplorer.expand('Root');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when moving source and target nodes, then edge path bending points preserve their relative position', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.select('diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const playwrightEdge1 = new PlaywrightEdge(page, 0);
    const playwrightEdge2 = new PlaywrightEdge(page, 1);
    const playwrightEdge3 = new PlaywrightEdge(page, 2);
    await expect(playwrightEdge1.edgeLocator).toBeAttached();
    await expect(playwrightEdge2.edgeLocator).toBeAttached();
    await expect(playwrightEdge3.edgeLocator).toBeAttached();

    const edge1Path = await playwrightEdge1.getEdgePath();
    const edge2Path = await playwrightEdge2.getEdgePath();
    const edge3Path = await playwrightEdge3.getEdgePath();

    const node1 = new PlaywrightNode(page, 'A');
    await node1.move({ x: 100, y: 100 });

    const edge1PathAfter = await playwrightEdge1.getEdgePath();
    const edge2PathAfter = await playwrightEdge2.getEdgePath();
    const edge3PathAfter = await playwrightEdge3.getEdgePath();

    expect(edge1Path).not.toBe(edge1PathAfter);
    expect(edge2Path).not.toBe(edge2PathAfter);
    expect(edge3Path).not.toBe(edge3PathAfter);

    const checkPath1 = checkPathUniformOffsets(edge1Path, edge1PathAfter);
    const checkPath2 = checkPathUniformOffsets(edge2Path, edge2PathAfter);
    const checkPath3 = checkPathUniformOffsets(edge3Path, edge3PathAfter);
    expect(checkPath1).toBeTruthy();
    expect(checkPath2).toBeTruthy();
    expect(checkPath3).toBeTruthy();
  });
});
