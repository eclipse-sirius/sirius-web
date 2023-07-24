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
describe('/projects/:projectId/edit - Project Context Menu', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
  });

  it('shows the project context menu and hides it by clicking outside', () => {
    cy.getByTestId('more').click({ force: true });
    cy.getByTestId('navbar-contextmenu').should('be.visible');
    cy.get('body').click();
    cy.getByTestId('navbar-contextmenu').should('not.exist');
  });

  it('shows the project context menu and hides it by typing esc', () => {
    cy.getByTestId('more').click({ force: true });
    cy.getByTestId('navbar-contextmenu').should('be.visible');
    cy.getByTestId('navbar-contextmenu').type('{esc}');
    cy.getByTestId('navbar-contextmenu').should('not.exist');
  });

  it('contains a download link', () => {
    cy.getByTestId('more').click();
    cy.getByTestId('download-link').should('have.attr', 'href');
  });

  it('can open the delete project modal', () => {
    cy.getByTestId('more').click();
    cy.getByTestId('navbar-contextmenu').findByTestId('delete').click();

    cy.get('.MuiDialog-container').should('be.visible');
    cy.getByTestId('navbar-contextmenu').should('not.be.visible');
  });

  it('can delete a project', () => {
    cy.getByTestId('more').click();
    cy.getByTestId('navbar-contextmenu').findByTestId('delete').click();

    cy.getByTestId('delete-project').click();
    cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/projects'));
  });

  it('can rename a project', () => {
    cy.getByTestId('navbar-New Project Name').should('not.exist');
    cy.getByTestId('navbar-Cypress Project').should('exist');

    cy.getByTestId('more').click();
    cy.getByTestId('navbar-contextmenu').findByTestId('rename').click();

    cy.getByTestId('rename-textfield').type('{selectAll}{backspace}');
    cy.getByTestId('rename-textfield').type('New Project Name');
    cy.getByTestId('rename-project').click();

    cy.getByTestId('navbar-New Project Name').should('exist');
    cy.getByTestId('navbar-Cypress Project').should('not.exist');
  });
});
