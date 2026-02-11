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
import { InternalNode, Node, Position, useUpdateNodeInternals, XYPosition, useStoreApi, Edge } from '@xyflow/react';
import { useStore } from '../../../representation/useStore';
import { NodeData, EdgeData } from '../../DiagramRenderer.types';
import { getNodesUpdatedWithHandles } from '../EdgeLayout';
import { useEditableEdgePath } from '../useEditableEdgePath';
import { cleanBendPoint, determineSegmentAxis, getHandlePositionFromXYPosition } from './RectilinearEdgeCalculation';
import { BendPointData } from './useBendingPoints.types';
import { UseEdgeDragStopHandlerValue } from './useEdgeDragStopHandler.types';

export const useEdgeDragStopHandler = (): UseEdgeDragStopHandlerValue => {
  const { getNodes, setEdges, setNodes } = useStore();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { synchronizeEdgeLayoutData } = useEditableEdgePath();
  const updateNodeInternals = useUpdateNodeInternals();

  const handleDragStop = (
    edgeId: string,
    source: XYPosition,
    setSource: (position: XYPosition) => void,
    sourceNode: InternalNode<Node<NodeData>>,
    sourceHandleId: string,
    sourcePosition: Position,
    target: XYPosition,
    setTarget: (position: XYPosition) => void,
    targetNode: InternalNode<Node<NodeData>>,
    targetHandleId: string,
    targetPosition: Position,
    isSourceSegment: boolean,
    isTargetSegment: boolean,
    localBendingPoints: BendPointData[]
  ) => {
    const edges = store.getState().edges;
    const edge = store.getState().edgeLookup.get(edgeId);
    if (edge?.data) {
      const newBendingPoint = cleanBendPoint([...localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder));
      edge.data.bendingPoints = newBendingPoint;
      let nodes = getNodes();

      if (isSourceSegment) {
        const newSource = { ...source };
        if (newBendingPoint.length === 0) {
          if (determineSegmentAxis(source, target) === 'x') {
            newSource.y = target.y;
          } else {
            newSource.x = target.x;
          }
          setSource(newSource);
        }
        let newPosition: Position | null = null;
        if (newBendingPoint[0]) {
          newPosition = getHandlePositionFromXYPosition(
            sourceNode,
            newSource,
            determineSegmentAxis(newSource, newBendingPoint[0])
          );
        }
        nodes = getNodesUpdatedWithHandles(
          nodes,
          sourceNode,
          edge.id,
          sourceHandleId,
          newSource,
          newPosition ?? sourcePosition
        );
      }

      if (isTargetSegment) {
        const newTarget = { ...target };
        if (newBendingPoint.length === 0) {
          if (determineSegmentAxis(source, target) === 'x') {
            newTarget.y = source.y;
          } else {
            newTarget.x = source.x;
          }
          setTarget(newTarget);
        }
        let newPosition: Position | null = null;
        const lastBendingPoint = newBendingPoint[newBendingPoint.length - 1];
        if (lastBendingPoint) {
          newPosition = getHandlePositionFromXYPosition(
            targetNode,
            newTarget,
            determineSegmentAxis(newTarget, lastBendingPoint)
          );
        }
        nodes = getNodesUpdatedWithHandles(
          nodes,
          targetNode,
          edge.id,
          targetHandleId,
          newTarget,
          newPosition ?? targetPosition
        );
      }
      edge.data.relativePositionBendingPoints = newBendingPoint.map((point) => ({
        x: point.x - source.x,
        y: point.y - source.y,
      }));

      setEdges(edges);
      setNodes(nodes);
      updateNodeInternals([sourceNode.id, targetNode.id]);
      synchronizeEdgeLayoutData(edges, nodes);
    }
  };

  return { handleDragStop };
};
