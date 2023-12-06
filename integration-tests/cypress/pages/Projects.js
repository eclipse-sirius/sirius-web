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

export class Projects {
  visit() {
    cy.visit('/projects');
  }

  getCreateProjectLink() {
    return cy.getByTestId('create');
  }

  getUploadProjectLink() {
    return cy.getByTestId('upload');
  }

  getProjectLink(name) {
    return cy.getByTestId('projects').contains('a', name);
  }

  deleteProject(name) {
    cy.getByTestId('projects').contains('tr', name).find('[data-testid="more"]').click();
    cy.getByTestId('delete').click();
    cy.getByTestId('delete-project').click();
  }
}
