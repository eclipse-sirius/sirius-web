/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { Project } from '../../pages/Project';
import { Flow } from '../../usecases/Flow';
import { Diagram } from '../../workbench/Diagram';
import { Explorer } from '../../workbench/Explorer';

const projectName = 'Cypress - Project Contextual Menu Actions';

describe('Project - Contextual Menu Actions', () => {
  context('Given a Robot flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('Rename Action', () => {
      context('When we try to rename with an invalid project name', () => {
        it('Then the rename button from the rename modal should be disabled', () => {
          const renameDialog = new Project().getProjectNavigationBar().getRenameDialog();
          renameDialog.clearValue();
          renameDialog.validate(false);
        });
      });
    });

    context('Share Action', () => {
      context('When we use the Share action', () => {
        it('Then the share dialog appears', () => {
          const shareDialog = new Project().getProjectNavigationBar().getShareDialog();
          shareDialog.getSharePathTextField().should('exist');
          cy.url().then((url) => {
            shareDialog
              .getSharePathTextField()
              .should('include.text', url)
              .should('include.text', '?workbenchConfiguration=');
          });
        });
      });
    });

    context('Duplicate Action', () => {
      context('When we try to duplicate a project', () => {
        it('Then we are redirected to a copy', () => {
          cy.url().should('include', `/projects/${projectId}/edit`);
          const duplicateButton = new Project().getProjectNavigationBar().getDuplicateButton();
          duplicateButton.click();
          cy.url().should('not.include', `/projects/${projectId}/edit`);
          cy.getByTestId('navbar-title').should('have.text', `${projectName} - Copy`);
        });
      });

      context('When we try to duplicate a project with a diagram opened', () => {
        it('Then we are redirected to a copy with no representation opened', () => {
          const explorer = new Explorer();
          explorer.expandWithDoubleClick('robot');
          explorer.getTreeItemByLabel('System').click();
          explorer.createRepresentation('System', 'Topography', 'Topography');

          new Diagram().getNodes('Topography', 'Central_Unit').should('be.visible');
          cy.getByTestId('representation-tab-Topography').should('be.visible');

          const duplicateButton = new Project().getProjectNavigationBar().getDuplicateButton();
          duplicateButton.click();

          cy.url().should('not.include', `/projects/${projectId}/edit`);
          cy.getByTestId('navbar-title').should('have.text', `${projectName} - Copy`);

          cy.getByTestId('representation-tab-Topography').should('not.exist');
        });
      });
    });
  });
});
