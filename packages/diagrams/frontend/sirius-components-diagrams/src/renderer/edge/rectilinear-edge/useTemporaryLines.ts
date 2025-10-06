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
import { InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { DraggableData } from 'react-draggable';
import { NodeData } from '../../DiagramRenderer.types';
import { XYPositionSetter } from './MultiLabelRectilinearEditableEdge.types';
import {
  determineSegmentAxis,
  generateNewBendPointOnSegment,
  generateNewHandlePoint,
  getMiddlePoint,
  isOutOfLines,
} from './RectilinearEdgeCalculation';
import { BendPointData, LocalBendingPointsSetter } from './useBendingPoints.types';
import { useEdgeDragStopHandler } from './useEdgeDragStopHandler';
import { MiddlePoint, UseTemporaryLinesValue, TemporaryLinesState } from './useTemporaryLines.types';

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

  const [middleBendingPoints, setMiddleBendingPoints] = useState<MiddlePoint[]>([]);

  const [state, setState] = useState<TemporaryLinesState>({
    isSourceSegment: false,
    isTargetSegment: false,
    dragInProgress: false,
  });

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
        const newPoint = generateNewBendPointOnSegment(
          eventData.x,
          eventData.y,
          direction,
          sourceNode.internals.positionAbsolute,
          sourcePosition,
          sourceNode.height ?? 0,
          sourceNode.width ?? 0,
          0
        );
        if (newPoint) {
          newPoints.forEach((point) => {
            point.pathOrder += 1;
          });
          newPoints.push(newPoint);
        }
        const newSource: XYPosition = generateNewHandlePoint(source, eventData.x, eventData.y, direction, sourceNode);
        setSource(newSource);
      } else {
        const newSource: XYPosition = { ...source };
        if (direction === 'x') {
          newSource.y = eventData.y;
          newSource.x =
            sourceNode.internals.positionAbsolute.x + (sourcePosition === Position.Right ? sourceNode.width ?? 0 : 0);
        } else if (direction === 'y') {
          newSource.x = eventData.x;
          newSource.y =
            sourceNode.internals.positionAbsolute.y + (sourcePosition === Position.Bottom ? sourceNode.height ?? 0 : 0);
        }
        setSource(newSource);
        setState((prevState) => ({ ...prevState, isSourceSegment: true }));
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
        const newPoint = generateNewBendPointOnSegment(
          eventData.x,
          eventData.y,
          direction,
          targetNode.internals.positionAbsolute,
          targetPosition,
          targetNode.height ?? 0,
          targetNode.width ?? 0,
          index + 1
        );
        if (newPoint) {
          newPoints.push(newPoint);
        }
        const newTarget = generateNewHandlePoint(target, eventData.x, eventData.y, direction, targetNode);
        setTarget(newTarget);
      } else {
        const newTarget: XYPosition = { ...target };
        if (direction === 'x') {
          newTarget.y = eventData.y;
          newTarget.x =
            targetNode.internals.positionAbsolute.x + (targetPosition === Position.Right ? targetNode.width ?? 0 : 0);
        } else if (direction === 'y') {
          newTarget.x = eventData.x;
          newTarget.y =
            targetNode.internals.positionAbsolute.y + (targetPosition === Position.Bottom ? targetNode.height ?? 0 : 0);
        }
        setTarget(newTarget);
        setState((prevState) => ({ ...prevState, isTargetSegment: true }));
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
    setState((prevState) => ({ ...prevState, dragInProgress: true }));
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
    if (!state.dragInProgress) {
      setMiddleBendingPoints(computeMiddlePoints());
    }
  }, [localBendingPoints, source.x, source.y, target.x, target.y]);

  return {
    middleBendingPoints,
    onTemporaryLineDragStop,
    onTemporaryLineDrag,
  };
};
