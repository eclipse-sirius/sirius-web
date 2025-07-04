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

describe('Diagram - growable list', () => {
  const domainName: string = 'diagramGrowableList';
  context('Given a view with multiple growbale list nodes', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - multiple growable list nodes`;
      const diagramTitle = 'Multiple growable list nodes';

      beforeEach(() => {
        const studio = new Studio();
        const explorer = new Explorer();
        const details = new Details();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });
        new Diagram().disableFitView();

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('parent{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        new Diagram().centerViewport();
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then child node are correctly initialized to respect growable nodes', () => {
        const diagram = new Diagram();
        diagram
          .getNodes(diagramTitle, 'List 1')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(176, 5);
          });
        diagram
          .getNodes(diagramTitle, 'SubList A')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(70, 5);
          });
        diagram
          .getNodes(diagramTitle, 'SubList B')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(70, 5);
          });
        diagram
          .getNodes(diagramTitle, 'List 2')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(70, 5);
          });
        diagram
          .getNodes(diagramTitle, 'List 3')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(164, 5);
          });
      });

      it('Then after height resizing, growable node should grow', () => {
        const diagram = new Diagram();
        diagram.getNodes(diagramTitle, 'parent').should('exist');

        diagram.selectNode(diagramTitle, 'parent');
        let initialWidthList1: number,
          initialHeightList1: number,
          initialWidthSubListA: number,
          initialHeightSubListA: number,
          initialWidthSubListB: number,
          initialHeightSubListB: number,
          initialWidthList2: number,
          initialHeightList2: number,
          initialWidthList3: number,
          initialHeightList3: number;

        diagram.getNodes(diagramTitle, 'SubList A').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthSubListA = rect?.width ?? 0;
          initialHeightSubListA = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'SubList B').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthSubListB = rect?.width ?? 0;
          initialHeightSubListB = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'List 1').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthList1 = rect?.width ?? 0;
          initialHeightList1 = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'List 2').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthList2 = rect?.width ?? 0;
          initialHeightList2 = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'List 3').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthList3 = rect?.width ?? 0;
          initialHeightList3 = rect?.height ?? 0;
        });

        diagram.resizeNode('bottom.right', { x: 0, y: 50 });
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(1000); //Wait for animation
        diagram.getNodes(diagramTitle, 'List 1').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.equal(initialWidthList1);
          expect(rect?.height).to.approximately(initialHeightList1 + 25, 5);
        });

        diagram.getNodes(diagramTitle, 'SubList A').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.equal(initialWidthSubListA);
          expect(rect?.height).to.approximately(initialHeightSubListA + 12.5, 5);
        });

        diagram.getNodes(diagramTitle, 'SubList B').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.equal(initialWidthSubListB);
          expect(rect?.height).to.approximately(initialHeightSubListB + 12.5, 5);
        });

        diagram.getNodes(diagramTitle, 'List 2').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.equal(initialWidthList2);
          expect(rect?.height).to.approximately(initialHeightList2, 2);
        });

        diagram.getNodes(diagramTitle, 'List 3').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.equal(initialWidthList3);
          expect(rect?.height).to.approximately(initialHeightList3 + 25, 5);
        });
      });

      it('Then after resizing smaller than the default width, all node should be resize to the default size', () => {
        const diagram = new Diagram();
        diagram.getNodes(diagramTitle, 'parent').should('exist');

        diagram.selectNode(diagramTitle, 'parent');
        let initialWidthList1: number,
          initialHeightList1: number,
          initialWidthSubListA: number,
          initialHeightSubListA: number,
          initialWidthSubListB: number,
          initialHeightSubListB: number,
          initialWidthList2: number,
          initialHeightList2: number,
          initialWidthList3: number,
          initialHeightList3: number;

        diagram.getNodes(diagramTitle, 'SubList A').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthSubListA = rect?.width ?? 0;
          initialHeightSubListA = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'SubList B').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthSubListB = rect?.width ?? 0;
          initialHeightSubListB = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'List 1').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthList1 = rect?.width ?? 0;
          initialHeightList1 = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'List 2').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthList2 = rect?.width ?? 0;
          initialHeightList2 = rect?.height ?? 0;
        });

        diagram.getNodes(diagramTitle, 'List 3').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          initialWidthList3 = rect?.width ?? 0;
          initialHeightList3 = rect?.height ?? 0;
        });

        diagram.resizeNode('bottom.right', { x: -30, y: 0 });
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(1000); //Wait for animation
        diagram.getNodes(diagramTitle, 'List 1').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.approximately(initialWidthList1, 5);
          expect(rect?.height).to.equal(initialHeightList1);
        });

        diagram.getNodes(diagramTitle, 'SubList A').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.approximately(initialWidthSubListA, 5);
          expect(rect?.height).to.equal(initialHeightSubListA);
        });

        diagram.getNodes(diagramTitle, 'SubList B').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.approximately(initialWidthSubListB, 5);
          expect(rect?.height).to.equal(initialHeightSubListB);
        });

        diagram.getNodes(diagramTitle, 'List 2').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.approximately(initialWidthList2, 5);
          expect(rect?.height).to.equal(initialHeightList2);
        });

        diagram.getNodes(diagramTitle, 'List 3').then(($el) => {
          const rect = $el[0]?.getBoundingClientRect();
          expect(rect?.width).to.approximately(initialWidthList3, 5);
          expect(rect?.height).to.equal(initialHeightList3);
        });
      });
    });
  });
});
