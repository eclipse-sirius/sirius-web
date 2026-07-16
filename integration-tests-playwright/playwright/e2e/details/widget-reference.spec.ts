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

test.describe('details - widget reference', () => {
  let projectId: string;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('details-reference', 'flow-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit`);
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('referenceAddReferenceValues.xml');
    await playwrightExplorer.expand('referenceAddReferenceValues.xml');
    await playwrightExplorer.expand('System');
    await playwrightExplorer.select('CompositeProcessor1');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when a value is added to a multi-valued reference, then only its id is sent', async ({ page }) => {
    const addReferenceValuesRequestPromise = page.waitForRequest((request) => {
      const postData = request.postData();
      return (
        request.url().includes('api/graphql') &&
        request.method() === 'POST' &&
        postData !== null &&
        JSON.parse(postData).operationName === 'addReferenceValues'
      );
    });

    const playwrightDetails = new PlaywrightDetails(page);
    await playwrightDetails.addReference('Incoming Flows', 'high');

    const addReferenceValuesRequest = await addReferenceValuesRequestPromise;
    const requestBody = addReferenceValuesRequest.postDataJSON();
    expect(requestBody.variables.input.newValueIds).toHaveLength(1);

    await expect(
      playwrightDetails.detailsLocator.getByTestId('Incoming Flows').getByTestId('reference-value-high')
    ).toBeVisible();
  });
});
