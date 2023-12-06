/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
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

  public deleteProject(name: string): void {
    cy.getByTestId('projects').contains('tr', name).find('[data-testid="more"]').click();
    cy.getByTestId('delete').click();
    cy.getByTestId('delete-project').click();
  }
}
