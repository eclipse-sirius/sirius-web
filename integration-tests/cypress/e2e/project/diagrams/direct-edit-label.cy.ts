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

describe('Diagram - edit node label', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        new Diagram().disableFitView();
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.selectRepresentation('Topography');
        new Diagram().centerViewport();
      });
    });

    it('Then we can use direct edit on a node', () => {
      const diagram = new Diagram();
      //Select a node
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.getNodes('Topography', 'DataSource1').click();
      //Trigger direct edit with f2
      cy.focused().trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
      cy.getByTestId('name-edit').should('exist');
      diagram.getPalette().should('not.exist');
      //Edit
      cy.getByTestId('name-edit').find('textarea').should('have.value', 'DataSource1');
      cy.getByTestId('name-edit').type('Edited{enter}', { delay: 10 });
      cy.getByTestId('name-edit').should('not.exist');
      diagram.getNodes('Topography', 'Edited').should('exist');
    });

    it('Then we can use direct edit on a node by typing', () => {
      const diagram = new Diagram();
      //Select a node
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.getNodes('Topography', 'DataSource1').click();
      //Edit
      cy.focused().click().type('Edited{enter}');
      cy.getByTestId('name-edit').should('not.exist');
      diagram.getNodes('Topography', 'Edited').should('exist');
    });

    it('Then during edit triggering escape cancel the current edition', () => {
      const diagram = new Diagram();
      //Select a node
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.getNodes('Topography', 'DataSource1').click();
      //Trigger direct edit with f2
      cy.focused().trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
      cy.getByTestId('name-edit').should('exist');
      diagram.getPalette().should('not.exist');
      //Cancel direct edit with escp
      cy.getByTestId('name-edit').find('textarea').should('have.value', 'DataSource1');
      cy.getByTestId('name-edit').type('test{esc}');
      cy.getByTestId('name-edit').should('not.exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
    });

    it('Then we can use direct edit on a node even if the palette is opened', () => {
      const diagram = new Diagram();
      //Select a node
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.getNodes('Topography', 'DataSource1').click();
      //Open the palette
      diagram.getNodes('Topography', 'DataSource1').rightclick();
      diagram.getPalette().should('exist');
      //Trigger direct edit with f2
      cy.focused().trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
      cy.getByTestId('name-edit').should('exist');
      diagram.getPalette().should('not.exist');
      //Edit
      cy.getByTestId('name-edit').find('textarea').should('have.value', 'DataSource1');
      cy.getByTestId('name-edit').type('Edited{enter}');
      cy.getByTestId('name-edit').should('not.exist');
      diagram.getNodes('Topography', 'Edited').should('exist');
    });

    it('Then we can use direct edit on a node even after closing the palette', () => {
      const diagram = new Diagram();
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
      //Trigger direct edit with f2
      cy.focused().trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
      cy.getByTestId('name-edit').should('exist');
      diagram.getPalette().should('not.exist');
      //Edit
      cy.getByTestId('name-edit').find('textarea').should('have.value', 'DataSource1');
      cy.getByTestId('name-edit').type('Edited{enter}');
      cy.getByTestId('name-edit').should('not.exist');
      diagram.getNodes('Topography', 'Edited').should('exist');
    });

    it('Then we can use direct after creating a new node', () => {
      const diagram = new Diagram();
      //Open the palette
      cy.getByTestId('rf__wrapper').should('exist').rightclick(100, 800).rightclick(100, 800);
      diagram.getPalette().should('exist');
      //Create a new node
      diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');
      diagram.getPalette().getByTestId('toolSection-Creation Tools').click();
      diagram.getPalette().getByTestId('tool-Composite Processor').should('exist');
      diagram.getPalette().getByTestId('tool-Composite Processor').click();
      diagram.getNodes('Topography', 'CompositeProcessor2').should('exist');
      //Trigger direct edit with f2
      diagram.getSelectedNodes('Topography', 'CompositeProcessor2').should('exist');
      cy.focused().trigger('keydown', { altKey: true, keyCode: 113, which: 113 }); // key code for F2
      cy.getByTestId('name-edit').should('exist');
      diagram.getPalette().should('not.exist');
      //Edit
      cy.getByTestId('name-edit').find('textarea').should('have.value', 'CompositeProcessor2');
      cy.getByTestId('name-edit').type('Edited2{enter}');
      cy.getByTestId('name-edit').should('not.exist');
      diagram.getNodes('Topography', 'Edited2').should('exist');
    });

    it('Then we can use direct by typing after creating a new node', () => {
      const diagram = new Diagram();
      //Open the palette
      cy.getByTestId('rf__wrapper').should('exist').rightclick(100, 800).rightclick(100, 800);
      diagram.getPalette().should('exist');
      //Create a new node
      diagram.getPalette().getByTestId('toolSection-Creation Tools').should('exist');
      diagram.getPalette().getByTestId('toolSection-Creation Tools').click();
      diagram.getPalette().getByTestId('tool-Composite Processor').should('exist');
      diagram.getPalette().getByTestId('tool-Composite Processor').click();
      diagram.getNodes('Topography', 'CompositeProcessor2').should('exist');
      //Edit
      diagram.getSelectedNodes('Topography', 'CompositeProcessor2').should('exist');
      cy.focused().type('Edited2{enter}');
      cy.getByTestId('name-edit').should('not.exist');
      diagram.getNodes('Topography', 'Edited2').should('exist');
    });

    afterEach(() => {
      cy.deleteProject(projectId);
    });
  });
});
