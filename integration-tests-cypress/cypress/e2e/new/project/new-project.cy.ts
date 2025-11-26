/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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

import { NewProject } from '../../../pages/NewProject';
import { Projects } from '../../../pages/Projects';
import { Explorer } from '../../../workbench/Explorer';

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

          new Projects().visit();
          cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/projects'));

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

  context('Given the projects browser page', () => {
    beforeEach(() => {
      new Projects().visit();
      cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/projects'));
    });

    context('When we select the "Blank project" button', () => {
      beforeEach(() => {
        cy.getByTestId('create-project-from-template-Blank Project').click();
      });

      it('Then we are redirected to the project creation page with the correct initial configuration', () => {
        // The URL indicates the selected template id
        cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/new/project\\?templateId=blank-project'));
        // The default name is properly initialized
        cy.getByTestId('name').should('have.value', 'Blank Project');
        // The chosen template is the one selected in the combo
        cy.getByTestId('template-selection-toggle').click();
        cy.getByTestId('template').should('be.visible').should('contain.text', 'Blank Project');
        // No libraries are selected
        cy.getByTestId('libraries-selection-toggle').click();
        cy.getByTestId('libraries-import-table').should('be.visible');
        cy.getByTestId('libraries-import-table')
          .find('[type="checkbox"]')
          .each(($checkbox) => expect($checkbox).not.to.be.checked);
      });

      context('Then when we validate the project creation with a custom name', () => {
        beforeEach(() => {
          cy.getByTestId('name').type(`{selectall}Custom Project Name`);
          cy.getByTestId('create-project').click();
        });

        it('Then a blank project is created and has the custom name', () => {
          // The URL indicates the selected template id
          cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/projects/[a-z0-9-]*/edit'));
          cy.getByTestId('navbar-title').should('contain.text', 'Custom Project Name');
        });
      });
    });

    context('When we select the "Studio" template from the "Show all templates" dialog', () => {
      beforeEach(() => {
        cy.getByTestId('show-all-templates').click();
        cy.getByTestId('create-project-from-template-Studio').click();
      });

      it('Then we are redirected to the project creation page with the correct initial configuration', () => {
        // The URL indicates the selected template id
        cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/new/project\\?templateId=studio-template'));
        // The default name is properly initialized
        cy.getByTestId('name').should('have.value', 'Studio');
        // The chosen template is the one selected in the combo
        cy.getByTestId('template-selection-toggle').click();
        cy.getByTestId('template').should('be.visible').should('contain.text', 'Studio');
        // No libraries are selected
        cy.getByTestId('libraries-selection-toggle').click();
        cy.getByTestId('libraries-import-table').should('be.visible');
        cy.getByTestId('libraries-import-table')
          .find('[type="checkbox"]')
          .each(($checkbox) => expect($checkbox).not.to.be.checked);
      });

      context('Then when we validate the project creation with a custom name', () => {
        beforeEach(() => {
          cy.getByTestId('name').type(`{selectall}Custom Studio`);
          cy.getByTestId('create-project').click();
        });

        it('Then a blank studio is created and has the custom name and correct initial content', () => {
          // The URL indicates the selected template id
          cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/projects/[a-z0-9-]*/edit'));
          cy.getByTestId('navbar-title').should('contain.text', 'Custom Studio');
          const explorer = new Explorer();
          explorer.getTreeItemByLabel('DomainNewModel').should('exist').should('be.visible');
          explorer.getTreeItemByLabel('ViewNewModel').should('exist').should('be.visible');
        });
      });

      context('Then when we select a different template', () => {
        beforeEach(() => {
          cy.getByTestId('template-selection-toggle').click();
          cy.getByTestId('template').click();
          cy.getByTestId('template-Papaya - Blank').click();
        });

        it('Then the default name is updated to match the new template selection', () => {
          cy.getByTestId('name').should('have.value', 'Papaya - Blank');
        });

        context('Then if we choose a library and create the project', () => {
          beforeEach(() => {
            // No libraries are selected
            cy.getByTestId('libraries-selection-toggle').click();
            cy.getByTestId('libraries-import-table').should('be.visible');
            cy.getByTestId('library-papaya:java:0.0.3').find('[type="checkbox"]').click();
            cy.getByTestId('create-project').click();
          });

          it('Then the project is created with the correct name and content', () => {
            // The URL indicates the selected template id
            cy.url().should('match', new RegExp(Cypress.config().baseUrl + '/projects/[a-z0-9-]*/edit'));
            cy.getByTestId('navbar-title').should('contain.text', 'Papaya - Blank');
            const explorer = new Explorer();
            explorer.getTreeItemByLabel('Papaya').should('exist').should('be.visible');
            explorer.getTreeItemByLabel('Java').should('exist').should('be.visible');
          });
        });
      });
    });
  });
});
