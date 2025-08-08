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
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

const projectName = 'Cypress - palette';

describe('Diagram - Palette', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
      new Diagram().disableFitView();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('robot');
      explorer.createRepresentation('System', 'Topography', 'diagram');
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When nothing is selected in the diagram', () => {
      it('Then a left-click on an edge should select the element in the diagram and in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // left click on the edge between Wifi and DSP (with label '4')
        cy.getByTestId('Label - 4').should('exist').click({ force: true });
        // the palette is closed
        diagram.getPalette().should('not.exist');
        // the edge between Wifi and DSP element is selected in the diagram
        diagram.getSelectedEdge('diagram', '4').should('exist');
        // the standard element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('standard');
        // the standard element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-standard').should('exist');
      });
    });
  });
});
