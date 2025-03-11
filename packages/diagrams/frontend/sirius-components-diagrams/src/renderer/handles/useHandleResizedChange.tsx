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
import {
  Edge,
  InternalNode,
  Node,
  NodeChange,
  NodeDimensionChange,
  Position,
  useStoreApi,
  XYPosition,
} from '@xyflow/react';
import { useCallback, useState } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DEFAULT_HANDLE_SIZE } from '../edge/EdgeLayout';
import { DiagramNodeType } from '../node/NodeTypes.types';
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

export const useHandleResizedChange = (): UseHandleResizedChangeValue => {
  const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = storeApi.getState();

  const [state, setState] = useState<UseHandleResizedChangeState>({
    initialWidth: null,
    initialHeight: null,
    finalWidth: null,
    finalHeight: null,
  });
  const applyResizeHandleChange = useCallback(
    (
      changes: NodeChange<Node<NodeData>>[],
      nodes: Node<NodeData, DiagramNodeType>[]
    ): Node<NodeData, DiagramNodeType>[] => {
      if (changes.length === 1 && changes[0] && changes[0].type === 'dimensions') {
        const change: NodeDimensionChange = changes[0];
        const resizedNode = nodeLookup.get(change.id);

        if (resizedNode && !state.initialHeight && !state.initialWidth && !!change.resizing) {
          setState((prevState) => ({
            ...prevState,
            initialHeight: resizedNode.height ? resizedNode.height : null,
            initialWidth: resizedNode.width ? resizedNode.width : null,
          }));
        } else if (state.initialHeight && state.initialWidth && !!change.resizing) {
          setState((prevState) => ({
            ...prevState,
            finalHeight: change.dimensions ? change.dimensions.height : null,
            finalWidth: change.dimensions ? change.dimensions.width : null,
          }));
        } else if (
          state.initialHeight &&
          state.initialWidth &&
          state.finalHeight &&
          state.finalWidth &&
          resizedNode &&
          !change.resizing
        ) {
          const coefHeight = state.finalHeight / state.initialHeight;
          const coefWidth = state.finalWidth / state.initialWidth;

          setState((prevState) => ({
            ...prevState,
            finalWidth: null,
            initialHeight: null,
            finalHeight: null,
            initialWidth: null,
          }));

          return nodes.map((node) => {
            if (node.id === resizedNode.id) {
              const connectionHandles = node.data.connectionHandles.map((handle) => {
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

              return {
                ...node,
                data: {
                  ...node.data,
                  connectionHandles: connectionHandles,
                },
              };
            }
            return node;
          });
        }
      }
      return nodes;
    },
    [state.finalHeight, state.finalHeight, state.initialHeight, state.initialWidth]
  );

  return { applyResizeHandleChange };
};
