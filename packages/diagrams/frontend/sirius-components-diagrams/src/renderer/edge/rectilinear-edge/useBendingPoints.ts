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

import { Node, Position, XYPosition } from '@xyflow/react';
import { useState, useEffect } from 'react';
import { DraggableData } from 'react-draggable';
import { useStore } from '../../../representation/useStore';
import { NodeData } from '../../DiagramRenderer.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import {
  getMiddlePoint,
  generateNewBendPointToPreserveRectilinearSegment,
  cleanBendPoint,
  determineSegmentAxis,
  getNewPointToGoAroundNode,
} from './RectilinearEdgeCalculation';
import { useEditableEdgePath } from '../useEditableEdgePath';
import { BendPointData, UseBendingPointsValue } from './useBendingPoints.types';

export const useBendingPoints = (
  edgeId: string,
  originalBendingPoints: XYPosition[],
  sourceX: number,
  sourceY: number,
  targetX: number,
  targetY: number,
  customEdge: boolean,
  sourcePosition: Position,
  targetPosition: Position
): UseBendingPointsValue => {
  const { getEdges, getNodes, setEdges } = useStore();
  const { synchronizeEdgeLayoutData } = useEditableEdgePath();

  const [localBendingPoints, setLocalBendingPoints] = useState<BendPointData[]>(
    originalBendingPoints.map((bendingPoint: XYPosition, index: number) => ({ ...bendingPoint, pathOrder: index }))
  );

  useEffect(() => {
    setLocalBendingPoints(originalBendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })));
  }, [originalBendingPoints.map((point) => point.x + point.y).join()]);

  const onBendingPointDragStop = (eventData: DraggableData, index: number) => {
    const bendingPointDragged = originalBendingPoints[index];
    if (bendingPointDragged) {
      const edges = getEdges();
      const edge = edges.find((edge) => edge.id === edgeId);
      const prevMiddle = getMiddlePoint(
        originalBendingPoints[index - 1] ?? { x: sourceX, y: sourceY },
        bendingPointDragged
      );
      const nextMiddle = getMiddlePoint(
        bendingPointDragged,
        originalBendingPoints[index + 1] ?? { x: targetX, y: targetY }
      );

      const newPoints = generateNewBendPointToPreserveRectilinearSegment(
        originalBendingPoints,
        index,
        eventData.x,
        eventData.y,
        prevMiddle,
        nextMiddle
      );
      const cleanedBendPoint = cleanBendPoint(newPoints.sort((a, b) => a.pathOrder - b.pathOrder));
      if (edge?.data) {
        edge.data.bendingPoints = cleanedBendPoint;
      }
      setEdges(edges);
      if (cleanedBendPoint.length > originalBendingPoints.length) {
        synchronizeEdgeLayoutData(edges, [...getNodes()] as Node<NodeData, DiagramNodeType>[]);
      }
    }
  };

  const onBendingPointDrag = (eventData: DraggableData, index: number) => {
    const bendingPointDragged = originalBendingPoints[index];
    if (bendingPointDragged) {
      const prevMiddle = getMiddlePoint(
        originalBendingPoints[index - 1] ?? { x: sourceX, y: sourceY },
        bendingPointDragged
      );
      const nextMiddle = getMiddlePoint(
        bendingPointDragged,
        originalBendingPoints[index + 1] ?? { x: targetX, y: targetY }
      );

      const newPoints = generateNewBendPointToPreserveRectilinearSegment(
        originalBendingPoints,
        index,
        eventData.x,
        eventData.y,
        prevMiddle,
        nextMiddle
      );

      setLocalBendingPoints(newPoints);
    }
  };

  useEffect(() => {
    if (customEdge) {
      const newPoints = [...originalBendingPoints];
      const firstPoint = newPoints[0];
      const secondPoint = newPoints[1];
      if (firstPoint && secondPoint) {
        if (determineSegmentAxis(firstPoint, secondPoint) === 'x') {
          firstPoint.x = sourceX;
        } else {
          firstPoint.y = sourceY;
        }
      } else {
        if (firstPoint) {
          if (determineSegmentAxis(firstPoint, { x: sourceX, y: sourceY }) === 'x') {
            firstPoint.y = sourceY;
          } else {
            firstPoint.x = sourceX;
          }
        }
      }
      setLocalBendingPoints(newPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })));
    }
  }, [sourceX, sourceY, originalBendingPoints.map((point) => point.x + point.y).join(), customEdge]);

  useEffect(() => {
    if (customEdge) {
      const newPoints = [...originalBendingPoints];
      const lastPoint = newPoints[newPoints.length - 1];
      const penultimatePoint = newPoints[newPoints.length - 2];
      if (lastPoint && penultimatePoint) {
        if (determineSegmentAxis(penultimatePoint, lastPoint) === 'x') {
          lastPoint.x = targetX;
        } else {
          lastPoint.y = targetY;
        }
      } else {
        if (lastPoint) {
          if (determineSegmentAxis({ x: targetX, y: targetY }, lastPoint) === 'x') {
            lastPoint.y = targetY;
          } else {
            lastPoint.x = targetX;
          }
        }
      }
      setLocalBendingPoints(newPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })));
    }
  }, [targetX, targetY, originalBendingPoints.map((point) => point.x + point.y).join(), customEdge]);

  const updateEdgeBendingPoints = (newPoints) => {
    const edges = getEdges();
    const edge = edges.find((edge) => edge.id === edgeId);
    if (edge?.data) {
      edge.data.bendingPoints = newPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index }));
    }
    setEdges(edges);
    synchronizeEdgeLayoutData(edges, [...getNodes()] as Node<NodeData, DiagramNodeType>[]);
  };

  useEffect(() => {
    if (customEdge) {
      const newPoints = [...originalBendingPoints];
      const firstPoint = newPoints[0];
      const secondPoint = newPoints[1];
      if (firstPoint && secondPoint) {
        const segmentAxis = determineSegmentAxis(firstPoint, secondPoint);
        const newPoint = getNewPointToGoAroundNode(segmentAxis, sourcePosition, sourceX, sourceY);

        if (newPoint) {
          newPoints.unshift(newPoint);
          if (segmentAxis === 'x') {
            firstPoint.x = newPoint.x;
          } else if (segmentAxis === 'y') {
            firstPoint.y = newPoint.y;
          }
          updateEdgeBendingPoints(newPoints);
        }
      }
    }
  }, [sourcePosition, customEdge]);

  useEffect(() => {
    if (customEdge) {
      const newPoints = [...originalBendingPoints];
      const lastPoint = newPoints[newPoints.length - 1];
      const penultimatePoint = newPoints[newPoints.length - 2];
      if (lastPoint && penultimatePoint) {
        const segmentAxis = determineSegmentAxis(penultimatePoint, lastPoint);
        const newPoint = getNewPointToGoAroundNode(segmentAxis, targetPosition, targetX, targetY);

        if (newPoint) {
          newPoints.push(newPoint);
          if (segmentAxis === 'x') {
            lastPoint.x = newPoint.x;
          } else if (segmentAxis === 'y') {
            lastPoint.y = newPoint.y;
          }
          updateEdgeBendingPoints(newPoints);
        }
      }
    }
  }, [targetPosition, customEdge]);

  return {
    localBendingPoints,
    setLocalBendingPoints,
    onBendingPointDragStop,
    onBendingPointDrag,
  };
};
