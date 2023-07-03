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

  it('check widget reference mono-valued', () => {
    // Create the view
    cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.getByTestId('View-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('create-object').should('be.enabled');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Form Description"]').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('New Form Description').click();
    cy.getByTestId('Domain Type').type('flow::DataFlow');
    cy.getByTestId('Name').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('Title Expression').type('{selectall}').type('WidgetRefRepresentation');
    cy.getByTestId('WidgetRefRepresentation').dblclick();
    cy.getByTestId('PageDescription').dblclick();
    cy.getByTestId('GroupDescription-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription')
      .click()
      .get('[data-value="Widgets Reference Widget Description"]')
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Reference Name Expression').should('exist');
    cy.getByTestId('Label Expression').type('Test Widget Reference');
    cy.getByTestId('Reference Name Expression').find('textarea').eq(0).type('target');

    cy.get('[title="Back to the homepage"]').click();

    // Create the instance
    cy.getByTestId('create-template-Flow').click();
    cy.getByTestId('NewSystem').click();
    cy.getByTestId('DataSource1').dblclick();
    cy.getByTestId('standard-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('WidgetRefRepresentation').should('exist').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('reference-value-Processor1').should('exist');
    cy.getByTestId('Test Widget Reference-more').should('exist');
    cy.getByTestId('Test Widget Reference-delete').should('exist');
    cy.getByTestId('Test Widget Reference-more').click();
    cy.getByTestId('browse-modal').find('[data-testid="tree-root-elements"]').should('exist');
    cy.getByTestId('browse-modal').find('[data-testid="CompositeProcessor1"]').should('exist');
    cy.getByTestId('browse-modal').find('[data-testid="DataSource1"]').should('not.exist');
    cy.getByTestId('browse-modal').find('[data-testid="selected"]').find('[data-testid="Processor1"]').should('exist');
    cy.getByTestId('browse-modal').find('[data-testid="CompositeProcessor1"]').click();
    cy.getByTestId('select-value').click();
    cy.getByTestId('reference-value-CompositeProcessor1').should('exist');
    cy.getByTestId('reference-value-Processor1').should('not.exist');
    cy.getByTestId('Test Widget Reference-delete').click();
    cy.getByTestId('reference-value-CompositeProcessor1').should('not.exist');
    cy.getByTestId('reference-value-none').should('exist');
    const dataTransfer = new DataTransfer();
    cy.getByTestId('CompositeProcessor1').trigger('dragstart', { dataTransfer });
    cy.getByTestId('reference-value-none').trigger('drop', { dataTransfer });
    cy.getByTestId('reference-value-CompositeProcessor1').should('exist');
    cy.getByTestId('reference-value-none').should('not.exist');
  });
});
