/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Project } from '../../../pages/Project';
import { Flow } from '../../../usecases/Flow';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

const projectName = 'Cypress - explorer toolbar';

describe('/projects/:projectId/edit - Tree toolbar', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
      const explorer = new Explorer();
      explorer.expand('robot');
      explorer.createRepresentation('Robot', 'Topography', 'diagram');

      new Diagram().getNodes('diagram', 'Wifi').should('exist');
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we interact with the explorer', () => {
      it('Then we can enable/disable tree filters', () => {
        cy.getByTestId('explorer://').contains('diagram');
        cy.getByTestId('tree-filter-menu-icon').click();
        cy.getByTestId('tree-filter-menu').should('be.visible');
        cy.getByTestId('tree-filter-menu-checkbox-Hide Representations').click();
        cy.getByTestId('explorer://').contains('diagram').should('not.exist');
        cy.getByTestId('explorer://').contains('Robot').click();
        cy.getByTestId('tree-filter-menu').should('not.exist');
        cy.getByTestId('tree-filter-menu-icon').click();
        cy.getByTestId('tree-filter-menu').should('be.visible');
        cy.getByTestId('tree-filter-menu-checkbox-Hide Representations').click();
        cy.getByTestId('explorer://').contains('Robot').click();
        cy.getByTestId('tree-filter-menu').should('not.exist');
        cy.getByTestId('explorer://').contains('diagram');
      });

      it('Then we can open the new model modal', () => {
        cy.getByTestId('new-model').click();

        cy.get('.MuiDialog-container').should('be.visible');
        cy.getByTestId('navbar-contextmenu').should('not.exist');

        cy.getByTestId('name').type('{esc}');
        cy.get('.MuiDialog-container').should('not.exist');
      });

      it('requires a name to create a new model', () => {
        cy.getByTestId('new-model').click();
        cy.getByTestId('create-document').should('be.disabled');
      });

      it('Then we can create a new document by clicking on the create button', () => {
        cy.getByTestId('new-model').click();

        cy.getByTestId('name-input').should('be.enabled');
        cy.getByTestId('name').type('nobel');

        cy.getByTestId('create-document').should('be.enabled');
        cy.getByTestId('create-document').click();

        cy.get('.MuiDialog-container').should('not.exist');
        cy.getByTestId('explorer://').contains('nobel');
        cy.getByTestId('selected').should('have.attr', 'data-treeitemlabel', 'nobel');
      });

      it('can open the upload document modal', () => {
        cy.getByTestId('upload-document-icon').click();

        cy.get('.MuiDialog-container').should('be.visible');

        cy.get('.MuiDialog-container').type('{esc}');
        cy.get('.MuiDialog-container').should('not.exist');
      });

      it('Then we can upload an existing document', () => {
        cy.getByTestId('upload-document-icon').click();

        cy.getByTestId('file').selectFile(
          {
            contents: 'cypress/fixtures/Robot.xmi',
            fileName: 'robot',
          },
          { force: true }
        );

        cy.getByTestId('upload-document-submit').click();

        cy.get('.MuiDialog-container').should('not.exist');
        cy.getByTestId('explorer://').contains('robot');
      });
    });
  });
});
