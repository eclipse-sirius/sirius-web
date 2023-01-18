/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

  it('Deactivate the synchronisation mode does not reveal the semantic element selected on a diagram but activating it will', () => {
    // 1. Deactivate the synchronisation mode
    cy.getByTestId('tree-synchronise').click();

    // 2. On the diagram, click on the 'CaptureSubSystem'
    cy.getByTestId('CaptureSubSystem').should('exist');
    cy.getByTestId('Rectangle - CaptureSubSystem').click(0, 0);

    // 3. CHECK that the 'CaptureSubSystem' node is visible and selected in the explorer
    cy.getByTestId('selected').contains('CaptureSubSystem');

    // 4. On the diagram, click on the 'DSP'
    cy.getByTestId('DSP').should('not.exist');
    cy.getByTestId('Label - DSP').click();

    // 5. CHECK that the 'DSP' node is not visible and not selected in the explorer
    cy.getByTestId('DSP').should('not.exist');
    cy.getByTestId('selected').should('not.exist');

    // 6. Activate the synchronisation mode
    cy.getByTestId('tree-synchronise').click();

    // 5. CHECK that the 'GPU' node is visible and selected
    cy.getByTestId('DSP').should('exist');
    cy.getByTestId('selected').findByTestId('DSP').should('exist');
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
    cy.getByTestId('explorer').contains('nobel');
  });

  it('can open the upload document modal', () => {
    cy.getByTestId('upload-document').click();

    cy.get('.MuiDialog-container').should('be.visible');

    cy.get('.MuiDialog-container').type('{esc}');
    cy.get('.MuiDialog-container').should('not.exist');
  });
});

//The upload function doesn't exist anymore.
it.skip('can upload an existing document', () => {
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
