/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { RectangleAnchor, SConnectableElement } from 'sprotty';
import { almostEquals, Point } from 'sprotty-protocol';
import { Edge, Ratio } from '../Diagram.types';
import { ISiriusAnchorComputer } from './siriusRoutingModule.types';

export class SiriusRectangleAnchor extends RectangleAnchor implements ISiriusAnchorComputer {
  /**
   * Rewrite this once we will support the self loop edge placement around its source/target.
   *
   * The current implementation only support self loop edge facing the north face of the edge source/target.
   */
  getSelfLoopAnchor(connectable: SConnectableElement, refPoint: Point, _offset?: number): Point {
    const bounds = connectable.bounds;
    return {
      x: refPoint.x,
      y: bounds.y,
    };
  }

  getReferencePosition(connectable: SConnectableElement, edge: Edge): Point {
    const bounds = connectable.bounds;
    let ratio: Ratio;
    if (edge.target === connectable) {
      ratio = edge.targetAnchorRelativePosition;
    } else {
      ratio = edge.sourceAnchorRelativePosition;
    }

    return {
      x: bounds.x + (bounds.width >= 0 ? ratio.x * bounds.width : 0),
      y: bounds.y + (bounds.height >= 0 ? ratio.y * bounds.height : 0),
    };
  }

  getSiriusAnchor(
    connectable: SConnectableElement,
    insideConnectableBoundsPoint: Point,
    otherEndRefPoint: Point,
    offset: number = 0
  ): Point {
    const bounds = connectable.bounds;
    const finder = new NearestPointFinder(insideConnectableBoundsPoint, otherEndRefPoint);
    if (!almostEquals(insideConnectableBoundsPoint.y, otherEndRefPoint.y)) {
      const xTop = this.getXIntersection(bounds.y, insideConnectableBoundsPoint, otherEndRefPoint);
      if (xTop >= bounds.x && xTop <= bounds.x + bounds.width) finder.addCandidate(xTop, bounds.y - offset);
      const xBottom = this.getXIntersection(bounds.y + bounds.height, insideConnectableBoundsPoint, otherEndRefPoint);
      if (xBottom >= bounds.x && xBottom <= bounds.x + bounds.width)
        finder.addCandidate(xBottom, bounds.y + bounds.height + offset);
    }
    if (!almostEquals(insideConnectableBoundsPoint.x, otherEndRefPoint.x)) {
      const yLeft = this.getYIntersection(bounds.x, insideConnectableBoundsPoint, otherEndRefPoint);
      if (yLeft >= bounds.y && yLeft <= bounds.y + bounds.height) finder.addCandidate(bounds.x - offset, yLeft);
      const yRight = this.getYIntersection(bounds.x + bounds.width, insideConnectableBoundsPoint, otherEndRefPoint);
      if (yRight >= bounds.y && yRight <= bounds.y + bounds.height)
        finder.addCandidate(bounds.x + bounds.width + offset, yRight);
    }
    return finder.best;
  }

  updateAnchor(connectable: SConnectableElement, edge: Edge, position: Point): void {
    const bounds = connectable.bounds;
    let ratio: Ratio = {
      x: 0.5,
      y: 0.5,
    };
    if (bounds.width > 0 && bounds.height > 0) {
      ratio = {
        x: (position.x - bounds.x) / bounds.width,
        y: (position.y - bounds.y) / bounds.height,
      };
    }
    if (edge.target === connectable) {
      edge.targetAnchorRelativePosition = ratio;
    } else {
      edge.sourceAnchorRelativePosition = ratio;
    }
  }
}

class NearestPointFinder {
  protected currentBest: Point | undefined;
  protected currentDist: number = -1;

  constructor(protected insideBoundsPoint: Point, protected refPoint: Point) {}

  addCandidate(x: number, y: number) {
    const dx = this.refPoint.x - x;
    const dy = this.refPoint.y - y;
    const dist = dx * dx + dy * dy;
    if (this.currentDist < 0 || dist < this.currentDist) {
      this.currentBest = {
        x: x,
        y: y,
      };
      this.currentDist = dist;
    }
  }

  get best(): Point {
    if (this.currentBest === undefined) {
      return this.insideBoundsPoint;
    } else {
      return this.currentBest;
    }
  }
}
