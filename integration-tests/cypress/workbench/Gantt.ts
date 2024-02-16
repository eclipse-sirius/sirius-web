/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { Project } from '../pages/Project';
import { Projects } from '../pages/Projects';
import { isCreateProjectFromTemplateSuccessPayload } from '../support/server/createProjectFromTemplateCommand';
import { Explorer } from './Explorer';

export class GanttTestHelper {
  checkSVGAttributeInTask(
    task: Cypress.Chainable<JQuery<HTMLElement>>,
    tagInsideSVG: string,
    attributeName: string,
    expectedValue: string
  ) {
    task.within(() => {
      cy.get(tagInsideSVG).then((element) => {
        expect(element.attr(attributeName)).eq(expectedValue);
      });
    });
  }

  public getGanttRepresentation(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('gantt-representation');
  }

  public getProjectTask(taskName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getGanttRepresentation().findByTestId(`task-project-${taskName}`);
  }
  public getMilestone(taskName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getGanttRepresentation().findByTestId(`task-milestone-${taskName}`);
  }
  public getTask(taskName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getGanttRepresentation().findByTestId(`task-bar-${taskName}`);
  }
  public getColumnHeader(title: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getGanttRepresentation().findByTestId(`table-column-header-${title}`);
  }
  public getTaskTitleCell(taskName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getGanttRepresentation().findByTestId(`title-table-cell-${taskName}`);
  }
  public getBeforeArea(taskName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getGanttRepresentation().findByTestId(`table-row-drop-before-${taskName}`);
  }
  public getAfterArea(taskName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getGanttRepresentation().findByTestId(`table-row-drop-after-${taskName}`);
  }

  public dragAndDrop(sourceTaskName: string, targetTaskName: string, position: 'before' | 'after' | 'inside') {
    const dataTransfer = new DataTransfer();

    this.getTaskTitleCell(sourceTaskName).trigger('dragstart', { dataTransfer });
    if (position === 'before') {
      this.getBeforeArea(targetTaskName).trigger('dragenter', 'center', { dataTransfer });
      this.getBeforeArea(targetTaskName).trigger('dragover', 'center', { dataTransfer });
      this.getBeforeArea(targetTaskName).trigger('drop', 'center', { dataTransfer });
    } else if (position === 'after') {
      this.getAfterArea(targetTaskName).trigger('dragenter', 'center', { dataTransfer });
      this.getAfterArea(targetTaskName).trigger('dragover', 'center', { dataTransfer });
      this.getAfterArea(targetTaskName).trigger('drop', 'center', { dataTransfer });
    } else if (position === 'inside') {
      this.getTaskTitleCell(targetTaskName).trigger('dragenter', 'center', { dataTransfer });
      this.getTaskTitleCell(targetTaskName).trigger('dragover', 'center', { dataTransfer, force: true });
      this.getTaskTitleCell(targetTaskName).trigger('drop', 'center', { dataTransfer, force: true });
    }
  }

  public checkTaskPositionInTable(taskName: string, expectedPosition: number) {
    this.getGanttRepresentation()
      .get('[class*="taskListTableRow"]')
      .eq(expectedPosition)
      .find(`[data-testid="title-table-cell-${taskName}"]`)
      .should('exist');
  }

  public createGanttRepresentation(rootElementName: string, GanttRepresentationName) {
    const explorer = new Explorer();
    explorer.getTreeItemByLabel('Task Model').should('exist');
    explorer.expand('Task Model');
    explorer.expand('Company');
    explorer.createRepresentation(rootElementName, 'Gantt Representation', GanttRepresentationName);
  }

  public deleteCurrentProject() {
    cy.url().then(($url) => {
      const prefix = Cypress.config().baseUrl + '/projects/';
      const projectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
      cy.deleteProject(projectId);
    });
  }

  public openGanttRepresentation(taskProjectId: string, projectName: string, representationName: string) {
    new Projects().visit();
    const project = new Project();
    project.visit(taskProjectId);
    project.disableDeletionConfirmationDialog();
    const explorer = new Explorer();
    explorer.expand('Task Model');
    explorer.expand('Company');
    explorer.expand(projectName);
    new Explorer().getTreeItemByLabel(representationName).click();
  }

  public closeGanttRepresentation(representationName: string) {
    cy.getByTestId(`close-representation-tab-${representationName}`).click();
  }

  /**
   * Creates the Gantt Task Sample view from the stereotype.
   * @returns the created studio project id.
   */
  public initGanttView(): Cypress.Chainable<string> {
    new Projects().visit();
    return cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const payload = res.body.data.createProjectFromTemplate;
      if (isCreateProjectFromTemplateSuccessPayload(payload)) {
        const studioProjectId = payload.project.id;
        new Project().visit(studioProjectId);
        new Explorer().createNewModel('Gantt Task Sample View', 'Gantt Task Sample View');
        return cy.wrap(studioProjectId);
      }
      return cy.wrap('');
    });
  }

  /**
   * Creates the Task Project and a Gantt representation on a project.
   * @returns the created task project id.
   */
  public createTaskProjectAndGanttRepresentation(
    rootElementName: string,
    GanttRepresentationName: string
  ): Cypress.Chainable<string> {
    new Projects().visit();
    return cy
      .createProjectFromTemplate('task-template')
      .then((res) => {
        const payload = res.body.data.createProjectFromTemplate;
        if (isCreateProjectFromTemplateSuccessPayload(payload)) {
          const taskProjectId = payload.project.id;
          new Project().visit(taskProjectId);
          new GanttTestHelper().createGanttRepresentation(rootElementName, GanttRepresentationName);
        }
      })
      .then(() => {
        return cy.url().then(($url) => {
          const prefix = Cypress.config().baseUrl + '/projects/';
          const taskProjectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
          return cy.wrap(taskProjectId);
        });
      });
  }
}
