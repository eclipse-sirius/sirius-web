/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { Edge, InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { useState, useEffect } from 'react';
import { DraggableData } from 'react-draggable';
import { useStore } from '../../../representation/useStore';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useEditableEdgePath } from '../useEditableEdgePath';
import {
  cleanBendPoint,
  getMiddlePoint,
  determineSegmentAxis,
  generateNewBendPointOnSourceSegment,
  generateNewBendPointOnTargetSegment,
} from './RectilinearEdgeCalculation';
import { MiddlePoint, UseTemporaryLinesValue } from './useTemporaryLines.types';
import { BendPointData, LocalBendingPointsSetter } from './useBendingPoints.types';
import { XYPositionSetter } from './MultiLabelRectilinearEditableEdge.types';
import { getNodesUpdatedWithHandles } from '../EdgeLayout';

export const useTemporaryLines = (
  edgeId: string,
  originalBendingPoints: XYPosition[],
  localBendingPoints: BendPointData[],
  setLocalBendingPoints: LocalBendingPointsSetter,
  sourceNode: InternalNode<Node<NodeData>>,
  sourceHandleId: string,
  sourcePosition: Position,
  source: XYPosition,
  setSource: XYPositionSetter,
  targetNode: InternalNode<Node<NodeData>>,
  targetHandleId: string,
  targetPosition: Position,
  target: XYPosition,
  setTarget: XYPositionSetter
): UseTemporaryLinesValue => {
  const { getEdges, getNodes, setEdges, setNodes } = useStore();
  const { synchronizeEdgeLayoutData } = useEditableEdgePath();

  const [middleBendingPoints, setMiddleBendingPoints] = useState<MiddlePoint[]>([]);
  const [isSourceSegment, setIsSourceSegment] = useState<boolean>(false);
  const [isTargetSegment, setIsTargetSegment] = useState<boolean>(false);
  const [dragInProgress, setDragInProgress] = useState<boolean>(false);

  const onTemporaryLineDragStop = (_eventData: DraggableData, _index: number) => {
    const edges: Edge<EdgeData>[] = getEdges();
    const edge = edges.find((edge) => edge.id === edgeId);
    if (edge?.data) {
      edge.data.bendingPoints = cleanBendPoint(localBendingPoints.sort((a, b) => a.pathOrder - b.pathOrder));
      let nodes = getNodes();
      if (isSourceSegment) {
        nodes = getNodesUpdatedWithHandles(nodes, sourceNode, edge.id, sourceHandleId, source, sourcePosition);
        setIsSourceSegment(false);
      }
      if (isTargetSegment) {
        nodes = getNodesUpdatedWithHandles(nodes, targetNode, edge.id, targetHandleId, target, targetPosition);
        setIsTargetSegment(false);
      }

      setEdges(edges);
      setNodes(nodes);
      synchronizeEdgeLayoutData(edges, nodes);
      setDragInProgress(false);
    }
  };

  const onTemporaryLineDrag = (eventData: DraggableData, temporaryPointIndex: number, direction: 'x' | 'y') => {
    let newPoints = [...originalBendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index }))];
    if (temporaryPointIndex === 0) {
      if (
        (direction === 'x' && sourceNode.internals.positionAbsolute.y > eventData.y) ||
        (direction === 'y' && sourceNode.internals.positionAbsolute.x > eventData.x)
      ) {
        newPoints = generateNewBendPointOnSourceSegment(
          originalBendingPoints,
          eventData.x,
          eventData.y,
          direction,
          sourceNode.internals.positionAbsolute,
          temporaryPointIndex
        );
      } else if (
        (direction === 'x' && sourceNode.internals.positionAbsolute.y + (sourceNode.height ?? 0) < eventData.y) ||
        (direction === 'y' && sourceNode.internals.positionAbsolute.x + (sourceNode.width ?? 0) < eventData.x)
      ) {
        newPoints = generateNewBendPointOnSourceSegment(
          originalBendingPoints,
          eventData.x,
          eventData.y,
          direction,
          {
            x: sourceNode.internals.positionAbsolute.x + (sourceNode.width ?? 0),
            y: sourceNode.internals.positionAbsolute.y + (sourceNode.height ?? 0),
          },
          temporaryPointIndex
        );
      } else {
        const currentPoint = newPoints[temporaryPointIndex];
        const newSource: XYPosition = { ...source };
        if (direction === 'x') {
          newSource.y = eventData.y;
          if (currentPoint) {
            currentPoint.y = eventData.y;
          }
        } else if (direction === 'y') {
          newSource.x = eventData.x;
          if (currentPoint) {
            currentPoint.x = eventData.x;
          }
        }
        setSource(newSource);
        setIsSourceSegment(true);
      }
    }
    if (temporaryPointIndex === originalBendingPoints.length) {
      if (
        (direction === 'x' && targetNode.internals.positionAbsolute.y > eventData.y) ||
        (direction === 'y' && targetNode.internals.positionAbsolute.x > eventData.x)
      ) {
        newPoints = generateNewBendPointOnTargetSegment(
          originalBendingPoints,
          eventData.x,
          eventData.y,
          direction,
          targetNode.internals.positionAbsolute,
          temporaryPointIndex
        );
      } else if (
        (direction === 'x' && targetNode.internals.positionAbsolute.y + (targetNode.height ?? 0) < eventData.y) ||
        (direction === 'y' && targetNode.internals.positionAbsolute.x + (targetNode.width ?? 0) < eventData.x)
      ) {
        newPoints = generateNewBendPointOnTargetSegment(
          originalBendingPoints,
          eventData.x,
          eventData.y,
          direction,
          {
            x: targetNode.internals.positionAbsolute.x + (targetNode.width ?? 0),
            y: targetNode.internals.positionAbsolute.y + (targetNode.height ?? 0),
          },
          temporaryPointIndex
        );
      } else {
        const prevPoint = newPoints[temporaryPointIndex - 1];
        const newTarget = { ...target };
        if (direction === 'x') {
          if (prevPoint) {
            prevPoint.y = eventData.y;
          }
          newTarget.y = eventData.y;
        } else if (direction === 'y') {
          if (prevPoint) {
            prevPoint.x = eventData.x;
          }
          newTarget.x = eventData.x;
        }
        setTarget(newTarget);
        setIsTargetSegment(true);
      }
    }
    if (temporaryPointIndex > 0 && temporaryPointIndex < originalBendingPoints.length) {
      const prevPoint = newPoints[temporaryPointIndex - 1];
      const currentPoint = newPoints[temporaryPointIndex];
      if (direction === 'x' && prevPoint && currentPoint) {
        prevPoint.y = eventData.y;
        currentPoint.y = eventData.y;
      } else if (direction === 'y' && prevPoint && currentPoint) {
        prevPoint.x = eventData.x;
        currentPoint.x = eventData.x;
      }
    }
    setLocalBendingPoints(newPoints);
    setDragInProgress(true);
  };

  const computeMiddlePoints = () => {
    const middlePoints: MiddlePoint[] = [];
    const reorderBendPoint = [...localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
    if (reorderBendPoint.length > 0) {
      for (let i = 0; i < reorderBendPoint.length; i++) {
        const p1 = i === 0 ? { x: source.x, y: source.y } : reorderBendPoint[i - 1];
        const p2 = reorderBendPoint[i];
        if (p1 && p2) {
          const direction = determineSegmentAxis(p1, p2);
          middlePoints.push({
            ...getMiddlePoint(p1, p2),
            direction: direction,
            segmentLength: direction === 'x' ? Math.abs(p1.x - p2.x) : Math.abs(p1.y - p2.y),
          });
        }
      }
      const lastPoint = reorderBendPoint[reorderBendPoint.length - 1];
      if (lastPoint) {
        const direction = determineSegmentAxis(lastPoint, target);
        middlePoints.push({
          ...getMiddlePoint(lastPoint, { x: target.x, y: target.y }),
          direction: direction,
          segmentLength: direction === 'x' ? Math.abs(lastPoint.x - target.x) : Math.abs(lastPoint.y - target.y),
        });
      }
    } else {
      const direction = determineSegmentAxis(source, target);
      middlePoints.push({
        ...getMiddlePoint({ x: source.x, y: source.y }, { x: target.x, y: target.y }),
        direction: direction,
        segmentLength: direction === 'x' ? Math.abs(source.x - target.x) : Math.abs(source.y - target.y),
      });
    }
    return middlePoints;
  };

  useEffect(() => {
    if (!dragInProgress) {
      setMiddleBendingPoints(computeMiddlePoints());
    }
  }, [localBendingPoints, source.x, source.y, target.x, target.y]);

  return {
    middleBendingPoints,
    onTemporaryLineDragStop,
    onTemporaryLineDrag,
  };
};
