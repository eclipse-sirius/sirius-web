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
    context('After creating a composite processor on the diagram', () => {
      it('The diagram palette should remember the last tool invoked from it', () => {
        const diagram = new Diagram();
        diagram.getDiagram('diagram').should('exist');

        // Open the diagram's palette
        cy.getByTestId('rf__wrapper').should('exist');
        cy.getByTestId('rf__wrapper').rightclick(10, 100);
        diagram.getPalette().should('exist');

        // The 'Composite Processor' tool (shortcut) is *not* initially visible
        diagram.getPalette().getByTestId('tool-Composite Processor').should('not.exist');
        // We need to go into the Creation Tools section to find and invoke it
        diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');
        diagram.getPalette().getByTestId('toolSection-Creation Tools').click();
        diagram.getPalette().getByTestId('tool-Composite Processor').should('exist');
        diagram.getPalette().getByTestId('tool-Composite Processor').click();

        // The palette closes
        diagram.getPalette().should('not.exist');
        // And the newly created element appears and is selected (incl. in the Explorer)
        diagram.getNodes('diagram', 'CompositeProcessor3').should('exist');
        diagram.getSelectedNodes('diagram', 'CompositeProcessor3');
        new Explorer().getSelectedTreeItems().contains('CompositeProcessor3');

        // Now we re-open the diagram's palette
        cy.getByTestId('rf__wrapper').should('exist');
        cy.getByTestId('rf__wrapper').rightclick(10, 200);

        // and expect the 'Composite Processor' tool' shortcut to be visible directly
        diagram.getPalette().should('exist');
        diagram.getPalette().getByTestId('tool-Composite Processor').should('exist');
        diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');

        // Close the diagram palette
        cy.getByTestId('rf__wrapper').click(50, 50);
        diagram.getPalette().should('not.exist');

        // Open the new composite processor's palette
        diagram.getNodes('diagram', 'CompositeProcessor3').rightclick();
        diagram.getPalette().should('exist');
        // the "last tool" shortcut should *not* be visible in the other context
        diagram.getPalette().getByTestId('tool-Composite Processor').should('not.exist');
        diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');
      });

      it.skip('After invoking a tool on the diagram, alt-click repeats the same tool', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        diagram.getDiagram('diagram').should('exist');

        // Open the diagram's palette
        cy.getByTestId('rf__wrapper').should('exist');
        cy.getByTestId('rf__wrapper').rightclick(10, 100);
        diagram.getPalette().should('exist');
        diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');
        diagram.getPalette().getByTestId('toolSection-Creation Tools').click();
        diagram.getPalette().getByTestId('tool-Composite Processor').should('exist');
        // Create new composite processor at (10, 100)
        diagram.getPalette().getByTestId('tool-Composite Processor').click();
        diagram.getPalette().should('not.exist');

        diagram.getNodes('diagram', 'CompositeProcessor3').should('exist');
        diagram.getSelectedNodes('diagram', 'CompositeProcessor3');
        explorer.getSelectedTreeItems().contains('CompositeProcessor3');

        // Close the diagram palette
        cy.getByTestId('rf__wrapper').click(10, 200);
        diagram.getPalette().should('not.exist');

        // FIXME Simulate alt-click on the diagram at (10, 200)
        cy.getByTestId('rf__wrapper').focus();
        cy.getByTestId('rf__wrapper').type('{alt}', { release: false });
        cy.getByTestId('rf__wrapper').trigger('click', 10, 200, { altKey: true, force: true });

        // The palette closes
        diagram.getPalette().should('not.exist');
        // And the newly created element appears and is selected (incl. in the Explorer)
        diagram.getNodes('diagram', 'CompositeProcessor4').should('exist');
        diagram.getSelectedNodes('diagram', 'CompositeProcessor4');
        new Explorer().getSelectedTreeItems().contains('CompositeProcessor4');
      });
    });
  });
});
