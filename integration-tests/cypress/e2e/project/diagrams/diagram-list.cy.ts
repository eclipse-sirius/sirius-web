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
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - list', () => {
  const domainName: string = 'diagramList';
  context('Given a view with a simple list node', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - simple list node`;
      const diagramTitle = 'Simple list node';

      beforeEach(() => {
        const studio = new Studio();
        const explorer = new Explorer();
        const details = new Details();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('list{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the node is correctly displayed with the proper separator', () => {
        const diagram = new Diagram();
        diagram.fitToScreen();
        // Compartment list without label
        diagram
          .getDiagram(diagramTitle)
          .findByTestId('List - undefined')
          .should('exist')
          .invoke('attr', 'style')
          .then((style) => {
            expect(style).to.contain('border-width: 0px 0px 1px');
          });
        // Compartment freeform
        diagram
          .getDiagram(diagramTitle)
          .findByTestId('FreeForm - list')
          .should('exist')
          .invoke('css', 'border-width')
          .then((borderWidth) => {
            expect(borderWidth).to.eq('0px');
          });
      });

      it('Then after width resizing, child nodes conserve their width', () => {
        const diagram = new Diagram();
        diagram.getNodes(diagramTitle, 'list').should('exist');
        diagram.fitToScreen();
        diagram.zoomOut();

        diagram.selectNode(diagramTitle, 'list');
        let initialLeft: number, initialTop: number, initialWidth: number, initialHeight: number;

        diagram
          .getDiagram(diagramTitle)
          .findByTestId('List - undefined')
          .then(($el) => {
            const rect = $el[0]?.getBoundingClientRect();
            initialLeft = rect?.left ?? 0;
            initialTop = rect?.top ?? 0;
            initialWidth = rect?.width ?? 0;
            initialHeight = rect?.height ?? 0;
          });

        diagram.resizeNode('bottom.right', { x: 50, y: 0 });

        diagram
          .getDiagram(diagramTitle)
          .findByTestId('List - undefined')
          .then(($el) => {
            const rect = $el[0]?.getBoundingClientRect();
            expect(rect?.left).to.equal(initialLeft);
            expect(rect?.top).to.equal(initialTop);
            expect(rect?.width).to.approximately(initialWidth + 50, 5);
            expect(rect?.height).to.equal(initialHeight);
          });
      });

      it('Then child node are correctly initialized to respect growable nodes', () => {
        const diagram = new Diagram();
        diagram.fitToScreen();
        // Compartment list without label
        diagram
          .getDiagram(diagramTitle)
          .findByTestId('List - undefined')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(265, 5);
          });
        // Compartment freeform
        diagram
          .getDiagram(diagramTitle)
          .findByTestId('FreeForm - list')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(486, 5);
          });
      });

      it('Then after height resizing, growable node should grow', () => {
        const diagram = new Diagram();
        diagram.getNodes(diagramTitle, 'list').should('exist');
        diagram.fitToScreen();
        diagram.zoomOut();
        diagram.zoomOut();

        diagram.selectNode(diagramTitle, 'list');
        let initialWidthList: number,
          initialHeightList: number,
          initialWidthFreeForm: number,
          initialHeightFreeForm: number;

        diagram
          .getDiagram(diagramTitle)
          .findByTestId('List - undefined')
          .then(($el) => {
            const rect = $el[0]?.getBoundingClientRect();
            initialWidthList = rect?.width ?? 0;
            initialHeightList = rect?.height ?? 0;
          });

        diagram
          .getDiagram(diagramTitle)
          .findByTestId('FreeForm - list')
          .then(($el) => {
            const rect = $el[0]?.getBoundingClientRect();
            initialWidthFreeForm = rect?.width ?? 0;
            initialHeightFreeForm = rect?.height ?? 0;
          });

        diagram.resizeNode('bottom.right', { x: 0, y: 50 });
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(1000); //Wait for animation
        diagram
          .getDiagram(diagramTitle)
          .findByTestId('List - undefined')
          .then(($el) => {
            const rect = $el[0]?.getBoundingClientRect();
            expect(rect?.width).to.equal(initialWidthList);
            expect(rect?.height).to.equal(initialHeightList);
          });

        diagram
          .getDiagram(diagramTitle)
          .findByTestId('FreeForm - list')
          .then(($el) => {
            const rect = $el[0]?.getBoundingClientRect();
            expect(rect?.width).to.equal(initialWidthFreeForm);
            expect(rect?.height).to.approximately(initialHeightFreeForm + 50, 5);
          });
      });
    });
  });
});
