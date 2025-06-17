/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
    cy.wait(1000);
  }

  public zoomOut() {
    cy.getByTestId('zoom-out').click();
    // eslint-disable-next-line cypress/no-unnecessary-waiting
    cy.wait(300); // wait for animation
  }

  public arrangeAll() {
    cy.getByTestId('arrange-all').click();
    /* eslint-disable-next-line cypress/no-unnecessary-waiting */
    cy.wait(2000);
  }

  public getLabel(diagramLabel: string, label: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel).findByTestId(`Label - ${label}`);
  }

  public getNodes(diagramLabel: string, nodeLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel).contains('.react-flow__node', nodeLabel);
  }

  public selectNode(diagramLabel: string, nodeLabel: string): void {
    this.getNodes(diagramLabel, nodeLabel).click('topLeft');
    this.getSelectedNodes(diagramLabel, nodeLabel).should('exist');
  }

  public getEdgePaths(diagramLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel).find('.react-flow__edge-path');
  }

  public getSelectedNodes(diagramLabel: string, nodeLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel).get('div.react-flow__node.selected').contains('.react-flow__node', nodeLabel);
  }

  public getSelectedEdges(diagramLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel).get('.react-flow__edge.selected');
  }

  public getSelectedEdge(diagramLabel: string, edgeLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDiagram(diagramLabel)
      .get('.react-flow__edge.selected')
      .getByTestId('Label - ' + edgeLabel);
  }

  public getPalette(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('Palette');
  }

  public getGroupPalette(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('GroupPalette');
  }

  public getDiagramScale(diagramLabel: string): Cypress.Chainable<number> {
    return this.getDiagram(diagramLabel)
      .find('.react-flow__viewport')
      .invoke('attr', 'style')
      .then((styleValue) => {
        let scale = 1;
        if (styleValue) {
          const match = /scale\(([^)]+)\)/.exec(styleValue);
          if (match && match[1]) {
            scale = parseFloat(match[1]);
          }
        }
        return scale;
      });
  }

  public getNodeCssValue(diagramLabel: string, nodeLabel: string, cssValue: string): Cypress.Chainable<number> {
    return this.getNodes(diagramLabel, nodeLabel)
      .invoke('css', cssValue)
      .then((widthValue) => {
        return parseInt(String(widthValue));
      });
  }

  public getLabelCssValue(diagramLabel: string, label: string, cssValue: string): Cypress.Chainable<number> {
    return this.getLabel(diagramLabel, label)
      .invoke('css', cssValue)
      .then((widthValue) => {
        return parseInt(String(widthValue));
      });
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

  /**
   * It seems this method has an issue with the drop on bottom right.
   * While it does not work on the CI, the behavior executing this test on local is buggy.
   * It drop the node on the bottom right but further than the _reactflow_ watermark making the whole reactflow viewport move.
   */
  public dropOnDiagram(diagramLabel: string, dataTransfer: DataTransfer): void {
    this.getDiagram(diagramLabel).getByTestId('rf__wrapper').trigger('drop', 'bottomRight', { dataTransfer });
  }

  public roundSvgPathData(pathData: string): string {
    const pathValues = pathData.split(/([a-zA-Z])/);
    const roundedPathValues: string[] = [];

    for (let i = 0; i < pathValues.length; i++) {
      const pathValue = pathValues[i];
      if (pathValue) {
        if (pathValue.match(/[-+]?\d*\.?\d+(?:[eE][-+]?\d+)?/g)) {
          roundedPathValues.push(parseFloat(pathValue).toFixed(0));
        } else {
          roundedPathValues.push(pathValue);
        }
      }
    }

    let roundedPathData = '';
    for (let i = 0; i < roundedPathValues.length; i++) {
      roundedPathData += roundedPathValues[i];
    }
    return roundedPathData;
  }

  public moveNode(diagramLabel: string, nodeLabel: string, { x, y }): void {
    cy.window().then((window) => {
      // eslint-disable-next-line cypress/no-assigning-return-values
      const elementToDrag = this.getNodes(diagramLabel, nodeLabel);
      return elementToDrag.then(($el) => {
        if ($el[0]) {
          const { left, top, width, height } = $el[0].getBoundingClientRect();
          const centerX = left + width / 2;
          const centerY = top + height / 2;
          const nextX: number = centerX + x;
          const nextY: number = centerY + y;

          return elementToDrag
            .trigger('mousedown', { view: window, force: true })
            .wait(50)
            .trigger('mousemove', centerX + 1, centerY + 1, { force: true })
            .wait(50)
            .trigger('mousemove', nextX, nextY, { force: true })
            .wait(50)
            .trigger('mouseup', { view: window, force: true });
        } else {
          return null;
        }
      });
    });
  }

  public resizeNode(direction: 'top.left' | 'top.right' | 'bottom.left' | 'bottom.right', { x, y }): void {
    cy.window().then((window) => {
      // eslint-disable-next-line cypress/no-assigning-return-values
      const nodeToResize = cy.get(`.react-flow__resize-control.nodrag.${direction}.handle`);
      return nodeToResize.then(($el) => {
        if ($el[0]) {
          const { left, top, width, height } = $el[0].getBoundingClientRect();
          const centerX = left + width / 2;
          const centerY = top + height / 2;
          const nextX: number = centerX + x;
          const nextY: number = centerY + y;

          return nodeToResize
            .trigger('mousedown', { view: window, force: true })
            .wait(150)
            .trigger('mousemove', { clientY: nextY, force: true })
            .wait(150)
            .trigger('mousemove', { clientX: nextX, force: true })
            .wait(150)
            .trigger('mouseup', { view: window, force: true });
        } else {
          return null;
        }
      });
    });
  }

  public moveBendPoint(bendPointIndex: number, { x, y }): void {
    cy.window().then((window) => {
      // eslint-disable-next-line cypress/no-assigning-return-values
      const bendPointToDrag = cy.getByTestId(`bend-point-${bendPointIndex}`);
      return bendPointToDrag.then(($el) => {
        if ($el[0]) {
          const { left, top, width, height } = $el[0].getBoundingClientRect();
          const centerX = left + width / 2;
          const centerY = top + height / 2;
          const nextX: number = centerX + x;
          const nextY: number = centerY + y;

          return bendPointToDrag
            .trigger('mousedown', { view: window, force: true })
            .wait(50)
            .trigger('mousemove', { clientX: nextX, clientY: nextY, force: true })
            .wait(50)
            .trigger('mouseup', { view: window, force: true });
        } else {
          return null;
        }
      });
    });
  }

  /**
   * This method compares two path svgs, comparing each point one by one.
   * A tolerance can be applied to the position of the points.
   */
  public isPathWithinTolerance(actualPath, expectedPath, tolerance = 0): boolean {
    const actualNumbers = actualPath.match(/\d+/g).map(Number);
    const expectedNumbers = expectedPath.match(/\d+/g).map(Number);

    if (actualNumbers.length !== expectedNumbers.length) {
      return false;
    }

    for (let i = 0; i < actualNumbers.length; i++) {
      if (Math.abs(actualNumbers[i] - expectedNumbers[i]) > tolerance) {
        return false;
      }
    }

    return true;
  }
}
