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
import { expect, test } from '@playwright/test';
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - delete elements', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    await page.evaluate(
      ([id]) => {
        window.localStorage.setItem('sirius-confirmation-dialog-disabled', JSON.stringify([id]));
      },
      [projectId]
    );

    const explorer = await new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    const representationItem = await explorer.getTreeItemLabel('Topography');
    representationItem.click();
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when two nodes and an edge are selected they can be deleted through the delete quick tool of the palette', async ({ page }) => {
    const dataSource1 = new PlaywrightNode(page, "DataSource1");
    await expect(dataSource1.nodeLocator).toBeAttached();
    const compositeProcessor1 = new PlaywrightNode(page, "CompositeProcessor1");
    await expect(compositeProcessor1.nodeLocator).toBeAttached();
    const flow = new PlaywrightEdge(page, 0);
    await expect(flow.edgeLocator).toBeAttached();
    await dataSource1.click();
    await compositeProcessor1.controlClick();
    await flow.controlClick();
    await compositeProcessor1.openPalette();
    await expect(page.getByTestId('Palette')).toBeAttached();
    await expect(page.getByTestId('Delete from model - Tool')).toBeAttached();
    await page.getByTestId('Delete from model - Tool').click();
    await expect(dataSource1.nodeLocator).not.toBeAttached();
    await expect(compositeProcessor1.nodeLocator).not.toBeAttached();
    await expect(flow.edgeLocator).not.toBeAttached();
  });
});
