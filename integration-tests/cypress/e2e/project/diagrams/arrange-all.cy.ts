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
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - arrange all', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.disableDeletionConfirmationDialog();
        new Diagram().disableFitView();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.selectRepresentation('Topography');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Check arrange all do not marked node as resizedByUser', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('CompositeProcessor1');
      explorer.rename('Processor1', 'CPU');
      diagram.getNodes('Topography', 'CPU').should('exist');
      diagram.arrangeAll();
      let nodeHeight: number;
      diagram.getNodes('Topography', 'CompositeProcessor1').then(($node) => {
        nodeHeight = $node.height() ?? 0;
      });
      explorer.delete('CPU');
      diagram.getNodes('Topography', 'CPU').should('not.exist');
      //CompositeProcessor should be resized to its empty size
      diagram.getNodes('Topography', 'CompositeProcessor1').then(($node) => {
        expect(nodeHeight).to.be.greaterThan(Math.trunc($node.height() ?? 0));
      });
    });

    it('Check loading indicator is present during arrange action', () => {
      cy.getByTestId('arrange-all').click();
      cy.getByTestId('arrange-all-circular-loading').should('exist');
    });
  });
  context('Given a view with edge on border node', () => {
    const domainName: string = 'diagramEdgeOnBorderNode';
    let instanceProjectId: string = '';
    const diagramDescriptionName = `${domainName} - simple edge on border node`;
    const diagramTitle = 'Simple edge on border node';

    beforeEach(() => {
      const studio = new Studio();
      const explorer = new Explorer();
      const details = new Details();
      studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
        instanceProjectId = res.projectId;
      });
      new Diagram().disableFitView();

      explorer.createObject('Root', 'entity1s-Entity1');
      details.getTextField('Name').should('have.value', '');
      details.getTextField('Name').type('parent{enter}');
      explorer.createObject('Root', 'entity1s-Entity1');
      details.getTextField('Name').type('A{enter}');
      explorer.createObject('Root', 'entity2s-Entity2');
      details.getTextField('Name').should('have.value', '');
      details.getTextField('Name').type('B{enter}');
      explorer.createObject('parent', 'entity1ContainEntity2-Entity2');
      details.getTextField('Name').should('have.value', '');
      details.getTextField('Name').type('C{enter}');
      explorer.createObject('parent', 'entity1ContainEntity1-Entity1');
      details.getTextField('Name').should('have.value', '');
      details.getTextField('Name').type('D{enter}');
      explorer.select('A');
      details.getTextField('Name').should('have.value', 'A');
      details.getReferenceWidget('Entity1 Linked To Entity2').should('exist');
      details.openReferenceWidgetOptions('Entity1 Linked To Entity2');
      details.selectReferenceWidgetOption('C');
      explorer.select('D');
      details.getTextField('Name').should('have.value', 'D');
      details.getReferenceWidget('Entity1 Linked To Entity2').should('exist');
      details.openReferenceWidgetOptions('Entity1 Linked To Entity2');
      details.selectReferenceWidgetOption('B');
      new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
      new Diagram().centerViewport();
    });

    afterEach(() => cy.deleteProject(instanceProjectId));

    it('Check arrange all take into account the edges', () => {
      const diagram = new Diagram();
      // eslint-disable-next-line cypress/no-unnecessary-waiting
      cy.wait(500);
      diagram.getNodes(diagramTitle, 'A').should('exist');
      diagram.getNodes(diagramTitle, 'B').should('exist');
      diagram.getNodes(diagramTitle, 'parent').should('exist');
      diagram.arrangeAll();
      let positionLeftA: number,
        positionTopA: number,
        positionLeftB: number,
        positionTopB: number,
        positionLeftParent: number,
        positionTopParent: number;
      diagram.getNodes(diagramTitle, 'A').then(($el) => {
        const rect = $el[0]?.getBoundingClientRect();
        positionLeftA = rect?.left ?? 0;
        positionTopA = rect?.top ?? 0;
      });
      diagram.getNodes(diagramTitle, 'B').then(($el) => {
        const rect = $el[0]?.getBoundingClientRect();
        positionLeftB = rect?.left ?? 0;
        positionTopB = rect?.top ?? 0;
      });

      diagram.getNodes(diagramTitle, 'parent').then(($el) => {
        const rect = $el[0]?.getBoundingClientRect();
        positionLeftParent = rect?.left ?? 0;
        positionTopParent = rect?.top ?? 0;
        // verify layout order: nodeA > nodeParent > nodeB
        expect(positionLeftB).to.be.greaterThan(positionLeftParent);
        expect(positionLeftParent).to.be.greaterThan(positionLeftA);
        expect(positionTopA).to.be.greaterThan(positionTopParent);
        expect(positionTopB).to.be.greaterThan(positionTopParent);
      });
    });
  });
});
