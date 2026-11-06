/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import { Edge, Node, useStoreApi, useUpdateNodeInternals, XYPosition } from '@xyflow/react';
import { useStore } from '../../../representation/useStore';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { getNodesUpdatedWithHandles } from '../EdgeLayout';
import { EdgeDragData } from './edgeGeometry.types';
import { createGeometryNodeLookup } from './resolveEdgeGeometry';
import { useEditableEdgePath } from '../useEditableEdgePath';
import {
  cleanBendPoint,
  determineSegmentAxis,
  getHandlePositionFromXYPosition,
} from '../rectilinear-edge/RectilinearEdgeCalculation';
import { UseEdgeDragStopHandlerValue } from './useEdgeDragStopHandler.types';

/** Saves an accepted drag directly, like the other diagram interaction hooks. */
export const useEdgeDragStopHandler = (): UseEdgeDragStopHandlerValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useStore();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const updateNodeInternals = useUpdateNodeInternals();
  const { synchronizeEdgeLayoutData } = useEditableEdgePath();

  const handleDragStop = (
    dragData: Pick<EdgeDragData, 'edgeId' | 'originalGeometry' | 'geometry' | 'isSourceSegment' | 'isTargetSegment'>
  ): { source: XYPosition; target: XYPosition } | null => {
    const { edgeId, originalGeometry, geometry, isSourceSegment, isTargetSegment } = dragData;
    const edge = getEdges().find((candidate) => {
      return candidate.id === edgeId;
    });
    if (!edge?.data) {
      return null;
    }
    const newBendingPoint = cleanBendPoint(
      [...geometry.bendingPoints].sort((a, b) => {
        return a.pathOrder - b.pathOrder;
      })
    );
    const source = { ...geometry.source };
    const target = { ...geometry.target };
    if (newBendingPoint.length === 0) {
      if (isSourceSegment) {
        if (determineSegmentAxis(source, target) === 'x') source.y = target.y;
        else source.x = target.x;
      }
      if (isTargetSegment) {
        if (determineSegmentAxis(source, target) === 'x') target.y = source.y;
        else target.x = source.x;
      }
    }
    let nodes = getNodes();
    const nodeLookup = createGeometryNodeLookup(nodes, store.getState().nodeLookup);
    const sourceNode = nodeLookup.get(edge.source);
    const targetNode = nodeLookup.get(edge.target);
    const firstBendingPoint = newBendingPoint[0];
    const lastBendingPoint = newBendingPoint[newBendingPoint.length - 1];
    if (
      sourceNode &&
      (isSourceSegment || source.x !== originalGeometry.source.x || source.y !== originalGeometry.source.y)
    ) {
      const newPosition = firstBendingPoint
        ? getHandlePositionFromXYPosition(sourceNode, source, determineSegmentAxis(source, firstBendingPoint))
        : null;
      nodes = getNodesUpdatedWithHandles(
        nodes,
        sourceNode,
        edge.id,
        edge.sourceHandle ?? '',
        source,
        newPosition ?? originalGeometry.sourcePosition
      );
    }
    if (
      targetNode &&
      (isTargetSegment || target.x !== originalGeometry.target.x || target.y !== originalGeometry.target.y)
    ) {
      const newPosition = lastBendingPoint
        ? getHandlePositionFromXYPosition(targetNode, target, determineSegmentAxis(target, lastBendingPoint))
        : null;
      const updatedTargetNode =
        createGeometryNodeLookup(nodes, store.getState().nodeLookup).get(edge.target) ?? targetNode;
      nodes = getNodesUpdatedWithHandles(
        nodes,
        updatedTargetNode,
        edge.id,
        edge.targetHandle ?? '',
        target,
        newPosition ?? originalGeometry.targetPosition
      );
    }
    const edges = getEdges().map((candidate) => {
      if (candidate.id !== edge.id || !candidate.data) {
        return candidate;
      }
      const { edgeDragData: _edgeDragData, ...data } = candidate.data;
      return {
        ...candidate,
        data: {
          ...data,
          bendingPoints: newBendingPoint,
          relativePositionBendingPoints: newBendingPoint.map((point) => {
            return { x: point.x - source.x, y: point.y - source.y };
          }),
        },
      };
    });
    setNodes(nodes);
    setEdges(edges);
    updateNodeInternals([edge.source, edge.target]);
    synchronizeEdgeLayoutData(edges, nodes);
    return { source, target };
  };

  return { handleDragStop };
};
