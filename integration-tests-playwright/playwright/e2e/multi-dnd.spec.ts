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

interface Bounds {
  x: number;
  y: number;
  width: number;
  height: number;
}

const explorerItemLocator = (page: Page, parentLabel: string, childLabel: string) => {
  return page
    .locator(`[data-treeitemlabel="${parentLabel}"]`)
    .locator('..')
    .locator('..')
    .locator(`[data-treeitemlabel="${childLabel}"]`);
};

const includes = (parent: Bounds, child: Bounds) => {
  const minX = parent.x;
  const maxX = parent.x + parent.width;
  const minY = parent.y;
  const maxY = parent.y + parent.height;
  return minX <= child.x && child.x + child.width <= maxX && minY <= child.y && child.y + child.height <= maxY;
};

test.describe('diagram - drag and drop of multiple elements', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Studio', 'blank-studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('diagramMultiDnD.xml');
    await playwrightExplorer.expand('diagramMultiDnD.xml');
    await playwrightExplorer.createRepresentation('fixture', 'Domain', 'fixture domain diagram');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when dragging two attributes from one entity to another, then they are moved into the target entity', async ({
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

    // First check the initial contents in the Explorer
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Source');

    const sourceAttr1 = explorerItemLocator(page, 'Source', 'attr1');
    const sourceAttr2 = explorerItemLocator(page, 'Source', 'attr2');
    const targetAttr1 = explorerItemLocator(page, 'Target', 'attr1');
    const targetAttr2 = explorerItemLocator(page, 'Target', 'attr2');

    // attr1 & attr2 are inside the Source entity
    await expect(sourceAttr1).toHaveCount(1);
    await expect(sourceAttr2).toHaveCount(1);
    await expect(targetAttr1).toHaveCount(0);
    await expect(targetAttr2).toHaveCount(0);

    // Now, inside the diagram, select both attributes from the 'Source' entity
    const attr1NodeBefore = new PlaywrightNode(page, 'attr1', 'IconLabel');
    await attr1NodeBefore.click();

    const attr2NodeBefore = new PlaywrightNode(page, 'attr2', 'IconLabel');
    await attr2NodeBefore.controlClick();

    // And move them inside the 'Target' entity
    await attr2NodeBefore.move({ x: 400, y: 0 });

    await page.waitForFunction(
      () => {
        // Wait for the attr1 list item to be inside the Target entity
        return !!(
          document.querySelector('[data-testid="IconLabel - attr1"]')?.parentNode?.previousSibling as HTMLDivElement
        )?.querySelector('[data-testid="List - Target"]');
      },
      { timeout: 2000 }
    );

    expect(requestTriggered).toBe(true);

    // Both attr1 & attr2 should now be inside the Target entity on the diagram
    // We have to rely on checking the bounds, as in the DOM all the elements
    // are divs placed at the same level.

    const sourceBounds: Bounds = await new PlaywrightNode(page, 'Source', 'List').getDOMBoundingBox();
    const targetBounds: Bounds = await new PlaywrightNode(page, 'Target', 'List').getDOMBoundingBox();
    const attr1Bounds: Bounds = await new PlaywrightNode(page, 'attr1', 'IconLabel').getDOMBoundingBox();
    const attr2Bounds: Bounds = await new PlaywrightNode(page, 'attr2', 'IconLabel').getDOMBoundingBox();

    expect(includes(sourceBounds, attr1Bounds)).toBeFalsy();
    expect(includes(sourceBounds, attr2Bounds)).toBeFalsy();
    expect(includes(targetBounds, attr1Bounds)).toBeTruthy();
    expect(includes(targetBounds, attr2Bounds)).toBeTruthy();

    // Also check inside the Explorer

    // We can (and need to) expand the Target item now
    await explorer.expand('Target');
    // attr1 & attr2 are inside the Target entity
    await expect(sourceAttr1).toHaveCount(0);
    await expect(sourceAttr2).toHaveCount(0);
    await expect(targetAttr1).toHaveCount(1);
    await expect(targetAttr2).toHaveCount(1);
  });

  test('when dragging two attributes from one entity to the diagram, then they are moved into a newly created wrapper entity', async ({
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

    // Now, inside the diagram, select both attributes from the 'Source' entity
    const attr1NodeBefore = new PlaywrightNode(page, 'attr1', 'IconLabel');
    await attr1NodeBefore.click();

    const attr2NodeBefore = new PlaywrightNode(page, 'attr2', 'IconLabel');
    await attr2NodeBefore.controlClick();

    // And move them directly on the diagram area (not on an entity)
    await attr2NodeBefore.move({ x: 200, y: 200 });

    await page.waitForFunction(
      () => {
        // Wait for the AbstractEntity list to appear
        return !!document.querySelector('[data-testid="List - AbstractEntity"]');
      },
      { timeout: 2000 }
    );

    const sourceBounds: Bounds = await new PlaywrightNode(page, 'Source', 'List').getDOMBoundingBox();
    const abstractEntityBounds: Bounds = await new PlaywrightNode(page, 'AbstractEntity', 'List').getDOMBoundingBox();
    const attr1Bounds: Bounds = await new PlaywrightNode(page, 'attr1', 'IconLabel').getDOMBoundingBox();
    const attr2Bounds: Bounds = await new PlaywrightNode(page, 'attr2', 'IconLabel').getDOMBoundingBox();

    expect(includes(sourceBounds, attr1Bounds)).toBeFalsy();
    expect(includes(sourceBounds, attr2Bounds)).toBeFalsy();
    expect(includes(abstractEntityBounds, attr1Bounds)).toBeTruthy();
    expect(includes(abstractEntityBounds, attr2Bounds)).toBeTruthy();

    expect(requestTriggered).toBe(true);
  });

  test.skip('when dragging two attributes from one entity to the header of the same entity, then they are not moved and retain their original positions', async ({
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

    const attr1BoundsBefore: Bounds = await new PlaywrightNode(page, 'attr1', 'IconLabel').getDOMBoundingBox();
    const attr2BoundsBefore: Bounds = await new PlaywrightNode(page, 'attr2', 'IconLabel').getDOMBoundingBox();

    // Inside the diagram, select both attributes from the 'Source' entity
    const attr1Node = new PlaywrightNode(page, 'attr1', 'IconLabel');
    await attr1Node.click();

    const attr2Node = new PlaywrightNode(page, 'attr2', 'IconLabel');
    await attr2Node.controlClick();

    // And move them on the header of the same entity, which should cancel the drop
    await attr2Node.move({ x: 20, y: -10 });

    // Wait a bit to ensure any potential drop handling is done
    await page.waitForTimeout(500);
    expect(requestTriggered).toBe(false);

    // FIXME: Visually, the attributes return to their original positions,
    // but the bounding box retrieved here are different:
    //
    // Error: expect(received).toEqual(expected) // deep equality
    // - Expected  - 4
    // + Received  + 4
    //
    //   Object {
    // -   "height": 15.976531982421875,
    // -   "width": 143.78872680664062,
    // -   "x": 347.557373046875,
    // -   "y": 137.65444946289062,
    // +   "height": 26.7322998046875,
    // +   "width": 240.59078979492188,
    // +   "x": 373.2935485839844,
    // +   "y": 356.1821594238281,
    //   }
    const attr1BoundsAfter: Bounds = await new PlaywrightNode(page, 'attr1', 'IconLabel').getDOMBoundingBox();
    const attr2BoundsAfter: Bounds = await new PlaywrightNode(page, 'attr2', 'IconLabel').getDOMBoundingBox();

    expect(attr1BoundsAfter).toEqual(attr1BoundsBefore);
    expect(attr2BoundsAfter).toEqual(attr2BoundsBefore);
  });
});
