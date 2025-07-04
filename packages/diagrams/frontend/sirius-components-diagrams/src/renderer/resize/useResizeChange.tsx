/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { Node, NodeChange, NodeDimensionChange, NodePositionChange } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { BorderNodePosition, NodeData } from '../DiagramRenderer.types';
import { getBorderNodeExtent } from '../layout/layoutBorderNodes';
import { ListNodeData } from '../node/ListNode.types';
import { UseResizeChangeValue } from './useResizeChange.types';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

const getBorderWidth = (resizedNode: Node<NodeData>): number => {
  let borderLeftWidth: number = 1;
  if (resizedNode.data.style.borderWidth) {
    if (typeof resizedNode.data.style.borderWidth === 'number') {
      borderLeftWidth = resizedNode.data.style.borderWidth;
    } else {
      borderLeftWidth = parseFloat(resizedNode.data.style.borderWidth);
    }
  }
  return borderLeftWidth;
};

const applyResizeToListContain = (
  resizedNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  change: NodeDimensionChange
): NodeChange<Node<NodeData>>[] => {
  const newChanges: NodeChange<Node<NodeData>>[] = [];
  if (isListData(resizedNode) && change.dimensions) {
    const borderWidth: number = getBorderWidth(resizedNode);
    const growableChildNodeId = nodes
      .filter(
        (node) =>
          !node.data.isBorderNode &&
          node.parentId === resizedNode.id &&
          resizedNode.data.growableNodeIds.includes(node.data.descriptionId)
      )
      .map((node) => node.id);
    const heightToAddToEachGrowableNode =
      growableChildNodeId.length > 0
        ? (change.dimensions.height - (resizedNode.height ?? 0)) / growableChildNodeId.length
        : 0;
    let growableNodeIndex = 0;
    nodes
      .filter((node) => !node.data.isBorderNode)
      .forEach((node) => {
        if (node.parentId === resizedNode.id && change.dimensions?.width) {
          const newDimensionChange: NodeChange<Node<NodeData>> = {
            id: node.id,
            type: 'dimensions',
            resizing: change.resizing,
            setAttributes: true,
            dimensions: {
              width: change.dimensions.width - borderWidth * 2,
              height: growableChildNodeId.includes(node.id)
                ? (node.height ?? 0) + heightToAddToEachGrowableNode
                : node.height ?? 0,
            },
          };
          newChanges.push(newDimensionChange);
          if (growableNodeIndex > 0) {
            newChanges.push({
              id: node.id,
              type: 'position',
              position: {
                x: node.position.x,
                y: node.position.y + growableNodeIndex * heightToAddToEachGrowableNode,
              },
            });
          }
          if (growableChildNodeId.includes(node.id)) {
            growableNodeIndex++;
          }
          newChanges.push(...applyResizeToListContain(node, nodes, newDimensionChange));
        }
      });
  }
  return newChanges;
};

const applyMoveToListContain = (
  movedNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  change: NodePositionChange
): NodeChange<Node<NodeData>> => {
  const parentNode = nodes.find((node) => node.id === movedNode.parentId);
  if (parentNode && isListData(parentNode)) {
    const borderWidth: number = getBorderWidth(parentNode);
    if (movedNode.id === change.id && change.position) {
      change = {
        id: change.id,
        type: 'position',
        position: { x: borderWidth, y: movedNode.position.y },
      };
    }
  }
  return change;
};

const applyMoveToBorderNodes = (resizedNode: Node<NodeData>, nodes: Node<NodeData>[], change: NodeDimensionChange) => {
  const newChanges: NodeChange<Node<NodeData>>[] = [];
  if (resizedNode.width && resizedNode.height && change.dimensions) {
    const offsetX: number = resizedNode.width - change.dimensions.width;
    const offsetY: number = resizedNode.height - change.dimensions?.height;
    nodes
      .filter((node) => node.data.isBorderNode)
      .forEach((node) => {
        if (node.parentId === resizedNode.id) {
          node.extent = getBorderNodeExtent(
            {
              ...resizedNode,
              width: change.dimensions?.width ?? 0,
              height: change.dimensions?.height ?? 0,
            },
            node
          );
          if (node.data.borderNodePosition === BorderNodePosition.EAST) {
            newChanges.push({
              id: node.id,
              type: 'position',
              position: { x: node.position.x - offsetX, y: node.position.y },
            });
          } else if (node.data.borderNodePosition === BorderNodePosition.SOUTH) {
            newChanges.push({
              id: node.id,
              type: 'position',
              position: { x: node.position.x, y: node.position.y - offsetY },
            });
          } else {
            newChanges.push({
              id: node.id,
              type: 'position',
              position: { x: node.position.x, y: node.position.y },
            });
          }
        }
      });
  }
  return newChanges;
};

const isResize = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange =>
  change.type === 'dimensions' && (change.resizing ?? false);
const isMove = (change: NodeChange<Node<NodeData>>): change is NodePositionChange =>
  change.type === 'position' && !change.dragging;

export const useResizeChange = (): UseResizeChangeValue => {
  const { getNodes } = useStore();

  const transformResizeListNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
      const newChanges: NodeChange<Node<NodeData>>[] = [];
      const updatedChanges: NodeChange<Node<NodeData>>[] = changes.map((change) => {
        if (isResize(change)) {
          const resizedNode = getNodes().find((node) => change.id === node.id);
          if (resizedNode) {
            newChanges.push(...applyResizeToListContain(resizedNode, getNodes(), change));
            newChanges.push(...applyMoveToBorderNodes(resizedNode, getNodes(), change));
          }
        }
        if (isMove(change)) {
          const movedNode = getNodes()
            .filter((node) => !node.data.isBorderNode)
            .find((node) => change.id === node.id);
          if (movedNode) {
            return applyMoveToListContain(movedNode, getNodes(), change);
          }
        }
        return change;
      });
      return [...newChanges, ...updatedChanges];
    },
    [getNodes]
  );

  return { transformResizeListNodeChanges };
};
