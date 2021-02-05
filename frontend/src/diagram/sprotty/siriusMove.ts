/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
import { MoveCommand, Point, ResolvedHandleMove, SEdge, SLabel, SModelElement, SNode, SRoutableElement } from 'sprotty';

export class SiriusMoveCommand extends MoveCommand {
  protected doMove(
    edge2move: Map<SRoutableElement, ResolvedHandleMove[]>,
    attachedEdgeShifts: Map<SRoutableElement, Point>
  ) {
    const allEdgesToReset = new Set<SEdge>();
    this.resolvedMoves.forEach((res) => {
      var element = res.element;
      if (this.isSNode(element)) {
        this.collectAllEdges(element, allEdgesToReset);
      }
      element.position = res.toPosition;
    });
    this.resetEdgesRoutingPoints(allEdgesToReset);
  }

  private resetEdgesRoutingPoints(AllEdgesToReset: Set<SEdge>) {
    AllEdgesToReset.forEach((edge) => {
      const router = this.edgeRouterRegistry!.get(edge.routerKind);
      edge.routingPoints = [];
      var routedPoints = router.route(edge);
      edge.routingPoints = routedPoints;
      this.resetLabelPosition(edge);
    });
  }

  private resetLabelPosition(edge: SEdge) {
    if (edge.routingPoints?.length === 2) {
      const label = edge.children.find((child) => this.isSLabel(child)) as SLabel;
      if (label) {
        var source = edge.routingPoints[0];
        var target = edge.routingPoints[1];
        var labelX = source.x + (target.x - source.x) / 2;
        var labelY = source.y + (target.y - source.y) / 2;
        label.position = {
          x: labelX,
          y: labelY,
        };
      }
    }
  }

  private isSLabel(element: SModelElement): element is SLabel {
    return element instanceof SLabel;
  }
  private collectAllEdges(element: SNode, allEdgesToReset: Set<SEdge>) {
    element.children.forEach((child) => {
      if (this.isSNode(child)) {
        this.collectAllEdges(child, allEdgesToReset);
      }
    });
    element.outgoingEdges.forEach((edge) => allEdgesToReset.add(edge));
    element.incomingEdges.forEach((edge) => allEdgesToReset.add(edge));
  }
  private isSNode(element: SModelElement): element is SNode {
    return element instanceof SNode;
  }
}
