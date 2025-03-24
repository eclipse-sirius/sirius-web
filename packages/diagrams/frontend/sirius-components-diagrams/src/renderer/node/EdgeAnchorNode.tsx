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
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { EdgeAnchorNodeData } from './EdgeAnchorNode.types';
import { NodeComponentsMap } from './NodeTypes';

const edgePathSelector = (state: ReactFlowState, edgeId: string) => state.edgeLookup.get(edgeId)?.data?.edgePath || '';

const handleStyle = (position: Position): React.CSSProperties => {
  const style: React.CSSProperties = {
    position: 'absolute',
    transform: 'none',
    opacity: '0',
    pointerEvents: 'none',
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
  return style;
};

export const EdgeAnchorNode: NodeComponentsMap['edgeAnchorNode'] = memo(
  ({ data, id, positionAbsoluteX, positionAbsoluteY }: NodeProps<Node<EdgeAnchorNodeData>>) => {
    const { setNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

    // Subscribe to the path of the edge used to position the node
    const edgePath = useStore((state) => edgePathSelector(state, id)) as string;

    // Refresh handles if connectionHandles changed
    useRefreshConnectionHandles(id, data.connectionHandles);

    // Update the node position if the path of the edge changed
    useEffect(() => {
      if (edgePath) {
        setNodes((prevNodes) =>
          prevNodes.map((prevNode) => {
            if (prevNode.id === id && edgePath) {
              //Get the middle point of the svgPath
              var svgPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
              svgPath.setAttribute('d', edgePath);
              const pathLength = svgPath.getTotalLength();
              const points = svgPath.getPointAtLength(pathLength * 0.5);

              return {
                ...prevNode,
                position: points,
              };
            }
            return prevNode;
          })
        );
      }
    }, [edgePath]);

    if (!positionAbsoluteX && !positionAbsoluteY) {
      return null;
    }

    return (
      <div style={{ width: 5, height: 5 }}>
        {data.connectionHandles.map((connectionHandle) => {
          return (
            <Handle
              id={connectionHandle.id ?? ''}
              style={handleStyle(connectionHandle.position)}
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
