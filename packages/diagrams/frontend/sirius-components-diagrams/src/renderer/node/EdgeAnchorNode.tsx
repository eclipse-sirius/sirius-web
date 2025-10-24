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
import { Edge, Handle, Node, NodeProps, Position, ReactFlowState, useReactFlow, useStore } from '@xyflow/react';
import { memo, useEffect } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getHandlePositionFromNodeAndPath } from '../edge/EdgeLayout';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { EdgeAnchorNodeData } from './EdgeAnchorNode.types';
import { NodeComponentsMap } from './NodeTypes';

const edgePathSelector = (state: ReactFlowState, edgeId: string) => state.edgeLookup.get(edgeId)?.data?.edgePath || '';

const handleStyle = (isHidden: boolean, position: Position): React.CSSProperties => {
  const style: React.CSSProperties = {
    position: 'absolute',
    transform: 'none',
    pointerEvents: 'none',
    border: '1px solid black',
  };
  switch (position) {
    case Position.Left:
    case Position.Right:
      style.top = '-75%';
      style.right = '100%';
      break;
    case Position.Top:
    case Position.Bottom:
      style.bottom = '100%';
      style.left = '-75%';
      break;
  }
  if (isHidden) {
    style.opacity = 0;
  }
  return style;
};

export const EdgeAnchorNode: NodeComponentsMap['edgeAnchorNode'] = memo(
  ({ data, id, positionAbsoluteX, positionAbsoluteY }: NodeProps<Node<EdgeAnchorNodeData>>) => {
    const { setNodes, getEdge, getNode } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

    // Subscribe to the path of the edge used to position the node
    const edgePath = useStore((state) => edgePathSelector(state, id)) as string;

    // Refresh handles if connectionHandles changed
    useRefreshConnectionHandles(id, data.connectionHandles);

    // Update the node position if the path of the edge changed
    useEffect(() => {
      if (edgePath || (!positionAbsoluteX && !positionAbsoluteY)) {
        setNodes((prevNodes) =>
          prevNodes.map((prevNode) => {
            if (prevNode.id === id && edgePath) {
              //Get the point on the svgPath where the EdgeAnchorNode will be placed
              var svgPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
              svgPath.setAttribute('d', edgePath);
              const pathLength = svgPath.getTotalLength();
              let points = svgPath.getPointAtLength(pathLength * 0.5);
              if (data.isLayouted && data.positionRatio) {
                points = svgPath.getPointAtLength(pathLength * data.positionRatio);
              }

              //Update the handles position
              const connectionHandles = data.connectionHandles.map((connectionHandle) => {
                const connectedEdge = getEdge(connectionHandle.edgeId);
                if (connectedEdge) {
                  const otherEndNodeId = connectedEdge.source === id ? connectedEdge.target : connectedEdge.source;
                  const otherEndNode = getNode(otherEndNodeId);
                  if (otherEndNode) {
                    const handlePosition = getHandlePositionFromNodeAndPath(edgePath, points, otherEndNode);
                    return {
                      ...connectionHandle,
                      position: handlePosition,
                    };
                  }
                }
                return connectionHandle;
              });

              return {
                ...prevNode,
                position: points,
                data: {
                  ...prevNode.data,
                  connectionHandles,
                },
              };
            }
            return prevNode;
          })
        );
      }
    }, [edgePath, data.positionRatio, !positionAbsoluteX && !positionAbsoluteY]);

    if (!positionAbsoluteX && !positionAbsoluteY) {
      return null;
    }

    return (
      <div style={{ width: 5, height: 5 }}>
        {data.connectionHandles.map((connectionHandle) => {
          return (
            <Handle
              id={connectionHandle.id ?? ''}
              style={handleStyle(connectionHandle.isHidden, connectionHandle.position)}
              type={connectionHandle.type}
              position={connectionHandle.position}
              key={connectionHandle.id}
              isConnectable={false}
            />
          );
        })}
      </div>
    );
  }
);
