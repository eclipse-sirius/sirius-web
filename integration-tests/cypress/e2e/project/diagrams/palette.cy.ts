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
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('robot');
      explorer.createRepresentation('System', 'Topography', 'diagram');
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
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
        // right click on the diagram background
        // workaround: the first right-click doesn't seem catch by Cypress, the second one does
        cy.getByTestId('rf__wrapper').should('exist').rightclick(100, 100).rightclick(100, 100);
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
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
        // right click on the edge between Wifi and DSP (with label '4')
        // workaround: the first right-click doesn't seem catch by Cypress, the second one does
        cy.getByTestId('Label - 4').should('exist').rightclick({ force: true }).rightclick({ force: true });
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
        // wait for the fit to screen
        diagram.getDiagram('diagram').should('exist');
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        diagram.getDiagram('diagram').should('exist');
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
        // Wifi element is selected in the diagram
        diagram.getNodes('diagram', 'Wifi').should('exist').click();
        diagram.getSelectedNodes('diagram', 'Wifi');
        // right click on the diagram background
        // workaround: the first right-click doesn't seem catch by Cypress, the second one does
        cy.getByTestId('rf__wrapper').should('exist').rightclick(100, 100).rightclick(100, 100);
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
        // wait for the fit to screen
        diagram.getDiagram('diagram').should('exist');
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        diagram.getDiagram('diagram').should('exist');
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        diagram.getDiagram('diagram').should('exist');
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
        // wait for the fit to screen
        diagram.getDiagram('diagram').should('exist');
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
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
  });
});
