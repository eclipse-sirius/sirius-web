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

import { InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { DraggableData } from 'react-draggable';
import { initializeEdgeDrag, moveBendingPoint } from '../geometry/edgeGeometry';
import { NodeData } from '../../DiagramRenderer.types';
import { XYPositionSetter } from './MultiLabelRectilinearEditableEdge.types';
import { determineSegmentAxis } from './RectilinearEdgeCalculation';
import { BendingPointsState, BendPointData, UseBendingPointsValue } from './BendingPointsCalculation.types';
import { useEdgeDragStopHandler } from './useEdgeDragStopHandler';

export const useBendingPoints = (
  edgeId: string,
  originalBendingPoints: XYPosition[],
  source: XYPosition,
  setSource: XYPositionSetter,
  sourceNode: InternalNode<Node<NodeData>>,
  sourceHandleId: string,
  sourcePosition: Position,
  target: XYPosition,
  setTarget: XYPositionSetter,
  targetNode: InternalNode<Node<NodeData>>,
  targetHandleId: string,
  targetPosition: Position,
  customEdge: boolean
): UseBendingPointsValue => {
  const { handleDragStop } = useEdgeDragStopHandler();

  const [localBendingPoints, setLocalBendingPoints] = useState<BendPointData[]>(
    originalBendingPoints.map((bendingPoint: XYPosition, index: number) => ({ ...bendingPoint, pathOrder: index }))
  );
  const [state, setState] = useState<BendingPointsState>({
    isSourceSegment: false,
    isTargetSegment: false,
    dragInProgress: false,
  });

  useEffect(() => {
    setLocalBendingPoints(originalBendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })));
  }, [originalBendingPoints.map((point) => point.x + point.y).join()]);

  const onBendingPointDragStop = (_eventData: DraggableData) => {
    handleDragStop(
      edgeId,
      source,
      setSource,
      sourceNode,
      sourceHandleId,
      sourcePosition,
      target,
      setTarget,
      targetNode,
      targetHandleId,
      targetPosition,
      state.isSourceSegment,
      state.isTargetSegment,
      localBendingPoints
    );
    setState((prevState) => ({ ...prevState, isSourceSegment: false, isTargetSegment: false, dragInProgress: false }));
  };

  const onBendingPointDrag = (eventData: DraggableData, index: number, direction: 'x' | 'y') => {
    if (originalBendingPoints[index]) {
      const edgeDragData = initializeEdgeDrag(
        {
          edgeId,
          source,
          target,
          bendingPoints: originalBendingPoints,
          sourceNode,
          targetNode,
          sourcePosition,
          targetPosition,
          customEdge: false,
        },
        { index, direction, isSegmentDragged: false }
      );
      const {
        geometry: { source: newSource, target: newTarget, bendingPoints: newPoints },
        isSourceSegment,
        isTargetSegment,
      } = moveBendingPoint(
        { ...edgeDragData, isSourceSegment: state.isSourceSegment, isTargetSegment: state.isTargetSegment },
        eventData
      );
      if (index === 0) {
        setSource(newSource);
      }
      if (index === originalBendingPoints.length - 1) {
        setTarget(newTarget);
      }
      setState((prevState) => ({ ...prevState, isSourceSegment, isTargetSegment, dragInProgress: true }));
      setLocalBendingPoints(newPoints);
    }
  };

  useEffect(() => {
    if (customEdge && !state.dragInProgress) {
      const newPoints = [...originalBendingPoints];
      const firstPoint = newPoints[0];
      const secondPoint = newPoints[1];
      if (firstPoint && secondPoint) {
        if (determineSegmentAxis(firstPoint, secondPoint) === 'x') {
          firstPoint.x = source.x;
        } else {
          firstPoint.y = source.y;
        }
      } else {
        if (firstPoint) {
          if (determineSegmentAxis(firstPoint, source) === 'x') {
            firstPoint.y = source.y;
          } else {
            firstPoint.x = source.x;
          }
        }
      }
      setLocalBendingPoints(newPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })));
    }
  }, [source.x, source.y, originalBendingPoints.map((point) => point.x + point.y).join(), customEdge]);

  useEffect(() => {
    if (customEdge && !state.dragInProgress) {
      const newPoints = [...originalBendingPoints];
      const lastPoint = newPoints[newPoints.length - 1];
      const penultimatePoint = newPoints[newPoints.length - 2];
      if (lastPoint && penultimatePoint) {
        if (determineSegmentAxis(penultimatePoint, lastPoint) === 'x') {
          lastPoint.x = target.x;
        } else {
          lastPoint.y = target.y;
        }
      } else {
        if (lastPoint) {
          if (determineSegmentAxis(target, lastPoint) === 'x') {
            lastPoint.y = target.y;
          } else {
            lastPoint.x = target.x;
          }
        }
      }
      setLocalBendingPoints(newPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })));
    }
  }, [target.x, target.y, originalBendingPoints.map((point) => point.x + point.y).join(), customEdge]);

  return {
    localBendingPoints,
    setLocalBendingPoints,
    onBendingPointDragStop,
    onBendingPointDrag,
    isBendingPointDragged: state.dragInProgress,
  };
};
