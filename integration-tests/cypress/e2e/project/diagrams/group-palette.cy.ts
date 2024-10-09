/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

const projectName = 'Cypress - group palette';
describe('Diagram - group palette', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('robot');
      explorer.createRepresentation('System', 'Topography', 'diagram');
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then the last distribute elements tool used is memorized', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      diagram.getDiagram('diagram').should('exist');
      diagram.fitToScreen();
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.getSelectedNodes('diagram', 'Wifi');
      diagram.getSelectedNodes('diagram', 'Central_Unit');
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Align left').should('exist');
      diagram.getGroupPalette().findByTestId('expand').click();
      diagram.getGroupPalette().findByTestId('Arrange in column').click();
      diagram.fitToScreen();
      diagram.getGroupPalette().should('not.exist');
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Align left').should('not.exist');
      diagram.getGroupPalette().findByTestId('Arrange in column').should('exist');
    });

    it('Then during multi selection the connection handles are not computed', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      diagram.getDiagram('diagram').should('exist');
      diagram.fitToScreen();
      explorer.select('Wifi');
      diagram.getSelectedNodes('diagram', 'Wifi');
      cy.getByTestId('creationhandle-top').should('exist');
      explorer.select('Central_Unit', true);
      diagram.getSelectedNodes('diagram', 'Wifi');
      diagram.getSelectedNodes('diagram', 'Central_Unit');
      cy.getByTestId('creationhandle-top').should('not.exist');
    });
  });
});
