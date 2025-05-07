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

    context('When opening the manage visibility menu on a parent node', () => {
      it('Then it s possible to hide and reveal all children from the main checkbox', () => {
        const diagram = new Diagram();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
        // Open manage visibility modal
        diagram.getNodes('diagram', 'Central_Unit').should('exist').trigger('mouseover', 'topRight');
        cy.getByTestId('manage-visibility').should('exist').click();
        // Hide all subnodes
        cy.getByTestId('manage_visibility_checkbox').should('exist').click();
        diagram.getNodes('diagram', 'DSP').should('not.exist');
        diagram.getNodes('diagram', 'Motion_Engine').should('not.exist');
        diagram.getNodes('diagram', '100').should('not.exist');
        // reveal all subnodes
        cy.getByTestId('manage_visibility_checkbox').should('exist').click();
        diagram.getNodes('diagram', 'DSP').should('exist');
        diagram.getNodes('diagram', 'Motion_Engine').should('exist');
        diagram.getNodes('diagram', '100').should('exist');
      });

      it('Then it s possible to hide and reveal all children from the main menu', () => {
        const diagram = new Diagram();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
        // Open manage visibility modal
        diagram.getNodes('diagram', 'Central_Unit').should('exist').trigger('mouseover', 'topRight');
        cy.getByTestId('manage-visibility').should('exist').click();
        // Open manage visibility menu
        cy.getByTestId('manage_visibility_menu').should('exist').click();
        // Hide all subnodes
        cy.getByTestId('manage_visibility_menu_hide_all_action').should('exist').click();
        diagram.getNodes('diagram', 'DSP').should('not.exist');
        diagram.getNodes('diagram', 'Motion_Engine').should('not.exist');
        diagram.getNodes('diagram', '100').should('not.exist');
        // reveal all subnodes
        cy.getByTestId('manage_visibility_menu_reveal_all_action').should('exist').click();
        diagram.getNodes('diagram', 'DSP').should('exist');
        diagram.getNodes('diagram', 'Motion_Engine').should('exist');
        diagram.getNodes('diagram', '100').should('exist');
      });

      it('Then it s possible to hide and reveal a specific children', () => {
        const diagram = new Diagram();
        // no element is selected in the diagram
        diagram.getDiagram('diagram').should('exist');
        diagram.getDiagram('diagram').get('div.react-flow__node.selected').should('not.exist');
        // wait for the fit to screen
        cy.get('@consoleDebug').should('be.calledWith', 'fit-to-screen has been performed:true');
        // Open manage visibility modal
        diagram.getNodes('diagram', 'Central_Unit').should('exist').trigger('mouseover', 'topRight');
        cy.getByTestId('manage-visibility').should('exist').click();
        // Hide all DSP subnode
        cy.getByTestId('manage_visibility_list_item_button_DSP').should('exist').click();
        diagram.getNodes('diagram', 'DSP').should('not.exist');
        // reveal DSP subnode
        cy.getByTestId('manage_visibility_list_item_button_DSP').should('exist').click();
        diagram.getNodes('diagram', 'DSP').should('exist');
      });
    });
  });
});
