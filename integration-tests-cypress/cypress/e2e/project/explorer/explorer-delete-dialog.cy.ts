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
import { Project } from '../../../pages/Project';
import { Flow } from '../../../usecases/Flow';
import { Explorer } from '../../../workbench/Explorer';

const projectName = 'Cypress - explorer-delete-dialog-1';
const projectName2 = 'Cypress - explorer-delete-dialog-2';

describe('Explorer', () => {
  context('Given two flow projects with robot documents', () => {
    let projectId: string = '';
    let projectId2: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
      })
      new Flow().createRobotProject(projectName2).then((createdProjectData) => {
        projectId2 = createdProjectData.projectId;
        new Project().visit(projectId);
      })
    });

    afterEach(() => {
      cy.deleteProject(projectId);
      cy.deleteProject(projectId2);
    });

    context('When we delete a model element in the first project', () => {
      it('Then the dialog is not disabled', () => {
        new Project().isDeletionConfirmationDialogDisabled(projectId).should('be.false');
      });
      it('Then the confirmation dialog is displayed', () => {
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.expandWithDoubleClick('System');
        explorer.delete('Central_Unit');
        cy.getByTestId('confirmation-dialog').should('exist');
      });
    });

    context('When we have disabled the dialog for the first project', () => {
      beforeEach(() => {
        new Project().disableDeletionConfirmationDialog(projectId);
      });
      it('Then the confirmation dialog is not displayed', () => {
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.expandWithDoubleClick('System');
        explorer.delete('Central_Unit');
        cy.getByTestId('confirmation-dialog').should('not.exist');
      });

      it("Then the dialog is not disabled for the second project", () => {
        new Project().isDeletionConfirmationDialogDisabled(projectId2).should('be.false');
      });
      it("Then the confirmation dialog is displayed for the second project", () => {
        new Project().visit(projectId2);
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('robot');
        explorer.expandWithDoubleClick('System');
        explorer.delete('Central_Unit');
        cy.getByTestId('confirmation-dialog').should('exist');
      });
    });
  });
});
