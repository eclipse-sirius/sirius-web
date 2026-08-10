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
import {
  Dimensions,
  Edge,
  Node,
  NodeChange,
  NodeDimensionChange,
  NodePositionChange,
  Rect,
  useStoreApi,
  XYPosition,
} from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { BorderNodePosition, EdgeData, NodeData } from '../DiagramRenderer.types';
import { getBorderNodeExtent } from '../layout/layoutBorderNodes';
import { borderNodeOffset, defaultNodeMargin } from '../layout/layoutParams';
import { ListNodeData } from '../node/ListNode.types';
import { isResizing, isMove, isResize, isResizeFinished } from '../node/nodeChangePredicates';
import { UseResizeChangeValue } from './useResizeChange.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { computeNodesBox, getHeaderHeightFootprint } from '../layout/layoutNode';
import { FreeFormNodeData } from '../node/FreeFormNode.types';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';
const isFreeFormData = (node: Node): node is Node<FreeFormNodeData> => node.type === 'freeFormNode';

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

const computeFreeFormContentContainmentDelta = (
  parentNode: Node<FreeFormNodeData>,
  nodesBox: Rect,
  dimensions: Dimensions
): XYPosition => {
  const borderWidth: number = getBorderWidth(parentNode);
  const headerHeightFootprint = getHeaderHeightFootprint(parentNode.data.insideLabel, 'TOP', borderWidth);
  const minX = defaultNodeMargin + borderWidth;
  const minY = defaultNodeMargin + borderWidth + headerHeightFootprint;
  const maxX = dimensions.width - defaultNodeMargin - borderWidth;
  const maxY = dimensions.height - defaultNodeMargin - borderWidth;

  let deltaX = 0;
  let deltaY = 0;

  if (nodesBox.x + nodesBox.width > maxX) {
    deltaX = maxX - (nodesBox.x + nodesBox.width);
  }
  if (nodesBox.y + nodesBox.height > maxY) {
    deltaY = maxY - (nodesBox.y + nodesBox.height);
  }

  if (nodesBox.x + deltaX < minX) {
    deltaX = minX - nodesBox.x;
  }
  if (nodesBox.y + deltaY < minY) {
    deltaY = minY - nodesBox.y;
  }

  return { x: deltaX, y: deltaY };
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
): NodePositionChange => {
  const parentNode = nodes.find((node) => node.id === movedNode.parentId);
  if (parentNode && movedNode.id === change.id && isListData(parentNode)) {
    const borderWidth: number = getBorderWidth(parentNode);
    if (change.position) {
      return {
        ...change,
        position: {
          ...change.position,
          x: borderWidth,
        },
      };
    }
  }
  return change;
};

const constrainFreeFormChildMoveChanges = (
  nodes: Node<NodeData>[],
  changes: NodeChange<Node<NodeData>>[]
): Map<string, NodePositionChange> => {
  const resizedNodeIds = changes.filter(isResize).map((change) => change.id);
  const moveChangesByParentNodeId = new Map<string, { movedNode: Node<NodeData>; change: NodePositionChange }[]>();

  changes.filter(isMove).forEach((change) => {
    const movedNode = nodes.find(
      (node) => node.id === change.id && !node.data.isBorderNode && !resizedNodeIds.includes(node.id)
    );
    const parentNode = nodes.find((node) => node.id === movedNode?.parentId);
    if (movedNode && parentNode && isFreeFormData(parentNode) && change.position) {
      const currentChanges = moveChangesByParentNodeId.get(parentNode.id) ?? [];
      moveChangesByParentNodeId.set(parentNode.id, [...currentChanges, { movedNode, change }]);
    }
  });

  const freeFormContainChanges = new Map<string, NodePositionChange>();
  moveChangesByParentNodeId.forEach((moveChanges, parentNodeId) => {
    const parentNode = nodes.find((node) => node.id === parentNodeId);
    if (parentNode && isFreeFormData(parentNode)) {
      const movedNodes = moveChanges.map(({ movedNode, change }) => ({
        ...movedNode,
        position: change.position!,
      }));
      const movedNodesBox = computeNodesBox(nodes, movedNodes);
      const delta = computeFreeFormContentContainmentDelta(parentNode, movedNodesBox, {
        width: parentNode.width ?? 0,
        height: parentNode.height ?? 0,
      });

      moveChanges.forEach(({ change }) => {
        if (change.position) {
          freeFormContainChanges.set(change.id, {
            ...change,
            position: {
              x: change.position.x + delta.x,
              y: change.position.y + delta.y,
            },
          });
        }
      });
    }
  });

  return freeFormContainChanges;
};

const applyMoveToBorderNodes = (resizedNode: Node<NodeData>, nodes: Node<NodeData>[], change: NodeDimensionChange) => {
  const newChanges: NodeChange<Node<NodeData>>[] = [];
  if (resizedNode.width && resizedNode.height && change.dimensions) {
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
            const eastBorderNodePositionX = (change.dimensions?.width ?? 0) - borderNodeOffset;
            newChanges.push({
              id: node.id,
              type: 'position',
              position: { x: eastBorderNodePositionX, y: node.position.y },
            });
          } else if (node.data.borderNodePosition === BorderNodePosition.SOUTH) {
            const southBorderNodePositionY = (change.dimensions?.height ?? 0) - borderNodeOffset;
            newChanges.push({
              id: node.id,
              type: 'position',
              position: { x: node.position.x, y: southBorderNodePositionY },
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

const createFreeFormChildMoveChangesAfterResize = (
  resizedNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  change: NodeDimensionChange
): NodeChange<Node<NodeData>>[] => {
  if (isFreeFormData(resizedNode) && change.dimensions) {
    const children = nodes.filter(
      (node) => node.parentId === resizedNode.id && !node.hidden && !node.data.isBorderNode
    );
    if (children.length > 0) {
      const childrenBox = computeNodesBox(nodes, children);
      const delta = computeFreeFormContentContainmentDelta(resizedNode, childrenBox, change.dimensions);

      return children.map((node) => {
        return {
          id: node.id,
          type: 'position',
          position: {
            x: node.position.x + delta.x,
            y: node.position.y + delta.y,
          },
        };
      });
    }
  }
  return [];
};

export const useResizeChange = (): UseResizeChangeValue => {
  const { getNodes } = useStore();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  const transformResizeNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
      const zoom = store.getState().transform[2];
      const nodes = getNodes();
      const constrainedFreeFormChildMoveChanges = constrainFreeFormChildMoveChanges(nodes, changes);
      const newChildNodeChanges: NodeChange<Node<NodeData>>[] = [];
      const newBorderNodeMoveChanges: NodeChange<Node<NodeData>>[] = [];
      const updatedChanges: NodeChange<Node<NodeData>>[] = changes.map((currentChange) => {
        if (isResizing(currentChange)) {
          const resizedNode = nodes.find((node) => currentChange.id === node.id);
          if (resizedNode) {
            newChildNodeChanges.push(...applyResizeToListContain(resizedNode, nodes, currentChange));
            newBorderNodeMoveChanges.push(...applyMoveToBorderNodes(resizedNode, nodes, currentChange));
            newChildNodeChanges.push(...applyMoveToListChild(resizedNode, nodes, currentChange, zoom));
            const isCurrentResizedNodeNotMoved =
              changes.filter((change) => isMove(change) && change.id === resizedNode.id).length === 0;
            if (isCurrentResizedNodeNotMoved) {
              newChildNodeChanges.push(...createFreeFormChildMoveChangesAfterResize(resizedNode, nodes, currentChange));
            }
          }
        }
        if (isMove(currentChange)) {
          const freeFormContainChange = constrainedFreeFormChildMoveChanges.get(currentChange.id);
          if (freeFormContainChange) {
            return freeFormContainChange;
          }

          const isCurrentMovedNodeNotResized = (node: Node<NodeData>) =>
            changes.filter((change) => isResize(change) && change.id === node.id).length === 0;

          const movedNode = nodes
            .filter((node) => !node.data.isBorderNode)
            .filter(isCurrentMovedNodeNotResized)
            .find((node) => currentChange.id === node.id);
          if (movedNode) {
            return applyMoveToListContain(movedNode, nodes, currentChange);
          }
          const borderNodeMoved = nodes
            .filter((node) => node.data.isBorderNode)
            .find((node) => currentChange.id === node.id);
          if (
            borderNodeMoved &&
            newBorderNodeMoveChanges.some(
              (borderNodeMoveChange) => isMove(borderNodeMoveChange) && borderNodeMoveChange.id === borderNodeMoved.id
            )
          ) {
            // We have already computed a new position for this border node
            currentChange.position = undefined;
          }
        }
        return currentChange;
      });
      return [...newBorderNodeMoveChanges, ...updatedChanges, ...newChildNodeChanges];
    },
    [getNodes]
  );

  const applyResizeByUserState = (
    changes: NodeChange<Node<NodeData>>[],
    nodes: Node<NodeData, DiagramNodeType>[]
  ): Node<NodeData, DiagramNodeType>[] => {
    const resizedNodeIds = changes.filter(isResizeFinished).map((change) => change.id);
    const resizedListIds = nodes
      .filter((node) => resizedNodeIds.includes(node.id) && isListData(node))
      .map((node) => node.id);

    return nodes.map((node) => {
      const isResized = resizedNodeIds.includes(node.id);
      const isChildOfResizedList = node.parentId && resizedListIds.includes(node.parentId);

      if (isResized || isChildOfResizedList) {
        return {
          ...node,
          data: {
            ...node.data,
            resizedByUser: true,
          },
        };
      }
      return node;
    });
  };

  return { transformResizeNodeChanges, applyResizeByUserState };
};
