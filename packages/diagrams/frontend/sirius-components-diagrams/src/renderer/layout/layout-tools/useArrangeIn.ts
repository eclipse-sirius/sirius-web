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
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { UseArrangeInValue } from './useArrangeIn.types';
import { getComparePositionFn, useProcessLayoutTool } from './useProcessLayoutTool';

const arrangeGapBetweenElements: number = 32;

export const useArrangeIn = (): UseArrangeInValue => {
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { processLayoutTool } = useProcessLayoutTool();

  const arrangeInRow = (selectedNodeIds: string[]) => {
    processLayoutTool(
      selectedNodeIds,
      (selectedNodes, refNode) => {
        let nextXPosition: number = refNode.position.x;
        const updatedSelectedNodes = selectedNodes
          .filter((node) => !node.data.pinned)
          .map((node) => {
            const updatedNode = {
              ...node,
              position: {
                ...node.position,
                x: nextXPosition,
                y: refNode.position.y,
              },
            };
            nextXPosition = updatedNode.position.x + (updatedNode.width ?? 0) + arrangeGapBetweenElements;
            return updatedNode;
          });

        return getNodes().map((node) => {
          const replacedNode = updatedSelectedNodes.find((updatedSelectedNode) => updatedSelectedNode.id === node.id);
          if (replacedNode) {
            return replacedNode;
          }
          return node;
        });
      },
      getComparePositionFn('horizontal'),
      null
    );
  };

  const arrangeInColumn = (selectedNodeIds: string[]) => {
    processLayoutTool(
      selectedNodeIds,
      (selectedNodes, refNode) => {
        let nextYPosition: number = refNode.position.y;
        const updatedSelectedNodes = selectedNodes
          .filter((node) => !node.data.pinned)
          .map((node) => {
            const updatedNode = {
              ...node,
              position: {
                ...node.position,
                x: refNode.position.x,
                y: nextYPosition,
              },
            };
            nextYPosition = updatedNode.position.y + (updatedNode.height ?? 0) + arrangeGapBetweenElements;
            return updatedNode;
          });

        return getNodes().map((node) => {
          const replacedNode = updatedSelectedNodes.find((updatedSelectedNode) => updatedSelectedNode.id === node.id);
          if (replacedNode) {
            return replacedNode;
          }
          return node;
        });
      },
      getComparePositionFn('vertical'),
      null
    );
  };

  const arrangeInGrid = (selectedNodeIds: string[]) => {
    processLayoutTool(
      selectedNodeIds,
      (selectedNodes, refNode) => {
        const columnNumber: number = Math.round(Math.sqrt(selectedNodeIds.length));
        let nextXPosition: number = refNode.position.x;
        let nextYPosition: number = refNode.position.y;
        const updatedSelectedNodes = selectedNodes
          .filter((node) => !node.data.pinned)
          .map((node, index) => {
            const columnIndex = index + 1;
            const updatedNode = {
              ...node,
              position: {
                ...node.position,
                x: nextXPosition,
                y: nextYPosition,
              },
            };
            nextXPosition = updatedNode.position.x + (updatedNode.width ?? 0) + arrangeGapBetweenElements;
            if (columnIndex % columnNumber === 0) {
              nextXPosition = refNode.position.x;
              nextYPosition =
                updatedNode.position.y +
                arrangeGapBetweenElements +
                selectedNodes
                  .slice(columnIndex - columnNumber, columnIndex)
                  .reduce((maxHeight, rowNode) => Math.max(maxHeight, rowNode.height ?? 0), 0);
            }
            return updatedNode;
          });

        return getNodes().map((node) => {
          const replacedNode = updatedSelectedNodes.find((updatedSelectedNode) => updatedSelectedNode.id === node.id);
          if (replacedNode) {
            return replacedNode;
          }
          return node;
        });
      },
      getComparePositionFn('horizontal'),
      null
    );
  };

  return {
    arrangeInColumn,
    arrangeInGrid,
    arrangeInRow,
  };
};
