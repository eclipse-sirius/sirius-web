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
import { PlaywrightProject } from '../../helpers/PlaywrightProject';
import { PlaywrightProjectSettings } from '../../helpers/PlaywrightProjectSettings';

test.describe('Project style customizations', () => {
  let projectId: string | null = null;

  test.beforeEach(() => {
    projectId = null;
  });

  test.afterEach(async ({ request }) => {
    if (projectId !== null) {
      await new PlaywrightProject(request).deleteProject(projectId);
    }
  });

  test('given a project whose style customizations can be updated, then activation changes are persisted', async ({
    page,
    request,
  }) => {
    const project = await new PlaywrightProject(request).createProject(
      'Playwright - Style Customizations Project',
      'flow-template'
    );
    projectId = project.projectId;

    const settings = new PlaywrightProjectSettings(page);
    await settings.visit(projectId);
    const appearancesSettings = await settings.getAppearancesSettings();
    const powerStatus = appearancesSettings.getStyleCustomizationCheckbox('Power Status');
    await appearancesSettings.expectStyleCustomizationState('Power Status', { checked: true });
    await appearancesSettings.expectStyleCustomizationState('Status', { checked: true });

    await powerStatus.click();
    await appearancesSettings.expectStyleCustomizationState('Power Status', { checked: false });
    await appearancesSettings.expectStyleCustomizationState('Status', { checked: true });

    await page.reload();
    await appearancesSettings.expectStyleCustomizationState('Power Status', { checked: false });
    await appearancesSettings.expectStyleCustomizationState('Status', { checked: true });

    await powerStatus.click();
    await appearancesSettings.expectStyleCustomizationState('Power Status', { checked: true });

    await page.reload();
    await appearancesSettings.expectStyleCustomizationState('Power Status', { checked: true });
    await appearancesSettings.expectStyleCustomizationState('Status', { checked: true });
  });

  test('given a project whose style customizations cannot be updated, then they remain visible in read-only mode', async ({
    page,
    request,
  }) => {
    const project = await new PlaywrightProject(request).createProject(
      'Playwright - Disabled Style Customizations Project',
      'flow-template'
    );
    projectId = project.projectId;

    const settings = new PlaywrightProjectSettings(page);
    await settings.visit(projectId);
    const appearancesSettings = await settings.getAppearancesSettings();
    const updateSpy = appearancesSettings.spyOnUpdateRequests();

    await expect(appearancesSettings.getStyleCustomizationCheckboxes()).toHaveCount(2);
    for (const name of ['Power Status', 'Status']) {
      await appearancesSettings.expectStyleCustomizationState(name, { checked: true, readOnly: true });
    }

    await appearancesSettings.getStyleCustomizationRow('Power Status').click();
    for (const name of ['Power Status', 'Status']) {
      await appearancesSettings.expectStyleCustomizationState(name, { checked: true, readOnly: true });
    }
    expect(updateSpy.called).toBe(false);
  });
});
