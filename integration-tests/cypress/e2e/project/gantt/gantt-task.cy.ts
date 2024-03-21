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

import { Details } from '../../../workbench/Details';
import { GanttTestHelper } from '../../../workbench/Gantt';

describe('Verify the Gantt Task actions', () => {
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

  it('can select a task and see its attributes in Details', () => {
    const ganttHelper = new GanttTestHelper();
    ganttHelper.getTask('Specification').click();
    const details = new Details();
    details.getTextField('Name').should('have.value', 'Specification');
    details.getTextField('Description').should('have.value', 'Description of the Specification');
    details.getTextField('Progress').should('have.value', '50');
    details.getDetailsView().findByTestId('Compute Start/End Dynamically').should('not.exist');
    // details.getCheckBox('Compute Start/End Dynamically').should('not.exist');

    ganttHelper.getProjectTask('Development').click();
    details.getCheckBox('Compute Start/End Dynamically').should('exist');
  });

  it('can change task characteristics from detail with effect in gantt', () => {
    const ganttHelper = new GanttTestHelper();
    const details = new Details();
    ganttHelper.getTask('Specification').click();

    details.getTextField('Name').type('Renamed{Enter}');
    ganttHelper.getTask('Specification').should('not.exist');
    ganttHelper.getTask('SpecificationRenamed').should('exist');

    ganttHelper.checkSVGAttributeInTask(ganttHelper.getTask('SpecificationRenamed'), 'rect', 'width', '82.5');
    details.getTextField('End Time').type('{selectall}{backspace}2023-12-13T17:30:00Z{Enter}');
    ganttHelper.checkSVGAttributeInTask(ganttHelper.getTask('SpecificationRenamed'), 'rect', 'width', '142.5');
    details.getTextField('Name').type('{selectAll}Specification{Enter}');
  });

  it('can create and delete a new task', () => {
    const ganttHelper = new GanttTestHelper();
    ganttHelper.getTask('Specification').click();
    ganttHelper.getGanttRepresentation().getByTestId('create-task').click();
    ganttHelper.getTask('New Task').should('exist');

    ganttHelper.getTask('New Task').click();
    ganttHelper.getGanttRepresentation().getByTestId('delete-task').click();
    ganttHelper.getTask('New Task').should('not.exist');
  });

  it.only('can create a task dependency', () => {
    const ganttHelper = new GanttTestHelper();
    ganttHelper.getTask('Front').click();
    ganttHelper
      .getGanttRepresentation()
      .findByTestId('task-relation-handle-right-Front')
      .trigger('mouseenter')
      .trigger('mousedown');

    ganttHelper
      .getGanttRepresentation()
      .findByTestId('task-relation-handle-left-Back')
      .trigger('mouseenter', { force: true })
      .trigger('mouseup', { force: true });

    ganttHelper.getGanttRepresentation().findByTestId('task-arrow-endOfTask-Front-startOfTask-Back').should('exist');
  });
});
