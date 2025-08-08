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
      new Diagram().centerViewport();
    });

    afterEach(() => cy.deleteProject(projectId));
    context('When nothing is selected in the diagram', () => {
      it('Then a left-click on the diagram should do nothing. The diagram is selected in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // left click on the diagram background
        cy.getByTestId('rf__wrapper').should('exist').click(100, 100);
        // the palette is closed
        diagram.getPalette().should('not.exist');
        // the diagram is selected in the Explorer
        explorer.getSelectedTreeItems().contains('diagram');
        // the diagram is selected in the Details
        details.getDetailsView().getByTestId('page-tab-diagram').should('exist');
      });
      it('Then a right-click on the diagram should open the diagram palette. The diagram is selected in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // right click on the diagram background
        cy.getByTestId('rf__wrapper').should('exist');
        cy.getByTestId('rf__wrapper').rightclick(100, 100);
        // the palette is opened
        diagram.getPalette().should('exist');
        // the diagram is selected in the Explorer
        explorer.getSelectedTreeItems().contains('diagram');
        // the diagram is selected in the Details
        details.getDetailsView().getByTestId('page-tab-diagram').should('exist');
      });
      it('Then a left-click on a node should select the element in the diagram and in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // left click on the Wifi node
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        // the palette is closed
        diagram.getPalette().should('not.exist');
        // the Wifi element is selected in the diagram
        diagram.getSelectedNodes('diagram', 'Wifi');
        // the Wifi element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('Wifi');
        // the Wifi element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-Wifi').should('exist');
      });
      it('Then a right-click on a node should select the element in the diagram and in the Explorer/Details views. It should also open the element palette.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // right click on the Wifi node
        // workaround: the first right-click doesn't seem catch by Cypress, the second one does
        diagram.getNodes('diagram', 'Wifi').should('exist').rightclick().rightclick();
        // the palette is opened
        diagram.getPalette().should('exist');
        // the Wifi element is selected in the diagram
        diagram.getSelectedNodes('diagram', 'Wifi');
        // the Wifi element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('Wifi');
        // the Wifi element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-Wifi').should('exist');
      });
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
      it('Then a right-click on an edge should select the element in the diagram and in the Explorer/Details views. It should also open the element palette.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // right click on the edge between Wifi and DSP (with label '4')
        // workaround: the first right-click doesn't seem catch by Cypress, the second one does
        cy.getByTestId('Label - 4').should('exist');
        cy.getByTestId('Label - 4').rightclick({ force: true });
        // the palette is opened
        diagram.getPalette().should('exist');
        // the edge between Wifi and DSP element is selected in the diagram
        diagram.getSelectedEdge('diagram', '4').should('exist');
        // the standard element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('standard');
        // the standard element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-standard').should('exist');
      });
    });
    context('When a node is selected in the diagram', () => {
      it('Then a left-click on the diagram should unselect the node. Nothing is selected in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        diagram.getDiagram('diagram').should('exist');
        // Wifi element is selected in the diagram
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        diagram.getSelectedNodes('diagram', 'Wifi');
        // left click on the diagram background
        cy.getByTestId('rf__wrapper').should('exist').click(100, 100);
        // the palette is closed
        diagram.getPalette().should('not.exist');
        // Nothing is selected in the Explorer (should probably be the diagram tree item but that is another problem that should be fixed separately)
        explorer.getSelectedTreeItems().should('have.length', 0);
        // Nothing is selected in the Details (should probably be the diagram properties but that is another problem that should be fixed separately)
        details.getDetailsView().find('h6').should('have.text', 'No object selected');
      });
      it('Then a right-click on the diagram should unselect the node. The diagram is selected in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        diagram.getDiagram('diagram').should('exist');
        // Wifi element is selected in the diagram
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        diagram.getSelectedNodes('diagram', 'Wifi');
        // right click on the diagram background
        cy.getByTestId('rf__wrapper').should('exist');
        cy.getByTestId('rf__wrapper').rightclick(100, 100);
        // the palette is closed
        diagram.getPalette().should('exist');
        // the diagram is selected in the Explorer
        explorer.getSelectedTreeItems().contains('diagram');
        // the diagram is selected in the Details
        details.getDetailsView().getByTestId('page-tab-diagram').should('exist');
      });
      it('Then a left-click on the same node should do nothing. The selection stays the same in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        diagram.getDiagram('diagram').should('exist');
        // Wifi element is selected in the diagram
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        diagram.getSelectedNodes('diagram', 'Wifi');
        // left click on the Wifi node
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        // the palette is closed
        diagram.getPalette().should('not.exist');
        // the Wifi element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('Wifi');
        // the Wifi element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-Wifi').should('exist');
      });
      it('Then a right-click on the same node should open the palette. The selection stays the same in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        diagram.getDiagram('diagram').should('exist');
        // Wifi element is selected in the diagram
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        diagram.getSelectedNodes('diagram', 'Wifi');
        // right click on the Wifi node
        diagram.getNodes('diagram', 'Wifi').should('exist').rightclick();
        // the palette is opened
        diagram.getPalette().should('exist');
        // the Wifi element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('Wifi');
        // the Wifi element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-Wifi').should('exist');
      });
      it('Then a left-click on another node should select this another node. The semantic element associated to this another node is selected in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        diagram.getDiagram('diagram').should('exist');
        // Wifi element is selected in the diagram
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        diagram.getSelectedNodes('diagram', 'Wifi');
        // left click on the GPU node
        diagram.getNodes('diagram', 'GPU').should('exist').click();
        // the palette is closed
        diagram.getPalette().should('not.exist');
        // the Wifi element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('GPU');
        // the Wifi element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-GPU').should('exist');
      });
      it('Then a right-click on another node should select this another node and open the palette. The semantic element associated to this another node is selected in the Explorer/Details views.', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        const details = new Details();
        diagram.getDiagram('diagram').should('exist');
        // Wifi element is selected in the diagram
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        diagram.getSelectedNodes('diagram', 'Wifi');
        // right click on the GPU node
        diagram.getNodes('diagram', 'GPU').should('exist').rightclick();
        // the palette is closed
        diagram.getPalette().should('exist');
        // the Wifi element is selected in the Explorer
        explorer.getSelectedTreeItems().contains('GPU');
        // the Wifi element is selected in the Details
        details.getDetailsView().getByTestId('page-tab-GPU').should('exist');
      });
    });
    context('After creating a composite processor on the diagram', () => {
      it('The diagram palette should remember the last tool invoked from it', () => {
        const diagram = new Diagram();
        diagram.getDiagram('diagram').should('exist');

        // Open the diagram's palette
        cy.getByTestId('rf__wrapper').should('exist');
        cy.getByTestId('rf__wrapper').rightclick(100, 100);
        diagram.getPalette().should('exist');

        // The 'Composite Processor' tool (shortcut) is *not* intialy visible
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
        cy.getByTestId('rf__wrapper').rightclick(100, 100);

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
        cy.getByTestId('rf__wrapper').rightclick(100, 100);
        diagram.getPalette().should('exist');
        diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');
        diagram.getPalette().getByTestId('toolSection-Creation Tools').click();
        diagram.getPalette().getByTestId('tool-Composite Processor').should('exist');
        diagram.getPalette().getByTestId('tool-Composite Processor').click();
        diagram.getPalette().should('not.exist');

        diagram.getNodes('diagram', 'CompositeProcessor3').should('exist');
        diagram.getSelectedNodes('diagram', 'CompositeProcessor3');
        explorer.getSelectedTreeItems().contains('CompositeProcessor3');

        // Close the diagram palette
        cy.getByTestId('rf__wrapper').click(50, 50);
        diagram.getPalette().should('not.exist');

        cy.getByTestId('rf__wrapper').focus();
        cy.getByTestId('rf__wrapper').trigger('click', 100, 100, {
          force: true,
          altKey: true,
        });

        // The palette closes
        diagram.getPalette().should('not.exist');
        // And the newly created element appears and is selected (incl. in the Explorer)
        diagram.getNodes('diagram', 'CompositeProcessor4').should('exist');
        diagram.getSelectedNodes('diagram', 'CompositeProcessor4');
        new Explorer().getSelectedTreeItems().contains('CompositeProcessor4');
      });
      it.skip('Then opening the palette on the composite processor does not show the last tool shortcut', () => {});
      it.skip('Then alt-click on the diagram creates a second composite processor', () => {});
    });
  });
});
