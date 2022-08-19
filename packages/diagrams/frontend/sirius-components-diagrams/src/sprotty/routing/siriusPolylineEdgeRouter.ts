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

import {
  PolylineEdgeRouter,
  RoutedPoint,
  SConnectableElement,
  SParentElement,
  SRoutableElement,
  translatePoint,
} from 'sprotty';
import { Bounds, Point } from 'sprotty-protocol';
import { Edge, Ratio } from '../Diagram.types';
import { isSiriusRectangleAnchor } from './siriusPolylineAnchor';

export class SiriusPolylineEdgeRouter extends PolylineEdgeRouter {
  refPosition(bounds: Bounds, ratio: Ratio): Point {
    return {
      x: bounds.x + (bounds.width >= 0 ? ratio.x * bounds.width : 0),
      y: bounds.y + (bounds.height >= 0 ? ratio.y * bounds.height : 0),
    };
  }

  route(edge: Edge): RoutedPoint[] {
    const source = edge.source;
    const target = edge.target;
    if (source === undefined || target === undefined) {
      return [];
    }

    let sourceAnchor: Point;
    let targetAnchor: Point;
    const options = this.getOptions(edge);
    const routingPoints = edge.routingPoints.length > 0 ? edge.routingPoints : [];
    this.cleanupRoutingPoints(edge, routingPoints, false, false);
    const rpCount = routingPoints !== undefined ? routingPoints.length : 0;
    const targetAbsolutePositionRef = this.refPosition(target.bounds, edge.targetAnchorRelativePosition);
    const sourceAbsolutePositionRef = this.refPosition(source.bounds, edge.sourceAnchorRelativePosition);
    if (rpCount === 0) {
      // Use the target absolute position as start anchor reference
      sourceAnchor = this.getTranslatedAnchorBetweenTwoPoints(
        source,
        sourceAbsolutePositionRef,
        targetAbsolutePositionRef,
        target.parent,
        edge,
        edge.sourceAnchorCorrection
      );
      // Use the source absolute position as end anchor reference
      targetAnchor = this.getTranslatedAnchorBetweenTwoPoints(
        target,
        targetAbsolutePositionRef,
        sourceAbsolutePositionRef,
        source.parent,
        edge,
        edge.targetAnchorCorrection
      );
    } else {
      // Use the first routing point as start anchor reference
      const p0 = routingPoints[0];
      sourceAnchor = this.getTranslatedAnchorBetweenTwoPoints(
        source,
        sourceAbsolutePositionRef,
        p0,
        edge.parent,
        edge,
        edge.sourceAnchorCorrection
      );
      // Use the last routing point as end anchor reference
      const pn = routingPoints[rpCount - 1];
      targetAnchor = this.getTranslatedAnchorBetweenTwoPoints(
        target,
        targetAbsolutePositionRef,
        pn,
        edge.parent,
        edge,
        edge.sourceAnchorCorrection
      );
    }

    const result: RoutedPoint[] = [];
    result.push({ kind: 'source', x: sourceAnchor.x, y: sourceAnchor.y });
    for (let i = 0; i < rpCount; i++) {
      const p = routingPoints[i];
      if (
        (i > 0 && i < rpCount - 1) ||
        (i === 0 &&
          Point.maxDistance(sourceAnchor, p) >= options.minimalPointDistance + (edge.sourceAnchorCorrection || 0)) ||
        (i === rpCount - 1 &&
          Point.maxDistance(p, targetAnchor) >= options.minimalPointDistance + (edge.targetAnchorCorrection || 0))
      ) {
        result.push({ kind: 'linear', x: p.x, y: p.y, pointIndex: i });
      }
    }
    result.push({ kind: 'target', x: targetAnchor.x, y: targetAnchor.y });
    return this.filterEditModeHandles(result, edge, options);
  }

  /**
   * When we are handling self loop edge and there are no routing points, adds two routing points that will be used by an anchor computer.
   */
  cleanupRoutingPoints(
    edge: SRoutableElement,
    routingPoints: Point[],
    updateHandles: boolean,
    addRoutingPoints: boolean
  ): void {
    if (edge.sourceId === edge.targetId && routingPoints.length === 0) {
      const spaceBetweenRp = edge.source.bounds.width / 3;
      const p1 = { x: edge.source.bounds.x + spaceBetweenRp, y: edge.source.bounds.y - 10 };
      const p2 = { x: p1.x + spaceBetweenRp, y: p1.y };
      routingPoints.push(p1);
      routingPoints.push(p2);
    } else {
      super.cleanupRoutingPoints(edge, routingPoints, updateHandles, addRoutingPoints);
    }
  }
  getTranslatedAnchorBetweenTwoPoints(
    connectable: SConnectableElement,
    insideConnectableBoundsRefPoint: Point,
    otherEndRefPoint: Point,
    refContainer: SParentElement,
    edge: Edge,
    anchorCorrection: number = 0
  ): Point {
    const translatedRefPoint = translatePoint(otherEndRefPoint, refContainer, connectable.parent);
    const strokeCorrection = 0.5 * connectable.strokeWidth;

    let anchor: Point;
    const anchorComputer = this.getAnchorComputer(connectable);
    if (isSiriusRectangleAnchor(anchorComputer)) {
      if (edge.sourceId === edge.targetId) {
        anchor = anchorComputer.getSelfLoopAnchor(connectable, translatedRefPoint, anchorCorrection + strokeCorrection);
      } else {
        anchor = anchorComputer.getAnchorBetweenTwoPoints(
          connectable,
          insideConnectableBoundsRefPoint,
          translatedRefPoint,
          anchorCorrection + strokeCorrection
        );
      }
    } else {
      anchor = anchorComputer.getAnchor(connectable, translatedRefPoint, anchorCorrection + strokeCorrection);
    }

    return translatePoint(anchor, connectable.parent, edge.parent);
  }
}
