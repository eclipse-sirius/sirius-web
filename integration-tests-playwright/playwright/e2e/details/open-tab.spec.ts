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

test.describe('details - open page', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('details-open-page', 'papaya-empty');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('detailsOpenTab.xml');
    await playwrightExplorer.expand('detailsOpenTab.xml');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a tab is selected and the selection changes, then the details view keeps the tab open if it exists', async ({
    page,
  }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    const playwrightDetails = new PlaywrightDetails(page);
    await playwrightExplorer.select('Project1');
    await playwrightDetails.selectTab('TestPage');
    let testPageSelected = await playwrightDetails.isTabSelected('TestPage');
    expect(testPageSelected).toBe(true);
    await playwrightExplorer.select('Project2');
    testPageSelected = await playwrightDetails.isTabSelected('TestPage');
    expect(testPageSelected).toBe(true);
    await playwrightExplorer.expand('Project1');
    await playwrightExplorer.select('Component');
    const componentPageSelected = await playwrightDetails.isTabSelected('Component');
    expect(componentPageSelected).toBe(true);
    await playwrightExplorer.select('Project1');
    testPageSelected = await playwrightDetails.isTabSelected('TestPage');
    expect(testPageSelected).toBe(false);
    const project1PageSelected = await playwrightDetails.isTabSelected('Project1');
    expect(project1PageSelected).toBe(true);
  });
});
