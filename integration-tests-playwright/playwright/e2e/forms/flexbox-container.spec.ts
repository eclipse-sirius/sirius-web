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
import { PlaywrightDetails } from '../../helpers/PlaywrightDetails';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('forms - flexbox container layout', () => {
  let studioProjectId: string | undefined;
  let flowProjectId: string | undefined;
  let formName: string;

  test.beforeEach(async ({ page, request }) => {
    studioProjectId = undefined;
    flowProjectId = undefined;
    const studio = await new PlaywrightProject(request).createProject('Flexbox studio', 'blank-studio-template');
    studioProjectId = studio.projectId;
    formName = `Flexbox-${studioProjectId}`;
    await page.goto(`/projects/${studioProjectId}/edit`);
    const explorer = new PlaywrightExplorer(page);
    await explorer.uploadDocument('formFlexboxContainer.xml');
    await explorer.expand('formFlexboxContainer.xml');
    await explorer.expand('View');
    await explorer.select('FlexboxForm');
    await new PlaywrightDetails(page).setText('Name', formName);
  });

  test.afterEach(async ({ request }) => {
    const projects = new PlaywrightProject(request);
    if (flowProjectId) {
      await projects.deleteProject(flowProjectId);
    }
    if (studioProjectId) {
      await projects.deleteProject(studioProjectId);
    }
  });

  test('when a form is rendered, then spacing and alignment are applied independently to nested containers', async ({
    page,
    request,
  }) => {
    const flow = await new PlaywrightProject(request).createProject('Flexbox runtime', 'flow-template');
    flowProjectId = flow.projectId;
    await page.goto(`/projects/${flowProjectId}/edit`);
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.createRepresentation('NewSystem', formName, 'Flexbox form');

    const form = page.locator('[data-representation-kind="form"]');
    const row = form.getByTestId('flexbox-Styled row');
    const column = row.getByTestId('flexbox-Nested column');
    await expect(row).toHaveCSS('margin', '0px 12px');
    await expect(row).toHaveCSS('padding', '16px 32px');
    const rowContent = row.locator(':scope > div').nth(1);
    await expect(rowContent).toHaveCSS('flex-direction', 'row');
    await expect(rowContent).toHaveCSS('align-items', 'flex-start');
    await expect(rowContent).toHaveCSS('justify-content', 'space-between');
    await expect(rowContent).toHaveCSS('gap', '8px 16px');

    await expect(column).toHaveCSS('margin', '2px 4px');
    await expect(column).toHaveCSS('padding', '6px 10px');
    const columnContent = column.locator(':scope > div').nth(1);
    await expect(columnContent).toHaveCSS('flex-direction', 'column');
    await expect(columnContent).toHaveCSS('align-items', 'flex-end');
    await expect(columnContent).toHaveCSS('justify-content', 'center');
    await expect(columnContent).toHaveCSS('gap', '12px');
  });

  test('when spacing is unspecified, then the existing defaults are preserved with and without a border', async ({
    page,
    request,
  }) => {
    const flow = await new PlaywrightProject(request).createProject('Flexbox defaults', 'flow-template');
    flowProjectId = flow.projectId;
    await page.goto(`/projects/${flowProjectId}/edit`);
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.createRepresentation('NewSystem', formName, 'Flexbox defaults');

    const form = page.locator('[data-representation-kind="form"]');

    const defaultContainer = form.getByTestId(`flexbox-Default container`);
    await expect(defaultContainer).toHaveCSS('margin', '0px');
    await expect(defaultContainer).toHaveCSS('padding', '0px');
    const defaultContent = defaultContainer.locator(':scope > div').nth(1);
    await expect(defaultContent).toHaveCSS('align-items', 'stretch');
    await expect(defaultContent).toHaveCSS('justify-content', 'stretch');
    await expect(defaultContent).toHaveCSS('gap', 'normal');

    const borderedContainer = form.getByTestId(`flexbox-Bordered container`);
    await expect(borderedContainer).toHaveCSS('margin', '4px');
    await expect(borderedContainer).toHaveCSS('padding', '4px');
    const borderedContent = borderedContainer.locator(':scope > div').nth(1);
    await expect(borderedContent).toHaveCSS('align-items', 'stretch');
    await expect(borderedContent).toHaveCSS('justify-content', 'stretch');
    await expect(borderedContent).toHaveCSS('gap', 'normal');
  });
});
