/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { UseJustifyElementsValue } from './useJustifyElements.types';
import { getComparePositionFn, useProcessLayoutTool } from './useProcessLayoutTool';

export const useJustifyElements = (): UseJustifyElementsValue => {
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { processLayoutTool } = useProcessLayoutTool();

  const justifyElements = (
    justifyElementsFn: (
      selectedNodes: Node<NodeData>[],
      selectedNodeIds: string[],
      refNode: Node<NodeData>
    ) => Node<NodeData>[]
  ) => {
    return useCallback((selectedNodeIds: string[], refElementId: string | null) => {
      processLayoutTool(
        selectedNodeIds,
        (selectedNodes, refNode) => {
          selectedNodes.sort(getComparePositionFn('horizontal'));
          return justifyElementsFn(selectedNodes, selectedNodeIds, refNode);
        },
        null,
        refElementId
      );
    }, []);
  };

  const justifyHorizontally = justifyElements(
    (selectedNodes: Node<NodeData>[], selectedNodeIds: string[], refNode: Node<NodeData>): Node<NodeData>[] => {
      const largestWidth: number = selectedNodes.reduce((width, node) => Math.max(width, node.width ?? 0), 0);
      return getNodes().map((node) => {
        if (
          !selectedNodeIds.includes(node.id) ||
          node.data.nodeDescription?.userResizable === 'NONE' ||
          node.data.pinned
        ) {
          return node;
        }
        return {
          ...node,
          width: largestWidth,
          position: {
            ...node.position,
            x: refNode.position.x,
          },
          data: {
            ...node.data,
            resizedByUser: true,
          },
        };
      });
    }
  );

  const justifyVertically = justifyElements(
    (selectedNodes: Node<NodeData>[], selectedNodeIds: string[], refNode: Node<NodeData>): Node<NodeData>[] => {
      const largestHeight: number = selectedNodes.reduce((height, node) => Math.max(height, node.height ?? 0), 0);
      return getNodes().map((node) => {
        if (
          !selectedNodeIds.includes(node.id) ||
          node.data.nodeDescription?.userResizable === 'NONE' ||
          node.data.pinned
        ) {
          return node;
        }
        return {
          ...node,
          height: largestHeight,
          position: {
            ...node.position,
            y: refNode.position.y,
          },
          data: {
            ...node.data,
            resizedByUser: true,
          },
        };
      });
    }
  );

  return {
    justifyHorizontally,
    justifyVertically,
  };
};
