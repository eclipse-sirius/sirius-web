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
import { useCallback, useState } from 'react';
import {
  Node,
  NodeChange,
  NodeDimensionChange,
  NodeInternals,
  NodePositionChange,
  useReactFlow,
  useStoreApi,
} from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { isDescendantOf } from '../layout/layoutNode';
import { horizontalHelperLinesSnapGap, verticalHelperLinesSnapGap } from '../layout/layoutParams';
import { HelperLines, UseHelperLinesState, UseHelperLinesValue } from './useHelperLines.types';

const isMove = (change: NodeChange, dragging: boolean): change is NodePositionChange =>
  change.type === 'position' && (!dragging || (typeof change.dragging === 'boolean' && change.dragging));

const isResize = (change: NodeChange): change is NodeDimensionChange =>
  change.type === 'dimensions' && (change.resizing ?? false);

interface RectangularBounds {
  x1: number;
  x2: number;
  y1: number;
  y2: number;
}

const getRectangularBounds = (nodePositionChanges: NodePositionChange[], nodes: Node[]): RectangularBounds => {
  return nodePositionChanges.reduce(
    (rect: RectangularBounds, change: NodePositionChange) => {
      if (change.positionAbsolute) {
        const x = change.positionAbsolute.x;
        const y = change.positionAbsolute.y;
        const node = nodes.find((n) => n.id === change.id);
        const right = x + (node?.width ?? 0);
        const bottom = y + (node?.height ?? 0);

        rect.x1 = Math.min(rect.x1, x);
        rect.x2 = Math.max(rect.x2, right);
        rect.y1 = Math.min(rect.y1, y);
        rect.y2 = Math.max(rect.y2, bottom);
      }
      return rect;
    },
    {
      x1: Infinity,
      x2: -Infinity,
      y1: Infinity,
      y2: -Infinity,
    }
  );
};

const getHelperLinesForMove = (
  movingNodesBounds: RectangularBounds,
  movingNodes: Node<NodeData>[],
  nodes: Node<NodeData>[],
  nodeInternal: NodeInternals
): HelperLines => {
  const noHelperLines: HelperLines = { horizontal: null, vertical: null, snapX: 0, snapY: 0 };
  let verticalSnapGap: number = verticalHelperLinesSnapGap;
  let horizontalSnapGap: number = horizontalHelperLinesSnapGap;
  const movingNodesWidth: number = movingNodesBounds.x2 - movingNodesBounds.x1;
  const movingNodesHeight: number = movingNodesBounds.y2 - movingNodesBounds.y1;
  return nodes
    .filter((node) => !movingNodes.some((n) => n.id === node.id))
    .filter((node) => !movingNodes.some((n) => isDescendantOf(n, node, nodeInternal)))
    .reduce<HelperLines>((helperLines, otherNode) => {
      if (otherNode.positionAbsolute) {
        const otherNodeBounds = {
          x1: otherNode.positionAbsolute.x,
          x2: otherNode.positionAbsolute.x + (otherNode.width ?? 0),
          y1: otherNode.positionAbsolute.y,
          y2: otherNode.positionAbsolute.y + (otherNode.height ?? 0),
        };

        const x1x1Gap = Math.abs(movingNodesBounds.x1 - otherNodeBounds.x1);
        if (x1x1Gap < verticalSnapGap) {
          helperLines.vertical = otherNodeBounds.x1;
          helperLines.snapX = otherNodeBounds.x1;
          verticalSnapGap = x1x1Gap;
        }

        const x2x1Gap = Math.abs(movingNodesBounds.x2 - otherNodeBounds.x1);
        if (x2x1Gap < verticalSnapGap) {
          helperLines.vertical = otherNodeBounds.x1;
          helperLines.snapX = otherNodeBounds.x1 - movingNodesWidth;
          verticalSnapGap = x2x1Gap;
        }

        const x1x2Gap = Math.abs(movingNodesBounds.x1 - otherNodeBounds.x2);
        if (x1x2Gap < verticalSnapGap) {
          helperLines.vertical = otherNodeBounds.x2;
          helperLines.snapX = otherNodeBounds.x2;
          verticalSnapGap = x1x2Gap;
        }

        const x2x2Gap = Math.abs(movingNodesBounds.x2 - otherNodeBounds.x2);
        if (x2x2Gap < verticalSnapGap) {
          helperLines.vertical = otherNodeBounds.x2;
          helperLines.snapX = otherNodeBounds.x2 - movingNodesWidth;
          verticalSnapGap = x2x2Gap;
        }

        const y1y1Gap = Math.abs(movingNodesBounds.y1 - otherNodeBounds.y1);
        if (y1y1Gap < horizontalSnapGap) {
          helperLines.horizontal = otherNodeBounds.y1;
          helperLines.snapY = otherNodeBounds.y1;
          horizontalSnapGap = y1y1Gap;
        }

        const y2y1Gap = Math.abs(movingNodesBounds.y2 - otherNodeBounds.y1);
        if (y2y1Gap < horizontalSnapGap) {
          helperLines.horizontal = otherNodeBounds.y1;
          helperLines.snapY = otherNodeBounds.y1 - movingNodesHeight;
          horizontalSnapGap = y2y1Gap;
        }

        const y1y2Gap = Math.abs(movingNodesBounds.y1 - otherNodeBounds.y2);
        if (y1y2Gap < horizontalSnapGap) {
          helperLines.horizontal = otherNodeBounds.y2;
          helperLines.snapY = otherNodeBounds.y2;
          horizontalSnapGap = y1y2Gap;
        }

        const y2y2Gap = Math.abs(movingNodesBounds.y2 - otherNodeBounds.y2);
        if (y2y2Gap < horizontalSnapGap) {
          helperLines.horizontal = otherNodeBounds.y2;
          helperLines.snapY = otherNodeBounds.y2 - movingNodesHeight;
          horizontalSnapGap = y2y2Gap;
        }

        const verticalCenterGap = Math.abs(
          movingNodesBounds.x1 + movingNodesWidth / 2 - (otherNodeBounds.x1 + (otherNode.width ?? 0) / 2)
        );
        if (verticalCenterGap < verticalSnapGap) {
          helperLines.vertical = otherNode.positionAbsolute.x + (otherNode.width ?? 0) / 2;
          helperLines.snapX = otherNodeBounds.x1 + (otherNode.width ?? 0) / 2 - movingNodesWidth / 2;
        }

        const horizontalCenterGap = Math.abs(
          movingNodesBounds.y1 + movingNodesHeight / 2 - (otherNodeBounds.y1 + (otherNode.height ?? 0) / 2)
        );
        if (horizontalCenterGap < horizontalSnapGap) {
          helperLines.horizontal = otherNode.positionAbsolute.y + (otherNode.height ?? 0) / 2;
          helperLines.snapY = otherNodeBounds.y1 + (otherNode.height ?? 0) / 2 - movingNodesHeight / 2;
        }
      }
      return helperLines;
    }, noHelperLines);
};

const getHelperLinesForResize = (
  change: NodeDimensionChange,
  resizingNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  nodeInternal: NodeInternals
): HelperLines => {
  const noHelperLines: HelperLines = { horizontal: null, vertical: null, snapX: 0, snapY: 0 };
  if (resizingNode.positionAbsolute && change.dimensions) {
    let verticalSnapGap: number = verticalHelperLinesSnapGap;
    let horizontalSnapGap: number = horizontalHelperLinesSnapGap;
    const resizingNodeBounds: { x1: number; x2: number; y1: number; y2: number } = {
      x1: resizingNode.positionAbsolute.x,
      x2: resizingNode.positionAbsolute.x + (change.dimensions.width ?? 0),
      y1: resizingNode.positionAbsolute.y,
      y2: resizingNode.positionAbsolute.y + (change.dimensions.height ?? 0),
    };
    return nodes
      .filter((node) => node.id != resizingNode.id)
      .filter((node) => !isDescendantOf(resizingNode, node, nodeInternal))
      .reduce<HelperLines>((helperLines, otherNode) => {
        if (otherNode.positionAbsolute) {
          const otherNodeBounds = {
            x1: otherNode.positionAbsolute.x,
            x2: otherNode.positionAbsolute.x + (otherNode.width ?? 0),
            y1: otherNode.positionAbsolute.y,
            y2: otherNode.positionAbsolute.y + (otherNode.height ?? 0),
          };

          const x2x1Gap = Math.abs(resizingNodeBounds.x2 - otherNodeBounds.x1);
          if (x2x1Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x1;
            helperLines.snapX = otherNodeBounds.x1;
            verticalSnapGap = x2x1Gap;
          }

          const x2x2Gap = Math.abs(resizingNodeBounds.x2 - otherNodeBounds.x2);
          if (x2x2Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x2;
            helperLines.snapX = otherNodeBounds.x2;
            verticalSnapGap = x2x2Gap;
          }

          const y2y1Gap = Math.abs(resizingNodeBounds.y2 - otherNodeBounds.y1);
          if (y2y1Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y1;
            helperLines.snapY = otherNodeBounds.y1;
            horizontalSnapGap = y2y1Gap;
          }

          const y2y2Gap = Math.abs(resizingNodeBounds.y2 - otherNodeBounds.y2);
          if (y2y2Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y2;
            helperLines.snapY = otherNodeBounds.y2;
            horizontalSnapGap = y2y2Gap;
          }
        }
        return helperLines;
      }, noHelperLines);
  }
  return noHelperLines;
};

const getHelperLinesForResizeAndMove = (
  resizingChange: NodeDimensionChange,
  movingChange: NodePositionChange,
  resizingNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  nodeInternal: NodeInternals
): HelperLines => {
  const noHelperLines: HelperLines = { horizontal: null, vertical: null, snapX: 0, snapY: 0 };
  if (resizingNode.positionAbsolute && resizingChange.dimensions && movingChange.position) {
    let verticalSnapGap: number = verticalHelperLinesSnapGap;
    let horizontalSnapGap: number = horizontalHelperLinesSnapGap;
    const nodeBounds: { x1: number; x2: number; y1: number; y2: number } = {
      x1: movingChange.position.x + resizingNode.positionAbsolute.x - resizingNode.position.x,
      x2:
        movingChange.position.x +
        resizingNode.positionAbsolute.x -
        resizingNode.position.x +
        (resizingChange.dimensions.width ?? 0),
      y1: movingChange.position.y + resizingNode.positionAbsolute.y - resizingNode.position.y,
      y2:
        movingChange.position.y +
        resizingNode.positionAbsolute.y -
        resizingNode.position.y +
        (resizingChange.dimensions.height ?? 0),
    };
    return nodes
      .filter((node) => node.id != resizingNode.id)
      .filter((node) => !isDescendantOf(resizingNode, node, nodeInternal))
      .reduce<HelperLines>((helperLines, otherNode) => {
        if (otherNode.positionAbsolute) {
          const otherNodeBounds = {
            x1: otherNode.positionAbsolute.x,
            x2: otherNode.positionAbsolute.x + (otherNode.width ?? 0),
            y1: otherNode.positionAbsolute.y,
            y2: otherNode.positionAbsolute.y + (otherNode.height ?? 0),
          };

          const x1x1Gap = Math.abs(nodeBounds.x1 - otherNodeBounds.x1);
          if (x1x1Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x1;
            helperLines.snapX = otherNodeBounds.x1;
            verticalSnapGap = x1x1Gap;
          }

          const x1x2Gap = Math.abs(nodeBounds.x1 - otherNodeBounds.x2);
          if (x1x2Gap < verticalSnapGap) {
            helperLines.vertical = otherNodeBounds.x2;
            helperLines.snapX = otherNodeBounds.x2;
            verticalSnapGap = x1x2Gap;
          }

          const y1y1Gap = Math.abs(nodeBounds.y1 - otherNodeBounds.y1);
          if (y1y1Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y1;
            helperLines.snapY = otherNodeBounds.y1;
            horizontalSnapGap = y1y1Gap;
          }

          const y1y2Gap = Math.abs(nodeBounds.y1 - otherNodeBounds.y2);
          if (y1y2Gap < horizontalSnapGap) {
            helperLines.horizontal = otherNodeBounds.y2;
            helperLines.snapY = otherNodeBounds.y2;
            horizontalSnapGap = y1y2Gap;
          }
        }
        return helperLines;
      }, noHelperLines);
  }
  return noHelperLines;
};

export const useHelperLines = (): UseHelperLinesValue => {
  const [enabled, setEnabled] = useState<boolean>(true);
  const [state, setState] = useState<UseHelperLinesState>({ vertical: null, horizontal: null });
  //Here we need the nodes in the ReactFlow store to get positionAbsolute
  const storeApi = useStoreApi();
  const { getNodes } = useReactFlow<NodeData, EdgeData>();
  const applyHelperLines = useCallback(
    (changes: NodeChange[]): NodeChange[] => {
      const nodeInternal: NodeInternals = storeApi.getState().nodeInternals;
      if (enabled && changes.every((change) => isMove(change, true))) {
        const nodePositionChanges = changes.map((change) => change as NodePositionChange);
        const movingNodes: Node[] = nodePositionChanges
          .map((change) => nodeInternal.get(change.id))
          .filter((node): node is NonNullable<typeof node> => node !== undefined)
          .filter((node) => !node.data.pinned);
        if (movingNodes && movingNodes.length > 0) {
          const movingNodesBounds = getRectangularBounds(nodePositionChanges, movingNodes);
          const helperLines: HelperLines = getHelperLinesForMove(
            movingNodesBounds,
            movingNodes,
            getNodes(),
            nodeInternal
          );
          setState({ vertical: helperLines.vertical, horizontal: helperLines.horizontal });
          const movingNodeBoundsSnapX = movingNodesBounds.x1 - (helperLines.snapX ?? 0);
          const movingNodeBoundsSnapY = movingNodesBounds.y1 - (helperLines.snapY ?? 0);
          nodePositionChanges.forEach((change) => {
            if (helperLines.snapX && change.position) {
              change.position.x -= movingNodeBoundsSnapX;
            }
            if (helperLines.snapY && change.position) {
              change.position.y -= movingNodeBoundsSnapY;
            }
          });
        }
      } else if (enabled && changes.length === 1 && changes[0]) {
        const change = changes[0];
        if (isResize(change)) {
          const resizingNode = nodeInternal.get(change.id);
          if (resizingNode) {
            const helperLines: HelperLines = getHelperLinesForResize(change, resizingNode, getNodes(), nodeInternal);
            setState({ vertical: helperLines.vertical, horizontal: helperLines.horizontal });
            if (helperLines.snapX && change.dimensions && resizingNode.positionAbsolute) {
              change.dimensions.width = Math.abs(resizingNode.positionAbsolute.x - helperLines.snapX);
            }
            if (helperLines.snapY && change.dimensions && resizingNode.positionAbsolute) {
              change.dimensions.height = Math.abs(resizingNode.positionAbsolute.y - helperLines.snapY);
            }
          }
        }
      } else if (enabled && changes.length === 2 && changes[0] && changes[1]) {
        const movingChange = changes[0];
        const resizingChange = changes[1];
        if (isMove(movingChange, false) && isResize(resizingChange)) {
          const resizingNode = nodeInternal.get(movingChange.id);
          if (resizingNode) {
            const helperLines: HelperLines = getHelperLinesForResizeAndMove(
              resizingChange,
              movingChange,
              resizingNode,
              getNodes(),
              nodeInternal
            );
            setState({ vertical: helperLines.vertical, horizontal: helperLines.horizontal });
            let snapOffsetX: number = 0;
            let snapOffsetY: number = 0;
            let parentNode = nodeInternal.get(resizingNode.parentNode || '');
            while (parentNode) {
              snapOffsetX -= parentNode.position.x;
              snapOffsetY -= parentNode.position.y;
              parentNode = nodeInternal.get(parentNode?.parentNode ?? '');
            }
            if (
              helperLines.snapX &&
              resizingChange.dimensions &&
              movingChange.position &&
              resizingNode.positionAbsolute &&
              resizingNode.width
            ) {
              movingChange.position.x = helperLines.snapX + snapOffsetX;
              resizingChange.dimensions.width =
                resizingNode.width + resizingNode.positionAbsolute.x - helperLines.snapX;
            }
            if (
              helperLines.snapY &&
              resizingChange.dimensions &&
              movingChange.position &&
              resizingNode.positionAbsolute &&
              resizingNode.height
            ) {
              movingChange.position.y = helperLines.snapY + snapOffsetY;
              resizingChange.dimensions.height =
                resizingNode.height + resizingNode.positionAbsolute.y - helperLines.snapY;
            }
          }
        }
      }
      return changes;
    },
    [enabled]
  );

  const resetHelperLines = useCallback(
    (changes: NodeChange[]): void => {
      if (
        enabled &&
        changes.every(
          (change) =>
            change &&
            ((change.type === 'position' && typeof change.dragging === 'boolean' && !change.dragging) ||
              (change.type === 'dimensions' && !change.resizing))
        )
      ) {
        setState({ vertical: null, horizontal: null });
      }
    },
    [enabled]
  );

  return {
    helperLinesEnabled: enabled,
    setHelperLinesEnabled: setEnabled,
    horizontalHelperLine: state.horizontal,
    verticalHelperLine: state.vertical,
    applyHelperLines,
    resetHelperLines,
  };
};
