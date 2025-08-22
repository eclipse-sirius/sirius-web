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

import { Node, XYPosition, Position, InternalNode } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { DraggableData } from 'react-draggable';
import { useStore } from '../../../representation/useStore';
import { NodeData } from '../../DiagramRenderer.types';
import { useEditableEdgePath } from '../useEditableEdgePath';
import {
  cleanBendPoint,
  determineSegmentAxis,
  generateNewBendPointOnSegment,
  generateNewHandlePoint,
  getHandlePositionFromXYPosition,
  isOutOfLines,
} from './RectilinearEdgeCalculation';
import { BendPointData, UseBendingPointsValue, BendingPointsState } from './useBendingPoints.types';
import { XYPositionSetter } from './MultiLabelRectilinearEditableEdge.types';
import { getNodesUpdatedWithHandles } from '../EdgeLayout';

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
  const { getEdges, getNodes } = useStore();
  const { synchronizeEdgeLayoutData } = useEditableEdgePath();

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
    const edges = getEdges();
    let nodes = getNodes();
    const edge = edges.find((edge) => edge.id === edgeId);

    const newBendingPoint = cleanBendPoint(localBendingPoints.sort((a, b) => a.pathOrder - b.pathOrder));
    if (edge?.data) {
      edge.data.bendingPoints = newBendingPoint;
      if (state.isSourceSegment) {
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
        setState((prevState) => ({ ...prevState, isSourceSegment: false }));
      }
      if (state.isTargetSegment) {
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
      }
      setState((prevState) => ({ ...prevState, dragInProgress: false }));
      synchronizeEdgeLayoutData(edges, nodes);
    }
  };

  const onBendingPointDrag = (eventData: DraggableData, index: number, direction: 'x' | 'y') => {
    let newPoints = [...originalBendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index }))];
    const bendingPointDragged = newPoints[index];
    if (bendingPointDragged) {
      bendingPointDragged.x = eventData.x;
      bendingPointDragged.y = eventData.y;
      if (index === 0) {
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
            const nextPoint = newPoints[index + 1];
            newPoints.forEach((point) => {
              point.pathOrder += 1;
            });
            newPoints.push(newPoint);
            if (nextPoint) {
              if (direction === 'y') {
                nextPoint.y = eventData.y;
              } else if (direction === 'x') {
                nextPoint.x = eventData.x;
              }
            }
          }
          const newSource: XYPosition = generateNewHandlePoint(source, eventData.x, eventData.y, direction, sourceNode);
          setSource(newSource);
        } else {
          const newSource: XYPosition = { ...source };
          const nextPoint = newPoints[index + 1];
          if (direction === 'x') {
            newSource.y = eventData.y;
            newSource.x =
              sourceNode.internals.positionAbsolute.x + (sourcePosition === Position.Right ? sourceNode.width ?? 0 : 0);
            if (nextPoint) {
              nextPoint.x = eventData.x;
            }
          } else if (direction === 'y') {
            newSource.x = eventData.x;
            newSource.y =
              sourceNode.internals.positionAbsolute.y +
              (sourcePosition === Position.Bottom ? sourceNode.height ?? 0 : 0);
            if (nextPoint) {
              nextPoint.y = eventData.y;
            }
          }
          setSource(newSource);
          setState((prevState) => ({ ...prevState, isSourceSegment: true }));
        }
      }
      if (index === originalBendingPoints.length - 1) {
        const lastSegmentDirection = direction === 'x' ? 'y' : 'x';
        if (isOutOfLines(eventData.x, eventData.y, lastSegmentDirection, targetNode)) {
          const newPoint = generateNewBendPointOnSegment(
            eventData.x,
            eventData.y,
            lastSegmentDirection,
            targetNode.internals.positionAbsolute,
            targetPosition,
            targetNode.height ?? 0,
            targetNode.width ?? 0,
            index + 1
          );
          if (newPoint) {
            newPoints.push(newPoint);
            const prevPoint = newPoints[index - 1];
            if (prevPoint) {
              if (direction === 'x') {
                prevPoint.y = eventData.y;
              } else if (direction === 'y') {
                prevPoint.x = eventData.x;
              }
            }
          }
          const newTarget: XYPosition = generateNewHandlePoint(
            target,
            eventData.x,
            eventData.y,
            lastSegmentDirection,
            targetNode
          );
          setTarget(newTarget);
        } else {
          const newTarget: XYPosition = { ...target };
          const prevPoint = newPoints[index - 1];
          if (direction === 'y') {
            newTarget.y = eventData.y;
            newTarget.x =
              targetNode.internals.positionAbsolute.x + (targetPosition === Position.Right ? targetNode.width ?? 0 : 0);
            if (prevPoint) {
              prevPoint.x = eventData.x;
            }
          } else if (direction === 'x') {
            newTarget.x = eventData.x;
            newTarget.y =
              targetNode.internals.positionAbsolute.y +
              (targetPosition === Position.Bottom ? targetNode.height ?? 0 : 0);
            if (prevPoint) {
              prevPoint.y = eventData.y;
            }
          }
          setTarget(newTarget);
          setState((prevState) => ({ ...prevState, isTargetSegment: true }));
        }
      }
      if (index > 0 && index < originalBendingPoints.length) {
        const nextPoint = newPoints[index + 1];
        const prevPoint = newPoints[index - 1];

        if (prevPoint && nextPoint) {
          if (direction === 'x') {
            nextPoint.x = eventData.x;
            prevPoint.y = eventData.y;
          } else if (direction === 'y') {
            prevPoint.x = eventData.x;
            nextPoint.y = eventData.y;
          }
        }
      }

      setState((prevState) => ({ ...prevState, dragInProgress: true }));
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
  };
};
