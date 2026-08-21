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

import { expect, test } from '../../fixtures/coverage';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('node-decorator', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectFlowForDecorator.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.expand('NewSystem');
    await playwrightExplorer.createRepresentation('NewSystem', 'Topography', 'Topography');
    await playwrightExplorer.select('Topography');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a diagram containing decorators is opened, then the decorators are visible', async ({ page }) => {
    const playwrightNode = new PlaywrightNode(page, 'DataSource');
    await expect(playwrightNode.nodeLocator).toBeAttached();

    const northEastDecorator = playwrightNode.nodeLocator.getByTestId('Decorator - south_east');
    await expect(northEastDecorator).toBeAttached();
  });
});

test.describe('node-decorator-on-edge-creation', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectDecorator.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('edgeDecorator');
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

  test('when we create an edge on the decorator, then the edge is created', async ({ page }) => {
    const edges = page.locator('[data-testid^="rf__edge-"]');

    const playwrightNode1a = new PlaywrightNode(page, 'Entity1a');
    await playwrightNode1a.click();
    await page.getByTestId('creationhandle--right').hover();
    await page.mouse.down();
    const playwrightNode2a = new PlaywrightNode(page, 'Entity2a');
    await playwrightNode2a.nodeLocator.hover({ position: { x: 10, y: 10 } });
    const northWestDecorator = playwrightNode2a.nodeLocator.getByTestId('Decorator - north_west');
    const box = await northWestDecorator.boundingBox();
    if (!box) {
      throw new Error('Could not resolve bounding box for north_west decorator');
    }

    const targetX = box.x + box.width / 2;
    const targetY = box.y + box.height / 2;

    await page.mouse.move(targetX, targetY);
    await expect(page.getByRole('tooltip', { name: 'decorator decorator decorator' })).not.toBeAttached();
    await page.mouse.up();
    await expect(edges).toHaveCount(1);
  });

  test('when hovering a decorator container with several decorator, then a tooltip appears', async ({ page }) => {
    const playwrightNode1a = new PlaywrightNode(page, 'Entity1a');
    await expect(playwrightNode1a.nodeLocator.getByTestId('Decorator - north_west')).toBeAttached();
    const northWestDecorator = playwrightNode1a.nodeLocator.getByTestId('Decorator - north_west');
    await expect(northWestDecorator.getByRole('tooltip', { name: 'Diagram' })).not.toBeAttached();
    //wait for tree item tooltip to disappear
    await northWestDecorator.hover();

    await expect(page.getByRole('tooltip', { name: 'decorator decorator decorator' })).toBeAttached();
  });
});
