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

const projectName = `Cypress Project`;

/**
 * Checks that the project with the supplied name is visible in the project list by the current user.
 */
const checkProjectIsVisible = (projectName) => {
  cy.visit('/projects');
  cy.getByTestId('create').should('exist'); // Wait for the UI to be rendered
  cy.getByTestId(projectName).should('exist');
};

describe('/projects', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.visit('/projects');
  });

  it('contains a link to the new project page', () => {
    cy.getByTestId('create').should('be.visible');
  });

  it('contains a link to the upload project page', () => {
    cy.getByTestId('upload').should('be.visible');
  });

  it('contains the list of projects', () => {
    cy.createProject(projectName).then(() => cy.reload());
    cy.getByTestId('projects').find('tr').should('have.length', 1);
  });
});
