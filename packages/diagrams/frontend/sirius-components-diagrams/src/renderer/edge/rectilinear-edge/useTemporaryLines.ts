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
import { useState, useEffect } from 'react';
import { DraggableData } from 'react-draggable';
import { useStore } from '../../../representation/useStore';
import { useEditableEdgePath } from '../useEditableEdgePath';
import { cleanBendPoint, getMiddlePoint } from './RectilinearEdgeCalculation';
import { MiddlePoint, UseTemporaryLinesValue } from './useTemporaryLines.types';
import { BendPointData, LocalBendingPointsSetter } from './useBendingPoints.types';

export const useTemporaryLines = (
  edgeId: string,
  localBendingPoints: BendPointData[],
  setLocalBendingPoints: LocalBendingPointsSetter,
  sourceX: number,
  sourceY: number,
  targetX: number,
  targetY: number
): UseTemporaryLinesValue => {
  const { getEdges, setEdges } = useStore();
  const { synchronizeEdgeLayoutData } = useEditableEdgePath();

  const [middleBendingPoints, setMiddleBendingPoints] = useState<MiddlePoint[]>([]);

  const onTemporaryLineDragStop = (_eventData: DraggableData, _index: number) => {
    const edges = getEdges();
    const edge = edges.find((edge) => edge.id === edgeId);
    if (edge?.data) {
      edge.data.bendingPoints = cleanBendPoint(localBendingPoints);
      setEdges(edges);
      synchronizeEdgeLayoutData(edges);
    }
  };

  const onTemporaryLineDrag = (eventData: DraggableData, temporaryPointIndex: number, direction: 'x' | 'y') => {
    const newPoints = [...localBendingPoints];
    const prevPoint = newPoints[temporaryPointIndex - 1];
    const currentPoint = newPoints[temporaryPointIndex];
    if (direction === 'x' && prevPoint && currentPoint) {
      prevPoint.x = eventData.x;
      currentPoint.x = eventData.x;
    } else if (direction === 'y' && prevPoint && currentPoint) {
      prevPoint.y = eventData.y;
      currentPoint.y = eventData.y;
    }
    setLocalBendingPoints(newPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })));
  };

  const computeMiddlePoints = () => {
    const middlePoints: MiddlePoint[] = [];
    const margin = 10;
    const reorderBendPoint = [...localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
    if (reorderBendPoint.length > 0) {
      for (let i = 0; i < reorderBendPoint.length; i++) {
        const p1 = i === 0 ? { x: sourceX, y: sourceY } : reorderBendPoint[i - 1];
        const p2 = reorderBendPoint[i];
        if (p1 && p2) {
          const direction = Math.abs(p1.x - p2.x) <= margin ? 'x' : 'y';
          middlePoints.push({
            ...getMiddlePoint(p1, p2),
            direction: direction,
            segmentLength: direction !== 'x' ? Math.abs(p1.x - p2.x) : Math.abs(p1.y - p2.y),
          });
        }
      }
      const lastPoint = reorderBendPoint[reorderBendPoint.length - 1];
      if (lastPoint) {
        const direction = lastPoint.x === targetX ? 'x' : 'y';
        middlePoints.push({
          ...getMiddlePoint(lastPoint, { x: targetX, y: targetY }),
          direction: direction,
          segmentLength: direction !== 'x' ? Math.abs(lastPoint.x - targetX) : Math.abs(lastPoint.y - targetY),
        });
      }
    } else {
      const direction = sourceX === targetX ? 'x' : 'y';
      middlePoints.push({
        ...getMiddlePoint({ x: sourceX, y: sourceY }, { x: targetX, y: targetY }),
        direction: direction,
        segmentLength: direction !== 'x' ? Math.abs(sourceX - targetX) : Math.abs(sourceY - targetY),
      });
    }
    return middlePoints;
  };

  useEffect(() => {
    setMiddleBendingPoints(computeMiddlePoints());
  }, [localBendingPoints, sourceX, sourceY, targetX, targetY]);

  return {
    middleBendingPoints,
    onTemporaryLineDragStop,
    onTemporaryLineDrag,
  };
};
