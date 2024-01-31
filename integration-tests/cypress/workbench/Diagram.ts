/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

export class Diagram {
  public getDiagram(diagramLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.get(`[data-representation-kind="diagram"][data-representation-label="${diagramLabel}"]`);
  }

  public fitToScreen() {
    cy.getByTestId('fit-to-screen').click();

    /* eslint-disable-next-line cypress/no-unnecessary-waiting */
    cy.wait(4000);
  }

  public getNodes(diagramLabel: string, nodeLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel).contains('.react-flow__node', nodeLabel);
  }

  public getSelectedNodes(diagramLabel: string, nodeLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel).get('div.react-flow__node.selected').contains('.react-flow__node', nodeLabel);
  }

  public getPalette(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('Palette');
  }

  public dragAndDropNode(sourceNodeTestId: string, targetNodeTestId: string): void {
    cy.window().then((win) => {
      cy.getByTestId('rf__wrapper')
        .findByTestId(targetNodeTestId)
        .then((node) => {
          const target = node[0];
          if (target) {
            const { x, y } = target.getBoundingClientRect();
            cy.getByTestId('rf__wrapper').findByTestId(sourceNodeTestId).trigger('mousedown', {
              button: 0,
              force: true,
              view: win,
            });
            // Move a first time to trigger nodeDrag (needed since nodeDragThreshold={1})
            cy.getByTestId('rf__wrapper').findByTestId(sourceNodeTestId).trigger('mousemove', 500, 500, {
              force: true,
              view: win,
            });
            // eslint-disable-next-line cypress/no-unnecessary-waiting
            cy.wait(400); // the time needed to calculate compatible nodes
            //Warning : THERE IS AN ISSUE WITH THE COORDINATES HERE, the code is not doing what is meant to do ...
            cy.getByTestId('rf__wrapper').findByTestId(sourceNodeTestId).trigger('mousemove', x, y, {
              force: true,
              view: win,
            });
            cy.getByTestId('rf__wrapper').findByTestId(sourceNodeTestId).trigger('mouseup', {
              view: win,
            });
            // eslint-disable-next-line cypress/no-unnecessary-waiting
            cy.wait(500); // the time needed to process the drop action
          }
        });
    });
  }

  public isNodeInside(childNodeTestId: string, parentNodeTestId: string): void {
    cy.window().then(() => {
      cy.getByTestId(parentNodeTestId).then(($parentNode) => {
        cy.getByTestId(childNodeTestId).then(($childNode) => {
          const parentPosition = $parentNode[0]?.getBoundingClientRect();
          const childPosition = $childNode[0]?.getBoundingClientRect();
          if (parentPosition && childPosition) {
            expect(childPosition.top >= parentPosition.top).to.be.true;
            expect(childPosition.bottom <= parentPosition.bottom).to.be.true;
            expect(childPosition.left >= parentPosition.left).to.be.true;
            expect(childPosition.right <= parentPosition.right).to.be.true;
          } else {
            expect(false, 'Nodes to be tested do not exist');
          }
        });
      });
    });
  }

  public shareDiagram(): void {
    cy.getByTestId('share').click();
  }

  public dropOnDiagram(diagramLabel: string, dataTransfer: DataTransfer): void {
    this.getDiagram(diagramLabel).getByTestId('rf__wrapper').trigger('drop', 'bottomRight', { dataTransfer });
  }
}
