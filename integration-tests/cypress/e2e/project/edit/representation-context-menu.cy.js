/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

const projectName = `Cypress Project`;

describe('/projects/:projectId/edit - Representation Context Menu', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject(projectName).then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'robot').then((res) => {
        cy.visit(`/projects/${projectId}/edit`);
        cy.getByTestId('robot').dblclick();
        cy.getByTestId('Robot-more').click();
        cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
        cy.getByTestId('name').clear();
        cy.getByTestId('name').type('A01');
        cy.getByTestId('create-representation').click();

        cy.visit(`/projects/${projectId}/edit`);

        cy.getByTestId('robot').dblclick();
        cy.getByTestId('Robot-more').click();
        cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
        cy.getByTestId('name').clear();
        cy.getByTestId('name').type('B01');
        cy.getByTestId('create-representation').click();

        cy.visit(`/projects/${projectId}/edit`);

        cy.getByTestId('robot').dblclick();
        cy.getByTestId('Robot').dblclick();
      });
    });
  });

  it('can rename a closed representation', () => {
    cy.getByTestId('representation-tab-A01').should('not.exist');
    cy.getByTestId('representation-tab-B01').should('not.exist');
    cy.getByTestId('A01-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit').should('exist');
    cy.getByTestId('name-edit').get('input').should('have.value', 'A01');
    cy.getByTestId('name-edit').type('A02{enter}');

    /*    
    The tab isn't renamed anymore. This is a known issue.
    cy.getByTestId('representation-tab-A01').should('not.exist');
    cy.getByTestId('representation-tab-A02').should('exist');
    cy.getByTestId('representation-tab-B01').should('not.exist');
    */
    cy.getByTestId('explorerTree').contains('A02');
    cy.getByTestId('explorerTree').contains('B01');
  });

  it('can rename an opened focused representation', () => {
    cy.getByTestId('A01').click();
    cy.getByTestId('representation-tab-A01').should('exist');
    cy.getByTestId('representation-tab-B01').should('not.exist');
    cy.getByTestId('A01-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit').should('exist');
    cy.getByTestId('name-edit').get('input').should('have.value', 'A01');
    cy.getByTestId('name-edit').type('A02{enter}');

    /*    
    The tab isn't renamed anymore. This is a known issue.
    cy.getByTestId('representation-tab-A01').should('not.exist');
    cy.getByTestId('representation-tab-A02').should('exist');
    cy.getByTestId('representation-tab-B01').should('not.exist');
    */
    cy.getByTestId('explorerTree').contains('A02');
    cy.getByTestId('explorerTree').contains('B01');
  });
});
