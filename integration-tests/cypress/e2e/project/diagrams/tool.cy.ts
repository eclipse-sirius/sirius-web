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
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Tools', () => {
  context('Given a papaya blank project with a class diagram', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Papaya().createPapayaBlankProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Papaya');
        explorer.expandWithDoubleClick('Project');
        explorer.expandWithDoubleClick('Component');
        explorer.createRepresentation('Component', 'Class Diagram', 'diagram');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When the palette is opened', () => {
      beforeEach(() => {
        const diagram = new Diagram();
        diagram.getDiagram('diagram').should('exist');
        cy.getByTestId('rf__wrapper').should('exist').rightclick(100, 100).rightclick(100, 100);
        diagram.getPalette().should('exist');
      });

      it('Then clicking on a tool that displays a selection dialog displays the appropriate dialog', () => {
        cy.getByTestId('Import existing types - Tool').should('exist').click();
        cy.getByTestId('selection-dialog').should('exist');
      });
    });
  });
});
