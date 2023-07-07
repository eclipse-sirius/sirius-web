/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
describe('/projects/:projectId/edit - Diagram', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'robot').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('can create views by Drag and Drop on an unsynchronized diagram when a representation is selected', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1__REACT_FLOW');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography unsynchronized').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Topography1__REACT_FLOW').click();

    //Drop with diagram as target
    const dataTransfer = new DataTransfer();
    cy.getByTestId('Central_Unit').trigger('dragstart', { dataTransfer });
    cy.getByTestId('rf__wrapper').trigger('drop', 'bottomRight', { dataTransfer });
    cy.wait(500); // Wait for representation to refresh
    cy.getByTestId('Rectangle - Central_Unit').should('exist');
  });
});
