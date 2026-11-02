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
import { useStore } from '../../../representation/useStore';
import { NodeData } from '../../DiagramRenderer.types';
import { XYPositionSetter } from './MultiLabelRectilinearEditableEdge.types';
import { calculateTemporaryLines, computeMiddlePoints } from './TemporaryLinesCalculation';
import { BendPointData, LocalBendingPointsSetter } from './BendingPointsCalculation.types';
import { useEdgeDragStopHandler } from './useEdgeDragStopHandler';
import { MiddlePoint, TemporaryLinesState, UseTemporaryLinesValue } from './TemporaryLinesCalculation.types';

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
  const { handleDragStop } = useEdgeDragStopHandler();
  const { setNodes } = useStore();

  const [middleBendingPoints, setMiddleBendingPoints] = useState<MiddlePoint[]>([]);

  const [state, setState] = useState<TemporaryLinesState>({
    isSourceSegment: false,
    isTargetSegment: false,
    dragInProgress: false,
  });

  const onDragStart = () => {
    setNodes((previousNodes) =>
      previousNodes.map((previousNode) => {
        if (previousNode.id === sourceNode.id || previousNode.id === targetNode.id) {
          return {
            ...previousNode,
            data: {
              ...previousNode.data,
              connectionHandles: previousNode.data.connectionHandles.map((handle) => {
                if (handle.edgeId === edgeId) {
                  return {
                    ...handle,
                    isHidden: true,
                  };
                }
                return handle;
              }),
            },
          };
        }
        return previousNode;
      })
    );
  };

  const onTemporaryLineDragStop = (_eventData: DraggableData, _index: number) => {
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

  const onTemporaryLineDrag = (eventData: DraggableData, index: number, direction: 'x' | 'y') => {
    const {
      source: newSource,
      target: newTarget,
      localBendingPoints: newPoints,
      isSourceSegment,
      isTargetSegment,
    } = calculateTemporaryLines(
      {
        originalGeometry: {
          source,
          target,
          bendingPoints: originalBendingPoints,
          sourceNode,
          targetNode,
          sourcePosition,
          targetPosition,
        },
        index,
        direction,
        isSourceSegment: state.isSourceSegment,
        isTargetSegment: state.isTargetSegment,
      },
      eventData
    );
    if (index === 0) {
      setSource(newSource);
    }
    if (index === originalBendingPoints.length) {
      setTarget(newTarget);
    }
    setLocalBendingPoints(newPoints);
    setState((prevState) => ({ ...prevState, isSourceSegment, isTargetSegment, dragInProgress: true }));
  };

  useEffect(() => {
    if (!state.dragInProgress) {
      setMiddleBendingPoints(computeMiddlePoints(localBendingPoints, source, target));
    }
  }, [localBendingPoints, source.x, source.y, target.x, target.y]);

  return {
    middleBendingPoints,
    onDragStart,
    onTemporaryLineDragStop,
    onTemporaryLineDrag,
    isSegmentDragged: state.dragInProgress,
  };
};
