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
import { Explorer } from '../../../workbench/Explorer';
import { Diagram } from '../../../workbench/Diagram';

const projectName = 'Cypress - flow';

describe('Flow', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we interact with the flow diagram', () => {
      it('Then I can create a topography diagram', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        explorer.getExplorerView().contains('robot');
        explorer.expand('robot');
        explorer.createRepresentation('Robot', 'Topography', 'diagramTopography');
        diagram.getDiagram('diagramTopography').should('exist');
        diagram.getNodes('diagramTopography', 'Central_Unit').should('exist');
      });

      it('Then I can create a topography unsynchronized diagram', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        explorer.getExplorerView().contains('robot');
        explorer.expand('robot');
        explorer.createRepresentation('Robot', 'Topography unsynchronized', 'diagramTopographyUnsynchronized');
        diagram.getDiagram('diagramTopographyUnsynchronized').should('exist');
        diagram.getNodes('diagramTopographyUnsynchronized', 'Central_Unit').should('not.exist');
      });

      it('Then I can create a topography with auto layout diagram', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        explorer.getExplorerView().contains('robot');
        explorer.expand('robot');
        explorer.createRepresentation('Robot', 'Topography with auto layout', 'diagramTopographyWithAutoLayout');
        diagram.getDiagram('diagramTopographyWithAutoLayout').should('exist');
        diagram.getNodes('diagramTopographyWithAutoLayout', 'Description').should('exist');
      });
    });
  });
});
