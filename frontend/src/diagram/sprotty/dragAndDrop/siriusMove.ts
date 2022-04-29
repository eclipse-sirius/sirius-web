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
  SChildElement,
  SConnectableElement,
  SEdge,
  SLabel,
  SModelElement,
  SNode,
  SRoutableElement,
} from 'sprotty';
import { Point } from 'sprotty-protocol';

const SPACING_EDGE_LABEL = 2;

export class SiriusMoveCommand extends MoveCommand {
  /**
   * Applies the move given in `edge2move` and `attachedEdgeShifts` to graphical elements.
   *
   * It first, uses `MoveCommand.doMove` to applies the default Sprotty behavior. Then, applies the move
   * to "contained" edges and to edge labels.
   *
   * @param edge2move The map of edge to resolved move of edge handles
   * @param attachedEdgeShifts The map of edge to delta move
   */
  protected doMove(
    edge2move: Map<SRoutableElement, ResolvedHandleMove[]>,
    attachedEdgeShifts: Map<SRoutableElement, Point>
  ): void {
    super.doMove(edge2move, attachedEdgeShifts);

    const allEdgesToUpdate = new Set<SEdge>();
    this.resolvedMoves.forEach((res) => {
      const element = res.element;
      if (this.isSNode(element)) {
        this.collectAllEdges(element, allEdgesToUpdate);
      }
      element.position = res.toPosition;
    });
    this.updateEdge(allEdgesToUpdate);

    this.updateEdgeFromHandleMoves(edge2move);
  }

  /**
   * Updates edges label and, if necessary, edges routing points.
   *
   * At this point provided edges source end or target - sometime both - end have been moved.
   * If both ends of an edge have been moved, updates routing points position in addition of the edge label.
   *
   * If only one edge end has been moved, updates the edge label position only if the edge does not have routing points.
   * If the edge has routing points, its label is either positioned on a routing point, or on a section of the edge, and
   * thus, the label is not affected by the move.
   *
   * @param edgeToUpdate The set of edge that have their source end or target end move, directly or indirectly
   */
  private updateEdge(edgeToUpdate: Set<SEdge>) {
    if (edgeToUpdate.size > 0) {
      const firstMove = (this.resolvedMoves.values().next() as IteratorYieldResult<ResolvedElementMove>).value;
      const delta = Point.subtract(firstMove.toPosition, firstMove.fromPosition);
      edgeToUpdate.forEach((edge) => {
        if (this.isContainedInResolvedMove(edge.source) && this.isContainedInResolvedMove(edge.target)) {
          // Both edge ends are contained in the move
          edge.routingPoints = edge.routingPoints.map((rp) => Point.add(rp, delta));
          this.updateEdgeLabelPositionFromDelta(edge, delta);
        } else {
          // Only one edge end is moving
          const router = this.edgeRouterRegistry!.get(edge.routerKind);
          const routedPoints: Point[] = router.route(edge);
          if (routedPoints.length === 2) {
            // Its a straight edge, and thus, the edge label must be updated
            this.updateEdgeLabelPositionFromEdgeSection(edge, {
              sectionStart: routedPoints[0],
              sectionEnd: routedPoints[1],
            });
          }
        }
      });
    }
  }

  /**
   * For each edge handle moves, updates the label position if its positioned on the moved handle or is on an edge section bounded by a moved handle.
   *
   * An edge label is either positioned on a routing point or a section of an edge depending on whether the number of the edge routing points is odd or even.
   *
   * @param edge2move The map of edge to resolved move of edge handles
   */
  private updateEdgeFromHandleMoves(edge2move: Map<SRoutableElement, ResolvedHandleMove[]>) {
    edge2move.forEach((resolvedHandleMoves, routableElement) => {
      const routingPoint = routableElement.routingPoints;
      if (routingPoint.length & 1) {
        const medianRoutingPointIndex = Math.floor(routingPoint.length / 2);
        resolvedHandleMoves.forEach((resolvedHandleMove) => {
          if (medianRoutingPointIndex === resolvedHandleMove.handle.pointIndex) {
            this.updateEdgeLabelPositionFromDelta(
              routableElement,
              Point.subtract(resolvedHandleMove.toPosition, resolvedHandleMove.fromPosition)
            );
          }
        });
      } else {
        const medianRoutingPointIndex = routingPoint.length / 2;
        resolvedHandleMoves.forEach((resolvedHandleMove) => {
          if (
            medianRoutingPointIndex === resolvedHandleMove.handle.pointIndex ||
            medianRoutingPointIndex === resolvedHandleMove.handle.pointIndex + 1
          ) {
            this.updateEdgeLabelPositionFromEdgeSection(routableElement, {
              sectionStart: routingPoint[medianRoutingPointIndex - 1],
              sectionEnd: routingPoint[medianRoutingPointIndex],
            });
          }
        });
      }
    });
  }

  private updateEdgeLabelPositionFromDelta(edge: SRoutableElement, delta: Point) {
    const label = edge.children.find<SLabel>(this.isSLabel);
    if (!!label) {
      label.position = Point.add(label.position, delta);
    }
  }

  private isContainedInResolvedMove(connectableElement: SConnectableElement | undefined): boolean {
    let isContainedInResolvedMove: boolean = false;
    let element: SChildElement | undefined = connectableElement;
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

  private updateEdgeLabelPositionFromEdgeSection(edge: SRoutableElement, updatedEdgeSection: EdgeSection) {
    const label = edge.children.find<SLabel>(this.isSLabel);
    if (!!label) {
      const sectionStart = updatedEdgeSection.sectionStart;
      const sectionEnd = updatedEdgeSection.sectionEnd;
      const labelX = sectionStart.x + (sectionEnd.x - sectionStart.x) / 2 - label.bounds.width / 2;
      let labelY = sectionStart.y + (sectionEnd.y - sectionStart.y) / 2;
      if (edge.routingPoints.length === 0) {
        labelY = labelY + SPACING_EDGE_LABEL;
      } else {
        labelY = labelY - label.bounds.height - SPACING_EDGE_LABEL;
      }
      label.position = {
        x: labelX,
        y: labelY,
      };
    }
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

  private isSLabel(element: SModelElement): element is SLabel {
    return element instanceof SLabel;
  }

  private isSNode(element: SModelElement): element is SNode {
    return element instanceof SNode;
  }
}

interface EdgeSection {
  sectionStart: Point;
  sectionEnd: Point;
}
