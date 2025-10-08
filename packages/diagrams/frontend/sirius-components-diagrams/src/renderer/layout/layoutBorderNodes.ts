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
import { CoordinateExtent, Node, XYPosition, Position, InternalNode } from '@xyflow/react';
import { NodeLookup } from '@xyflow/system';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { BorderNodePosition, NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { borderNodeOffset, borderNodeReferencePositionRatio, borderNodeGap } from './layoutParams';

export const isEastBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePosition.EAST;
};
export const isWestBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePosition.WEST;
};
export const isNorthBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePosition.NORTH;
};
export const isSouthBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePosition.SOUTH;
};

export const getBorderNodeExtent = (
  parentNode: Node<NodeData>,
  borderNode: Node<NodeData>
): CoordinateExtent | 'parent' => {
  let coordinateExtent: CoordinateExtent | 'parent' = 'parent';
  if (parentNode.width && parentNode.height && borderNode.height && borderNode.width) {
    coordinateExtent = [
      [0 - borderNode.width + borderNodeOffset, 0 - borderNode.height + borderNodeOffset],
      [
        parentNode.width - borderNodeOffset + borderNode.width,
        parentNode.height - borderNodeOffset + borderNode.height,
      ],
    ];
  }
  return coordinateExtent;
};

export const computeBorderNodeExtents = (nodes: Node<NodeData, DiagramNodeType>[]): void => {
  nodes
    .filter((node) => node.data.isBorderNode)
    .forEach((borderNode) => {
      const parentNode = nodes.find((node) => node.id === borderNode.parentId);
      if (parentNode) {
        borderNode.extent = getBorderNodeExtent(parentNode, borderNode);
      }
    });
};

export const computeBorderNodePositions = (nodes: Node<NodeData, DiagramNodeType>[]): void => {
  nodes
    .filter((node) => node.data.isBorderNode)
    .forEach((borderNode) => {
      const parentNode = nodes.find((node) => node.id === borderNode.parentId);
      if (parentNode) {
        const newPosition = findBorderNodePosition(borderNode.position, borderNode, parentNode);
        borderNode.data.borderNodePosition = newPosition ?? borderNode.data.borderNodePosition;
      }
    });
};

export const findBorderNodePosition = (
  borderNodePosition: XYPosition | undefined,
  borderNode: Node,
  parentNode: Node | undefined
): BorderNodePosition | null => {
  if (borderNodePosition && borderNode.width && borderNode.height && parentNode?.width && parentNode.height) {
    if (Math.trunc(borderNodePosition.x + borderNode.width) - borderNodeOffset === 0) {
      return BorderNodePosition.WEST;
    }
    if (Math.trunc(borderNodePosition.x) + borderNodeOffset === Math.trunc(parentNode.width)) {
      return BorderNodePosition.EAST;
    }
    if (Math.trunc(borderNodePosition.y + borderNode.height) - borderNodeOffset === 0) {
      return BorderNodePosition.NORTH;
    }
    if (Math.trunc(borderNodePosition.y) + borderNodeOffset === Math.trunc(parentNode.height)) {
      return BorderNodePosition.SOUTH;
    }
  }
  return null;
};

export const getNewlyAddedBorderNodePosition = (
  newlyAddedNode: Node<NodeData, DiagramNodeType>,
  parentNode: Node<NodeData, string> | undefined,
  referencePosition: GQLReferencePosition
): void => {
  if (parentNode) {
    if (referencePosition.position.x < (parentNode.width ?? 0) * borderNodeReferencePositionRatio) {
      newlyAddedNode.data.borderNodePosition = BorderNodePosition.WEST;
    } else if (referencePosition.position.x > (parentNode.width ?? 0) * (1 - borderNodeReferencePositionRatio)) {
      newlyAddedNode.data.borderNodePosition = BorderNodePosition.EAST;
    } else if (referencePosition.position.y < (parentNode.height ?? 0) * borderNodeReferencePositionRatio) {
      newlyAddedNode.data.borderNodePosition = BorderNodePosition.NORTH;
    } else if (referencePosition.position.y > (parentNode.height ?? 0) * (1 - borderNodeReferencePositionRatio)) {
      newlyAddedNode.data.borderNodePosition = BorderNodePosition.SOUTH;
    }
  }
};

export const convertPositionToBorderNodePosition = (position: Position): BorderNodePosition => {
  switch (position) {
    case Position.Top:
      return BorderNodePosition.NORTH;
    case Position.Right:
      return BorderNodePosition.EAST;
    case Position.Bottom:
      return BorderNodePosition.SOUTH;
    case Position.Left:
      return BorderNodePosition.WEST;
    default:
      return BorderNodePosition.EAST;
  }
};

export const getBorderNodeParentIfExist = (
  node: InternalNode<Node<NodeData>>,
  nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>
): InternalNode<Node<NodeData>> => {
  if (node && node.data.isBorderNode && node.parentId) {
    const parentNode = nodeLookup.get(node.parentId);
    if (parentNode) {
      return parentNode;
    }
  }
  return node;
};

export const computeBorderNodeXYPositionFromBorderNodePosition = (
  node: Node<NodeData>,
  nodes: Node<NodeData>[],
  nodesBeingUpdated: Node<NodeData>[],
  newBorderNodePosition: BorderNodePosition,
  nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>
): XYPosition | null => {
  if (node.data.borderNodePosition !== newBorderNodePosition) {
    const parentNode = nodeLookup.get(node.parentId ?? '');
    if (parentNode) {
      const siblingNodes = nodes
        .filter((n) => n.data.isBorderNode)
        .filter((n) => n.parentId === parentNode.id)
        .map((n) => {
          const nodeBeingUpdated = nodesBeingUpdated.find((n2) => n2.id === n.id);
          return nodeBeingUpdated ? nodeBeingUpdated : n;
        })
        .filter((n) => n.data.borderNodePosition === newBorderNodePosition);
      switch (newBorderNodePosition) {
        case BorderNodePosition.WEST:
          return {
            x: -(node.width ?? 0) - borderNodeOffset,
            y: siblingNodes.reduce((posY, siblingNode) => {
              if (siblingNode.position.y >= posY) {
                return siblingNode.position.y + (siblingNode?.height ?? 0) + borderNodeGap;
              }
              return posY;
            }, 0),
          };
        case BorderNodePosition.EAST:
          return {
            x: (parentNode.width ?? 0) - borderNodeOffset,
            y: siblingNodes.reduce((posY, siblingNode) => {
              if (siblingNode.position.y >= posY) {
                return siblingNode.position.y + (siblingNode?.height ?? 0) + borderNodeGap;
              }
              return posY;
            }, 0),
          };
        case BorderNodePosition.NORTH:
          return {
            x: siblingNodes.reduce((posX, siblingNode) => {
              if (siblingNode.position.x >= posX) {
                return siblingNode.position.x + (siblingNode?.width ?? 0) + borderNodeGap;
              }
              return posX;
            }, 0),
            y: -(node.height ?? 0) - borderNodeOffset,
          };
        case BorderNodePosition.SOUTH:
          return {
            x: siblingNodes.reduce((posX, siblingNode) => {
              if (siblingNode.position.x >= posX) {
                return siblingNode.position.x + (siblingNode?.width ?? 0) + borderNodeGap;
              }
              return posX;
            }, 0),
            y: (parentNode.height ?? 0) - borderNodeOffset,
          };
      }
    }
  }
  return null;
};
