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
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - toolbar', () => {
  let projectId;

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when diagramDescription.toolbar is null, then the toolbar is not visible', async ({ page, request }) => {
    // Intercept GraphQL responses and modify getDiagramDescription to set toolbar to null
    await page.route('**/api/graphql', async (route) => {
      const response = await route.fetch();
      const json = await response.json();

      if (json.data?.viewer?.editingContext?.representation?.description) {
        const description = json.data.viewer.editingContext.representation.description;
        if ('toolbar' in description) {
          description.toolbar = null;
        }
      }

      await route.fulfill({ response, json });
    });

    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);

    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    const representationItem = await explorer.getTreeItemLabel('Topography');
    await representationItem.click();

    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    // Verify the toolbar buttons are not present
    await expect(page.getByTestId('fit-to-screen')).not.toBeAttached();
    await expect(page.getByTestId('share')).not.toBeAttached();
    await expect(page.getByTestId('zoom-out')).not.toBeAttached();
    await expect(page.getByTestId('hide-mini-map')).not.toBeAttached();
    await expect(page.getByTestId('show-mini-map')).not.toBeAttached();
  });
});
