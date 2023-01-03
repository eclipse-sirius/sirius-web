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

  it('can open the new model modal', () => {
    cy.getByTestId('more').click();
    cy.getByTestId('new-model').click();

    cy.get('.MuiDialog-container').should('be.visible');
    cy.getByTestId('navbar-contextmenu').should('not.exist');

    cy.getByTestId('name').type('{esc}');
    cy.get('.MuiDialog-container').should('not.exist');
  });

  it('requires a name to create a new model', () => {
    cy.getByTestId('more').click();
    cy.getByTestId('new-model').click();
    cy.getByTestId('create-document').should('be.disabled');
  });

  it('can create a new document by clicking on the create button', () => {
    cy.getByTestId('more').click();
    cy.getByTestId('new-model').click();

    cy.getByTestId('name-input').should('be.enabled');
    cy.getByTestId('name').type('nobel');

    cy.getByTestId('create-document').should('be.enabled');
    cy.getByTestId('create-document').click();

    cy.get('.MuiDialog-container').should('not.exist');
    cy.getByTestId('explorer').contains('nobel');
    cy.getByTestId('selected').contains('nobel');
  });

  it('can open the upload document modal', () => {
    cy.getByTestId('more').click();
    cy.getByTestId('upload-document').click();

    cy.get('.MuiDialog-container').should('be.visible');
    cy.getByTestId('navbar-contextmenu').should('not.be.visible');

    cy.get('.MuiDialog-container').type('{esc}');
    cy.get('.MuiDialog-container').should('not.exist');
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
});

//The upload function doesn't exist anymore.
it.skip('can upload an existing document', () => {
  cy.getByTestId('more').click();
  cy.getByTestId('upload-document').click();

  cy.fixture('Robot.xmi').then((fileContent) => {
    cy.getByTestId('file').upload({
      fileContent,
      fileName: 'robot',
      mimeType: 'text/xml',
      encoding: 'utf8',
    });

    cy.getByTestId('upload-document').click();

    cy.get('.MuiDialog-container').should('not.exist');
    cy.getByTestId('explorer').contains('robot');
  });
});
