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
import { PlaywrightNode } from '../helpers/PlaywrightNode';
import { PlaywrightNodeLabel } from '../helpers/PlaywrightNodeLabel';
import { PlaywrightProject } from '../helpers/PlaywrightProject';
import { PlaywrightDetails } from '../helpers/PlaywrightDetails';

test.describe('diagram - label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });

    const project = await new PlaywrightProject(request).createProjectFromTemplate('flow-template');
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
        const style = window.getComputedStyle(label);
        return style.borderColor === 'rgb(190, 26, 120)' && style.borderWidth === '1px';
      },
      { timeout: 2000 }
    );
  });

  test('when a node with an empty label is selected, then the label is not highlighted', async ({ page }) => {
    const dataSourceNode = new PlaywrightNode(page, 'DataSource1');
    await dataSourceNode.click();
    const playwrightDetails = new PlaywrightDetails(page);
    await playwrightDetails.setText('Name', '');

    await page.waitForFunction(
      () => {
        const label = document.querySelector(`[data-testid="Label content - "]`);
        if (!label) {
          return false;
        }
        const style = window.getComputedStyle(label);
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
    await dataSourceLabel.move({ x: initialBox.x + 50, y: initialBox.y + 150 }, positionOnLabelToStartDragging);

    await page.waitForFunction(
      ({ expectedX, expectedY }) => {
        const label = document.querySelector(`[data-testid="Label - DataSource1"]`);
        if (!label) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        return labelBoundingBox.x === expectedX && labelBoundingBox.y === expectedY;
      },
      {
        expectedX: initialBox.x + 50 - positionOnLabelToStartDragging.x,
        expectedY: initialBox.y + 150 - positionOnLabelToStartDragging.y,
      },
      { timeout: 2000 }
    );

    const playwrightNode = new PlaywrightNode(page, 'DataSource1');
    await playwrightNode.resetNodeLabelPosition();

    await page.waitForFunction(
      ({ expectedX, expectedY }) => {
        const label = document.querySelector(`[data-testid="Label - DataSource1"]`);
        if (!label) {
          return false;
        }
        const labelBoundingBox = label.getBoundingClientRect();
        return labelBoundingBox.x === expectedX && labelBoundingBox.y === expectedY;
      },
      {
        expectedX: initialBox.x,
        expectedY: initialBox.y,
      },
      { timeout: 2000 }
    );
  });
});
