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

import { HandleElement, Position, XYPosition, internalsSymbol } from 'reactflow';
import { BorderNodePosition } from '../DiagramRenderer.types';
import { ConnectionHandle } from '../handles/ConnectionHandles.types';
import { isDescendantOf, isSiblingOrDescendantOf } from '../layout/layoutNode';
import {
  GetEdgeParameters,
  GetEdgeParametersWhileMoving,
  GetHandleCoordinatesByPosition,
  GetNodeCenter,
  GetParameters,
  GetUpdatedConnectionHandlesParameters,
  NodeCenter,
} from './EdgeLayout.types';

export const getUpdatedConnectionHandles: GetUpdatedConnectionHandlesParameters = (
  sourceNode,
  targetNode,
  sourcePosition,
  targetPosition,
  sourceHandle,
  targetHandle
) => {
  const sourceConnectionHandles: ConnectionHandle[] = sourceNode.data.connectionHandles.map(
    (nodeConnectionHandle: ConnectionHandle) => {
      if (nodeConnectionHandle.id === sourceHandle && nodeConnectionHandle.type === 'source') {
        nodeConnectionHandle.position = sourcePosition;
      }
      return nodeConnectionHandle;
    }
  );

  const targetConnectionHandles: ConnectionHandle[] = targetNode.data.connectionHandles.map(
    (nodeConnectionHandle: ConnectionHandle) => {
      if (nodeConnectionHandle.id === targetHandle && nodeConnectionHandle.type === 'target') {
        nodeConnectionHandle.position = targetPosition;
      }
      return nodeConnectionHandle;
    }
  );

  return {
    sourceConnectionHandles,
    targetConnectionHandles,
  };
};

export const getEdgeParametersWhileMoving: GetEdgeParametersWhileMoving = (
  movingNode,
  source,
  target,
  visiblesNodes,
  layoutDirection
) => {
  const { position: sourcePosition } = getParameters(movingNode, source, target, visiblesNodes, layoutDirection);
  const { position: targetPosition } = getParameters(movingNode, target, source, visiblesNodes, layoutDirection);

  return {
    sourcePosition,
    targetPosition,
  };
};

export const getEdgeParameters: GetEdgeParameters = (source, target, visiblesNodes, layoutDirection) => {
  const { position: sourcePosition } = getParameters(null, source, target, visiblesNodes, layoutDirection);
  const { position: targetPosition } = getParameters(null, target, source, visiblesNodes, layoutDirection);

  return {
    sourcePosition,
    targetPosition,
  };
};

const isVerticalLayoutDirection = (layoutDirection: string): boolean =>
  layoutDirection === 'DOWN' || layoutDirection === 'UP';
const isHorizontalLayoutDirection = (layoutDirection: string): boolean =>
  layoutDirection === 'RIGHT' || layoutDirection === 'LEFT';

const computeBorderNodeHandlePosition = (
  borderNodePosition: BorderNodePosition | null,
  isInside: boolean
): Position => {
  switch (borderNodePosition) {
    case BorderNodePosition.EAST:
      return isInside ? Position.Left : Position.Right;
    case BorderNodePosition.SOUTH:
      return isInside ? Position.Top : Position.Bottom;
    case BorderNodePosition.WEST:
      return isInside ? Position.Right : Position.Left;
    case BorderNodePosition.NORTH:
      return isInside ? Position.Bottom : Position.Top;
    default:
      return isInside ? Position.Left : Position.Right;
  }
};

const getParameters: GetParameters = (movingNode, nodeA, nodeB, visiblesNodes, layoutDirection) => {
  if (nodeA.data.isBorderNode) {
    const isInside = isSiblingOrDescendantOf(nodeA, nodeB, (nodeId) =>
      visiblesNodes.find((node) => node.id === nodeId)
    );
    return {
      position: computeBorderNodeHandlePosition(nodeA.data.borderNodePosition, isInside),
    };
  }
  let centerA: NodeCenter;
  if (movingNode && movingNode.id === nodeA.id) {
    centerA = {
      x: (movingNode.positionAbsolute?.x ?? 0) + (nodeA.width ?? 0) / 2,
      y: (movingNode.positionAbsolute?.y ?? 0) + (nodeA.height ?? 0) / 2,
    };
  } else {
    centerA = getNodeCenter(nodeA, visiblesNodes);
  }

  let centerB: NodeCenter;
  if (movingNode && movingNode.id === nodeB.id) {
    centerB = {
      x: (movingNode.positionAbsolute?.x ?? 0) + (nodeB.width ?? 0) / 2,
      y: (movingNode.positionAbsolute?.y ?? 0) + (nodeB.height ?? 0) / 2,
    };
  } else {
    centerB = getNodeCenter(nodeB, visiblesNodes);
  }
  const horizontalDifference = Math.abs(centerA.x - centerB.x);
  const verticalDifference = Math.abs(centerA.y - centerB.y);
  const isDescendant = isDescendantOf(nodeB, nodeA, (nodeId) => visiblesNodes.find((node) => node.id === nodeId));
  let position: Position;
  if (isVerticalLayoutDirection(layoutDirection)) {
    if (isDescendant) {
      position = centerA.y <= centerB.y ? Position.Top : Position.Bottom;
    } else {
      position = centerA.y > centerB.y ? Position.Top : Position.Bottom;
    }
  } else if (isHorizontalLayoutDirection(layoutDirection)) {
    if (isDescendant) {
      position = centerA.x <= centerB.x ? Position.Left : Position.Right;
    } else {
      position = centerA.x > centerB.x ? Position.Left : Position.Right;
    }
  } else {
    if (horizontalDifference > verticalDifference) {
      if (isDescendant) {
        position = centerA.x <= centerB.x ? Position.Left : Position.Right;
      } else {
        position = centerA.x > centerB.x ? Position.Left : Position.Right;
      }
    } else {
      if (isDescendant) {
        position = centerA.y <= centerB.y ? Position.Top : Position.Bottom;
      } else {
        position = centerA.y > centerB.y ? Position.Top : Position.Bottom;
      }
    }
  }
  return {
    position,
  };
};

export const getNodeCenter: GetNodeCenter = (node, visiblesNodes) => {
  if (node.positionAbsolute?.x && node.positionAbsolute?.y) {
    return {
      x: (node.positionAbsolute?.x ?? 0) + (node.width ?? 0) / 2,
      y: (node.positionAbsolute?.y ?? 0) + (node.height ?? 0) / 2,
    };
  } else {
    let parentNode = visiblesNodes.find((nodeParent) => nodeParent.id === node.parentNode);
    let position = {
      x: (node.position?.x ?? 0) + (node.width ?? 0) / 2,
      y: (node.position?.y ?? 0) + (node.height ?? 0) / 2,
    };
    while (parentNode) {
      position = {
        x: position.x + parentNode.position?.x ?? 0,
        y: position.y + parentNode.position?.y ?? 0,
      };
      let parentNodeId = parentNode.parentNode ?? '';
      parentNode = visiblesNodes.find((nodeParent) => nodeParent.id === parentNodeId);
    }
    return position;
  }
};

export const getHandleCoordinatesByPosition: GetHandleCoordinatesByPosition = (
  node,
  handlePosition,
  handleId,
  calculateCustomNodeEdgeHandlePosition
) => {
  let handle: HandleElement | undefined = (node[internalsSymbol]?.handleBounds?.source ?? []).find(
    (handle) => handle.id === handleId
  );
  if (!handle) {
    handle = (node[internalsSymbol]?.handleBounds?.target ?? []).find((handle) => handle.id === handleId);
  }

  let handleXYPosition: XYPosition = { x: 0, y: 0 };
  if (handle && handlePosition) {
    if (calculateCustomNodeEdgeHandlePosition) {
      handleXYPosition = calculateCustomNodeEdgeHandlePosition(node, handlePosition, handle);
    } else {
      let offsetX = handle.width / 2;
      let offsetY = handle.height / 2;

      switch (handlePosition) {
        case Position.Left:
          offsetX = handle.width;
          break;
        case Position.Right:
          offsetX = 0;
          break;
        case Position.Top:
          offsetY = handle.height;
          break;
        case Position.Bottom:
          offsetY = 0;
          break;
      }
      handleXYPosition = {
        x: handle.x + offsetX,
        y: handle.y + offsetY,
      };
    }
  }

  return {
    x: (node.positionAbsolute?.x ?? 0) + handleXYPosition.x,
    y: (node.positionAbsolute?.y ?? 0) + handleXYPosition.y,
  };
};
