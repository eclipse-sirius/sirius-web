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
  getHandlePositionFromXYPosition,
  generateNewSourcePoint,
  isOutOfLines,
  generateNewTargetPoint,
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
      const newBendingPoint = cleanBendPoint(localBendingPoints.sort((a, b) => a.pathOrder - b.pathOrder));
      edge.data.bendingPoints = newBendingPoint;
      let nodes = getNodes();
      if (isSourceSegment) {
        let newPosition: Position | null = null;
        if (newBendingPoint[0]) {
          newPosition = getHandlePositionFromXYPosition(
            sourceNode,
            source,
            determineSegmentAxis(source, newBendingPoint[0])
          );
        }
        nodes = getNodesUpdatedWithHandles(
          nodes,
          sourceNode,
          edge.id,
          sourceHandleId,
          source,
          newPosition ?? sourcePosition
        );
        setIsSourceSegment(false);
      }
      if (isTargetSegment) {
        let newPosition: Position | null = null;
        const lastBendingPoint = newBendingPoint[newBendingPoint.length - 1];
        if (lastBendingPoint) {
          newPosition = getHandlePositionFromXYPosition(
            targetNode,
            target,
            determineSegmentAxis(target, lastBendingPoint)
          );
        }
        nodes = getNodesUpdatedWithHandles(
          nodes,
          targetNode,
          edge.id,
          targetHandleId,
          target,
          newPosition ?? targetPosition
        );
        setIsTargetSegment(false);
      }

      setEdges(edges);
      setNodes(nodes);
      synchronizeEdgeLayoutData(edges, nodes);
      setDragInProgress(false);
    }
  };

  const onTemporaryLineDrag = (eventData: DraggableData, index: number, direction: 'x' | 'y') => {
    let newPoints = [...originalBendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index }))];
    const currentPoint = newPoints[index];
    if (index === 0) {
      if (currentPoint) {
        if (direction === 'x') {
          currentPoint.y = eventData.y;
        } else if (direction === 'y') {
          currentPoint.x = eventData.x;
        }
      }
      if (isOutOfLines(eventData.x, eventData.y, direction, sourceNode)) {
        const newPoint = generateNewBendPointOnSourceSegment(
          eventData.x,
          eventData.y,
          direction,
          sourceNode.internals.positionAbsolute,
          sourcePosition,
          sourceNode.height ?? 0,
          sourceNode.width ?? 0
        );
        if (newPoint) {
          newPoints.forEach((point) => {
            point.pathOrder += 1;
          });
          newPoints.push(newPoint);
        }
        const newSource: XYPosition = generateNewSourcePoint(source, eventData.x, eventData.y, direction, sourceNode);
        setSource(newSource);
      } else {
        const newSource: XYPosition = { ...source };
        if (direction === 'x') {
          newSource.y = eventData.y;
        } else if (direction === 'y') {
          newSource.x = eventData.x;
        }
        setSource(newSource);
        setIsSourceSegment(true);
      }
    }
    if (index === originalBendingPoints.length) {
      const prevPoint = newPoints[index - 1];
      if (prevPoint) {
        if (direction === 'x') {
          prevPoint.y = eventData.y;
        } else if (direction === 'y') {
          prevPoint.x = eventData.x;
        }
      }
      if (isOutOfLines(eventData.x, eventData.y, direction, targetNode)) {
        const newPoint = generateNewBendPointOnTargetSegment(
          eventData.x,
          eventData.y,
          direction,
          targetNode.internals.positionAbsolute,
          index,
          targetPosition,
          targetNode.height ?? 0,
          targetNode.width ?? 0
        );
        if (newPoint) {
          newPoints.push(newPoint);
        }
        const newTarget = generateNewTargetPoint(target, eventData.x, eventData.y, direction, targetNode);
        setTarget(newTarget);
      } else {
        const newTarget: XYPosition = { ...target };
        if (direction === 'x') {
          newTarget.y = eventData.y;
        } else if (direction === 'y') {
          newTarget.x = eventData.x;
        }
        setTarget(newTarget);
        setIsTargetSegment(true);
      }
    }
    if (index > 0 && index < originalBendingPoints.length) {
      if (currentPoint) {
        const prevPoint = newPoints[index - 1];
        if (direction === 'x' && prevPoint) {
          currentPoint.y = eventData.y;
          prevPoint.y = eventData.y;
        } else if (direction === 'y' && prevPoint) {
          currentPoint.x = eventData.x;
          prevPoint.x = eventData.x;
        }
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
