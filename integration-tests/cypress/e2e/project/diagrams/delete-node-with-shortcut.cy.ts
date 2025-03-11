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
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - del node', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.selectRepresentation('Topography');
      });
    });

    it('Then we can use del key to delete a node', () => {
      const diagram = new Diagram();
      //Wait for the fit to screen
      cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
      //Select a node
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.getNodes('Topography', 'DataSource1').click();
      //Trigger delete
      cy.focused().type('{del}');
      diagram.getPalette().should('not.exist');
      diagram.getNodes('Topography', 'DataSource1').should('not.exist');
    });

    it('Then we can t use del key to delete a node if the palette is opened', () => {
      const diagram = new Diagram();
      //Wait for the fit to screen
      cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
      //Select a node
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.getNodes('Topography', 'DataSource1').click();
      //Open the palette
      diagram.getNodes('Topography', 'DataSource1').rightclick();
      diagram.getPalette().should('exist');
      //Trigger delete
      cy.focused().type('{del}');
      diagram.getPalette().should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
    });

    it('Then we can use del key to delete a node after closing the palette with escp', () => {
      const diagram = new Diagram();
      //Wait for the fit to screen
      cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
      //Select a node
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.getNodes('Topography', 'DataSource1').click();
      //Open the palette
      diagram.getNodes('Topography', 'DataSource1').rightclick();
      diagram.getPalette().should('exist');
      //Close the palette
      diagram.getPalette().type('test{esc}');
      diagram.getPalette().should('not.exist');
      //Trigger delete
      cy.focused().type('{del}');
      diagram.getNodes('Topography', 'DataSource1').should('not.exist');
    });

    it('Then we can use del key to delete a node after it has been created', () => {
      const diagram = new Diagram();
      // wait for the fit to screen
      cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
      //Open the palette
      cy.getByTestId('rf__wrapper').should('exist').rightclick(100, 800).rightclick(100, 800);
      diagram.getPalette().should('exist');
      //Create a new node
      diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');
      diagram.getPalette().getByTestId('toolSection-Creation Tools').click();
      diagram.getPalette().getByTestId('tool-Composite Processor').should('exist');
      diagram.getPalette().getByTestId('tool-Composite Processor').click();
      diagram.getNodes('Topography', 'CompositeProcessor2').should('exist');
      cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
      diagram.getSelectedNodes('Topography', 'CompositeProcessor2').should('exist');
      //Trigger delete
      cy.focused().type('{del}');
      diagram.getNodes('Topography', 'CompositeProcessor2').should('not.exist');
    });

    afterEach(() => {
      cy.deleteProject(projectId);
    });
  });
});
