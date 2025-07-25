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

  public centerViewport() {
    cy.get('.react-flow__viewport').should('exist');
    cy.get('.react-flow__viewport').invoke('attr', 'style', 'transform: translate(280px, 300px) scale(1);');
    /* eslint-disable-next-line cypress/no-unnecessary-waiting */
    cy.wait(500);
  }

  public disableFitView() {
    cy.window().then((win) => {
      // @ts-expect-error: we use a variable in the DOM to disable `fitView` functionality for Cypress tests.
      win.document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS = true;
    });
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
    this.getNodes(diagramLabel, nodeLabel).click('topLeft', { force: true });
    cy.get('div.react-flow__node.selected').contains('.react-flow__node', nodeLabel).should('exist');
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
            .wait(250)
            .trigger('mousemove', centerX + 1, centerY + 1, { force: true })
            .wait(250)
            .trigger('mousemove', nextX, nextY, { force: true })
            .wait(250)
            .trigger('mouseup', { view: window, force: true });
        } else {
          return null;
        }
      });
    });
  }

  public moveSourceHandleToLeftPosition(edge: Cypress.Chainable, diagramTitle, selector): void {
    const diagram = new Diagram();
    let initialWidth: number, initialHeight: number;
    diagram.getNodes(diagramTitle, selector).then(($el) => {
      if ($el[0]) {
        const rect = $el[0].getBoundingClientRect();
        initialWidth = rect.width;
        initialHeight = rect.height;

        const centerX = initialWidth * 0.25;
        const centerY = initialHeight * 0.75;
        edge
          .get('.react-flow__edgeupdater.react-flow__edgeupdater-source')
          .trigger('mousedown', { force: true, button: 0 });

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(400);
        diagram.getNodes(diagramTitle, selector).trigger('mousemove', centerX, centerY, { force: true });

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(400);
        diagram.getNodes(diagramTitle, selector).trigger('mouseup', centerX, centerY, { force: true });
      }
    });
  }

  public moveSelectedEdgeTargetHandleToMiddleOfTheNode(diagramTitle: string, toNode: string): void {
    const diagram = new Diagram();
    cy.get('.react-flow__edge:first').as('edge').click({ force: true });
    cy.get('@edge')
      .get('.react-flow__edgeupdater.react-flow__edgeupdater-target')
      .trigger('mousedown', { force: true, button: 0 });

    diagram.getNodes(diagramTitle, toNode).trigger('mousemove', { force: true, position: 'center' });

    // eslint-disable-next-line cypress/no-unnecessary-waiting
    cy.wait(500);
    diagram.getNodes(diagramTitle, toNode).trigger('mouseup', { force: true, position: 'center' });
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

  public resizeNodeFeedback(direction: 'top.left' | 'top.right' | 'bottom.left' | 'bottom.right', { x, y }): void {
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
            .wait(150);
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

  public createEdgeFromTopHandleToCenterNode(diagramTitle: string, fromNode: string, toNode: string): void {
    const diagram = new Diagram();

    this.selectNode(diagramTitle, fromNode);
    cy.getByTestId('creationhandle-top').should('exist');

    cy.getByTestId('creationhandle-top').trigger('mousedown', { force: true, button: 0 });

    diagram.getNodes(diagramTitle, toNode).then(($el) => {
      if ($el[0]) {
        const rect = $el[0].getBoundingClientRect();
        const centerX = rect.width * 0.5;
        const centerY = rect.height * 0.5;

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(400);
        diagram.getNodes(diagramTitle, toNode).trigger('mousemove', centerX, centerY, { force: true });

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(400);
        diagram.getNodes(diagramTitle, toNode).trigger('mouseup', centerX, centerY, { force: true });
      }
    });
  }

  public createEdgeFromTopHandleToBottomSideNode(diagramTitle: string, fromNode: string, toNode: string): void {
    const diagram = new Diagram();
    this.selectNode(diagramTitle, fromNode);
    cy.getByTestId('creationhandle-top').should('exist');

    cy.getByTestId('creationhandle-top').trigger('mousedown', { force: true, button: 0 });

    // eslint-disable-next-line cypress/no-unnecessary-waiting
    diagram.getNodes(diagramTitle, toNode).then(($el) => {
      if ($el[0]) {
        const rect = $el[0].getBoundingClientRect();
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(400);
        diagram.getNodes(diagramTitle, toNode).trigger('mousemove', rect.width / 2, rect.height - 5, { force: true });

        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(400);
        diagram.getNodes(diagramTitle, toNode).trigger('mouseup', rect.width / 2, rect.height - 5, { force: true });
      }
    });
  }

  public moveEdgeSegment(segmentIndex: number, { x, y }): void {
    cy.window().then((window) => {
      // eslint-disable-next-line cypress/no-assigning-return-values
      const segmentToDrag = cy.getByTestId(`temporary-moving-line-${segmentIndex}`);
      return segmentToDrag.then(($el) => {
        if ($el[0]) {
          const { left, top, width, height } = $el[0].getBoundingClientRect();
          const centerX = left + width / 2;
          const centerY = top + height / 2;
          const nextX: number = centerX + x;
          const nextY: number = centerY + y;

          return segmentToDrag
            .trigger('mousedown', { view: window, force: true })
            .wait(250)
            .trigger('mousemove', { clientX: nextX, clientY: nextY, force: true })
            .wait(250)
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

  public dragNode(selector, { x, y }): void {
    cy.window().then((window) => {
      // eslint-disable-next-line cypress/no-assigning-return-values
      const elementToDrag = cy.get(selector as string);
      return elementToDrag.then(($el) => {
        if ($el[0]) {
          const { left, top, width, height } = $el[0].getBoundingClientRect();
          const centerX = left + width / 2;
          const centerY = top + height / 2;
          const nextX: number = centerX + x;
          const nextY: number = centerY + y;

          return elementToDrag
            .trigger('mousedown', { view: window, force: true })
            .wait(250)
            .trigger('mousemove', centerX + 1, centerY + 1, { force: true })
            .wait(250)
            .trigger('mousemove', nextX, nextY, { force: true })
            .wait(250)
            .trigger('mousemove', centerX - 1, centerY - 1, { force: true })
            .wait(250)
            .trigger('mouseup', { view: window, force: true });
        } else {
          return null;
        }
      });
    });
  }
}
