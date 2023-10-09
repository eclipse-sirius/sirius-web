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
  });

  const createNewObjectInExplorer = (parentItem, treeItemName, expectedNewElementId) => {
    cy.getByTestId(parentItem).should('exist');
    cy.getByTestId(`${parentItem}-more`).click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('new-object-modal').should('exist');
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click().get(`[data-value="${treeItemName}"]`).should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('new-object-modal').should('not.exist');
    cy.getByTestId(`${parentItem}-toggle`).next().should('have.attr', 'data-expanded', 'true');

    // Make sure the created element is selected in the explorer
    cy.getByTestId('explorer://').findByTestId('selected').findByTestId(expectedNewElementId).should('exist');
    cy.getByTestId('explorer://').findByTestId('selected').invoke('attr', 'data-treeitemid').as('treeItemId');
    // Make sure the created element properties are display in the property view
    cy.get('@treeItemId').then((treeItemId) => {
      cy.getByTestId('form').findByTestId(`page-tab-${treeItemId}`).should('have.attr', 'aria-selected', 'true');
    });
    return cy.get('@treeItemId');
  };

  it('check widget read-only mode in form', () => {
    // Create the view
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').click();
    createNewObjectInExplorer('View', 'Form Description', 'New Form Description').then((_newElementId) => {
      cy.getByTestId('Domain Type').type('flow::System');
      cy.getByTestId('Name').type('{selectall}').type('ReadOnlyRepresentation');
      cy.getByTestId('Title Expression').type('{selectall}').type('ReadOnlyRepresentation');
    });

    cy.getByTestId('ReadOnlyRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();

    createNewObjectInExplorer('GroupDescription', 'Widgets Button Description', 'ButtonDescription').then(
      (_newElementId) => {
        cy.getByTestId('Button Label Expression').type('Test Button');
        cy.getByTestId('Is Enabled Expression').type('aql:self.temperature==0');
      }
    );

    cy.get('[title="Back to the homepage"]').click();
    // Check the representation
    cy.getByTestId('create-template-Flow').click();

    cy.getByTestId('NewSystem').dblclick();
    cy.getByTestId('NewSystem-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('ReadOnlyRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Test Button').should('exist').should('not.be.disabled');
    cy.getByTestId('NewSystem').click();
    cy.getByTestId('Temperature').type('{selectall}').type('2').type('{enter}');
    cy.getByTestId('Test Button').should('be.disabled');
    cy.getByTestId('Temperature').type('{selectall}').type('0').type('{enter}');
    cy.getByTestId('Test Button').should('not.be.disabled');
  });

  it('check the flexbox read-only mode is dispatched to children', () => {
    // Create the view
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    createNewObjectInExplorer('View', 'Form Description', 'New Form Description').then((_newElementId) => {
      cy.getByTestId('Domain Type').type('flow::System');
      cy.getByTestId('Name').type('{selectall}').type('ReadOnlyRepresentation');
      cy.getByTestId('Title Expression').type('{selectall}').type('ReadOnlyRepresentation');
    });
    cy.getByTestId('ReadOnlyRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();

    createNewObjectInExplorer(
      'GroupDescription',
      'Widgets Flexbox Container Description',
      'FlexboxContainerDescription'
    ).then((_newElementId) => {
      cy.getByTestId('Label Expression').type('Test flexbox container');
      cy.getByTestId('Is Enabled Expression').type("aql:self.name='NewSystem'");
    });

    createNewObjectInExplorer('FlexboxContainerDescription', 'Textfield Description', 'TextfieldDescription').then(
      (_newElementId) => {
        cy.getByTestId('Label Expression').type('Name');
        cy.getByTestId('Value Expression').type('aql:self.name');
      }
    );

    cy.get('[title="Back to the homepage"]').click();
    // Check the representation
    cy.getByTestId('create-template-Flow').click();

    cy.getByTestId('NewSystem').click();
    cy.getByTestId('NewSystem-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('ReadOnlyRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('NewSystem').click();
    cy.getByTestId('form').findByTestId('Name').type('2').type('{enter}');
    cy.getByTestId('page').findByTestId('input-Name').should('be.disabled');
  });
});
