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

import { HandleElement, Position, internalsSymbol } from 'reactflow';
import { ConnectionHandle } from '../handles/ConnectionHandles.types';
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
  visiblesNodes
) => {
  const { position: sourcePosition } = getParameters(movingNode, source, target, visiblesNodes);
  const { position: targetPosition } = getParameters(movingNode, target, source, visiblesNodes);

  return {
    sourcePosition,
    targetPosition,
  };
};

export const getEdgeParameters: GetEdgeParameters = (source, target, visiblesNodes) => {
  const { position: sourcePosition } = getParameters(null, source, target, visiblesNodes);
  const { position: targetPosition } = getParameters(null, target, source, visiblesNodes);

  return {
    sourcePosition,
    targetPosition,
  };
};

const getParameters: GetParameters = (movingNode, nodeA, nodeB, visiblesNodes) => {
  let centerA: NodeCenter;
  if (movingNode && movingNode.id === nodeA.id) {
    centerA = {
      x: movingNode.positionAbsolute?.x ?? 0 + (nodeA.width ?? 0) / 2,
      y: movingNode.positionAbsolute?.y ?? 0 + (nodeA.height ?? 0) / 2,
    };
  } else {
    centerA = getNodeCenter(nodeA, visiblesNodes);
  }

  let centerB: NodeCenter;
  if (movingNode && movingNode.id === nodeB.id) {
    centerB = {
      x: movingNode.positionAbsolute?.x ?? 0 + (nodeB.width ?? 0) / 2,
      y: movingNode.positionAbsolute?.y ?? 0 + (nodeB.height ?? 0) / 2,
    };
  } else {
    centerB = getNodeCenter(nodeB, visiblesNodes);
  }
  const horizontallDifference = Math.abs(centerA.x - centerB.x);
  const verticalDifference = Math.abs(centerA.y - centerB.y);

  let position: Position;
  if (horizontallDifference > verticalDifference) {
    position = centerA.x > centerB.x ? Position.Left : Position.Right;
  } else {
    position = centerA.y > centerB.y ? Position.Top : Position.Bottom;
  }

  return {
    position,
  };
};

export const getNodeCenter: GetNodeCenter = (node, visiblesNodes) => {
  if (node.positionAbsolute?.x && node.positionAbsolute?.y) {
    return {
      x: node.positionAbsolute?.x ?? 0 + (node.width ?? 0) / 2,
      y: node.positionAbsolute?.y ?? 0 + (node.height ?? 0) / 2,
    };
  } else {
    let parentNode = visiblesNodes.find((nodeParent) => nodeParent.id === node.parentNode);
    let position = {
      x: node.position?.x ?? 0 + (node.width ?? 0) / 2,
      y: node.position?.y ?? 0 + (node.height ?? 0) / 2,
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

export const getHandleCoordinatesByPosition: GetHandleCoordinatesByPosition = (node, handlePosition, handleId) => {
  let handle: HandleElement | undefined = (node[internalsSymbol]?.handleBounds?.source ?? []).find(
    (handle) => handle.id === handleId
  );
  if (!handle) {
    handle = (node[internalsSymbol]?.handleBounds?.target ?? []).find((handle) => handle.id === handleId);
  }

  if (handle && handlePosition) {
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

    const x = (node.positionAbsolute?.x ?? 0) + handle.x + offsetX;
    const y = (node.positionAbsolute?.y ?? 0) + handle.y + offsetY;

    return {
      x,
      y,
    };
  }

  return { x: 0, y: 0 };
};
