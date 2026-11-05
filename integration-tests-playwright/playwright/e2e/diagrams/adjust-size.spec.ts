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
import { PlaywrightDiagram } from '../../helpers/PlaywrightDiagram';
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
    await new PlaywrightDiagram(page).waitForInitialLayout(() => playwrightExplorer.select('Topography'));
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

test.describe('diagram - adjust size with wrapped label', () => {
  let projectId: string;
  const nodeName =
    'A sufficiently long requirement description that wraps over several lines when the node returns to its default width';

  test.beforeEach(async ({ page, request }) => {
    await page.addInitScript(() => {
      // @ts-expect-error: disable automatic viewport changes during the resize interaction.
      window.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
    const project = await new PlaywrightProject(request).createProject('Adjust size wrapped label', 'blank-project');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);
    const explorer = new PlaywrightExplorer(page);
    await explorer.uploadDocument('diagramAdjustSizeWrappedLabel.xml');
    await explorer.expand('diagramAdjustSizeWrappedLabel.xml');
    await new PlaywrightDiagram(page).waitForInitialLayout(() =>
      explorer.createRepresentation(
        'Root',
        'diagramLabel - Wrap the label without changing the node width',
        'Adjust size wrapped label'
      )
    );
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when adjusting a widened node with a wrapped label, then the label remains inside the node', async ({
    page,
  }) => {
    const node = new PlaywrightNode(page, nodeName);
    await node.click();
    await new PlaywrightDiagram(page).hideDebugPanel();
    await node.resize({ width: 350, height: 100 });
    await expect
      .poll(() => node.nodeLocator.evaluate((element) => Math.round(parseFloat(getComputedStyle(element).width))))
      .toBe(500);
    await node.waitForAnimationToFinish();
    const positionBefore = await node.getDOMXYPosition();

    await node.openPalette();
    await page.getByTestId('Palette').getByTestId('Adjust size - Tool').click();

    await expect
      .poll(() =>
        node.nodeStyleLocator.evaluate((element, position) => {
          const text = element.querySelector<HTMLElement>('[data-svg="text"]');
          const content = text?.parentElement;
          if (!text || !content) {
            return null;
          }
          const nodeBounds = element.getBoundingClientRect();
          const textBounds = text.getBoundingClientRect();
          const style = getComputedStyle(element);
          const scale = nodeBounds.width / (element as HTMLElement).offsetWidth;
          const tolerance = 1;
          return {
            width: Math.round(parseFloat(getComputedStyle(element.parentElement!).width)),
            textFits:
              textBounds.left >= nodeBounds.left + parseFloat(style.borderLeftWidth) * scale - tolerance &&
              textBounds.right <= nodeBounds.right - parseFloat(style.borderRightWidth) * scale + tolerance &&
              textBounds.top >= nodeBounds.top + parseFloat(style.borderTopWidth) * scale - tolerance &&
              textBounds.bottom <= nodeBounds.bottom - parseFloat(style.borderBottomWidth) * scale + tolerance,
            textUnclipped:
              text.scrollWidth <= text.clientWidth + tolerance &&
              text.scrollHeight <= text.clientHeight + tolerance &&
              content.scrollWidth <= content.clientWidth + tolerance &&
              content.scrollHeight <= content.clientHeight + tolerance,
            positionUnchanged:
              Math.abs(nodeBounds.x - position.x) <= tolerance && Math.abs(nodeBounds.y - position.y) <= tolerance,
          };
        }, positionBefore)
      )
      .toEqual({ width: 150, textFits: true, textUnclipped: true, positionUnchanged: true });
  });
});
