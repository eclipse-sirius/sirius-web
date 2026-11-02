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
import { Edge, EdgeProps, InternalNode, Node, useInternalNode } from '@xyflow/react';
import { useContext, memo } from 'react';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { getEdgeEndpointCoordinates } from './EdgeLayout';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelObliqueEditableEdge } from './oblique-edge/MultiLabelObliqueEditableEdge';

export const ObliqueEdgeWrapper = memo((props: EdgeProps<Edge<MultiLabelEdgeData>>) => {
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

  const sourceEndpoint = getEdgeEndpointCoordinates(
    sourceNode,
    sourcePosition,
    sourceHandleId ?? '',
    markerStart,
    sourceLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );
  const targetEndpoint = getEdgeEndpointCoordinates(
    targetNode,
    targetPosition,
    targetHandleId ?? '',
    markerEnd,
    targetLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );

  if (data?.relativePositionBendingPoints && sourceNode.data.moving && targetNode.data.moving) {
    data.bendingPoints = data.relativePositionBendingPoints.map((relativePositionBendingPoint) => ({
      x: relativePositionBendingPoint.x + sourceEndpoint.x,
      y: relativePositionBendingPoint.y + sourceEndpoint.y,
    }));
  }

  return (
    <MultiLabelObliqueEditableEdge
      {...props}
      sourceX={sourceEndpoint.x}
      sourceY={sourceEndpoint.y}
      targetX={targetEndpoint.x}
      targetY={targetEndpoint.y}
      bendingPoints={data?.bendingPoints ?? []}
    />
  );
});
