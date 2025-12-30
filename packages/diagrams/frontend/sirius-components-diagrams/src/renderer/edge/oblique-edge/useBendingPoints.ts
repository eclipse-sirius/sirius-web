/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { Edge, Node, useStoreApi, XYPosition } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { DraggableData } from 'react-draggable';
import { UseBendingPointsValue, BendingPointsState } from './useBendingPoints.types';
import { useEditableEdgePath } from '../useEditableEdgePath';
import { useStore } from '../../../representation/useStore';
import { NodeData, EdgeData } from '../../DiagramRenderer.types';
import { isMultipleOfTwo, getMiddlePoint } from '../rectilinear-edge/RectilinearEdgeCalculation';

const calculateMidPoints = (
  points: XYPosition[],
  sourceX: number,
  sourceY: number,
  targetX: number,
  targetY: number
): XYPosition[] => {
  if (points.length === 0) {
    return [getMiddlePoint({ x: sourceX, y: sourceY }, { x: targetX, y: targetY })];
  }
  const result: XYPosition[] = [];

  const firstPoint = points[0];
  if (firstPoint) {
    result.push(getMiddlePoint({ x: sourceX, y: sourceY }, firstPoint));
  }

  for (let i = 0; i < points.length; i++) {
    const prevPoint = points[i - 1];
    const currentPoint = points[i];
    if (currentPoint) {
      if (prevPoint) {
        result.push(getMiddlePoint(prevPoint, currentPoint));
      }
      result.push(currentPoint);
    }
  }

  const lastPoint = points[points.length - 1];
  if (lastPoint) {
    result.push(getMiddlePoint(lastPoint, { x: targetX, y: targetY }));
  }

  return result;
};

export const useBendingPoints = (
  edgeId: string,
  originalBendingPoints: XYPosition[],
  sourceX: number,
  sourceY: number,
  targetX: number,
  targetY: number
): UseBendingPointsValue => {
  const { getNodes, setEdges } = useStore();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { synchronizeEdgeLayoutData } = useEditableEdgePath();
  const [state, setState] = useState<BendingPointsState>({
    localBendingPoints: [],
    draggingPointIndex: null,
  });

  useEffect(() => {
    setState((prevState) => ({
      ...prevState,
      localBendingPoints: calculateMidPoints(originalBendingPoints, sourceX, sourceY, targetX, targetY),
    }));
  }, [originalBendingPoints.map((point) => point.x + point.y).join(), sourceX, sourceY, targetX, targetY]);

  const onBendingPointDragStop = (_eventData: DraggableData, index: number) => {
    const edges = store.getState().edges;
    const edge = store.getState().edgeLookup.get(edgeId);
    if (edge?.data) {
      const newBendingPoints: XYPosition[] = state.localBendingPoints.filter(
        (_, i) => !isMultipleOfTwo(i) || i === index
      );
      edge.data.bendingPoints = newBendingPoints;
      setEdges(edges);
      synchronizeEdgeLayoutData(edges, getNodes());
      setState((prevState) => ({
        ...prevState,
        localBendingPoints: calculateMidPoints(newBendingPoints, sourceX, sourceY, targetX, targetY),
        draggingPointIndex: null,
      }));
    }
  };

  const onBendingPointDrag = (eventData: DraggableData, index: number) => {
    if (isMultipleOfTwo(index)) {
      setState((prevState) => {
        const newPoints = [...prevState.localBendingPoints];
        newPoints[index] = { x: eventData.x, y: eventData.y };
        return { localBendingPoints: newPoints, draggingPointIndex: index };
      });
    } else {
      setState((prevState) => {
        const newPoints = [...prevState.localBendingPoints];
        newPoints[index] = { x: eventData.x, y: eventData.y };
        return {
          localBendingPoints: calculateMidPoints(
            newPoints.filter((_point, index) => !isMultipleOfTwo(index)),
            sourceX,
            sourceY,
            targetX,
            targetY
          ),
          draggingPointIndex: index,
        };
      });
    }
  };

  return {
    localBendingPoints: state.localBendingPoints,
    draggingPointIndex: state.draggingPointIndex,
    onBendingPointDragStop,
    onBendingPointDrag,
  };
};
