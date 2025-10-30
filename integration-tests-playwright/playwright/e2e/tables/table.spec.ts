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
import { test, expect } from '@playwright/test';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightTable } from '../../helpers/PlaywrightTable';

test.describe('table', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Papaya', 'papaya-empty');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('tablePapayaPackage.xml');
    await playwrightExplorer.expand('tablePapayaPackage.xml');
    await playwrightExplorer.expand('Project');
    await playwrightExplorer.expand('Component');
    await playwrightExplorer.createRepresentation('Package', 'Papaya package table', 'table');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a table representation is opened, then the table is displayed correctly', async ({ page }) => {
    const table = new PlaywrightTable(page);
    await expect(table.tableLocator).toBeAttached();
    const rowCount = await table.getRowCount();
    expect(rowCount).toBe(8);
  });
});
