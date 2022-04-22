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
  ResolvedHandleMove,
  RoutedPoint,
  SConnectableElement,
  SParentElement,
  SRoutableElement,
  translatePoint,
} from 'sprotty';
import { Bounds, Point } from 'sprotty-protocol';
import { Edge } from '../Diagram.types';
import { SiriusDanglingAnchor } from './siriusDanglingAnchor';
import { isSiriusAnchor } from './siriusRoutingModule.types';

export class SiriusPolylineEdgeRouter extends PolylineEdgeRouter {
  getReferencePosition(connectable: SConnectableElement, edge: Edge): Point {
    const anchorComputer = this.getAnchorComputer(connectable);
    if (isSiriusAnchor(anchorComputer)) {
      return anchorComputer.getReferencePosition(connectable, edge);
    }
    return Bounds.center(connectable.bounds);
  }

  override route(edge: Edge): RoutedPoint[] {
    const source = edge.source;
    const target = edge.target;
    if (source === undefined || target === undefined) {
      return [];
    }

    let sourceAnchor: Point;
    let targetAnchor: Point;
    const options = this.getOptions(edge);
    const routingPoints = edge.routingPoints.length > 0 ? edge.routingPoints : [];
    this.cleanupRoutingPoints(edge, routingPoints, false, true);
    const rpCount = routingPoints !== undefined ? routingPoints.length : 0;
    const targetAbsolutePositionRef = this.getReferencePosition(target, edge);
    const sourceAbsolutePositionRef = this.getReferencePosition(source, edge);
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
  override cleanupRoutingPoints(
    edge: SRoutableElement,
    routingPoints: Point[],
    _updateHandles: boolean,
    addRoutingPoints: boolean
  ): void {
    if (edge.sourceId === edge.targetId && routingPoints.length === 0 && addRoutingPoints) {
      const spaceBetweenRp = edge.source.bounds.width / 3;
      const p1 = { x: edge.source.bounds.x + spaceBetweenRp, y: edge.source.bounds.y - 10 };
      const p2 = { x: p1.x + spaceBetweenRp, y: p1.y };
      routingPoints.push(p1);
      routingPoints.push(p2);
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
    if (isSiriusAnchor(anchorComputer)) {
      if (edge.sourceId === edge.targetId) {
        anchor = anchorComputer.getSelfLoopAnchor(connectable, translatedRefPoint, anchorCorrection + strokeCorrection);
      } else {
        anchor = anchorComputer.getSiriusAnchor(
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

  override applyHandleMoves(edge: SRoutableElement, moves: ResolvedHandleMove[]): void {
    const remainingMoves = moves.slice();
    moves.forEach((move) => {
      const handle = move.handle;
      if (handle.kind === 'source' && !(edge.source instanceof SiriusDanglingAnchor)) {
        // detach source
        const anchor = new SiriusDanglingAnchor();
        anchor.id = edge.id + '_dangling-source';
        anchor.original = edge.source;
        anchor.position = move.toPosition;
        handle.root.add(anchor);
        handle.danglingAnchor = anchor;
        edge.sourceId = anchor.id;
      } else if (handle.kind === 'target' && !(edge.target instanceof SiriusDanglingAnchor)) {
        // detach target
        const anchor = new SiriusDanglingAnchor();
        anchor.id = edge.id + '_dangling-target';
        anchor.original = edge.target;
        anchor.position = move.toPosition;
        handle.root.add(anchor);
        handle.danglingAnchor = anchor;
        edge.targetId = anchor.id;
      }
      if (handle.danglingAnchor) {
        handle.danglingAnchor.position = move.toPosition;
        remainingMoves.splice(remainingMoves.indexOf(move), 1);
      }
    });
    if (remainingMoves.length > 0) this.applyInnerHandleMoves(edge, remainingMoves);
    this.cleanupRoutingPoints(edge, edge.routingPoints, true, true);
  }

  /**
   * Updates the edge anchor regarding the move.
   *
   * @param edge The edge which one of its end has been moved
   * @param newSourceId The new source id if the source end has been reconnected, the current edge source end, or undefined
   * @param newTargetId The new target id if the target end has been reconnected, the current edge target end, or undefined
   */
  override applyReconnect(edge: Edge, newSourceId?: string, newTargetId?: string): void {
    let previousSource: SConnectableElement | undefined;
    let previousTarget: SConnectableElement | undefined;

    /**
     * Get the previous source and target if applicable.
     * If the previous source or target are not undefined, they should be a SiriusDanglingAnchor.
     */
    if (!!newSourceId && newSourceId !== edge.sourceId) {
      previousSource = edge.source;
    }
    if (!!newTargetId && newTargetId !== edge.targetId) {
      previousTarget = edge.target;
    }

    super.applyReconnect(edge, newSourceId, newTargetId);

    if (!!previousSource) {
      const newConnectableElement = edge.source;
      const anchorComputer = this.getAnchorComputer(newConnectableElement);
      if (isSiriusAnchor(anchorComputer)) {
        const translatedPoint = translatePoint(
          previousSource.position,
          previousSource.parent,
          newConnectableElement.parent
        );
        // Use the translated position of the SiriusDanglingAnchor to compute the ratio of the source edge end.
        anchorComputer.updateAnchor(newConnectableElement, edge, translatedPoint);
      }
    }
    if (!!previousTarget) {
      const newConnectableElement = edge.target;
      const anchorComputer = this.getAnchorComputer(newConnectableElement);
      if (isSiriusAnchor(anchorComputer)) {
        const translatedPoint = translatePoint(
          previousTarget.position,
          previousTarget.parent,
          newConnectableElement.parent
        );
        // Use the translated position of the SiriusDanglingAnchor to compute the ratio of the target edge end.
        anchorComputer.updateAnchor(newConnectableElement, edge, translatedPoint);
      }
    }
  }
}
