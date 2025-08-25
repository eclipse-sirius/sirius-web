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
import { Papaya } from '../../../usecases/Papaya';
import { Explorer } from '../../../workbench/Explorer';
import { ImpactAnalysis } from '../../../workbench/ImpactAnalysis';

describe('Impact analysis - update library', () => {
  context('Given a papaya project with a library', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Papaya().createPapayaLibraryExampleProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we update the library', () => {
      beforeEach(() => {
        const explorer = new Explorer();
        explorer.openTreeItemAction('Java');
        cy.getByTestId(`update-library`).should('exist').click();
        cy.getByTestId(`select-java@0.0.2`).should('exist').click();
        cy.getByTestId(`submit-update-library`).should('exist').click();
      });

      it('Then the impact analysis dialog is displayed with the good report', () => {
        const impactAnalysis = new ImpactAnalysis();
        impactAnalysis.getImpactAnalysisDialog().should('exist');
        impactAnalysis
          .getAdditionalReportData(0)
          .should('exist')
          .should('contain', '[BROKEN] Papaya - Interface.extends (previously set to Map<K, V>)');
      });
    });
  });
});
