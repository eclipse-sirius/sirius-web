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
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - edges creation', () => {
  const domainName: string = 'diagramEdges';
  context('Given a view with a 2 nodes and several edge creation tools', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - simple edges`;
      const diagramTitle = 'Simple edges';

      beforeEach(() => {
        const studio = new Studio();
        const explorer = new Explorer();
        const details = new Details();
        new Diagram().disableFitView();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('Entity1{enter}');
        explorer.createObject('Root', 'entity2s-Entity2');
        details.getTextField('Name').type('Entity2{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then we can create an edge between 2 nodes with the handle auto layouted', () => {
        const diagram = new Diagram();
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(300);
        diagram.createEdgeFromTopHandleToCenterNode(diagramTitle, 'Entity1', 'Entity2');

        cy.getByTestId('connectorContextualMenu-E1toE2A').should('exist');
        cy.getByTestId('connectorContextualMenu-E1toE2B').should('exist');
        cy.getByTestId('connectorContextualMenu-E1toE2A').click();

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(500);
        cy.get('.react-flow__edge:first').should('exist');

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(300);
        cy.get('.react-flow__edge:first').click({ force: true });
        cy.get('.react-flow__edge:first').should('have.class', 'selected');

        diagram
          .getNodes(diagramTitle, 'Entity2')
          .get('.react-flow__handle.react-flow__handle-left:first')
          .should('have.css', 'background-color')
          .and('eq', 'rgb(0, 0, 0)');
      });

      it('Then we can create an edge between 2 nodes with the handle manualy positioned and reconnect it to the middle of the node to swithc to auto layout for the handle', () => {
        const diagram = new Diagram();
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(300);

        diagram.createEdgeFromTopHandleToBottomSideNode(diagramTitle, 'Entity1', 'Entity2');

        cy.getByTestId('connectorContextualMenu-E1toE2A').should('exist');
        cy.getByTestId('connectorContextualMenu-E1toE2B').should('exist');
        cy.getByTestId('connectorContextualMenu-E1toE2A').click();

        cy.get('.react-flow__edge:first').should('exist');

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(300);
        cy.get('.react-flow__edge:first').as('edge').click({ force: true });
        cy.get('.react-flow__edge:first').should('have.class', 'selected');

        diagram
          .getNodes(diagramTitle, 'Entity2')
          .get('.react-flow__handle.react-flow__handle-bottom:first')
          .should('have.css', 'background-color')
          .and('eq', 'rgb(255, 255, 255)');

        diagram.moveSelectedEdgeTargetHandleToMiddleOfTheNode(diagramTitle, 'Entity2');

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(300);
        cy.get('.react-flow__edge:first').should('have.class', 'selected');

        diagram
          .getNodes(diagramTitle, 'Entity2')
          .get('.react-flow__handle.react-flow__handle-left:first')
          .should('have.css', 'background-color')
          .and('eq', 'rgb(0, 0, 0)');
      });
    });
  });
});
