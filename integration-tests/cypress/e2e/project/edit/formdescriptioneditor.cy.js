/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
describe('/projects/:projectId/edit - FormDescriptionEditor', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('try to move a toolbar action into another empty group', () => {
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.getByTestId('View-more').click();

    // create the form description
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-object').should('be.enabled');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Form Description"]').click();
    cy.getByTestId('create-object').click();
    // create the form description editor
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('New Form Description-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('create-representation').click();
    // create a second group
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Group').trigger('dragstart', { dataTransfer });
    cy.getByTestId('FormDescriptionEditor-DropArea').trigger('drop', { dataTransfer });
    // create a toolbar action in the first group
    cy.get('[data-testid^="Group-ToolbarActions-NewAction-"]').eq(0).click();
    // move the toolbar action from the first group to the second one
    cy.getByTestId('ToolbarAction').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-ToolbarActions-DropArea-"]').eq(1).trigger('drop', { dataTransfer });
  });

  it('rename a form description editor', () => {
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.getByTestId('View-more').click();
    // create the form description
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-object').should('be.enabled');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Form Description"]').click();
    cy.getByTestId('create-object').click();
    // create a button widget under the group of the form description editor
    cy.getByTestId('New Form Description').dblclick();
    cy.getByTestId('GroupDescription').dblclick();
    cy.getByTestId('GroupDescription-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-object').should('be.enabled');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Widgets Button Description"]').click();
    cy.getByTestId('create-object').click();
    // create the form description editor
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('New Form Description-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('create-representation').click();
    // rename the form description editor
    cy.getByTestId('FormDescriptionEditor').click();
    cy.getByTestId('FormDescriptionEditor-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit').type('Renamed-FormDescriptionEditor{enter}');
    cy.getByTestId('Renamed-FormDescriptionEditor').should('exist');
  });
});
