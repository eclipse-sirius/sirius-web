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

const projectName = 'Cypress - pin nodes';
describe('Diagram - Pin unpin nodes', () => {
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
    it('Then it is possible to pin unpin node', () => {
      const diagram = new Diagram();
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getPalette().should('exist');
      cy.getByTestId('Pin-element').should('exist');
      cy.getByTestId('Unpin-element').should('not.exist');
      cy.getByTestId('Pin-element').click();

      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getPalette().should('exist');
      cy.getByTestId('Pin-element').should('not.exist');
      cy.getByTestId('Unpin-element').should('exist');
    });

    it('Then it is possible to unpin all nodes', () => {
      const diagram = new Diagram();
      diagram.fitToScreen();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getPalette().should('exist');
      cy.getByTestId('Pin-element').should('exist');
      cy.getByTestId('Unpin-element').should('not.exist');
      cy.getByTestId('Pin-element').click();

      diagram.fitToScreen();
      cy.getByTestId('unpin-all-elements').click();
      diagram.getNodes('diagram', 'Wifi').click();
      diagram.getPalette().should('exist');
      cy.getByTestId('Pin-element').should('exist');
      cy.getByTestId('Unpin-element').should('not.exist');
    });
  });
});
