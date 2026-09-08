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

test.describe('details - rich text links', () => {
  let projectId: string;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('details-rich-text-link', 'papaya-empty');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('detailsOpenTab.xml');
    await playwrightExplorer.expand('detailsOpenTab.xml');
    await playwrightExplorer.expand('Project1');
    await playwrightExplorer.select('Component');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a link is inserted, then it is displayed', async ({ page }) => {
    const playwrightDetails = new PlaywrightDetails(page);
    const richText = playwrightDetails.detailsLocator.getByTestId('Description');
    const editor = richText.locator('[contenteditable="true"]');
    const initialUrl = 'https://www.eclipse.org/';

    await editor.fill('Sirius Web');
    await expect(editor).toHaveText('Sirius Web');
    await editor.press('ControlOrMeta+A');
    await richText.getByLabel('Insert Link').click();
    await expect(page.getByLabel('Link URL')).toBeVisible();
    await page.getByLabel('Link URL').fill(initialUrl);
    await page.getByLabel('Confirm link').click();

    const insertedLink = editor.getByRole('link', { name: 'Sirius Web' });
    await expect(insertedLink).toHaveAttribute('href', initialUrl);
  });
});
