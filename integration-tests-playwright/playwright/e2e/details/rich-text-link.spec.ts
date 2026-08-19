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

  test('when a link is inserted, then it can be opened, edited, and deleted', async ({ page }) => {
    const playwrightDetails = new PlaywrightDetails(page);
    const richText = playwrightDetails.detailsLocator.getByTestId('Description');
    const editor = richText.locator('[contenteditable="true"]');
    const initialUrl = 'https://www.eclipse.org/';
    const updatedUrl = 'https://www.eclipse.org/sirius/';

    await editor.fill('Sirius Web');
    await expect(editor).toHaveText('Sirius Web');
    await editor.press('ControlOrMeta+A');
    await richText.getByLabel('Insert Link').click();
    await expect(page.getByLabel('Link URL')).toBeVisible();
    await page.getByLabel('Link URL').fill(initialUrl);
    await page.getByLabel('Confirm link').click();

    const insertedLink = editor.getByRole('link', { name: 'Sirius Web' });
    await expect(insertedLink).toHaveAttribute('href', initialUrl);

    await insertedLink.click();
    const popupUrl = page.getByRole('link', { name: initialUrl });
    await expect(popupUrl).toBeVisible();
    await expect(page.getByLabel('Edit link')).toBeVisible();
    await expect(page.getByLabel('Delete link')).toBeVisible();

    const popupPromise = page.waitForEvent('popup');
    await popupUrl.click();
    const popupPage = await popupPromise;
    expect(popupPage.url()).toBe(initialUrl);
    await popupPage.close();

    await insertedLink.click();
    await page.getByLabel('Edit link').click();
    await page.getByLabel('Link URL').fill(updatedUrl);
    await page.getByLabel('Confirm link').click();
    await expect(insertedLink).toHaveAttribute('href', updatedUrl);

    await insertedLink.click();
    await page.getByLabel('Delete link').click();
    await expect(editor.getByRole('link', { name: 'Sirius Web' })).toHaveCount(0);
    await expect(editor).toContainText('Sirius Web');
  });

  test('when the focus leaves the rich text editor, then the link editor is closed', async ({ page }) => {
    const playwrightDetails = new PlaywrightDetails(page);
    const richText = playwrightDetails.detailsLocator.getByTestId('Description');
    const editor = richText.locator('[contenteditable="true"]');

    await editor.fill('Sirius Web');
    await expect(editor).toHaveText('Sirius Web');
    await editor.press('ControlOrMeta+A');
    await richText.getByLabel('Insert Link').click();
    await expect(page.getByLabel('Link URL')).toBeVisible();
    await page.getByLabel('Link URL').fill('https://www.eclipse.org/');
    await page.getByLabel('Confirm link').click();

    await editor.getByRole('link', { name: 'Sirius Web' }).click();
    const linkEditor = page.getByTestId('link-editor');
    await expect(linkEditor).toBeVisible();

    await page.getByTestId('upload-document-icon').focus();

    await expect(linkEditor).toBeHidden();
  });

  test('when the rich text value is refreshed, then the link editor is closed', async ({ context, page }) => {
    const playwrightDetails = new PlaywrightDetails(page);
    const richText = playwrightDetails.detailsLocator.getByTestId('Description');
    const editor = richText.locator('[contenteditable="true"]');

    await editor.fill('Sirius Web');
    await expect(editor).toHaveText('Sirius Web');
    await editor.press('ControlOrMeta+A');
    await richText.getByLabel('Insert Link').click();
    await expect(page.getByLabel('Link URL')).toBeVisible();
    await page.getByLabel('Link URL').fill('https://www.eclipse.org/');
    await page.getByLabel('Confirm link').click();
    await editor.getByRole('link', { name: 'Sirius Web' }).click();

    const linkEditor = page.getByTestId('link-editor');
    await expect(linkEditor).toBeVisible();

    const secondPage = await context.newPage();
    try {
      await secondPage.goto(`/projects/${projectId}/edit`);
      const secondPageExplorer = new PlaywrightExplorer(secondPage);
      await secondPageExplorer.expand('detailsOpenTab.xml');
      await secondPageExplorer.expand('Project1');
      await secondPageExplorer.select('Component');

      const secondPageDetails = new PlaywrightDetails(secondPage);
      const secondPageEditor = secondPageDetails.detailsLocator
        .getByTestId('Description')
        .locator('[contenteditable="true"]');
      const updatedDescription = 'Updated from another page';
      await secondPageEditor.fill(updatedDescription);
      await expect(secondPageEditor).toHaveText(updatedDescription);
      await secondPage.getByTestId('upload-document-icon').focus();

      await expect(editor).toContainText(updatedDescription);
      await expect(linkEditor).toBeHidden();
    } finally {
      await secondPage.close();
    }
  });
});
