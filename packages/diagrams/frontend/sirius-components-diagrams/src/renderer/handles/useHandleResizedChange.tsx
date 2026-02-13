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
import {
  Edge,
  InternalNode,
  Node,
  NodeChange,
  NodeDimensionChange,
  Position,
  useStoreApi,
  useUpdateNodeInternals,
  XYPosition,
} from '@xyflow/react';
import { useCallback, useState } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DEFAULT_HANDLE_SIZE } from '../edge/EdgeLayout';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ConnectionHandle } from './ConnectionHandles.types';
import { UseHandleResizedChangeState, UseHandleResizedChangeValue } from './useHandleResizedChange.types';

const getHandlePosition = (
  node: InternalNode<Node<NodeData>>,
  position: Position,
  x: number,
  y: number
): XYPosition => {
  let XYPosition: XYPosition = { x, y };
  if (node.height && node.width) {
    switch (position) {
      case Position.Bottom:
        XYPosition = {
          ...XYPosition,
          y: 0 - DEFAULT_HANDLE_SIZE,
        };
        break;
      case Position.Top:
        XYPosition = {
          ...XYPosition,
          y: 0,
        };
        break;
      case Position.Left:
        XYPosition = {
          ...XYPosition,
          x: 0,
        };
        break;
      case Position.Right:
        XYPosition = {
          ...XYPosition,
          x: 0 - DEFAULT_HANDLE_SIZE,
        };
        break;
    }
  }

  return XYPosition;
};

const isResize = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange => change.type === 'dimensions';

export const useHandleResizedChange = (): UseHandleResizedChangeValue => {
  const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = storeApi.getState();
  const updateNodeInternals = useUpdateNodeInternals();

  const [state, setState] = useState<UseHandleResizedChangeState>({
    initialWidth: new Map(),
    initialHeight: new Map(),
  });
  const applyResizeHandleChange = useCallback(
    (
      changes: NodeChange<Node<NodeData>>[],
      nodes: Node<NodeData, DiagramNodeType>[]
    ): Node<NodeData, DiagramNodeType>[] => {
      const updatedConnectionHandles: Map<string, ConnectionHandle[]> = new Map();
      changes.forEach((change: NodeChange<Node<NodeData>>) => {
        if (isResize(change)) {
          const resizedNode = nodeLookup.get(change.id);
          if (resizedNode) {
            const resizedNodeInitialHeight: number | undefined = state.initialHeight.get(resizedNode.id);
            const resizedNodeInitialWidth: number | undefined = state.initialWidth.get(resizedNode.id);

            if (!resizedNodeInitialHeight && !resizedNodeInitialWidth && !!change.resizing) {
              setState((prevState) => ({
                ...prevState,
                initialWidth: new Map(prevState.initialWidth).set(
                  resizedNode.id,
                  resizedNode.width ? resizedNode.width : 0
                ),
                initialHeight: new Map(prevState.initialHeight).set(
                  resizedNode.id,
                  resizedNode.height ? resizedNode.height : 0
                ),
              }));
            } else if (resizedNodeInitialHeight && resizedNodeInitialWidth && !change.resizing) {
              const coefHeight = (change.dimensions?.height ?? 0) / resizedNodeInitialHeight;
              const coefWidth = (change.dimensions?.width ?? 0) / resizedNodeInitialWidth;
              const connectionHandles: ConnectionHandle[] = resizedNode.data.connectionHandles.map((handle) => {
                if (handle.XYPosition) {
                  const XYPosition = getHandlePosition(
                    resizedNode,
                    handle.position,
                    coefWidth * handle.XYPosition.x,
                    coefHeight * handle.XYPosition.y
                  );
                  return {
                    ...handle,
                    XYPosition: {
                      x: XYPosition.x,
                      y: XYPosition.y,
                    },
                  };
                }
                return handle;
              });
              updatedConnectionHandles.set(resizedNode.id, connectionHandles);
              setState((prevState) => ({
                ...prevState,
                initialWidth: new Map(),
                initialHeight: new Map(),
              }));
            }
          }
        }
      });
      if (updatedConnectionHandles.size > 0) {
        updateNodeInternals(Array.from(updatedConnectionHandles.keys()));
        return nodes.map((node) => {
          const updatedConnectionHandle = updatedConnectionHandles.get(node.id);
          if (updatedConnectionHandle) {
            return {
              ...node,
              data: {
                ...node.data,
                connectionHandles: updatedConnectionHandle,
              },
            };
          }
          return node;
        });
      }
      return nodes;
    },
    [state.initialHeight, state.initialWidth]
  );

  return { applyResizeHandleChange };
};
