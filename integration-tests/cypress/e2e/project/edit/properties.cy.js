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
describe('/projects/:projectId/edit - Details', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'robot').then((res) => {
        const emptyDomainDescriptionId = 'ed3249f1-c8a0-3bfb-8e1d-ec4b4cfc2604';
        cy.createDocument(projectId, emptyDomainDescriptionId, 'Sample Domain').then(() => {
          cy.visit(`/projects/${projectId}/edit`);
        });
      });
    });
  });

  it('shows the properties of the selected object', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('form').contains('Robot');

    cy.getByTestId('Wifi').click();
    cy.getByTestId('form').contains('Wifi');
  });

  it('should not be empty when "unsupported" element is selected', () => {
    cy.getByTestId('view-Details').contains('No object selected');

    cy.getByTestId('Sample Domain').click();
    cy.getByTestId('view-Details').contains('No object selected');

    cy.getByTestId('Sample Domain').dblclick();
    cy.get('[title="domain::Domain"]').click();
    cy.getByTestId('Sample Domain').click();
    cy.getByTestId('view-Details').contains('No object selected');
  });

  it('can edit a textfield using enter', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();

    cy.getByTestId('Name').clear();
    cy.getByTestId('Name').type('NewName{enter}');
    cy.getByTestId('explorer://').contains('NewName');
    cy.getByTestId('form').contains('NewName');
  });

  it('can edit a textfield using the loss of focus', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();

    cy.getByTestId('Name').clear();
    cy.getByTestId('Name').type('NewName');

    cy.getByTestId('explorer://').click({ force: true });

    cy.getByTestId('explorer://').contains('NewName');
    cy.getByTestId('NewName').click();
    cy.getByTestId('form').contains('NewName');
  });
});
