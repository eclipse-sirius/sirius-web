/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

describe('/projects/:projectId/edit - Tree toolbar', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'flow').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });

    cy.getByTestId('flow').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Topography').click();
  });

  it('can open the new model modal', () => {
    cy.getByTestId('new-model').click();

    cy.get('.MuiDialog-container').should('be.visible');
    cy.getByTestId('navbar-contextmenu').should('not.exist');

    cy.getByTestId('name').type('{esc}');
    cy.get('.MuiDialog-container').should('not.exist');
  });

  it('requires a name to create a new model', () => {
    cy.getByTestId('new-model').click();
    cy.getByTestId('create-document').should('be.disabled');
  });

  it('can create a new document by clicking on the create button', () => {
    cy.getByTestId('new-model').click();

    cy.getByTestId('name-input').should('be.enabled');
    cy.getByTestId('name').type('nobel');

    cy.getByTestId('create-document').should('be.enabled');
    cy.getByTestId('create-document').click();

    cy.get('.MuiDialog-container').should('not.exist');
    cy.getByTestId('explorer://').contains('nobel');
  });

  it('can open the upload document modal', () => {
    cy.getByTestId('upload-document-icon').click();

    cy.get('.MuiDialog-container').should('be.visible');

    cy.get('.MuiDialog-container').type('{esc}');
    cy.get('.MuiDialog-container').should('not.exist');
  });

  it('can upload an existing document', () => {
    cy.getByTestId('upload-document-icon').click();

    cy.getByTestId('file').selectFile(
      {
        contents: 'cypress/fixtures/Robot.xmi',
        fileName: 'robot',
      },
      { force: true }
    );

    cy.getByTestId('upload-document-submit').click();

    cy.get('.MuiDialog-container').should('not.exist');
    cy.getByTestId('explorer://').contains('robot');
  });
});
