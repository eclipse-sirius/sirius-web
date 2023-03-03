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
describe('/projects/:projectId/edit - Document Context Menu', () => {
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

  it('can open the new object modal', () => {
    cy.getByTestId('robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();

    cy.get('.MuiDialog-container').should('be.visible');
  });

  //fails during build
  it('can create a new object by clicking on the create button', () => {
    cy.getByTestId('robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();

    cy.getByTestId('create-object').click();

    cy.getByTestId('explorerTree').contains('Domain');
  });

  //fails during build
  it('can select the created root object', () => {
    cy.getByTestId('robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();

    cy.getByTestId('create-object').click();

    cy.getByTestId('explorerTree').contains('Domain');
    cy.getByTestId('selected').contains('Domain');
  });

  it('can delete a document', () => {
    cy.getByTestId('robot');

    cy.getByTestId('robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();

    cy.getByTestId('treeitem-contextmenu').should('not.exist');
    cy.getByTestId('robot').should('not.exist');
  });

  it('can rename a document', () => {
    cy.getByTestId('robot');

    cy.getByTestId('robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();

    cy.getByTestId('name-edit').type('renamed-robot{enter}');
    cy.getByTestId('renamed-robot').should('exist');
  });

  it('contains a download link', () => {
    cy.getByTestId('robot-more').click();

    cy.getByTestId('download').should('have.attr', 'href');
  });

  it.skip('can display all object types', () => {
    cy.getByTestId('robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    cy.getByTestId('domain').click().get('[data-value="http://www.eclipse.org/sirius-web/domain"]').click();

    cy.getByTestId('suggested').click({ force: true });
    cy.getByTestId('type').click();
    cy.get('[data-value="Entity"]').click();

    cy.getByTestId('create-object').click();

    cy.getByTestId('explorerTree').contains('NewEntity');
  });
});
