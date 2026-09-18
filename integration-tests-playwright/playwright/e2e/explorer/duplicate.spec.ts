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

test.describe('explorer - duplicate object', () => {
  let projectId: string;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject(
      'Playwright - duplicate object',
      'studio-template'
    );
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit/`);
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an attribute is duplicated into an entity, then both copies appear in the explorer', async ({ page }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('DomainNewModel');
    const domain = explorer.explorerLocator.locator('[data-treeitemkind$="entity=Domain"]');
    await expect(domain).toHaveAttribute('data-treeitemlabel', /.+/);
    const domainName = await domain.getAttribute('data-treeitemlabel');
    if (domainName === null) {
      throw new Error('Missing domain label');
    }
    await explorer.expand(domainName);
    await explorer.expand('Entity1');
    await explorer.expand('Entity2');

    const attributes = await explorer.getTreeItemLabel('attribute2');
    await expect(attributes).toHaveCount(1);
    await explorer.openPalette('attribute2');
    await page.getByTestId('duplicate-object').click();

    const dialog = page.getByTestId('duplicate-object-dialog');
    await dialog.locator('[data-treeitemlabel="DomainNewModel"]').dblclick();
    await dialog.locator(`[data-treeitemlabel="${domainName}"]`).dblclick();
    await dialog.locator('[data-treeitemlabel="Entity2"]').click();
    await expect(dialog.getByTestId('containment-feature-name').getByRole('combobox')).toHaveText('Add in attributes');
    await dialog.getByTestId('duplicate-object-button').click();
    await expect(dialog).not.toBeAttached();
    await expect(attributes).toHaveCount(2);
  });
});
