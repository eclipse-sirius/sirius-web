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

import { Explorer } from '../../../workbench/Explorer';
import { GanttTestHelper } from '../../../workbench/Gantt';

describe('Verify the Gantt Representation', () => {
  let studioProjectId: string = '';
  before(() => {
    // We create the Gantt View from the stereotype before executing the tests
    new GanttTestHelper().initGanttView().then((projectId) => {
      studioProjectId = projectId;
    });
  });
  after(() => {
    // We delete the created studio once all tests have been executed
    cy.deleteProject(studioProjectId);
  });

  context('We new verify the Gantt Creation, Deletion and renaming', () => {
    let taskProjectId: string;
    before(() => {
      new GanttTestHelper()
        .createTaskProjectAndGanttRepresentation('Project Dev', 'New Gantt Representation')
        .then((projectId) => {
          taskProjectId = projectId;
        });
    });

    beforeEach(() => {
      new GanttTestHelper().openGanttRepresentation(taskProjectId, 'Project Dev', 'New Gantt Representation');
    });

    after(() => {
      cy.deleteProject(taskProjectId);
    });

    it('can create the Gantt Representation', () => {
      new Explorer().getSelectedTreeItems().contains('New Gantt Representation').should('exist');
      new GanttTestHelper().getGanttRepresentation().should('exist');
    });

    it('can verify the Gantt Representation on the Dev project', () => {
      new Explorer().getSelectedTreeItems().contains('New Gantt Representation').should('exist');

      const gantt = new GanttTestHelper();
      //We verify that all tasks are present.
      const taskTitles: string[] = ['Idea', 'Specification', 'Front', 'Back', 'Review'];
      taskTitles.forEach((title) => {
        gantt.getTask(title).should('exist');
      });
      const projectTitles: string[] = ['Development', 'Code Development'];
      projectTitles.forEach((title) => {
        gantt.getProjectTask(title).should('exist');
      });
      gantt.getMilestone('Release').should('exist');
    });

    it('can rename the Gantt Representation', () => {
      const explorer = new Explorer();
      explorer.getTreeItemByLabel('New Gantt Representation').should('exist');
      new GanttTestHelper().getGanttRepresentation().should('exist');
      explorer.rename('New Gantt Representation', 'renamed');
      explorer.getTreeItemByLabel('New Gantt Representation').should('not.exist');
      explorer.getTreeItemByLabel('renamed').should('exist');

      cy.getByTestId('representation-tab-renamed').should('exist');
      explorer.rename('renamed', 'New Gantt Representation');
    });

    it('can remove the Gantt Representation', () => {
      const explorer = new Explorer();
      explorer.getTreeItemByLabel('New Gantt Representation').should('exist');
      new GanttTestHelper().getGanttRepresentation().should('exist');
      explorer.delete('New Gantt Representation');
      explorer.getTreeItemByLabel('New Gantt Representation').should('not.exist');
      cy.getByTestId('representation-area').find('h5').should('have.text', 'The Gantt does not exist anymore');
    });
  });

  context('We verify the Gantt table columns', () => {
    let taskProjectId: string;
    before(() => {
      new GanttTestHelper()
        .createTaskProjectAndGanttRepresentation('Project Dev', 'New Gantt Representation')
        .then((projectId) => {
          taskProjectId = projectId;
        });
    });

    beforeEach(() => {
      new GanttTestHelper().openGanttRepresentation(taskProjectId, 'Project Dev', 'New Gantt Representation');
    });

    after(() => {
      cy.deleteProject(taskProjectId);
    });
    it('can display the table columns', () => {
      const ganttHelper = new GanttTestHelper();
      const columnTitles = ['Name', 'Start Date', 'Start Date', 'Progress'];
      columnTitles.forEach((title) => {
        ganttHelper.getColumnHeader(title).should('exist');
      });
      ganttHelper.getGanttRepresentation().findByTestId('display-task-list-columns').click();
      columnTitles.forEach((title) => {
        ganttHelper.getColumnHeader(title).should('not.exist');
      });
      ganttHelper.getGanttRepresentation().findByTestId('display-task-list-columns').click();
      columnTitles.forEach((title) => {
        ganttHelper.getColumnHeader(title).should('exist');
      });

      //hide one column
      ganttHelper.getGanttRepresentation().findByTestId('columns-select').click();
      cy.getByTestId('columnType-START_DATE').click();
      ganttHelper.getColumnHeader('Start Date').should('not.exist');
      cy.getByTestId('columnType-START_DATE').click();
      ganttHelper.getColumnHeader('Start Date').should('exist');
      cy.get('body').click(0, 0);

      // hide all columns
      ganttHelper.getGanttRepresentation().findByTestId('display-task-list-columns').click();
      columnTitles.forEach((title) => {
        ganttHelper.getColumnHeader(title).should('not.exist');
      });
      ganttHelper.getGanttRepresentation().findByTestId('display-task-list-columns').click();
      columnTitles.forEach((title) => {
        ganttHelper.getColumnHeader(title).should('exist');
      });
      ganttHelper.getGanttRepresentation().findByTestId('columns-select').click();
      cy.getByTestId('columnType-START_DATE').click();
      cy.get('body').click(0, 0);
      ganttHelper.getGanttRepresentation().findByTestId('display-task-list-columns').click();
      ganttHelper.getColumnHeader('Start Date').should('not.exist');
    });

    it('can change the columns width', () => {
      const ganttHelper = new GanttTestHelper();

      ganttHelper.getColumnHeader('Name').then((header) => {
        expect(header.width()).eq(200);
      });
      ganttHelper
        .getGanttRepresentation()
        .findByTestId('table-column-header-resize-handle-Name')
        .trigger('mousedown')
        .trigger('mousemove', { clientX: 450 })
        .trigger('mouseup');

      ganttHelper.getColumnHeader('Name').then((header) => {
        expect(header.width()).to.approximately(40, 2);
      });
      ganttHelper.getColumnHeader('End Date').then((header) => {
        expect(header.width()).to.approximately(120, 2);
      });
    });
  });

  context('We verify the Gantt toolbar', () => {
    let taskProjectId: string;
    before(() => {
      new GanttTestHelper()
        .createTaskProjectAndGanttRepresentation('Project Dev', 'New Gantt Representation')
        .then((projectId) => {
          taskProjectId = projectId;
        });
    });

    beforeEach(() => {
      new GanttTestHelper().openGanttRepresentation(taskProjectId, 'Project Dev', 'New Gantt Representation');
    });

    after(() => {
      cy.deleteProject(taskProjectId);
    });

    it('can change the zoom level', () => {
      cy.getByTestId('zoom-level').get('input').should('have.value', 'Day');
      cy.getByTestId('representation-area').findByTestId('zoom-level').click();
      cy.getByTestId('zoom-level-Hour').click();
      cy.getByTestId('zoom-level-Hour').should('exist');
      cy.getByTestId('fit-to-screen').click();
      cy.getByTestId('zoom-level').get('input').should('have.value', 'Day');
    });

    it('can share the representation', () => {
      cy.getByTestId('representation-area').findByTestId('share').click();

      cy.url().then((url) => {
        cy.window().then((win) => {
          win.navigator.clipboard.readText().then((text) => {
            expect(text).to.eq(url);
          });
        });
      });
    });
  });
});
