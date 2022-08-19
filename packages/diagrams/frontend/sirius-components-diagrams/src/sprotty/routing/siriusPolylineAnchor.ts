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

import { IAnchorComputer, RectangleAnchor, SConnectableElement } from 'sprotty';
import { almostEquals, Point } from 'sprotty-protocol';

export class SiriusRectangleAnchor extends RectangleAnchor {
  /**
   * Rewrite this once we will support the self loop edge placement around its source/target.
   *
   * The current implementation only support self loop edge facing the north face of the edge source/target.
   */
  getSelfLoopAnchor(connectable: SConnectableElement, refPoint: Point, offset?: number): Point {
    const bounds = connectable.bounds;
    return {
      x: refPoint.x,
      y: bounds.y,
    };
  }

  /**
   * Compute an anchor position for routing an end of an edge towards this element - the other edge end -.
   *
   * @param connectable The node or port an edge should be connected to.
   * @param insideConnectableBoundsPoint The point from which the edge is routed from, in the same coordinate system as the connectable. It should represent a point inside the bounds of the connectable.
   * @param otherEndRefPoint The point from which the edge is routed towards, in the same coordinate system as the connectable.
   * @param offset An optional offset value to be considered in the anchor computation;
   *               positive values should shift the anchor away from this element, negative values
   *               should shift the anchor more to the inside. Use this to adapt ot arrow heads.
   * @returns the anchor position
   */
  getAnchorBetweenTwoPoints(
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

export const isSiriusRectangleAnchor = (anchorComputer: IAnchorComputer): anchorComputer is SiriusRectangleAnchor =>
  anchorComputer instanceof SiriusRectangleAnchor;
