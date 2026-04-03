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
import { PlaywrightDetails } from '../../helpers/PlaywrightDetails';
import { PlaywrightDiagram } from '../../helpers/PlaywrightDiagram';
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightLabel } from '../../helpers/PlaywrightLabel';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightNodeLabel } from '../../helpers/PlaywrightNodeLabel';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - label', () => {
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

  test('when a node with an outside label is selected, then the label is highlighted', async ({ page }) => {
    const dataSourceNode = new PlaywrightNode(page, 'DataSource1');
    await dataSourceNode.click();

    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label content - DataSource1"]`);
        if (!label) {
          return false;
        }
        const draggableDiv = label.closest('.react-draggable');
        if (!draggableDiv) {
          return false;
        }
        const style = window.getComputedStyle(draggableDiv);
        return style.borderColor === 'rgb(190, 26, 120)' && style.borderWidth === '1px';
      },
      { timeout: 2000 }
    );
  });

  test('when a node with an empty label is selected, then the label is not highlighted', async ({ page }) => {
    const dataSourceNode = new PlaywrightNode(page, 'DataSource1');
    await dataSourceNode.click();

    await dataSourceNode.openPalette();
    await page.getByTestId('toolSection-Show in').click();
    await page.getByTestId('push-diagram-selection-to-details').click();

    const playwrightDetails = new PlaywrightDetails(page);
    await playwrightDetails.setText('Name', '');

    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label content - "]`);
        if (!label) {
          return false;
        }
        const draggableDiv = label.closest('.react-draggable');
        if (!draggableDiv) {
          return false;
        }
        const style = window.getComputedStyle(draggableDiv);
        return style.borderWidth === '0px';
      },
      { timeout: 2000 }
    );
  });

  test('moving an outside node label by click', async ({ page }) => {
    const dataSourceLabel = new PlaywrightNodeLabel(page, 'DataSource1');
    await dataSourceLabel.click();

    const positionOnLabelToStartDragging = { x: 10, y: 10 };
    const initialBox = await dataSourceLabel.labelLocator.boundingBox();
    expect(initialBox).toBeDefined();
    await dataSourceLabel.move(
      { x: (initialBox?.x ?? 0) + 50, y: (initialBox?.y ?? 0) + 150 },
      positionOnLabelToStartDragging
    );

    await page.waitForFunction(
      ({ expectedX, expectedY }) => {
        const label = document.querySelector(`[data-testid="Label - DataSource1"]`);
        if (!label) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        return (
          labelBoundingBox.x > expectedX - 5 &&
          labelBoundingBox.x < expectedX + 5 &&
          labelBoundingBox.y > expectedY - 5 &&
          labelBoundingBox.y < expectedY + 5
        );
      },
      {
        expectedX: (initialBox?.x ?? 0) + 50 - positionOnLabelToStartDragging.x,
        expectedY: (initialBox?.y ?? 0) + 150 - positionOnLabelToStartDragging.y,
      },
      { timeout: 2000 }
    );

    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    await playwrightNode.resetNodeLabelPosition();

    await page.waitForFunction(
      ({ expectedX, expectedY }) => {
        const label = document.querySelector(`[data-testid="Label - DataSource1"]`);
        if (!label || !expectedX || !expectedY) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        return (
          labelBoundingBox.x > expectedX - 5 &&
          labelBoundingBox.x < expectedX + 5 &&
          labelBoundingBox.y > expectedY - 5 &&
          labelBoundingBox.y < expectedY + 5
        );
      },
      {
        expectedX: initialBox?.x,
        expectedY: initialBox?.y,
      },
      { timeout: 2000 }
    );
  });
});

test.describe('diagram - label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-resizable-label', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramResizableLabel.xml');
    await playwrightExplorer.expand('diagramResizableLabel.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramEdges - simple edges', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a resizable label is selected, then anchor are displayed', async ({ page }) => {
    const entity1Node = new PlaywrightNode(page, 'Entity1');

    const edge = new PlaywrightEdge(page);
    const entity1OutsideLabel = new PlaywrightLabel(page, 'entity1-outside-label');
    await expect(entity1OutsideLabel.labelLocator).toBeAttached();
    await expect(page.locator('.react-resizable-handle')).toHaveCount(0);
    await entity1Node.nodeLocator.hover({ position: { x: 10, y: 10 } });
    await expect(page.locator('.react-resizable-handle')).toHaveCount(0); // should not be displayed on hover either
    await entity1Node.click();
    await expect(page.locator('.react-resizable-handle')).toHaveCount(1);

    await entity1Node.move({ x: -100, y: -100 });
    await edge.click();

    await expect(page.locator('.react-resizable-handle')).toHaveCount(3);

    const details = new PlaywrightDetails(page);
    await details.setText('Begin Name', '');
    await expect(page.locator('.react-resizable-handle')).toHaveCount(2); // No resize handle if there is no text
  });

  test('when a label is resize, then dom size change', async ({ page }) => {
    const entity2Node = new PlaywrightNode(page, 'Entity2');

    const entity2OutsideLabel = new PlaywrightLabel(page, 'entity2-outside-label');
    await expect(entity2OutsideLabel.labelLocator).toBeAttached();
    await entity2Node.nodeLocator.click({ position: { x: 50, y: 10 } });
    await expect(page.locator('.react-resizable-handle')).toHaveCount(1);
    const contentBoxBefore = await page.getByTestId('Label content - entity2-outside-label').boundingBox();
    const box = await page.locator('.react-resizable-handle').boundingBox();

    expect(box).not.toBeNull();
    if (!box) {
      return;
    }
    await page.locator('.react-resizable-handle').hover();
    const startX = box.x + box.width / 2;
    const startY = box.y + box.height / 2;
    await page.mouse.down();
    await page.mouse.move(startX - 50, startY + 50, { steps: 2 });
    await page.mouse.up();
    await page.waitForFunction(
      ({ expectedWidth, expectedHeight }) => {
        const label = document.querySelector(`[data-testid="Label content - entity2-outside-label"]`);
        if (!label || !expectedWidth || !expectedHeight) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        return labelBoundingBox.width < expectedWidth && labelBoundingBox.height > expectedHeight;
      },
      {
        expectedWidth: contentBoxBefore?.width,
        expectedHeight: contentBoxBefore?.height,
      },
      { timeout: 2000 }
    );
  });
});
test.describe('diagram - label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectWithLabelResized.zip');
    await expect(page.locator('[data-testid^="explorer://"]')).toBeAttached();
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when the reset label size tool is triggered on an outside label, then label size is reset', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('diagramWithLabelResized');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');

    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const outsideLabel = new PlaywrightLabel(page, 'OutsideLabel');
    const outsideLabelContentBoxBefore = await outsideLabel.labelContentLocator.boundingBox();

    const entity1Node = new PlaywrightNode(page, 'Entity1');
    await entity1Node.openPalette();
    await expect(page.getByTestId('Reset labels sizes - Tool')).toBeAttached();
    await page.getByTestId('Reset labels sizes - Tool').click();

    const outsideLabelContentBoxAfter = await outsideLabel.labelContentLocator.boundingBox();

    expect(outsideLabelContentBoxAfter?.height).not.toBe(outsideLabelContentBoxBefore?.height);
    expect(outsideLabelContentBoxAfter?.width).not.toBe(outsideLabelContentBoxBefore?.width);

    await entity1Node.openPalette();
    await expect(page.getByTestId('Reset labels sizes - Tool')).not.toBeAttached();
  });

  test('when the reset label size tool is triggered on an edge, then all labels size are reset', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('diagramWithLabelResized');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');

    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const beginLabelContentBoxBefore = await page.getByTestId('Label content - Begin').boundingBox();
    const centerLabelContentBoxBefore = await page.getByTestId('Label content - Center').boundingBox();
    const endLabelContentBoxBefore = await page.getByTestId('Label content - End').boundingBox();

    const edge = new PlaywrightEdge(page);
    await edge.openPalette();
    await expect(page.getByTestId('Reset labels sizes - Tool')).toBeAttached();
    await page.getByTestId('Reset labels sizes - Tool').click();

    const beginLabelContentBoxAfter = await page.getByTestId('Label content - Begin').boundingBox();
    const centerLabelContentBoxAfter = await page.getByTestId('Label content - Center').boundingBox();
    const endLabelContentBoxAfter = await page.getByTestId('Label content - End').boundingBox();

    expect(beginLabelContentBoxAfter?.height).not.toBe(beginLabelContentBoxBefore?.height);
    expect(beginLabelContentBoxAfter?.width).not.toBe(beginLabelContentBoxBefore?.width);
    expect(centerLabelContentBoxAfter?.height).not.toBe(centerLabelContentBoxBefore?.height);
    expect(centerLabelContentBoxAfter?.width).not.toBe(centerLabelContentBoxBefore?.width);
    expect(endLabelContentBoxAfter?.height).not.toBe(endLabelContentBoxBefore?.height);
    expect(endLabelContentBoxAfter?.width).not.toBe(endLabelContentBoxBefore?.width);

    await edge.openPalette();
    await expect(page.getByTestId('Reset labels sizes - Tool')).not.toBeAttached();
  });

  test('when a label is hidden, then no anchors are visible', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('diagramWithLabelResized');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');

    const entity1Node = new PlaywrightNode(page, 'Entity1');
    const edge = new PlaywrightEdge(page);
    const entity1OutsideLabel = new PlaywrightLabel(page, 'OutsideLabel');

    await expect(entity1OutsideLabel.labelLocator).toBeAttached();
    await entity1Node.click();

    await expect(page.locator('.react-resizable-handle')).toHaveCount(1);
    await entity1Node.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Hide"]').last().click();
    await entity1Node.closePalette();
    await expect(page.locator('.react-resizable-handle')).toHaveCount(0);

    await edge.click();
    await expect(page.locator('.react-resizable-handle')).toHaveCount(3);
    await edge.openPalette();
    await page.getByTestId('toolSection-Appearance').click();
    await page.locator('[data-testid="toolSection-Appearance-Hide"]').first().click();
    await edge.closePalette();
    await expect(page.locator('.react-resizable-handle')).toHaveCount(2);
  });

  test('when a label moved is resize, then it position is unchanged', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('diagramWithLabelResized');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await page.getByTestId('Label - Moved').click();
    const labelBoxBefore = await page.getByTestId('Label - Moved').boundingBox();
    const labelResizer = page.locator('.react-resizable-handle');
    await labelResizer.first().hover();
    await page.mouse.down();
    await page.mouse.move(20, 20);
    await page.mouse.up();
    const labelBoxAfter = await page.getByTestId('Label - Moved').boundingBox();
    expect(labelBoxAfter?.x).toEqual(labelBoxBefore?.x);
    expect(labelBoxAfter?.y).toEqual(labelBoxBefore?.y);
  });
});

test.describe('diagram - label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('diagram-border-node-label', 'blank-project');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramWithBorderNodeWithOutsideLabel.xml');
    await playwrightExplorer.expand('diagramWithBorderNodeWithOutsideLabel.xml');
    await playwrightExplorer.createRepresentation('Root', 'diagramBorderNode - simple border', 'diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });
  test('when a border node has an outside label, then it is not placed on the parent node', async ({ page }) => {
    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label content - north"]`);
        const borderNode = document.querySelector(`[data-testid="FreeForm - north"]`);
        if (!label || !borderNode) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        const borderNodeBoundingBox = borderNode.getBoundingClientRect();
        // border node position = north => label position = north
        return labelBoundingBox.y < borderNodeBoundingBox.y;
      },
      { timeout: 2000 }
    );
    const borderNode = new PlaywrightNode(page, 'north');
    await borderNode.waitForAnimationToFinish();
    await borderNode.move({ x: 600, y: 75 });

    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label content - north"]`);
        const borderNode = document.querySelector(`[data-testid="FreeForm - north"]`);
        if (!label || !borderNode) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        const borderNodeBoundingBox = borderNode.getBoundingClientRect();
        // border node position = east => label position = east
        return (
          labelBoundingBox.y > borderNodeBoundingBox.y &&
          Math.trunc(labelBoundingBox.x) >= Math.trunc(borderNodeBoundingBox.x + borderNodeBoundingBox.width)
        );
      },
      { timeout: 2000 }
    );
  });
});

test.describe('diagram - label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Studio', 'studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when dragging a center edge label, then no offset apply on the drop position', async ({ page }) => {
    await page.getByTestId('arrange-all-main-button').click();
    const rootNode = new PlaywrightNode(page, 'Root', 'List');
    await rootNode.waitForAnimationToFinish();
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    const labelBoundingBox = await page.getByTestId('Label - entity1s [0..*]').boundingBox();
    await page.getByTestId('Label - entity1s [0..*]').hover({ position: { x: 10, y: 10 } });
    await page.mouse.down();
    await page.mouse.move((labelBoundingBox?.x ?? 0) + 100, (labelBoundingBox?.y ?? 0) + 100);
    await page.mouse.up();

    await page.waitForFunction(
      ({ expectedX, expectedY }) => {
        const label = document.querySelector(`[data-testid="Label - entity1s [0..*]"]`);
        if (!label) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        return (
          labelBoundingBox.x >= expectedX - 2 &&
          labelBoundingBox.x <= expectedX + 2 &&
          labelBoundingBox.y >= expectedY - 2 &&
          labelBoundingBox.y <= expectedY + 2
        );
      },
      { expectedX: (labelBoundingBox?.x ?? 0) + 100 - 10, expectedY: (labelBoundingBox?.y ?? 0) + 100 - 10 },
      { timeout: 2000 }
    );
  });
});

test.describe('diagram - label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectLabelAlignment.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('labelAlignment');
    await playwrightExplorer.expand('Root');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when the inside label has a position and overflow to none, then label position is correct', async ({
    page,
  }) => {
    await new PlaywrightExplorer(page).select('diagram none');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    // TOP
    // LEFT
    const nodeTopLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 0);
    await nodeTopLeft.waitForAnimationToFinish();
    await new PlaywrightDiagram(page).hideDebugPanel();
    const labelTopLeft = new PlaywrightLabel(page, 'TOP_LEFT');
    const nodeTopLeftBoundingBox = await nodeTopLeft.nodeLocator.boundingBox();
    const labelTopLeftBoundingBox = await labelTopLeft.labelContentLocator.locator('[data-svg="text"]').boundingBox();
    expect((labelTopLeftBoundingBox?.x ?? 0) - (nodeTopLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopLeftBoundingBox?.x ?? 0) - (nodeTopLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelTopLeftBoundingBox?.y ?? 0) - (nodeTopLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopLeftBoundingBox?.y ?? 0) - (nodeTopLeftBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopLeftBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //CENTER
    const nodeTopCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 1);
    const labelTopCenter = new PlaywrightLabel(page, 'TOP_CENTER');
    const nodeTopCenterBoundingBox = await nodeTopCenter.nodeLocator.boundingBox();
    const labelTopCenterBoundingBox = await labelTopCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelTopCenterBoundingBox?.x ?? 0) - (nodeTopCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelTopCenterBoundingBox?.x ?? 0) - (nodeTopCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelTopCenterBoundingBox?.y ?? 0) - (nodeTopCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopCenterBoundingBox?.y ?? 0) - (nodeTopCenterBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopCenterBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //RIGHT
    const nodeTopRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 2);
    const labelTopRight = new PlaywrightLabel(page, 'TOP_RIGHT');
    const nodeTopRightBoundingBox = await nodeTopRight.nodeLocator.boundingBox();
    const labelTopRightBoundingBox = await labelTopRight.labelContentLocator.locator('[data-svg="text"]').boundingBox();
    expect((labelTopRightBoundingBox?.x ?? 0) - (nodeTopRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(140);
    expect((labelTopRightBoundingBox?.x ?? 0) - (nodeTopRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(130);
    expect((labelTopRightBoundingBox?.y ?? 0) - (nodeTopRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopRightBoundingBox?.y ?? 0) - (nodeTopRightBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopRightBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line

    // MIDDLE
    // LEFT
    const nodeMiddleLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 3);
    await nodeMiddleLeft.waitForAnimationToFinish();
    const labelMiddleLeft = new PlaywrightLabel(page, 'MIDDLE_LEFT');
    const nodeMiddleLeftBoundingBox = await nodeMiddleLeft.nodeLocator.boundingBox();
    const labelMiddleLeftBoundingBox = await labelMiddleLeft.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleLeftBoundingBox?.x ?? 0) - (nodeMiddleLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelMiddleLeftBoundingBox?.x ?? 0) - (nodeMiddleLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelMiddleLeftBoundingBox?.y ?? 0) - (nodeMiddleLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleLeftBoundingBox?.y ?? 0) - (nodeMiddleLeftBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleLeftBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //CENTER
    const nodeMiddleCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 4);
    const labelMiddleCenter = new PlaywrightLabel(page, 'MIDDLE_CENTER');
    const nodeMiddleCenterBoundingBox = await nodeMiddleCenter.nodeLocator.boundingBox();
    const labelMiddleCenterBoundingBox = await labelMiddleCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleCenterBoundingBox?.x ?? 0) - (nodeMiddleCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelMiddleCenterBoundingBox?.x ?? 0) - (nodeMiddleCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelMiddleCenterBoundingBox?.y ?? 0) - (nodeMiddleCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleCenterBoundingBox?.y ?? 0) - (nodeMiddleCenterBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleCenterBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //RIGHT
    const nodeMiddleRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 5);
    const labelMiddleRight = new PlaywrightLabel(page, 'MIDDLE_RIGHT');
    const nodeMiddleRightBoundingBox = await nodeMiddleRight.nodeLocator.boundingBox();
    const labelMiddleRightBoundingBox = await labelMiddleRight.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleRightBoundingBox?.x ?? 0) - (nodeMiddleRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(140);
    expect((labelMiddleRightBoundingBox?.x ?? 0) - (nodeMiddleRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(125);
    expect((labelMiddleRightBoundingBox?.y ?? 0) - (nodeMiddleRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleRightBoundingBox?.y ?? 0) - (nodeMiddleRightBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleRightBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line

    // BOTTOM
    // LEFT
    const nodeBottomLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 6);
    await nodeBottomLeft.waitForAnimationToFinish();
    const labelBottomLeft = new PlaywrightLabel(page, 'BOTTOM_LEFT');
    const nodeBottomLeftBoundingBox = await nodeBottomLeft.nodeLocator.boundingBox();
    const labelBottomLeftBoundingBox = await labelBottomLeft.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomLeftBoundingBox?.x ?? 0) - (nodeBottomLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelBottomLeftBoundingBox?.x ?? 0) - (nodeBottomLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelBottomLeftBoundingBox?.y ?? 0) - (nodeBottomLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomLeftBoundingBox?.y ?? 0) - (nodeBottomLeftBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(155);
    expect(labelBottomLeftBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //CENTER
    const nodeBottomCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 7);
    const labelBottomCenter = new PlaywrightLabel(page, 'BOTTOM_CENTER');
    const nodeBottomCenterBoundingBox = await nodeBottomCenter.nodeLocator.boundingBox();
    const labelBottomCenterBoundingBox = await labelBottomCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomCenterBoundingBox?.x ?? 0) - (nodeBottomCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelBottomCenterBoundingBox?.x ?? 0) - (nodeBottomCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelBottomCenterBoundingBox?.y ?? 0) - (nodeBottomCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomCenterBoundingBox?.y ?? 0) - (nodeBottomCenterBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(155);
    expect(labelBottomCenterBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //RIGHT
    const nodeBottomRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 8);
    const labelBottomRight = new PlaywrightLabel(page, 'BOTTOM_RIGHT');
    const nodeBottomRightBoundingBox = await nodeBottomRight.nodeLocator.boundingBox();
    const labelBottomRightBoundingBox = await labelBottomRight.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomRightBoundingBox?.x ?? 0) - (nodeBottomRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(140);
    expect((labelBottomRightBoundingBox?.x ?? 0) - (nodeBottomRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(125);
    expect((labelBottomRightBoundingBox?.y ?? 0) - (nodeBottomRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomRightBoundingBox?.y ?? 0) - (nodeBottomRightBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(155);
    expect(labelBottomRightBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
  });

  test('when the inside label has a position and overflow to wrap, then label position is correct', async ({
    page,
  }) => {
    await new PlaywrightExplorer(page).select('diagram wrap');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    // TOP
    // LEFT
    const nodeTopLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 0);
    await nodeTopLeft.waitForAnimationToFinish();
    await new PlaywrightDiagram(page).hideDebugPanel();
    const labelTopLeft = new PlaywrightLabel(page, 'TOP_LEFT');
    const nodeTopLeftBoundingBox = await nodeTopLeft.nodeLocator.boundingBox();
    const labelTopLeftBoundingBox = await labelTopLeft.labelContentLocator.locator('[data-svg="text"]').boundingBox();
    expect((labelTopLeftBoundingBox?.x ?? 0) - (nodeTopLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopLeftBoundingBox?.x ?? 0) - (nodeTopLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelTopLeftBoundingBox?.y ?? 0) - (nodeTopLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopLeftBoundingBox?.y ?? 0) - (nodeTopLeftBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopLeftBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines
    //CENTER
    const nodeTopCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 1);
    const labelTopCenter = new PlaywrightLabel(page, 'TOP_CENTER');
    const nodeTopCenterBoundingBox = await nodeTopCenter.nodeLocator.boundingBox();
    const labelTopCenterBoundingBox = await labelTopCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelTopCenterBoundingBox?.x ?? 0) - (nodeTopCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelTopCenterBoundingBox?.x ?? 0) - (nodeTopCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelTopCenterBoundingBox?.y ?? 0) - (nodeTopCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopCenterBoundingBox?.y ?? 0) - (nodeTopCenterBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopCenterBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines
    //RIGHT
    const nodeTopRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 2);
    const labelTopRight = new PlaywrightLabel(page, 'TOP_RIGHT');
    const nodeTopRightBoundingBox = await nodeTopRight.nodeLocator.boundingBox();
    const labelTopRightBoundingBox = await labelTopRight.labelContentLocator.locator('[data-svg="text"]').boundingBox();
    expect((labelTopRightBoundingBox?.x ?? 0) - (nodeTopRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(145);
    expect((labelTopRightBoundingBox?.x ?? 0) - (nodeTopRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(130);
    expect((labelTopRightBoundingBox?.y ?? 0) - (nodeTopRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopRightBoundingBox?.y ?? 0) - (nodeTopRightBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopRightBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines

    // MIDDLE
    // LEFT
    const nodeMiddleLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 3);
    await nodeMiddleLeft.waitForAnimationToFinish();
    const labelMiddleLeft = new PlaywrightLabel(page, 'MIDDLE_LEFT');
    const nodeMiddleLeftBoundingBox = await nodeMiddleLeft.nodeLocator.boundingBox();
    const labelMiddleLeftBoundingBox = await labelMiddleLeft.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleLeftBoundingBox?.x ?? 0) - (nodeMiddleLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelMiddleLeftBoundingBox?.x ?? 0) - (nodeMiddleLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelMiddleLeftBoundingBox?.y ?? 0) - (nodeMiddleLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleLeftBoundingBox?.y ?? 0) - (nodeMiddleLeftBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleLeftBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines
    //CENTER
    const nodeMiddleCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 4);
    const labelMiddleCenter = new PlaywrightLabel(page, 'MIDDLE_CENTER');
    const nodeMiddleCenterBoundingBox = await nodeMiddleCenter.nodeLocator.boundingBox();
    const labelMiddleCenterBoundingBox = await labelMiddleCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleCenterBoundingBox?.x ?? 0) - (nodeMiddleCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelMiddleCenterBoundingBox?.x ?? 0) - (nodeMiddleCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelMiddleCenterBoundingBox?.y ?? 0) - (nodeMiddleCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleCenterBoundingBox?.y ?? 0) - (nodeMiddleCenterBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleCenterBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines
    //RIGHT
    const nodeMiddleRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 5);
    const labelMiddleRight = new PlaywrightLabel(page, 'MIDDLE_RIGHT');
    const nodeMiddleRightBoundingBox = await nodeMiddleRight.nodeLocator.boundingBox();
    const labelMiddleRightBoundingBox = await labelMiddleRight.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleRightBoundingBox?.x ?? 0) - (nodeMiddleRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(145);
    expect((labelMiddleRightBoundingBox?.x ?? 0) - (nodeMiddleRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(125);
    expect((labelMiddleRightBoundingBox?.y ?? 0) - (nodeMiddleRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleRightBoundingBox?.y ?? 0) - (nodeMiddleRightBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleRightBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines

    // BOTTOM
    // LEFT
    const nodeBottomLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 6);
    await nodeBottomLeft.waitForAnimationToFinish();
    const labelBottomLeft = new PlaywrightLabel(page, 'BOTTOM_LEFT');
    const nodeBottomLeftBoundingBox = await nodeBottomLeft.nodeLocator.boundingBox();
    const labelBottomLeftBoundingBox = await labelBottomLeft.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomLeftBoundingBox?.x ?? 0) - (nodeBottomLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelBottomLeftBoundingBox?.x ?? 0) - (nodeBottomLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelBottomLeftBoundingBox?.y ?? 0) - (nodeBottomLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomLeftBoundingBox?.y ?? 0) - (nodeBottomLeftBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(145);
    expect(labelBottomLeftBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines
    //CENTER
    const nodeBottomCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 7);
    const labelBottomCenter = new PlaywrightLabel(page, 'BOTTOM_CENTER');
    const nodeBottomCenterBoundingBox = await nodeBottomCenter.nodeLocator.boundingBox();
    const labelBottomCenterBoundingBox = await labelBottomCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomCenterBoundingBox?.x ?? 0) - (nodeBottomCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelBottomCenterBoundingBox?.x ?? 0) - (nodeBottomCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelBottomCenterBoundingBox?.y ?? 0) - (nodeBottomCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomCenterBoundingBox?.y ?? 0) - (nodeBottomCenterBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(145);
    expect(labelBottomCenterBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines
    //RIGHT
    const nodeBottomRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 8);
    const labelBottomRight = new PlaywrightLabel(page, 'BOTTOM_RIGHT');
    const nodeBottomRightBoundingBox = await nodeBottomRight.nodeLocator.boundingBox();
    const labelBottomRightBoundingBox = await labelBottomRight.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomRightBoundingBox?.x ?? 0) - (nodeBottomRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(145);
    expect((labelBottomRightBoundingBox?.x ?? 0) - (nodeBottomRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(125);
    expect((labelBottomRightBoundingBox?.y ?? 0) - (nodeBottomRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomRightBoundingBox?.y ?? 0) - (nodeBottomRightBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(145);
    expect(labelBottomRightBoundingBox?.height).toBeGreaterThan(8); // Check label is split on 2 lines
  });

  test('when the inside label has a position and overflow to ellipse, then label position is correct', async ({
    page,
  }) => {
    await new PlaywrightExplorer(page).select('diagram ellipse');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    // TOP
    // LEFT
    const nodeTopLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 0);
    await nodeTopLeft.waitForAnimationToFinish();
    await new PlaywrightDiagram(page).hideDebugPanel();
    const labelTopLeft = new PlaywrightLabel(page, 'TOP_LEFT');
    const nodeTopLeftBoundingBox = await nodeTopLeft.nodeLocator.boundingBox();
    const labelTopLeftBoundingBox = await labelTopLeft.labelContentLocator.locator('[data-svg="text"]').boundingBox();
    expect((labelTopLeftBoundingBox?.x ?? 0) - (nodeTopLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopLeftBoundingBox?.x ?? 0) - (nodeTopLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelTopLeftBoundingBox?.y ?? 0) - (nodeTopLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopLeftBoundingBox?.y ?? 0) - (nodeTopLeftBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopLeftBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //CENTER
    const nodeTopCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 1);
    const labelTopCenter = new PlaywrightLabel(page, 'TOP_CENTER');
    const nodeTopCenterBoundingBox = await nodeTopCenter.nodeLocator.boundingBox();
    const labelTopCenterBoundingBox = await labelTopCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelTopCenterBoundingBox?.x ?? 0) - (nodeTopCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelTopCenterBoundingBox?.x ?? 0) - (nodeTopCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelTopCenterBoundingBox?.y ?? 0) - (nodeTopCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopCenterBoundingBox?.y ?? 0) - (nodeTopCenterBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopCenterBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //RIGHT
    const nodeTopRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 2);
    const labelTopRight = new PlaywrightLabel(page, 'TOP_RIGHT');
    const nodeTopRightBoundingBox = await nodeTopRight.nodeLocator.boundingBox();
    const labelTopRightBoundingBox = await labelTopRight.labelContentLocator.locator('[data-svg="text"]').boundingBox();
    expect((labelTopRightBoundingBox?.x ?? 0) - (nodeTopRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(145);
    expect((labelTopRightBoundingBox?.x ?? 0) - (nodeTopRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(130);
    expect((labelTopRightBoundingBox?.y ?? 0) - (nodeTopRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(10);
    expect((labelTopRightBoundingBox?.y ?? 0) - (nodeTopRightBoundingBox?.y ?? 0)).toBeGreaterThan(0);
    expect(labelTopRightBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line

    // MIDDLE
    // LEFT
    const nodeMiddleLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 3);
    await nodeMiddleLeft.waitForAnimationToFinish();
    const labelMiddleLeft = new PlaywrightLabel(page, 'MIDDLE_LEFT');
    const nodeMiddleLeftBoundingBox = await nodeMiddleLeft.nodeLocator.boundingBox();
    const labelMiddleLeftBoundingBox = await labelMiddleLeft.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleLeftBoundingBox?.x ?? 0) - (nodeMiddleLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelMiddleLeftBoundingBox?.x ?? 0) - (nodeMiddleLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelMiddleLeftBoundingBox?.y ?? 0) - (nodeMiddleLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleLeftBoundingBox?.y ?? 0) - (nodeMiddleLeftBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleLeftBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //CENTER
    const nodeMiddleCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 4);
    const labelMiddleCenter = new PlaywrightLabel(page, 'MIDDLE_CENTER');
    const nodeMiddleCenterBoundingBox = await nodeMiddleCenter.nodeLocator.boundingBox();
    const labelMiddleCenterBoundingBox = await labelMiddleCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleCenterBoundingBox?.x ?? 0) - (nodeMiddleCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelMiddleCenterBoundingBox?.x ?? 0) - (nodeMiddleCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelMiddleCenterBoundingBox?.y ?? 0) - (nodeMiddleCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleCenterBoundingBox?.y ?? 0) - (nodeMiddleCenterBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleCenterBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //RIGHT
    const nodeMiddleRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 5);
    const labelMiddleRight = new PlaywrightLabel(page, 'MIDDLE_RIGHT');
    const nodeMiddleRightBoundingBox = await nodeMiddleRight.nodeLocator.boundingBox();
    const labelMiddleRightBoundingBox = await labelMiddleRight.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelMiddleRightBoundingBox?.x ?? 0) - (nodeMiddleRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(145);
    expect((labelMiddleRightBoundingBox?.x ?? 0) - (nodeMiddleRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(130);
    expect((labelMiddleRightBoundingBox?.y ?? 0) - (nodeMiddleRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(85);
    expect((labelMiddleRightBoundingBox?.y ?? 0) - (nodeMiddleRightBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(75);
    expect(labelMiddleRightBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line

    // BOTTOM
    // LEFT
    const nodeBottomLeft = new PlaywrightNode(page, 'Entity1', 'FreeForm', 6);
    await nodeBottomLeft.waitForAnimationToFinish();
    const labelBottomLeft = new PlaywrightLabel(page, 'BOTTOM_LEFT');
    const nodeBottomLeftBoundingBox = await nodeBottomLeft.nodeLocator.boundingBox();
    const labelBottomLeftBoundingBox = await labelBottomLeft.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomLeftBoundingBox?.x ?? 0) - (nodeBottomLeftBoundingBox?.x ?? 0)).toBeLessThanOrEqual(10);
    expect((labelBottomLeftBoundingBox?.x ?? 0) - (nodeBottomLeftBoundingBox?.x ?? 0)).toBeGreaterThan(0);
    expect((labelBottomLeftBoundingBox?.y ?? 0) - (nodeBottomLeftBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomLeftBoundingBox?.y ?? 0) - (nodeBottomLeftBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(155);
    expect(labelBottomLeftBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //CENTER
    const nodeBottomCenter = new PlaywrightNode(page, 'Entity1', 'FreeForm', 7);
    const labelBottomCenter = new PlaywrightLabel(page, 'BOTTOM_CENTER');
    const nodeBottomCenterBoundingBox = await nodeBottomCenter.nodeLocator.boundingBox();
    const labelBottomCenterBoundingBox = await labelBottomCenter.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomCenterBoundingBox?.x ?? 0) - (nodeBottomCenterBoundingBox?.x ?? 0)).toBeLessThanOrEqual(75);
    expect((labelBottomCenterBoundingBox?.x ?? 0) - (nodeBottomCenterBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(60);
    expect((labelBottomCenterBoundingBox?.y ?? 0) - (nodeBottomCenterBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomCenterBoundingBox?.y ?? 0) - (nodeBottomCenterBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(155);
    expect(labelBottomCenterBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
    //RIGHT
    const nodeBottomRight = new PlaywrightNode(page, 'Entity1', 'FreeForm', 8);
    const labelBottomRight = new PlaywrightLabel(page, 'BOTTOM_RIGHT');
    const nodeBottomRightBoundingBox = await nodeBottomRight.nodeLocator.boundingBox();
    const labelBottomRightBoundingBox = await labelBottomRight.labelContentLocator
      .locator('[data-svg="text"]')
      .boundingBox();
    expect((labelBottomRightBoundingBox?.x ?? 0) - (nodeBottomRightBoundingBox?.x ?? 0)).toBeLessThanOrEqual(145);
    expect((labelBottomRightBoundingBox?.x ?? 0) - (nodeBottomRightBoundingBox?.x ?? 0)).toBeGreaterThanOrEqual(130);
    expect((labelBottomRightBoundingBox?.y ?? 0) - (nodeBottomRightBoundingBox?.y ?? 0)).toBeLessThanOrEqual(165);
    expect((labelBottomRightBoundingBox?.y ?? 0) - (nodeBottomRightBoundingBox?.y ?? 0)).toBeGreaterThanOrEqual(155);
    expect(labelBottomRightBoundingBox?.height).toBeLessThan(8); // Check label is only on 1 line
  });
});
