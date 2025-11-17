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
import { expect, test } from '@playwright/test';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - group selection', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectGroupResize.zip');
    await expect(page.locator('[data-testid^="explorer://"]')).toBeAttached();
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when selecting several nodes, the last one is always highlighted', async ({ page }) => {
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Others...');
    await playwrightExplorer.expand('Root');
    await playwrightExplorer.select('diagramResize diagram');
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();
    await page.keyboard.down('ControlOrMeta');

    const playwrightNode1 = new PlaywrightNode(page, 'Entity1 - Resize Both');
    const playwrightNode2 = new PlaywrightNode(page, 'Entity2 - Resize None');
    const playwrightNode3 = new PlaywrightNode(page, 'Entity3 - Resize HORIZONTAL');
    const playwrightNode4 = new PlaywrightNode(page, 'Entity4 - Resize VERTICAL');

    await playwrightNode1.click();
    expect(await playwrightNode1.isLastOneSelected());

    await playwrightNode2.click();
    expect(await playwrightNode2.isLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());

    await playwrightNode3.click();
    expect(await playwrightNode3.isLastOneSelected());
    expect(await playwrightNode2.isNotLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());

    await playwrightNode4.click();
    expect(await playwrightNode4.isLastOneSelected());
    expect(await playwrightNode3.isNotLastOneSelected());
    expect(await playwrightNode2.isNotLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());

    await playwrightNode2.click();
    await playwrightNode4.click();
    expect(await playwrightNode3.isLastOneSelected());
    expect(await playwrightNode1.isNotLastOneSelected());
  });
});
