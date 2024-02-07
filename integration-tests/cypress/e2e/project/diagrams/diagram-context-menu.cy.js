/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
describe('/projects/:projectId/edit - Diagram Context Menu', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'robot').then((res) => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('can delete a representation', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('diagram');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('explorer://').contains('diagram');

    cy.getByTestId('diagram-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-cancel').click();
    cy.getByTestId('confirmation-dialog').should('not.exist');

    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-ok').click();

    cy.getByTestId('diagram').should('not.exist');
    cy.getByTestId('confirmation-dialog').should('not.exist');
  });
});
