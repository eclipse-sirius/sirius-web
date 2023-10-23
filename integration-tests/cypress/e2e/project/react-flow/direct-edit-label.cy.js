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
describe('/projects/:projectId/edit - Direct edit label', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
  });
  it('Check node default size is used for node creation', () => {
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('Entity1 Node').dblclick();
    cy.getByTestId('NodePalette').dblclick();
    cy.getByTestId('Edit Label-more').click();
    cy.getByTestId('delete').click();

    cy.get('[title="Back to the homepage"]').click();

    // Create the domain instance
    cy.url().should('eq', Cypress.config().baseUrl + '/projects');
    cy.get('[title="Blank Studio"]').should('be.visible');
    cy.getByTestId('create').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
    cy.getByTestId('name').should('be.visible');
    cy.getByTestId('name').type('Instance');
    cy.getByTestId('create-project').click();

    cy.getByTestId('empty').click();
    cy.getByTestId('Others...-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('domain').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('domain').find('div').first().should('not.have.attr', 'aria-disabled');
    cy.getByTestId('domain').click();
    cy.getByTestId('domain').get('[data-value^="domain://"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.get('[data-testid$=" Diagram Description"]').should('exist').click();
    cy.getByTestId('name').clear().type('diagram__REACT_FLOW');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity1s Entity1"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Entity1').click();
    cy.getByTestId('Name').type('Entity1{enter}');

    cy.getByTestId('Rectangle - Entity1').click();
    cy.getByTestId('Edit - Tool').should('not.exist');
    cy.getByTestId('Rectangle - Entity1').trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
    cy.getByTestId('name-edit').should('not.exist');

    cy.getByTestId('Root-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity2s Entity2"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Entity2').click();
    cy.getByTestId('Name').type('Entity2{enter}');

    cy.getByTestId('Rectangle - Entity2').click();
    cy.getByTestId('Edit - Tool').should('exist');
    cy.getByTestId('Rectangle - Entity2').trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
    cy.getByTestId('name-edit').should('exist');
  });
});
