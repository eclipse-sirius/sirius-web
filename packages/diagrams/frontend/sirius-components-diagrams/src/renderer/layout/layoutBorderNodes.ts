/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { CoordinateExtent, Node, XYPosition } from 'reactflow';
import { BorderNodePosition, NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { borderNodeOffset, borderNodeReferencePositionRatio } from './layoutParams';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';

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
      [parentNode.width - borderNodeOffset, parentNode.height - borderNodeOffset],
    ];
  }
  return coordinateExtent;
};

export const computeBorderNodeExtents = (nodes: Node<NodeData, DiagramNodeType>[]): void => {
  nodes
    .filter((node) => node.data.isBorderNode)
    .forEach((borderNode) => {
      const parentNode = nodes.find((node) => node.id === borderNode.parentNode);
      if (parentNode) {
        borderNode.extent = getBorderNodeExtent(parentNode, borderNode);
      }
    });
};

export const computeBorderNodePositions = (nodes: Node<NodeData, DiagramNodeType>[]): void => {
  nodes
    .filter((node) => node.data.isBorderNode)
    .forEach((borderNode) => {
      const parentNode = nodes.find((node) => node.id === borderNode.parentNode);
      if (parentNode) {
        const newPosition = findBorderNodePosition(borderNode.position, borderNode, parentNode);
        borderNode.data.borderNodePosition = newPosition ?? BorderNodePosition.EAST;
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
