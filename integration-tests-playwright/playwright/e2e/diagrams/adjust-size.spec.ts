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
import { PlaywrightNode } from '../../helpers/PlaywrightNode';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('diagram - adjust size', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    await new PlaywrightProject(request).uploadProject(page, 'projectAdjustSize.zip');
    const playwrightExplorer = new PlaywrightExplorer(page);
    await playwrightExplorer.expand('Flow');
    await playwrightExplorer.expand('NewSystem');
    await playwrightExplorer.select('Topography');
    const url = page.url();
    const parts = url.split('/');
    const projectsIndex = parts.indexOf('projects');
    projectId = parts[projectsIndex + 1];
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an adjust size tool is triggered on a multi selection, then all nodes size are adjusted', async ({
    page,
  }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    const c1Node = new PlaywrightNode(page, 'C1');
    const c2Node = new PlaywrightNode(page, 'C2');
    const c3Node = new PlaywrightNode(page, 'C3');

    await c2Node.click();
    await c1Node.controlClick();
    await c3Node.controlClick();
    await c2Node.openPalette();

    expect(await c3Node.isLastOneSelected());
    expect(await c2Node.isNotLastOneSelected());
    expect(await c1Node.isNotLastOneSelected());

    await expect(page.getByTestId('Palette')).toBeAttached();
    await page.getByTestId('toolSection-Layout').click();
    await page.getByTestId('Palette').getByTestId('Adjust size - Tool').click();

    const nodeDefaultWidth = 150;
    const nodeDefaultHeight = 70;

    // Wait for animation to finish
    await page.waitForFunction(
      ({ expectedWidth }) => {
        const spans = Array.from(document.querySelectorAll('div[data-testid="nodePanelInfos"] span'));
        const widthSpan = spans.find((span) => span.textContent?.includes('Width'));
        if (!widthSpan) {
          return false;
        }
        const widthText = widthSpan.textContent?.trim();
        return widthText && parseInt(widthText.replace('Width : ', ''), 10) === expectedWidth;
      },
      { expectedWidth: nodeDefaultWidth },
      { timeout: 2000 }
    );

    const c1SizeAfter = await c1Node.getReactFlowSize('C1', false);
    const c2SizeAfter = await c2Node.getReactFlowSize('C2', false);
    const c3SizeAfter = await c2Node.getReactFlowSize('C3', false);

    expect(c1SizeAfter.width).toBe(nodeDefaultWidth);
    expect(c1SizeAfter.height).toBe(nodeDefaultHeight);
    expect(c2SizeAfter.width).toBe(nodeDefaultWidth);
    expect(c2SizeAfter.height).toBe(nodeDefaultHeight);
    expect(c3SizeAfter.width).toBe(nodeDefaultWidth);
    expect(c3SizeAfter.height).toBe(nodeDefaultHeight);
  });
});
