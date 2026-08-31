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

import { test } from '@playwright/test';
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('filter selection menu', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);

    const explorer = await new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    const representationItem = await explorer.getTreeItemLabel('Topography');
    await representationItem.click();
    await explorer.expand('NewSystem');
    await explorer.expand('Flow');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when selecting all nodes with the filter selection menu, then all nodes are selected and root nodes can be unselect afterwards', async ({
    page,
  }) => {
    const node1 = new PlaywrightNode(page, 'CompositeProcessor1');
    const node2 = new PlaywrightNode(page, 'DataSource1');
    const childNode1 = new PlaywrightNode(page, 'Processor1');

    await node1.isNotSelected();
    await node2.isNotSelected();
    await childNode1.isNotSelected();

    const filterMenu = page.getByTestId(`toolbar_filter_selection`);
    await filterMenu.click();
    const filterMenu_select_all_nodes = page.getByTestId(`filter_selection_select_all_nodes`);
    await filterMenu_select_all_nodes.click();

    await node1.isSelected();
    await node2.isSelected();
    await childNode1.isSelected();

    await filterMenu.click();
    const filterMenu_unselect_child_nodes = page.getByTestId(`filter_selection_unselect_child_nodes`);
    await filterMenu_unselect_child_nodes.click();

    await node1.isSelected();
    await node2.isSelected();
    await childNode1.isNotSelected();

    await node1.openPalette();
    await page.getByTestId('toolSection-Appearance').isVisible();
  });

  test('when selecting all edges with the filter selection menu, then all edges are selected and can be unselected afterwards', async ({
    page,
  }) => {
    const edgeCount = await page.locator('[data-testid^="rf__edge-"]').count();
    const edges = Array.from({ length: edgeCount }, (_, index) => new PlaywrightEdge(page, index));

    for (const edge of edges) {
      await edge.isNotSelected();
    }

    const filterMenu = page.getByTestId('toolbar_filter_selection');
    await filterMenu.click();
    await page.getByTestId('filter_selection_select_all_edges').click();

    for (const edge of edges) {
      await edge.isSelected();
    }
    await page.locator('[data-testid^="connectionHandle"]').first().waitFor({ state: 'visible' });

    await filterMenu.click();
    await page.getByTestId('filter_selection_unselect_all_edges').click();

    for (const edge of edges) {
      await edge.isNotSelected();
    }
    await page.locator('[data-testid^="connectionHandle"]').first().waitFor({ state: 'hidden' });
  });
});
