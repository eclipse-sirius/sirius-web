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

const expandRobot = async (explorer: PlaywrightExplorer): Promise<void> => {
  await explorer.expand('robot');
  await explorer.expand('System');
};

const expectSelection = async (explorer: PlaywrightExplorer, labels: string[]): Promise<void> => {
  await expect
    .poll(async (): Promise<(string | null)[]> => {
      return explorer.explorerLocator
        .locator('[data-treeitemid][data-testid="selected"]')
        .evaluateAll((items): (string | null)[] => {
          return items
            .map((item): string | null => {
              return item.getAttribute('data-treeitemlabel');
            })
            .sort();
        });
    })
    .toEqual([...labels].sort());
};

test.describe('explorer - selection', () => {
  let projectId: string;
  let explorer: PlaywrightExplorer;

  test.beforeEach(async ({ page, request }) => {
    const project = await new PlaywrightProject(request).createProject(
      'Playwright - explorer-selection',
      'flow-template'
    );
    projectId = project.projectId;
    await page.goto(`/projects/${projectId}/edit/`);
    explorer = new PlaywrightExplorer(page);
    await explorer.createNewModel('robot', 'robot_flow');
    await expect(page.getByTestId('create-new-model')).not.toBeAttached();
  });

  test.afterEach(async ({ request }) => {
    await new PlaywrightProject(request).deleteProject(projectId);
  });

  test('when items are clicked successively, then only the last clicked item is selected', async () => {
    await expandRobot(explorer);
    await explorer.select('Central_Unit');
    await expectSelection(explorer, ['Central_Unit']);
    await explorer.select('Wifi');
    await explorer.select('Capture_Subsystem');
    await expectSelection(explorer, ['Capture_Subsystem']);
  });

  test('when an unselected item is Control-clicked, then it is added to the selection', async () => {
    await expandRobot(explorer);
    await explorer.select('Central_Unit');
    await (await explorer.getTreeItemLabel('Wifi')).click({ modifiers: ['ControlOrMeta'] });
    await expectSelection(explorer, ['Central_Unit', 'Wifi']);
  });

  test('when a selected item is Control-clicked, then it is deselected', async () => {
    await expandRobot(explorer);
    await explorer.select('Central_Unit');
    await (await explorer.getTreeItemLabel('Wifi')).click({ modifiers: ['ControlOrMeta'] });
    await expectSelection(explorer, ['Central_Unit', 'Wifi']);
    await (await explorer.getTreeItemLabel('Central_Unit')).click({ modifiers: ['ControlOrMeta'] });
    await expectSelection(explorer, ['Wifi']);
    await (await explorer.getTreeItemLabel('Wifi')).click({ modifiers: ['ControlOrMeta'] });
    await expectSelection(explorer, []);
  });

  test('when an item is Shift-clicked, then the range from the pivot is selected', async () => {
    await expandRobot(explorer);
    await explorer.select('Central_Unit');
    await (await explorer.getTreeItemLabel('Wifi')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Central_Unit', 'Capture_Subsystem', 'Wifi']);
  });

  test('when items are Shift-clicked successively, then the range is updated from the original pivot', async () => {
    await expandRobot(explorer);
    await explorer.select('Central_Unit');
    await (await explorer.getTreeItemLabel('Wifi')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Central_Unit', 'Capture_Subsystem', 'Wifi']);
    await (await explorer.getTreeItemLabel('robot')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['robot', 'System', 'Central_Unit']);
  });

  test('when an item is clicked after a range selection, then only that item is selected', async () => {
    await expandRobot(explorer);
    await explorer.select('Central_Unit');
    await (await explorer.getTreeItemLabel('Wifi')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Central_Unit', 'Capture_Subsystem', 'Wifi']);
    await explorer.select('Wifi');
    await expectSelection(explorer, ['Wifi']);
  });

  test('when a range includes expanded items, then their visible children are selected', async () => {
    await expandRobot(explorer);
    await explorer.expand('Central_Unit');
    await explorer.select('Central_Unit');
    await (await explorer.getTreeItemLabel('Wifi')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Central_Unit', 'DSP', 'Motion_Engine', 'active', 'Capture_Subsystem', 'Wifi']);
  });

  test('when an unselected item is Control-clicked after a range selection, then it is added to the selection', async () => {
    await expandRobot(explorer);
    await explorer.expand('Capture_Subsystem');
    await explorer.select('Radar_Capture');
    await (await explorer.getTreeItemLabel('Radar')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera', 'Radar']);
    await (await explorer.getTreeItemLabel('GPU')).click({ modifiers: ['ControlOrMeta'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera', 'Radar', 'GPU']);
  });

  test('when a selected item is Control-clicked after a range selection, then it is removed from the selection', async () => {
    await expandRobot(explorer);
    await explorer.expand('Capture_Subsystem');
    await explorer.select('Radar_Capture');
    await (await explorer.getTreeItemLabel('Radar')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera', 'Radar']);
    await (await explorer.getTreeItemLabel('Back_Camera')).click({ modifiers: ['ControlOrMeta'] });
    await expectSelection(explorer, ['Radar_Capture', 'Radar']);
  });

  test('when an item is Control-Shift-clicked, then the selection is extended from the pivot', async () => {
    await expandRobot(explorer);
    await explorer.expand('Capture_Subsystem');
    await explorer.select('Radar_Capture');
    await (await explorer.getTreeItemLabel('Radar')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera', 'Radar']);
    await (await explorer.getTreeItemLabel('active')).click({ modifiers: ['ControlOrMeta', 'Shift'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera', 'Radar', 'Engine', 'GPU', 'active']);
  });

  test('when Control-clicking sets a new pivot, then Control-Shift-clicking adds the range from that pivot', async () => {
    await expandRobot(explorer);
    await explorer.expand('Capture_Subsystem');
    await explorer.select('Radar_Capture');
    await (await explorer.getTreeItemLabel('Back_Camera')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera']);
    await (await explorer.getTreeItemLabel('Engine')).click({ modifiers: ['ControlOrMeta'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera', 'Engine']);
    await (await explorer.getTreeItemLabel('active')).click({ modifiers: ['ControlOrMeta', 'Shift'] });
    await expectSelection(explorer, ['Radar_Capture', 'Back_Camera', 'Engine', 'GPU', 'active']);
  });

  test('when an item is Shift-clicked without a pivot, then the range starts at the first tree item', async ({
    page,
  }) => {
    await explorer.createNewModel('robot2', 'robot_flow');
    await expect(page.getByTestId('create-new-model')).not.toBeAttached();
    await expect(await explorer.getTreeItemLabel('robot2')).toBeVisible();
    await page.goto(`/projects/${projectId}/edit/`);
    await expect(await explorer.getTreeItemLabel('robot2')).toBeVisible();
    await expectSelection(explorer, []);
    await (await explorer.getTreeItemLabel('robot2')).click({ modifiers: ['Shift'] });
    await expectSelection(explorer, ['Flow', 'robot', 'robot2']);
  });
});
