/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { useSelection, Selection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, NodeChange, NodeDimensionChange, NodePositionChange, useStoreApi } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { BorderNodePosition, EdgeData, NodeData } from '../DiagramRenderer.types';
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
    const growableChildNodes = nodes.filter(
      (node) =>
        !node.data.isBorderNode &&
        !node.hidden &&
        node.parentId === resizedNode.id &&
        resizedNode.data.growableNodeIds.includes(node.data.descriptionId)
    );
    const heightDimensionChange = change.dimensions.height - (resizedNode.height ?? 0);
    const growableChildNodeId = growableChildNodes
      .filter((node) => {
        if (heightDimensionChange > 0) {
          return (node.height ?? 0) >= (node.data.minComputedHeight ?? 0);
        }
        return (node.height ?? 0) > (node.data.minComputedHeight ?? 0);
      })
      .map((node) => node.id);
    const heightToAddToEachGrowableNode =
      growableChildNodeId.length > 0 ? heightDimensionChange / growableChildNodeId.length : 0;
    let offsetYPosition = 0;
    nodes
      .filter((node) => !node.data.isBorderNode && !node.hidden)
      .forEach((node) => {
        if (node.parentId === resizedNode.id && change.dimensions?.width) {
          let heightToAdd = 0;
          if (growableChildNodeId.includes(node.id)) {
            if ((node.height ?? 0) + heightToAddToEachGrowableNode < (node.data.minComputedHeight ?? 0)) {
              heightToAdd = node.data.minComputedHeight! - node.height!;
            } else {
              heightToAdd = heightToAddToEachGrowableNode;
            }
          }
          const newDimensionChange: NodeChange<Node<NodeData>> = {
            id: node.id,
            type: 'dimensions',
            resizing: change.resizing,
            setAttributes: true,
            dimensions: {
              width: change.dimensions.width - borderWidth * 2,
              height: (node.height ?? 0) + heightToAdd,
            },
          };
          newChanges.push(newDimensionChange);
          newChanges.push({
            id: node.id,
            type: 'position',
            position: {
              x: node.position.x,
              y: node.position.y + offsetYPosition,
            },
          });
          offsetYPosition += heightToAdd;
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
        ...change,
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

const applyMoveToListChild = (
  resizedNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  _change: NodeDimensionChange,
  zoom: number
): NodeChange<Node<NodeData>>[] => {
  if (isListData(resizedNode)) {
    const insideLabel = resizedNode.data.insideLabel;
    if (insideLabel && insideLabel.isHeader && insideLabel.headerPosition === 'TOP') {
      const element = document.querySelector(`[data-id="${insideLabel.id}"]`);
      if (element) {
        const borderOffset = insideLabel.displayHeaderSeparator
          ? getBorderWidth(resizedNode) * 2
          : getBorderWidth(resizedNode);
        const newLabelHeight = element.getBoundingClientRect().height / zoom + borderOffset + resizedNode.data.topGap;
        return nodes
          .filter((node) => node.parentId === resizedNode.id && !node.hidden && !node.data.isBorderNode)
          .map((node, index, array) => {
            const previousSibling = array[index - 1];
            let newPositionY: number = newLabelHeight;
            if (previousSibling) {
              newPositionY = previousSibling.position.y + (previousSibling.height ?? 0);
            }
            return {
              id: node.id,
              type: 'position',
              position: { x: node.position.x, y: newPositionY },
            };
          });
      }
    }
  }
  return [];
};

const applyMultiSelectResize = (
  change: NodeDimensionChange,
  resizedNode: Node<NodeData>,
  selection: Selection,
  nodes: Node<NodeData>[]
): NodeChange<Node<NodeData>>[] => {
  const resizeWidthOffset: number = (change.dimensions?.width ?? 0) - (resizedNode.width ?? 0);
  const resizeHeightOffset: number = (change.dimensions?.height ?? 0) - (resizedNode.height ?? 0);
  return nodes
    .filter((node) => node.id !== change.id && selection.entries.some((entry) => entry.id === node.data.targetObjectId))
    .filter((node) => node.data.nodeDescription?.userResizable !== 'NONE' && !node.data.isListChild)
    .map((node) => {
      let newWidth: number = (node.width ?? 0) + resizeWidthOffset;
      let newHeight: number = (node.height ?? 0) + resizeHeightOffset;
      if (node.data.nodeDescription?.userResizable === 'HORIZONTAL') {
        newHeight = node.height ?? 0;
      }
      if (node.data.nodeDescription?.userResizable === 'VERTICAL') {
        newWidth = node.width ?? 0;
      }
      if (node.data.minComputedHeight && newHeight < node.data.minComputedHeight) {
        newHeight = node.data.minComputedHeight;
      }
      if (node.data.minComputedWidth && newWidth < node.data.minComputedWidth) {
        newWidth = node.data.minComputedWidth;
      }
      return {
        ...change,
        id: node.id,
        dimensions: {
          height: newHeight,
          width: newWidth,
        },
      };
    });
};

const isResizing = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange =>
  change.type === 'dimensions' && (change.resizing ?? false);
const isResize = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange => change.type === 'dimensions';
const isMove = (change: NodeChange<Node<NodeData>>): change is NodePositionChange =>
  change.type === 'position' && !change.dragging;

export const useResizeChange = (): UseResizeChangeValue => {
  const { getNodes } = useStore();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const zoom = store.getState().transform[2];
  const { selection } = useSelection();

  const transformResizeListNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
      const newResizeListContainChanges: NodeChange<Node<NodeData>>[] = [];
      const newBorderNodeMoveChanges: NodeChange<Node<NodeData>>[] = [];
      const newMultiSelectResizeChanges: NodeChange<Node<NodeData>>[] = [];
      const updatedChanges: NodeChange<Node<NodeData>>[] = changes.map((change) => {
        if (isResizing(change)) {
          const resizedNode = getNodes().find((node) => change.id === node.id);
          if (resizedNode) {
            newResizeListContainChanges.push(...applyResizeToListContain(resizedNode, getNodes(), change));
            newBorderNodeMoveChanges.push(...applyMoveToBorderNodes(resizedNode, getNodes(), change));
            newResizeListContainChanges.push(...applyMoveToListChild(resizedNode, getNodes(), change, zoom));
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
        if (isResize(change)) {
          const resizedNode = getNodes().find((node) => change.id === node.id);
          if (resizedNode) {
            newMultiSelectResizeChanges.push(...applyMultiSelectResize(change, resizedNode, selection, getNodes()));
          }
        }
        return change;
      });
      return [
        ...newBorderNodeMoveChanges,
        ...updatedChanges,
        ...newResizeListContainChanges,
        ...newMultiSelectResizeChanges,
      ];
    },
    [getNodes]
  );

  return { transformResizeListNodeChanges };
};
