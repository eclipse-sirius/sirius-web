/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
  MoveCommand,
  ResolvedElementMove,
  ResolvedHandleMove,
  RoutedPoint,
  SChildElement,
  SConnectableElement,
  SEdge,
  SLabel,
  SModelElement,
  SNode,
  SRoutableElement,
} from 'sprotty';
import { Point } from 'sprotty-protocol';

export class SiriusMoveCommand extends MoveCommand {
  protected doMove(
    edge2move: Map<SRoutableElement, ResolvedHandleMove[]>,
    attachedEdgeShifts: Map<SRoutableElement, Point>
  ) {
    super.doMove(edge2move, attachedEdgeShifts);
    const allEdgesToUpdate = new Set<SEdge>();
    this.resolvedMoves.forEach((res) => {
      var element = res.element;
      if (this.isSNode(element)) {
        this.collectAllEdges(element, allEdgesToUpdate);
      }
      element.position = res.toPosition;
    });
    this.updateRelatedEdges(allEdgesToUpdate, attachedEdgeShifts);
  }

  private updateRelatedEdges(allEdgesToUpdate: Set<SEdge>, attachedEdgeShifts: Map<SRoutableElement, Point>) {
    allEdgesToUpdate.forEach((edge) => {
      const firstMove = (this.resolvedMoves.values().next() as IteratorYieldResult<ResolvedElementMove>).value;
      const delta = Point.subtract(firstMove.toPosition, firstMove.fromPosition);
      const edgeIsMoving = this.updateMovingEdge(edge, attachedEdgeShifts, delta);
      this.updateEdgeLabelPosition(edge, edgeIsMoving, delta);
    });
  }

  private updateMovingEdge(edge: SEdge, attachedEdgeShifts: Map<SRoutableElement, Point>, delta: Point): boolean {
    let edgeIsMoving: boolean = !!attachedEdgeShifts.get(edge);
    if (!edgeIsMoving && this.isContainedInResolvedMove(edge.source) && this.isContainedInResolvedMove(edge.target)) {
      edge.routingPoints = edge.routingPoints.map((rp) => Point.add(rp, delta));
      edgeIsMoving = true;
    }
    return edgeIsMoving;
  }

  private updateEdgeLabelPosition(edge: SEdge, edgeIsMoving: boolean, delta: Point): void {
    const router = this.edgeRouterRegistry!.get(edge.routerKind);
    const routedPoints = router.route(edge);
    if (edge.sourceId === edge.targetId) {
      // Do not put source and target routed point as routing point.
      edge.routingPoints = routedPoints.slice(1, -1);
    }
    this.resetLabelPosition(edge, edgeIsMoving, routedPoints, delta);
  }
  private isContainedInResolvedMove(connectableElement: SConnectableElement): boolean {
    let isContainedInResolvedMove: boolean = false;
    let element: SChildElement = connectableElement;
    while (!isContainedInResolvedMove && !!element) {
      isContainedInResolvedMove = this.resolvedMoves.get(element.id) !== undefined;
      if (element.parent instanceof SChildElement) {
        element = element.parent;
      } else {
        element = undefined;
      }
    }
    return isContainedInResolvedMove;
  }

  private resetLabelPosition(edge: SEdge, edgeHasBeenMoved: boolean, routedPoints: RoutedPoint[], delta: Point) {
    if (routedPoints.length === 2) {
      const label = edge.children.find((child) => this.isSLabel(child)) as SLabel;
      if (label) {
        const source = routedPoints[0];
        const target = routedPoints[1];
        const labelX = source.x + (target.x - source.x) / 2;
        const labelY = source.y + (target.y - source.y) / 2;
        label.position = {
          x: labelX,
          y: labelY,
        };
      }
    } else if (edgeHasBeenMoved) {
      const label = edge.children.find((child) => this.isSLabel(child)) as SLabel;
      if (!!label) {
        label.position = Point.add(label.position, delta);
      }
    }
  }

  private isSLabel(element: SModelElement): element is SLabel {
    return element instanceof SLabel;
  }
  private collectAllEdges(element: SNode, allEdgesToUpdate: Set<SEdge>) {
    element.children.forEach((child) => {
      if (this.isSNode(child)) {
        this.collectAllEdges(child, allEdgesToUpdate);
      }
    });
    element.outgoingEdges.forEach((edge) => allEdgesToUpdate.add(edge));
    element.incomingEdges.forEach((edge) => allEdgesToUpdate.add(edge));
  }
  private isSNode(element: SModelElement): element is SNode {
    return element instanceof SNode;
  }
}
