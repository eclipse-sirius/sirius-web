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
import { Explorer } from '../../../workbench/Explorer';
import { Diagram } from '../../../workbench/Diagram';
import { Flow } from '../../../usecases/Flow';

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
      explorer.expand('robot');
      explorer.createRepresentation('Robot', 'Topography', 'diagram');
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then the group palette is displayed when using multi selection', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getPalette().should('not.exist');
      diagram.getGroupPalette().should('exist');

      diagram.getNodes('diagram', 'DSP').click();
      diagram.getPalette().should('exist');
      diagram.getGroupPalette().should('not.exist');
    });

    it('Then the default tools are available', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Hide elements').should('exist');
      diagram.getGroupPalette().findByTestId('Fade elements').should('exist');
      diagram.getGroupPalette().findByTestId('Pin elements').should('exist');
    });

    it('Then the distribute elements tools are available', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('expand').click();
      diagram.getGroupPalette().findByTestId('Distribute elements horizontally').should('exist');
      diagram.getGroupPalette().findByTestId('Distribute elements vertically').should('exist');
      diagram.getGroupPalette().findByTestId('Align left').should('exist');
      diagram.getGroupPalette().findByTestId('Align right').should('exist');
      diagram.getGroupPalette().findByTestId('Align center').should('exist');
      diagram.getGroupPalette().findByTestId('Align top').should('exist');
      diagram.getGroupPalette().findByTestId('Align bottom').should('exist');
      diagram.getGroupPalette().findByTestId('Justify horizontally').should('exist');
      diagram.getGroupPalette().findByTestId('Justify vertically').should('exist');
      diagram.getGroupPalette().findByTestId('Arrange in row').should('exist');
      diagram.getGroupPalette().findByTestId('Arrange in column').should('exist');
      diagram.getGroupPalette().findByTestId('Arrange in grid').should('exist');
    });

    it('Then the last distribute elements tool used is memorized', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.select('Wifi');
      explorer.select('Central_Unit', true);
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Distribute elements horizontally').should('exist');
      diagram.getGroupPalette().findByTestId('expand').click();
      diagram.getGroupPalette().findByTestId('Arrange in column').click();
      diagram.fitToScreen();
      diagram.getGroupPalette().should('not.exist');
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getGroupPalette().should('exist');
      diagram.getGroupPalette().findByTestId('Distribute elements horizontally').should('not.exist');
      diagram.getGroupPalette().findByTestId('Arrange in column').should('exist');
    });
  });

});
