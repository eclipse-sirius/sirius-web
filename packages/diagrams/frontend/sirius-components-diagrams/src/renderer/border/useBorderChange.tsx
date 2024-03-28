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
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { findBorderNodePosition } from '../layout/layoutBorderNodes';
import { borderNodeOffset } from '../layout/layoutParams';
import { UseBorderChangeValue } from './useBorderChange.types';

const isNewPositionInsideIsParent = (newNodePosition: XYPosition, movedNode: Node, parentNode: Node): boolean => {
  if (
    movedNode.width &&
    movedNode.height &&
    parentNode?.computed?.positionAbsolute &&
    parentNode.width &&
    parentNode.height
  ) {
    return (
      parentNode.computed.positionAbsolute.x + borderNodeOffset < newNodePosition.x + movedNode.width &&
      parentNode.computed.positionAbsolute.x + parentNode.width - borderNodeOffset > newNodePosition.x &&
      parentNode.computed.positionAbsolute.y + borderNodeOffset < newNodePosition.y + movedNode.height &&
      parentNode.computed.positionAbsolute.y + parentNode.height - borderNodeOffset > newNodePosition.y
    );
  }
  return false;
};

export const useBorderChange = (): UseBorderChangeValue => {
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const transformBorderNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
      return changes.map((change) => {
        if (change.type === 'position' && change.positionAbsolute) {
          const movedNode = getNodes().find((node) => change.id === node.id);
          if (movedNode?.data.isBorderNode) {
            const parentNode = getNodes().find((node) => movedNode?.parentNode === node.id);
            if (
              parentNode &&
              movedNode.computed &&
              isNewPositionInsideIsParent(change.positionAbsolute, movedNode, parentNode)
            ) {
              //Invalid position, reset to the initial one
              change.position = movedNode.position;
              change.positionAbsolute = movedNode.computed.positionAbsolute;
            } else {
              movedNode.data.borderNodePosition = findBorderNodePosition(change.position, movedNode, parentNode);
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
