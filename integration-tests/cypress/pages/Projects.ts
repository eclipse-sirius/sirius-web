/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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

export class Projects {
  public visit(): Cypress.Chainable<Cypress.AUTWindow> {
    return cy.visit('/projects');
  }

  public getCreateProjectLink(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('create');
  }

  public getUploadProjectLink(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('upload');
  }

  public getProjectLink(name: string): Cypress.Chainable<JQuery<HTMLAnchorElement>> {
    return cy.getByTestId('projects').contains('a', name);
  }

  public goBackToTheHomepage(): void {
    cy.get('[title="Back to the homepage"]').click();
  }

  public getActionMenu(name: string): ProjectActionMenu {
    cy.getByTestId('projects').contains('tr', name).find('[data-testid="more"]').click();
    return new ProjectActionMenu();
  }
}

class ProjectActionMenu {
  public getDownloadLink(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('project-download-action');
  }

  public getRenameButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('rename');
  }

  public deleteProject(): void {
    cy.getByTestId('delete').click();
    cy.getByTestId('delete-project').click();
  }
}
