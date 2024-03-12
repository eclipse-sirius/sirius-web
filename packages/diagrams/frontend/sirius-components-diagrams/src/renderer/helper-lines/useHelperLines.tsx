/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useState } from 'react';
import { Node, NodeChange, NodePositionChange, useReactFlow } from 'reactflow';
import { UseHelperLinesState, UseHelperLinesValue, HelperLines } from './useHelperLines.types';
import { NodeData, EdgeData } from '../DiagramRenderer.types';
import { isDescendantOf } from '../layout/layoutNode';

const isMove = (change: NodeChange): change is NodePositionChange =>
  change.type === 'position' && typeof change.dragging === 'boolean' && change.dragging;

const getHelperLines = (
  change: NodePositionChange,
  movingNode: Node<NodeData>,
  nodes: Node<NodeData>[]
): HelperLines => {
  const noHelperLines: HelperLines = { horizontal: null, vertical: null, snapX: 0, snapY: 0 };
  const getNodeById = (id: string | undefined) => nodes.find((n) => n.id === id);
  if (change.positionAbsolute) {
    const movingNodeBounds = {
      x1: change.positionAbsolute.x,
      x2: change.positionAbsolute.x + (movingNode.width ?? 0),
      y1: change.positionAbsolute.y,
      y2: change.positionAbsolute.y + (movingNode.height ?? 0),
    };
    let verticalSnapGap: number = 10;
    let horizontalSnapGap: number = 10;
    return nodes
      .filter((node) => node.id != movingNode.id)
      .filter((node) => !isDescendantOf(movingNode, node, getNodeById))
      .reduce<HelperLines>((helperLines, otherNode) => {
        if (otherNode.positionAbsolute) {
          const otherNodeBounds = {
            x1: otherNode.positionAbsolute.x,
            x2: otherNode.positionAbsolute.x + (otherNode.width ?? 0),
            y1: otherNode.positionAbsolute.y,
            y2: otherNode.positionAbsolute.y + (otherNode.height ?? 0),
          };

          const x1x1Gap = Math.abs(movingNodeBounds.x1 - otherNodeBounds.x1);
          if (x1x1Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x1;
            helperLines.snapX = otherNodeBounds.x1;
            verticalSnapGap = x1x1Gap;
          }

          const x2x1Gap = Math.abs(movingNodeBounds.x2 - otherNodeBounds.x1);
          if (x2x1Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x1;
            helperLines.snapX = otherNodeBounds.x1 - (movingNode.width ?? 0);
            verticalSnapGap = x2x1Gap;
          }

          const x1x2Gap = Math.abs(movingNodeBounds.x1 - otherNodeBounds.x2);
          if (x1x2Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x2;
            helperLines.snapX = otherNodeBounds.x2;
            verticalSnapGap = x1x2Gap;
          }

          const x2x2Gap = Math.abs(movingNodeBounds.x2 - otherNodeBounds.x2);
          if (x2x2Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x2;
            helperLines.snapX = otherNodeBounds.x2 - (movingNode.width ?? 0);
            verticalSnapGap = x2x2Gap;
          }

          const y1y1Gap = Math.abs(movingNodeBounds.y1 - otherNodeBounds.y1);
          if (y1y1Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y1;
            helperLines.snapY = otherNodeBounds.y1;
            horizontalSnapGap = y1y1Gap;
          }

          const y2y1Gap = Math.abs(movingNodeBounds.y2 - otherNodeBounds.y1);
          if (y2y1Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y1;
            helperLines.snapY = otherNodeBounds.y1 - (movingNode.height ?? 0);
            horizontalSnapGap = y2y1Gap;
          }

          const y1y2Gap = Math.abs(movingNodeBounds.y1 - otherNodeBounds.y2);
          if (y1y2Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y2;
            helperLines.snapY = otherNodeBounds.y2;
            horizontalSnapGap = y1y2Gap;
          }

          const y2y2Gap = Math.abs(movingNodeBounds.y2 - otherNodeBounds.y2);
          if (y2y2Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y2;
            helperLines.snapY = otherNodeBounds.y2 - (movingNode.height ?? 0);
            horizontalSnapGap = y2y2Gap;
          }

          const verticalCenterGap = Math.abs(
            movingNodeBounds.x1 + (movingNode.width ?? 0) / 2 - (otherNodeBounds.x1 + (otherNode.width ?? 0) / 2)
          );
          if (verticalCenterGap < verticalSnapGap) {
            helperLines.vertical = otherNode.positionAbsolute.x + (otherNode.width ?? 0) / 2;
            helperLines.snapX = otherNodeBounds.x1 + (otherNode.width ?? 0) / 2 - (movingNode.width ?? 0) / 2;
          }

          const horizontalCenterGap = Math.abs(
            movingNodeBounds.y1 + (movingNode.height ?? 0) / 2 - (otherNodeBounds.y1 + (otherNode.height ?? 0) / 2)
          );
          if (horizontalCenterGap < horizontalSnapGap) {
            helperLines.horizontal = otherNode.positionAbsolute.y + (otherNode.height ?? 0) / 2;
            helperLines.snapY = otherNodeBounds.y1 + (otherNode.height ?? 0) / 2 - (movingNode.height ?? 0) / 2;
          }
        }
        return helperLines;
      }, noHelperLines);
  }
  return noHelperLines;
};

export const useHelperLines = (): UseHelperLinesValue => {
  const [enabled, setEnabled] = useState<boolean>(false);
  const [state, setState] = useState<UseHelperLinesState>({ vertical: null, horizontal: null });
  const { getNodes } = useReactFlow<NodeData, EdgeData>();

  const applyHelperLines = (changes: NodeChange[]): NodeChange[] => {
    if (enabled && changes.length === 1 && changes[0]) {
      const change = changes[0];
      if (isMove(change)) {
        const movingNode = getNodes().find((node) => node.id === change.id);
        if (movingNode && !movingNode.data.pinned) {
          const helperLines: HelperLines = getHelperLines(change, movingNode, getNodes());
          setState({ vertical: helperLines.vertical, horizontal: helperLines.horizontal });
          let snapOffsetX: number = 0;
          let snapOffsetY: number = 0;
          let parentNode = getNodes().find((node) => node.id === movingNode.parentNode);
          while (parentNode) {
            snapOffsetX -= parentNode.position.x;
            snapOffsetY -= parentNode.position.y;
            parentNode = getNodes().find((node) => node.id === parentNode?.parentNode ?? '');
          }
          if (helperLines.snapX && change.position) {
            change.position.x = helperLines.snapX + snapOffsetX;
          }
          if (helperLines.snapY && change.position) {
            change.position.y = helperLines.snapY + snapOffsetY;
          }
        }
      }
    }
    return changes;
  };

  const resetHelperLines = (changes: NodeChange[]): void => {
    if (enabled && changes[0]?.type === 'reset') {
      setState({ vertical: null, horizontal: null });
    }
  };

  return {
    helperLinesEnabled: enabled,
    setHelperLinesEnabled: setEnabled,
    horizontalHelperLine: state.horizontal,
    verticalHelperLine: state.vertical,
    applyHelperLines,
    resetHelperLines,
  };
};
