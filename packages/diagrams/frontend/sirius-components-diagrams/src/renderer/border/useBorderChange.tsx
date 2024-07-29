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
import { Edge, Node, NodeChange, useReactFlow, XYPosition } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { BorderNodePosition, EdgeData, NodeData } from '../DiagramRenderer.types';
import { findBorderNodePosition } from '../layout/layoutBorderNodes';
import { borderNodeOffset } from '../layout/layoutParams';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseBorderChangeValue } from './useBorderChange.types';

const isNewPositionInsideIsParent = (newNodePosition: XYPosition, movedNode: Node, parentNode: Node): boolean => {
  if (movedNode.width && movedNode.height && parentNode?.position && parentNode.width && parentNode.height) {
    return (
      parentNode.position.x + borderNodeOffset < newNodePosition.x + movedNode.width &&
      parentNode.position.x + parentNode.width - borderNodeOffset > newNodePosition.x &&
      parentNode.position.y + borderNodeOffset < newNodePosition.y + movedNode.height &&
      parentNode.position.y + parentNode.height - borderNodeOffset > newNodePosition.y
    );
  }
  return false;
};

const findNearestBorderPosition = (
  newNodePosition: XYPosition,
  parentNode: Node<NodeData>
): BorderNodePosition | null => {
  if (parentNode.position) {
    const northDistance = Math.abs(newNodePosition.y - parentNode.position.y);
    const eastDistance = Math.abs((parentNode.width ?? 0) + parentNode.position.x - newNodePosition.x);
    const southDistance = Math.abs(newNodePosition.y - (parentNode.height ?? 0) - parentNode.position.y);
    const westDistance = Math.abs(parentNode.position.x - newNodePosition.x);

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
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const transformBorderNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[], oldNodes: Node<NodeData>[]): NodeChange<Node<NodeData>>[] => {
      return changes.map((change) => {
        if (change.type === 'position' && change.position && change.position) {
          const movedNode = getNodes().find((node) => change.id === node.id);
          if (movedNode && movedNode.data.isBorderNode) {
            const parentNode = getNodes().find((node) => movedNode.parentId === node.id);
            const parentLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
              nodeLayoutHandler.canHandle(parentNode as Node<NodeData, DiagramNodeType>)
            );
            if (
              parentNode &&
              parentNode.position &&
              isNewPositionInsideIsParent(change.position, movedNode, parentNode) &&
              !parentLayoutHandler?.calculateCustomNodeBorderNodePosition
            ) {
              const nearestBorder = findNearestBorderPosition(change.position, parentNode);
              if (nearestBorder === BorderNodePosition.NORTH) {
                change.position.y = borderNodeOffset - (movedNode.height ?? 0);
                change.position.y = parentNode.position.y + borderNodeOffset - (movedNode.height ?? 0);
              } else if (nearestBorder === BorderNodePosition.SOUTH) {
                change.position.y = (parentNode.height ?? 0) - borderNodeOffset;
                change.position.y = parentNode.position.y + (parentNode.height ?? 0) - borderNodeOffset;
              } else if (nearestBorder === BorderNodePosition.WEST) {
                change.position.x = borderNodeOffset - (movedNode.width ?? 0);
                change.position.x = parentNode.position.x + borderNodeOffset - (movedNode.width ?? 0);
              } else if (nearestBorder === BorderNodePosition.EAST) {
                change.position.x = (parentNode.width ?? 0) - borderNodeOffset;
                change.position.x = parentNode.position.x + (parentNode.width ?? 0) - borderNodeOffset;
              } else {
                //Invalid position, reset to the initial one
                change.position = movedNode.position;
                change.position = movedNode.position;
              }
            }
            const oldMovedNode = oldNodes.find((n) => n.id === movedNode.id);
            const newPosition = findBorderNodePosition(change.position, movedNode, parentNode);
            if (oldMovedNode && oldMovedNode.data.borderNodePosition !== newPosition) {
              oldMovedNode.data.borderNodePosition = newPosition;
            }
            if (parentLayoutHandler?.calculateCustomNodeBorderNodePosition && parentNode) {
              change.position = parentLayoutHandler.calculateCustomNodeBorderNodePosition(
                parentNode,
                {
                  x: change.position.x,
                  y: change.position.y,
                  width: movedNode.width ?? 0,
                  height: movedNode.height ?? 0,
                },
                true
              );
            }
          }
        }
        return change;
      });
    },
    []
  );

  return { transformBorderNodeChanges };
};
