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

test.describe('explorer - key bindings', () => {
  let projectId;
  let playwrightExplorer;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Studio', 'blank-studio-template');
    projectId = project.projectId;

    await page.goto(`/projects/${projectId}/edit/`);

    playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.uploadDocument('studioKeyBindings.xml');
  });

  test('When clicking on a tree item and entering a valid key binding, then the corresponding entry is executed', async ({
    page,
  }) => {
    // Reload the page to display the explorer selection button (which is only present if a domain model is in the editing context)
    page.reload();
    await playwrightExplorer.openExplorer('Domain explorer by DSL');
    await playwrightExplorer.expand('studioKeyBindings.xml');
    await playwrightExplorer.expand('domain');
    await playwrightExplorer.select('[Entity] Entity1 {}');
    const entity1Item = await playwrightExplorer.getTreeItemLabel('[Entity] Entity1 {}');
    entity1Item.press('ControlOrMeta+a');
    await expect(page.getByTestId('impact-analysis-dialog')).toBeAttached();
  });
});
