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

/**
 * This test suite will be used to validate the proper lifecycle of our application.
 *
 * For that, we will open and close in various ways several representations and we will evaluate if we have
 * the proper number of representations and if they behave properly.
 */
describe('/projects/:projectId/edit - Representations', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';

      cy.createDocument(projectId, robot_flow_id, 'robot').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('can open a tree, a diagram and then a form', function () {
    const projectId = this.projectId;

    cy.getByTestId('robot').dblclick();

    console.info('Creating a Topography diagram');
    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('newName');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography').click();
    cy.getByTestId('create-representation').click();

    cy.get('#diagram>svg.sprotty-graph').should('have.length', 1);

    cy.getByTestId('Robot').click();
  });

  it('can open a tree then a form and switch objects', function () {
    const projectId = this.projectId;

    cy.getByTestId('robot').dblclick();

    cy.getByTestId('Robot').click();
    cy.getByTestId('form').contains('Robot').should('exist');

    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Central_Unit').click();
    cy.getByTestId('form').contains('Central_Unit').should('exist');

    cy.getByTestId('CaptureSubSystem').click();
    cy.getByTestId('form').contains('CaptureSubSystem').should('exist');

    cy.getByTestId('Wifi').click();
    cy.getByTestId('form').contains('Wifi').should('exist');
  });

  it('can open a diagram and switch diagrams', function () {
    const projectId = this.projectId;

    cy.getByTestId('robot').dblclick();

    console.info('Creating a Topography diagram');

    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('create-representation').click();
    cy.getByTestId('representation-tab-Topography1').should('exist');

    cy.get('#diagram>svg.sprotty-graph').should('have.length', 1);

    console.info('Creating a second Topography diagram');

    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography2');
    cy.getByTestId('create-representation').click();
    cy.getByTestId('representation-tab-Topography2').should('exist');

    cy.wait(2000);
    cy.get('#diagram>svg.sprotty-graph').should('have.length', 1);

    console.info('Switching to the Topography diagram');

    cy.getByTestId('representation-tab-Topography1').click();

    cy.wait(2000);
    cy.get('#diagram>svg.sprotty-graph').should('have.length', 1);

    cy.getByTestId('Topography2-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();

    cy.wait(2000);

    console.info('Switching to the second Topography diagram');

    cy.getByTestId('representation-tab-Topography2').click();
    cy.contains('The diagram does not exist').should('exist');

    cy.wait(2000);

    console.info('Switching to the Topography diagram');

    cy.getByTestId('representation-tab-Topography1').click();

    console.info('Deleting the Topography diagram');
    cy.get('#diagram>svg.sprotty-graph').should('have.length', 1);

    cy.getByTestId('Topography1-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();

    cy.contains('The diagram does not exist').should('exist');

    cy.wait(2000);
  });
});
