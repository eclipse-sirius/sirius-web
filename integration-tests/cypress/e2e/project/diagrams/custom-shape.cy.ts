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
import { Project } from '../../../pages/Project';
import { Studio } from '../../../usecases/Studio';

describe.skip('/projects/:projectId/edit - Custom Shape', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    new Studio().createStudioProject().then((createdProjectData) => {
      const project = new Project();
      project.visit(createdProjectData.projectId);
      project.disableDeletionConfirmationDialog();
    });
  });

  it('Check ellipse node creation', () => {
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('Entity1 Node').dblclick();

    cy.getByTestId('RectangularNodeStyleDescription-more').click();
    cy.getByTestId('delete').click();
    cy.getByTestId('Entity1 Node-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="combobox"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription')
      .get('[data-value="style-EllipseNodeStyleDescription"]')
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();

    cy.get('[aria-label="Back to the homepage"]').click();

    // Create the domain instance
    cy.url().should('eq', Cypress.config().baseUrl + '/projects');
    cy.get('[aria-label="Blank Studio"]').should('be.visible');
    cy.getByTestId('create').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
    cy.getByTestId('name').should('be.visible');
    cy.getByTestId('name').type('Instance');
    cy.getByTestId('create-project').click();

    cy.getByTestId('empty').click();
    cy.getByTestId('Others...-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('domain').children('[role="combobox"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('domain').find('div').first().should('not.have.attr', 'aria-disabled');
    cy.getByTestId('domain').click();
    cy.getByTestId('domain').get('[data-value^="domain://"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription')
      .children('[role="combobox"]')
      .invoke('text')
      .should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.get('[data-testid$=" Diagram Description"]').should('exist').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('representation-area').should('exist');

    cy.getByTestId('Root-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="combobox"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="entity1s-Entity1"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Ellipse - ').should('have.css', 'border-radius', '50%');
  });
});
