/*******************************************************************************
 * Copyright (c) 2024  Obeo.
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

import { GanttTestHelper } from '../../../workbench/Gantt';

describe('Verify the actions in Gantt table', () => {
  let studioProjectId: string = '';
  let taskProjectId: string = '';
  before(() => {
    // We create the Gantt View from the stereotype before executing the tests
    new GanttTestHelper().initGanttView().then((projectId) => {
      studioProjectId = projectId;
    });

    new GanttTestHelper()
      .createTaskProjectAndGanttRepresentation('Project Dev', 'New Gantt Representation')
      .then((projectId) => {
        taskProjectId = projectId;
      });
  });
  after(() => {
    cy.deleteProject(studioProjectId);
    cy.deleteProject(taskProjectId);
  });

  beforeEach(() => {
    new GanttTestHelper().openGanttRepresentation(taskProjectId, 'Project Dev', 'New Gantt Representation');
  });

  it('can drag&drop tasks', () => {
    const ganttHelper = new GanttTestHelper();

    ganttHelper.dragAndDrop('Specification', 'Idea', 'before');
    ganttHelper.checkTaskPositionInTable('Specification', 0);

    ganttHelper.dragAndDrop('Specification', 'Release', 'after');
    ganttHelper.checkTaskPositionInTable('Specification', 7);

    // drop inside a release has no effect
    ganttHelper.dragAndDrop('Specification', 'Release', 'inside');
    ganttHelper.checkTaskPositionInTable('Specification', 7);

    ganttHelper.dragAndDrop('Specification', 'Code Development', 'inside');
    ganttHelper.checkTaskPositionInTable('Specification', 5);

    ganttHelper.dragAndDrop('Specification', 'Front', 'before');
    ganttHelper.checkTaskPositionInTable('Specification', 3);

    ganttHelper.dragAndDrop('Specification', 'Code Development', 'after');
    ganttHelper.checkTaskPositionInTable('Specification', 5);
    ganttHelper.checkTaskPositionInTable('Front', 3);

    ganttHelper.dragAndDrop('Front', 'Back', 'after');
    ganttHelper.checkTaskPositionInTable('Front', 4);

    // check that it is not possible to drag in inner tasks
    ganttHelper.checkTaskPositionInTable('Development', 1);
    ganttHelper.dragAndDrop('Development', 'Review', 'inside');
    ganttHelper.checkTaskPositionInTable('Development', 1);

    // check the move of a whole tree
    ganttHelper.dragAndDrop('Code Development', 'Specification', 'inside');
    ganttHelper.checkTaskPositionInTable('Code Development', 3);
    ganttHelper.checkTaskPositionInTable('Back', 4);
  });
});
