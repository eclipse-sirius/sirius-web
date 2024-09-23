/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import { NewProject } from '../../../pages/NewProject';

describe('Project creation', () => {
  context('Given the new project form', () => {
    beforeEach(() => new NewProject().visit());

    context('When we manipulate the form', () => {
      it('Then it requires a name', () => {
        new NewProject().getCreateProjectButton().should('be.disabled');
      });

      it('Then it requires a valid name', () => {
        const newProject = new NewProject();
        newProject.getNameField().type('Cy');
        newProject.getCreateProjectButton().should('be.disabled');

        newProject.getNameField().type('Cypress Project');
        newProject.getCreateProjectButton().should('be.enabled');
      });
    });

    context('When create a new project', () => {
      afterEach(() => {
        cy.url().then(($url) => {
          const prefix = Cypress.config().baseUrl + '/projects/';
          const projectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
          cy.deleteProject(projectId);
        });
      });

      it('Then it navigates to the edit project view on successful project creation by clicking on the create button', () => {
        const newProject = new NewProject();
        newProject.getNameField().type('Cypress Project - New');
        newProject.getCreateProjectButton().should('be.enabled').click();

        cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/projects/[a-z0-9-]*/edit'));
      });
    });
  });
});
