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

/**
 * This test suite will be used to validate the proper lifecycle of our application.
 *
 * For that, we will open and close in various ways several representations and we will evaluate if we have
 * the proper number of representations and if they behave properly.
 */
describe('/projects/:projectId/edit - Related ELements View', () => {
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

  it('can open the related elements view', function () {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('viewselector-Related Elements').click();
    cy.getByTestId('view-Related Elements').within(() => {
      cy.getByTestId('form').should('exist');
    });
  });
});
