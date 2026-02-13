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
import { PlaywrightEdge } from '../../helpers/PlaywrightEdge';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

test.describe('edge-label', () => {
  let projectId;
  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject('Flow', 'flow-template');
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit`);

    const explorer = await new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.select('Topography');
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when an edge has a center label, then the label does not stick the edge path', async ({ page }) => {
    await expect(page.getByTestId('rf__wrapper')).toBeAttached();

    await page.getByTestId('arrange-all-menu').click();
    await page.getByTestId('arrange-all-elk-rect-packing').click();

    const playwrightEdge = new PlaywrightEdge(page);

    await playwrightEdge.click();
    await playwrightEdge.isSelected();

    const edgePath = await playwrightEdge.getEdgePath();
    await page.waitForFunction(
      ({ edgePath }) => {
        const label =
          document.querySelector(`[data-testid="Label - 6"]`)?.parentElement?.parentElement?.parentElement
            ?.parentElement; // get the div with the transform style
        if (!label || !edgePath) {
          return false;
        }
        const transformMatrix = window.getComputedStyle(label)?.transform;
        const edgeRegex = /L\s*(-?\d+\.?\d*)/;
        const edgeMatch = edgePath.match(edgeRegex);
        const edgeX = edgeMatch ? parseFloat(edgeMatch[1] ?? '-2') : null;

        const matrixRegex = /matrix\(\s*[^,]+\s*,\s*[^,]+\s*,\s*[^,]+\s*,\s*[^,]+\s*,\s*(-?\d+\.?\d*)\s*,/; // regex to get the tx value
        const matrixMatch = transformMatrix.match(matrixRegex);
        const matrixX = matrixMatch ? parseFloat(matrixMatch[1] ?? '-1') : null;
        return edgeX === matrixX;
      },
      { edgePath: edgePath },
      { timeout: 2000 }
    );
  });
});
