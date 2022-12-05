/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
describe('/projects/:projectId/edit - Robot Diagram', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'flow').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });

    cy.getByTestId('flow').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Topography').click();
  });

  // SBE: Skipped because of the new behavior with the FitToScreenAction
  it.skip('edit a diagram delete all edges', () => {
    for (let index = 7; index > 0; index--) {
      cy.get('#diagram>svg>g>g>path').should('have.length', index);
      cy.get('#diagram>svg>g>g>path').then(($paths) => {
        const [$path, ...others] = $paths;
        cy.wrap($path).click();
        cy.getByTestId('PopupToolbar').should('exist');
        cy.getByTestId('PopupToolbar').findByTestId('Delete - Tool').click();
        cy.getByTestId('PopupToolbar').should('not.exist');
      });
    }
    cy.get('#diagram>svg>g>g>path').should('have.length', 0);
  });
});
