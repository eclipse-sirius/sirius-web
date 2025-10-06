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
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

function isMultipleOfTwo(num: number): boolean {
  return num % 2 === 0;
}

function roundToDecimal(num: number): number {
  return Math.round(num * 10) / 10;
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

  const sourceLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(sourceNode as Node<NodeData, DiagramNodeType>)
  );
  const targetLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(targetNode as Node<NodeData, DiagramNodeType>)
  );

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
    bendingPoints = data.bendingPoints;
  } else {
    const [smoothEdgePath] = getSmoothStepPath({
      sourceX: roundToDecimal(sourceX),
      sourceY: roundToDecimal(sourceY),
      sourcePosition,
      targetX: roundToDecimal(targetX),
      targetY: roundToDecimal(targetY),
      targetPosition,
    });

    const quadraticCurvePoints: {
      x: number;
      y: number;
    }[] = smoothEdgePath.includes('NaN') ? [] : parse(smoothEdgePath).filter((segment) => segment.code === 'Q');

    if (quadraticCurvePoints.length > 0) {
      const firstPoint = quadraticCurvePoints[0];
      if (firstPoint) {
        switch (sourcePosition) {
          case Position.Right:
          case Position.Left:
            bendingPoints.push({ x: firstPoint.x, y: sourceY });
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
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
            bendingPoints.push({ x: sourceX, y: firstPoint.y });
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
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
