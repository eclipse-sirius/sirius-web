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
import { Edge, InternalNode, Node, NodeChange, useStoreApi, XYPosition } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { BorderNodePosition, EdgeData, NodeData } from '../DiagramRenderer.types';
import { findBorderNodePosition } from '../layout/layoutBorderNodes';
import { getPositionAbsoluteFromNodeChange } from '../layout/layoutNode';
import { borderNodeOffset } from '../layout/layoutParams';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseBorderChangeValue } from './useBorderChange.types';

const isNewPositionInsideIsParent = (
  newNodePosition: XYPosition,
  movedNode: Node,
  parentNode: InternalNode<Node<NodeData>>
): boolean => {
  if (movedNode.width && movedNode.height && parentNode.width && parentNode.height) {
    return (
      parentNode.internals.positionAbsolute.x + borderNodeOffset < newNodePosition.x + movedNode.width &&
      parentNode.internals.positionAbsolute.x + parentNode.width - borderNodeOffset > newNodePosition.x &&
      parentNode.internals.positionAbsolute.y + borderNodeOffset < newNodePosition.y + movedNode.height &&
      parentNode.internals.positionAbsolute.y + parentNode.height - borderNodeOffset > newNodePosition.y
    );
  }
  return false;
};

const findNearestBorderPosition = (
  newNodePosition: XYPosition,
  parentNode: InternalNode<Node<NodeData>>
): BorderNodePosition | null => {
  const northDistance = Math.abs(newNodePosition.y - parentNode.internals.positionAbsolute.y);
  const eastDistance = Math.abs((parentNode.width ?? 0) + parentNode.internals.positionAbsolute.x - newNodePosition.x);
  const southDistance = Math.abs(
    newNodePosition.y - (parentNode.height ?? 0) - parentNode.internals.positionAbsolute.y
  );
  const westDistance = Math.abs(parentNode.internals.positionAbsolute.x - newNodePosition.x);

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
};

export const useBorderChange = (): UseBorderChangeValue => {
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  const transformBorderNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[], oldNodes: Node<NodeData>[]): NodeChange<Node<NodeData>>[] => {
      return changes.map((change) => {
        if (change.type === 'position' && change.position) {
          const nodeLookup = storeApi.getState().nodeLookup;
          const movedNodePositionAbsolute = getPositionAbsoluteFromNodeChange(change, nodeLookup);
          const movedNode = nodeLookup.get(change.id || '');
          if (movedNode && movedNode.data.isBorderNode && movedNodePositionAbsolute) {
            const parentNode = nodeLookup.get(movedNode.parentId || '');

            const parentLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
              nodeLayoutHandler.canHandle(parentNode as Node<NodeData, DiagramNodeType>)
            );
            if (
              parentNode &&
              isNewPositionInsideIsParent(movedNodePositionAbsolute, movedNode, parentNode) &&
              !parentLayoutHandler?.calculateCustomNodeBorderNodePosition
            ) {
              const nearestBorder = findNearestBorderPosition(movedNodePositionAbsolute, parentNode);
              switch (nearestBorder) {
                case BorderNodePosition.NORTH:
                  change.position.y = borderNodeOffset - (movedNode.height ?? 0);
                  break;
                case BorderNodePosition.SOUTH:
                  change.position.y = (parentNode.height ?? 0) - borderNodeOffset;
                  break;
                case BorderNodePosition.WEST:
                  change.position.x = borderNodeOffset - (movedNode.width ?? 0);
                  break;
                case BorderNodePosition.EAST:
                  change.position.x = (parentNode.width ?? 0) - borderNodeOffset;
                  break;
                default: //Invalid position, reset to the initial one
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
