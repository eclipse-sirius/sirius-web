/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

describe('Diagram - edges', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        new Diagram().disableFitView();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.selectRepresentation('Topography');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Check bend points are available when selecting an edge', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('DataSource1');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      explorer.select('standard');
      cy.getByTestId('bend-point-0').should('exist');
      cy.getByTestId('bend-point-1').should('exist');
      cy.getByTestId('temporary-moving-line-1').should('exist');
    });

    it('Check no new bend point is created when simply clicking on a bendpoint', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('DataSource1');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      explorer.select('standard');
      cy.getByTestId('bend-point-2').should('exist');
      cy.getByTestId('bend-point-2').click();
      cy.getByTestId('bend-point-4').should('not.exist');
    });

    it('Check that when reconnecting an edge, the style of the handle is updated and can be reseted with a tool', () => {
      const diagram = new Diagram();

      // We select a node before an edge to avoid a known issue with selection
      diagram.selectNode('Topography', 'DataSource1');
      // eslint-disable-next-line cypress/no-unnecessary-waiting
      cy.wait(300);

      cy.get('.react-flow__edge:first').as('edge').click({ force: true });
      cy.get('.react-flow__edge:first').should('have.class', 'selected');

      diagram
        .getNodes('Topography', 'DataSource1')
        .get('.react-flow__handle.react-flow__handle-right:first')
        .should('have.css', 'background-color')
        .and('eq', 'rgb(0, 0, 0)');

      diagram.moveSourceHandleToLeftPosition(cy.get('@edge'), 'Topography', 'DataSource1');

      // eslint-disable-next-line cypress/no-unnecessary-waiting
      cy.wait(300);
      cy.get('.react-flow__edge:first').click({ force: true });

      diagram
        .getNodes('Topography', 'DataSource1')
        .get('.react-flow__handle.react-flow__handle-left:first')
        .should('have.css', 'background-color')
        .and('eq', 'rgb(255, 255, 255)');

      cy.get('.react-flow__edge:first').rightclick({ force: true });
      cy.getByTestId('Reset-handles').should('exist');
      cy.getByTestId('Reset-handles').click();

      diagram
        .getNodes('Topography', 'DataSource1')
        .get('.react-flow__handle.react-flow__handle-right:first')
        .should('have.css', 'background-color')
        .and('eq', 'rgb(0, 0, 0)');
    });

    it('Then moving source segment change the source handle position', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('DataSource1');
      diagram.getNodes('Topography', 'CompositeProcessor1').should('exist');
      diagram.moveNode('Topography', 'CompositeProcessor1', { x: 100, y: 0 });
      explorer.select('standard');
      diagram
        .getEdgePaths('Topography')
        .eq(0)
        .invoke('attr', 'd')
        .then((dValue) => {
          expect(diagram.isPathWithinTolerance(diagram.roundSvgPathData(dValue ?? ''), 'M66L129L129L192', 2)).to.be
            .true;
        });
      // eslint-disable-next-line cypress/no-unnecessary-waiting
      cy.wait(500);
      diagram.moveEdgeSegment(0, { x: 0, y: 200 });
      diagram
        .getEdgePaths('Topography')
        .eq(0)
        .invoke('attr', 'd')
        .then((dValue) => {
          expect(diagram.isPathWithinTolerance(diagram.roundSvgPathData(dValue ?? ''), 'M66L66L129L129L192', 2)).to.be
            .true;
        });
    });

    it('Then moving target segment change the target handle position', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('DataSource1');
      diagram.getNodes('Topography', 'CompositeProcessor1').should('exist');
      diagram.moveNode('Topography', 'CompositeProcessor1', { x: 100, y: 0 });
      explorer.select('standard');
      diagram
        .getEdgePaths('Topography')
        .eq(0)
        .invoke('attr', 'd')
        .then((dValue) => {
          expect(diagram.isPathWithinTolerance(diagram.roundSvgPathData(dValue ?? ''), 'M66L129L129L192', 2)).to.be
            .true;
        });
      // eslint-disable-next-line cypress/no-unnecessary-waiting
      cy.wait(500);
      diagram.moveEdgeSegment(2, { x: 0, y: 300 });
      diagram
        .getEdgePaths('Topography')
        .eq(0)
        .invoke('attr', 'd')
        .then((dValue) => {
          expect(diagram.isPathWithinTolerance(diagram.roundSvgPathData(dValue ?? ''), 'M66L129L129L193L192', 2)).to.be
            .true;
        });
    });
  });
});
