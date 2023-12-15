/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { EdgeData, NodeData } from '../DiagramRenderer.types';
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

export const useBorderChange = (): UseBorderChangeValue => {
  const { getNodes } = useReactFlow<NodeData, EdgeData>();

  const transformBorderNodeChanges = useCallback((changes: NodeChange[]): NodeChange[] => {
    return changes.map((change) => {
      if (change.type === 'position' && change.positionAbsolute) {
        const movedNode = getNodes().find((node) => change.id === node.id);
        if (movedNode?.data.isBorderNode) {
          const parentNode = getNodes().find((node) => movedNode?.parentNode === node.id);
          if (parentNode && isNewPositionInsideIsParent(change.positionAbsolute, movedNode, parentNode)) {
            //Invalid position, reset to the initial one
            change.position = movedNode.position;
            change.positionAbsolute = movedNode.positionAbsolute;
          } else {
            movedNode.data.borderNodePosition = findBorderNodePosition(change.position, movedNode, parentNode);
          }
        }
      }
      return change;
    });
  }, []);

  return { transformBorderNodeChanges };
};
