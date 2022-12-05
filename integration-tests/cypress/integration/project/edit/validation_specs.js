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

describe('/projects/:projectId/edit - Validation', () => {
  beforeEach(() => {
    cy.deleteAllProjects();

    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const emptyDomainDescriptionId = 'ed3249f1-c8a0-3bfb-8e1d-ec4b4cfc2604';
      cy.createDocument(projectId, emptyDomainDescriptionId, 'Sample Domain').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('should mark the domain URI has invalid', () => {
    cy.getByTestId('Sample Domain').dblclick();

    cy.get('[title="domain::Domain"]').click();

    cy.getByTestId('Name').find('p').should('not.exist');
    cy.getByTestId('Name').clear().type('{enter}');
    cy.getByTestId('Name').find('p').contains('The name is not well-formed.');
  });
});
