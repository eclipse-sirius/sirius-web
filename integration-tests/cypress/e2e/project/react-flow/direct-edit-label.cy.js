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
  it('Check direct edit is trigger only if related tool exist', () => {
    cy.getByTestId('DomainNewModel').dblclick();
    cy.get('[title="domain::Domain"]').then(($div) => {
      cy.wrap($div.data().testid).as('domainValue');
    });

    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('Entity1 Node').dblclick();
    cy.getByTestId('NodePalette').dblclick();
    cy.getByTestId('Edit Label-more').click();
    cy.getByTestId('delete').click();

    cy.get('[title="Back to the homepage"]').click();

    // Create the domain instance
    cy.get('@domainValue').then((domainValue) => {
      cy.createInstanceFromDomainModel(domainValue, true);
    });

    cy.getByTestId('Root-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity1s Entity1"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Entity1').click();
    cy.getByTestId('Name').type('Entity1{enter}');

    cy.getByTestId('fit-to-screen').click();
    cy.getByTestId('Rectangle - Entity1').click();
    cy.getByTestId('Palette').should('exist');
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

    cy.getByTestId('fit-to-screen').click();
    cy.getByTestId('Rectangle - Entity2').click();
    cy.getByTestId('Edit - Tool').should('exist');
    cy.getByTestId('Rectangle - Entity2').trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
    cy.getByTestId('name-edit').should('exist');
  });
});
