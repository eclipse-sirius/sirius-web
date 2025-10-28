/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import {
  Edge,
  EdgeProps,
  getSmoothStepPath,
  InternalNode,
  Node,
  Position,
  useInternalNode,
  XYPosition,
} from '@xyflow/react';
import { memo, useContext } from 'react';
import parse from 'svg-path-parser';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData, RectilinearTurnPreference } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

function isMultipleOfTwo(num: number): boolean {
  return num % 2 === 0;
}

const DEFAULT_TURN_PREFERENCE: RectilinearTurnPreference = 'target';
const MIN_TURN_CLEARANCE = 4;

function interpolateHalfway(from: number, to: number): number {
  return from + (to - from) * 0.5;
}

function computePreferredTurnCoordinate(
  defaultCoordinate: number,
  sourceCoordinate: number,
  targetCoordinate: number,
  preference: RectilinearTurnPreference
): number {
  const safeDefault = Number.isFinite(defaultCoordinate) ? defaultCoordinate : sourceCoordinate;
  switch (preference) {
    case 'source':
      return interpolateHalfway(safeDefault, sourceCoordinate);
    case 'target':
      return interpolateHalfway(safeDefault, targetCoordinate);
    case 'middle': {
      const midpoint = (sourceCoordinate + targetCoordinate) / 2;
      return Number.isFinite(midpoint) ? midpoint : safeDefault;
    }
    default:
      return safeDefault;
  }
}

function enforceMinimumClearance(
  coordinate: number,
  origin: number,
  fallback: number,
  directionHint: number
): number {
  const clearance = Math.abs(coordinate - origin);
  if (clearance >= MIN_TURN_CLEARANCE) {
    return coordinate;
  }
  const fallbackClearance = Math.abs(fallback - origin);
  if (fallbackClearance >= MIN_TURN_CLEARANCE) {
    return fallback;
  }
  const direction = directionHint !== 0 ? directionHint : fallbackClearance !== 0 ? Math.sign(fallback - origin) : 1;
  return origin + MIN_TURN_CLEARANCE * direction;
}

export const SmoothStepEdgeWrapper = memo((props: EdgeProps<Edge<MultiLabelEdgeData>>) => {
  const {
    source,
    target,
    markerEnd,
    markerStart,
    sourcePosition,
    targetPosition,
    sourceHandleId,
    targetHandleId,
    data,
  } = props;
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const sourceNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(source);
  const targetNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(target);

  if (!sourceNode || !targetNode) {
    return null;
  }

  // The rendering flow:
  // 1. Resolve layout handlers to let nodes customize handle positions when needed.
  // 2. Compute handle coordinates for both endpoints (honouring node-specific overrides).
  // 3. Offset the endpoints so markers still touch the node border visually.
  // 4. Build the rectilinear bending points, either reusing user data or converting a smooth path.
  // 5. Delegate to the multi-label rectilinear edge renderer with the assembled geometry.

  const sourceLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(sourceNode as Node<NodeData, DiagramNodeType>)
  );
  const targetLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(targetNode as Node<NodeData, DiagramNodeType>)
  );

  // Coordinates always lie on the declared position of the node, optionally adjusted by the handler.
  let { x: sourceX, y: sourceY } = getHandleCoordinatesByPosition(
    sourceNode,
    sourcePosition,
    sourceHandleId ?? '',
    sourceLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );
  let { x: targetX, y: targetY } = getHandleCoordinatesByPosition(
    targetNode,
    targetPosition,
    targetHandleId ?? '',
    targetLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );

  // trick to have the source of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleSourceRadius = markerStart == undefined || markerStart.includes('None') ? 2 : 3;
  switch (sourcePosition) {
    case Position.Right:
      sourceX = sourceX + handleSourceRadius;
      break;
    case Position.Left:
      sourceX = sourceX - handleSourceRadius;
      break;
    case Position.Top:
      sourceY = sourceY - handleSourceRadius;
      break;
    case Position.Bottom:
      sourceY = sourceY + handleSourceRadius;
      break;
  }
  // trick to have the target of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleTargetRadius = markerEnd == undefined || markerEnd.includes('None') ? 2 : 3;
  switch (targetPosition) {
    case Position.Right:
      targetX = targetX + handleTargetRadius;
      break;
    case Position.Left:
      targetX = targetX - handleTargetRadius;
      break;
    case Position.Top:
      targetY = targetY - handleTargetRadius;
      break;
    case Position.Bottom:
      targetY = targetY + handleTargetRadius;
      break;
  }

  let bendingPoints: XYPosition[] = [];
  if (data && data.bendingPoints && data.bendingPoints.length > 0) {
    // Preserve user-authored geometry; downstream component treats this as a custom edge.
    bendingPoints = data.bendingPoints;
  } else {
    // Fallback: translate the smooth quadratic path from React Flow into Manhattan-style bends.
    const [smoothEdgePath] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
    });

    const quadraticCurvePoints: {
      x: number;
      y: number;
    }[] = smoothEdgePath.includes('NaN') ? [] : parse(smoothEdgePath).filter((segment) => segment.code === 'Q');

    if (quadraticCurvePoints.length > 0) {
      // When no custom bends are given, we shape the first turn according to the chosen strategy.
      const turnPreference: RectilinearTurnPreference =
        data?.rectilinearTurnPreference ?? DEFAULT_TURN_PREFERENCE;
      const firstPoint = quadraticCurvePoints[0];
      if (firstPoint) {
        switch (sourcePosition) {
          case Position.Right:
          case Position.Left:
            // Seed the first bend so the polyline departs horizontally from a left/right handle.
            {
              const defaultTurnX = firstPoint.x ?? sourceX;
              const preferredTurnX = enforceMinimumClearance(
                computePreferredTurnCoordinate(defaultTurnX, sourceX, targetX, turnPreference),
                sourceX,
                defaultTurnX,
                Math.sign(defaultTurnX - sourceX) || Math.sign(targetX - sourceX)
              );
              quadraticCurvePoints[0] = { ...quadraticCurvePoints[0], x: preferredTurnX };
              bendingPoints.push({ x: preferredTurnX, y: sourceY });
            }
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
                // Even/odd index alternation swaps axis, keeping the path rectilinear.
                if (isMultipleOfTwo(i)) {
                  bendingPoints.push({ x: currentPoint.x, y: previousPoint.y });
                } else {
                  bendingPoints.push({ x: previousPoint.x, y: currentPoint.y });
                }
              }
            }
            break;
          case Position.Top:
          case Position.Bottom:
            // Seed the first bend so the polyline departs vertically from a top/bottom handle.
            {
              const defaultTurnY = firstPoint.y ?? sourceY;
              const preferredTurnY = enforceMinimumClearance(
                computePreferredTurnCoordinate(defaultTurnY, sourceY, targetY, turnPreference),
                sourceY,
                defaultTurnY,
                Math.sign(defaultTurnY - sourceY) || Math.sign(targetY - sourceY)
              );
              quadraticCurvePoints[0] = { ...quadraticCurvePoints[0], y: preferredTurnY };
              bendingPoints.push({ x: sourceX, y: preferredTurnY });
            }
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
                // Even/odd index alternation swaps axis, keeping the path rectilinear.
                if (isMultipleOfTwo(i)) {
                  bendingPoints.push({ x: previousPoint.x, y: currentPoint.y });
                } else {
                  bendingPoints.push({ x: currentPoint.x, y: previousPoint.y });
                }
              }
            }
            break;
        }
      }
    }
  }

  return (
    <MultiLabelRectilinearEditableEdge
      {...props}
      sourceX={sourceX}
      sourceY={sourceY}
      targetX={targetX}
      targetY={targetY}
      bendingPoints={bendingPoints}
      customEdge={!!(data && data.bendingPoints && data.bendingPoints.length > 0)}
      sourceNode={sourceNode}
      targetNode={targetNode}
    />
  );
});
