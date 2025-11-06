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
import { PlaywrightDetails } from '../helpers/PlaywrightDetails';
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightNodeLabel } from '../helpers/PlaywrightNodeLabel';
import { PlaywrightProject } from '../helpers/PlaywrightProject';
import { PlaywrightExplorer } from '../helpers/PlaywrightExplorer';
import { PlaywrightLabel } from '../helpers/PlaywrightLabel';
import { PlaywrightEdge } from '../helpers/PlaywrightEdge';

test.describe('diagram - label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });

    const project = await new PlaywrightProject(request).createProjectFromTemplate('Flow', 'flow-template', [
      PlaywrightProject.FLOW_NATURE,
    ]);
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/${project.representationId}`);
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
    const project = await new PlaywrightProject(request).createProject('diagram-resizable-label');
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

    await entity1Node.move({ x: -50, y: -50 });

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
