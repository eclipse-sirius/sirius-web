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

export class Deck {
  public getDeckRepresentation(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('deck-representation');
  }

  public getLane(laneLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDeckRepresentation().findByTestId(`lane-${laneLabel}`);
  }

  public getCard(laneLabel: string, cardLabel): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getLane(laneLabel).findByTestId(`card-${cardLabel}`);
  }

  public createDailyDeckRepresentation(rootElementName: string, deckRepresentationName) {
    const explorer = new Explorer();
    explorer.getTreeItemByLabel('Task Model').should('exist');
    explorer.expand('Task Model');
    explorer.expand('Company');
    explorer.createRepresentation(rootElementName, 'Deck Daily Representation', deckRepresentationName);
  }

  public deleteCurrentProject() {
    cy.url().then(($url) => {
      const prefix = Cypress.config().baseUrl + '/projects/';
      const projectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
      cy.deleteProject(projectId);
    });
  }

  public openDeckRepresentation(taskProjectId: string, projectName: string, representationName: string) {
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

  public closeDeckRepresentation(representationName: string) {
    cy.getByTestId(`close-representation-tab-${representationName}`).click();
  }

  /**
   * Creates the Deck view from the stereotype.
   * @returns the created studio project id.
   */
  public initDeckView(): Cypress.Chainable<string> {
    new Projects().visit();
    return cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const payload = res.body.data.createProjectFromTemplate;
      if (isCreateProjectFromTemplateSuccessPayload(payload)) {
        const studioProjectId = payload.project.id;
        new Project().visit(studioProjectId);
        cy.getByTestId('new-model').click();
        cy.getByTestId('name-input').type('Deck Task Sample View');
        cy.getByTestId('stereotype').click();
        cy.get('li').filter(':contains("Deck Task Sample View")').click();
        cy.getByTestId('create-document').click();
        return cy.wrap(studioProjectId);
      }
      return cy.wrap('');
    });
  }

  /**
   * Creates the Deck view from the stereotype.
   * @returns the created studio project id.
   */
  public createTaskProjectAndDeckRepresentation(
    rootElementName: string,
    deckRepresentationName: string
  ): Cypress.Chainable<string> {
    new Projects().visit();
    return cy
      .createProjectFromTemplate('task-template')
      .then((res) => {
        const payload = res.body.data.createProjectFromTemplate;
        if (isCreateProjectFromTemplateSuccessPayload(payload)) {
          const taskProjectId = payload.project.id;
          new Project().visit(taskProjectId);
          new Deck().createDailyDeckRepresentation(rootElementName, deckRepresentationName);
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

  public collapseLane(laneName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getLane(laneName).findByTestId('lane-collapse-btn').click({ force: true });
  }

  public changeVisibility(laneName: string, cardsName: string[], expectedVisibilityInitialState: boolean): void {
    this.getLane(laneName).findByTestId(`lane-${laneName}-more`).click();
    for (const cardName of cardsName) {
      cy.getByTestId('lane-contextmenu')
        .findByTestId(`hide-reveal-card-${cardName}`)
        .findByTestId(`visibility-${expectedVisibilityInitialState ? 'on' : 'off'}`)
        .should('exist')
        .click();
    }
    cy.getByTestId('apply-card-visibility').click();
  }

  public expandLane(laneName: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getLane(laneName).findByTestId('lane-expand-btn').click({ force: true });
  }

  public isCollapse(laneName: string, expected: boolean): Cypress.Chainable<JQuery<HTMLElement>> {
    return expected
      ? this.getLane(laneName).findByTestId('lane-expand-btn').should('exist')
      : this.getLane(laneName).findByTestId('lane-collapse-btn').should('exist');
  }
}
