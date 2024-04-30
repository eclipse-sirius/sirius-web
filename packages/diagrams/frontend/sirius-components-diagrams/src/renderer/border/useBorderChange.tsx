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
import { useCallback } from 'react';
import { Node, NodeChange, useReactFlow, XYPosition } from 'reactflow';
import { EdgeData, NodeData, BorderNodePosition } from '../DiagramRenderer.types';
import { findBorderNodePosition } from '../layout/layoutBorderNodes';
import { borderNodeOffset } from '../layout/layoutParams';
import { UseBorderChangeValue } from './useBorderChange.types';

const isNewPositionInsideIsParent = (newNodePosition: XYPosition, movedNode: Node, parentNode: Node): boolean => {
  if (movedNode.width && movedNode.height && parentNode?.positionAbsolute && parentNode.width && parentNode.height) {
    return (
      parentNode.positionAbsolute.x + borderNodeOffset < newNodePosition.x + movedNode.width &&
      parentNode.positionAbsolute.x + parentNode.width - borderNodeOffset > newNodePosition.x &&
      parentNode.positionAbsolute.y + borderNodeOffset < newNodePosition.y + movedNode.height &&
      parentNode.positionAbsolute.y + parentNode.height - borderNodeOffset > newNodePosition.y
    );
  }
  return false;
};

const findNearestBorderPosition = (
  newNodePosition: XYPosition,
  parentNode: Node<NodeData>
): BorderNodePosition | null => {
  if (parentNode.positionAbsolute) {
    const northDistance = Math.abs(newNodePosition.y - parentNode.positionAbsolute.y);
    const eastDistance = Math.abs((parentNode.width ?? 0) + parentNode.positionAbsolute.x - newNodePosition.x);
    const southDistance = Math.abs(newNodePosition.y - (parentNode.height ?? 0) - parentNode.positionAbsolute.y);
    const westDistance = Math.abs(parentNode.positionAbsolute.x - newNodePosition.x);

    const minDistance = Math.min(...[northDistance, eastDistance, southDistance, westDistance]);
    if (minDistance === northDistance) {
      return BorderNodePosition.NORTH;
    } else if (minDistance === eastDistance) {
      return BorderNodePosition.EAST;
    } else if (minDistance === southDistance) {
      return BorderNodePosition.SOUTH;
    } else {
      return BorderNodePosition.WEST;
    }
  }
  return null;
};

export const useBorderChange = (): UseBorderChangeValue => {
  const { getNodes } = useReactFlow<NodeData, EdgeData>();

  const transformBorderNodeChanges = useCallback((changes: NodeChange[], oldNodes: Node<NodeData>[]): NodeChange[] => {
    return changes.map((change) => {
      if (change.type === 'position' && change.position && change.positionAbsolute) {
        const movedNode = getNodes().find((node) => change.id === node.id);
        if (movedNode && movedNode.data.isBorderNode) {
          const parentNode = getNodes().find((node) => movedNode.parentNode === node.id);
          if (
            parentNode &&
            parentNode.positionAbsolute &&
            isNewPositionInsideIsParent(change.positionAbsolute, movedNode, parentNode)
          ) {
            const nearestBorder = findNearestBorderPosition(change.positionAbsolute, parentNode);
            if (nearestBorder === BorderNodePosition.NORTH) {
              change.position.y = borderNodeOffset - (movedNode.height ?? 0);
              change.positionAbsolute.y = parentNode.positionAbsolute.y + borderNodeOffset - (movedNode.height ?? 0);
            } else if (nearestBorder === BorderNodePosition.SOUTH) {
              change.position.y = (parentNode.height ?? 0) - borderNodeOffset;
              change.positionAbsolute.y = parentNode.positionAbsolute.y + (parentNode.height ?? 0) - borderNodeOffset;
            } else if (nearestBorder === BorderNodePosition.WEST) {
              change.position.x = borderNodeOffset - (movedNode.width ?? 0);
              change.positionAbsolute.x = parentNode.positionAbsolute.x + borderNodeOffset - (movedNode.width ?? 0);
            } else if (nearestBorder === BorderNodePosition.EAST) {
              change.position.x = (parentNode.width ?? 0) - borderNodeOffset;
              change.positionAbsolute.x = parentNode.positionAbsolute.x + (parentNode.width ?? 0) - borderNodeOffset;
            } else {
              //Invalid position, reset to the initial one
              change.position = movedNode.position;
              change.positionAbsolute = movedNode.positionAbsolute;
            }
          }
          const oldMovedNode = oldNodes.find((n) => n.id === movedNode.id);
          const newPosition = findBorderNodePosition(change.position, movedNode, parentNode);
          if (oldMovedNode && oldMovedNode.data.borderNodePosition !== newPosition) {
            oldMovedNode.data.borderNodePosition = newPosition;
          }
        }
      }
      return change;
    });
  }, []);

  return { transformBorderNodeChanges };
};
