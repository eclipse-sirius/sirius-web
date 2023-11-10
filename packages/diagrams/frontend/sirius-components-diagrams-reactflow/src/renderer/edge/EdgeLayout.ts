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
import { GetEdgeParameters, GetHandleCoordinatesByPosition, GetNodeCenter, GetParameters } from './EdgeLayout.types';

export const getEdgeParameters: GetEdgeParameters = (source, target, edgeId, visiblesNodes) => {
  const { x: sourceX, y: sourceY, position: sourcePosition } = getParameters(source, target, edgeId, visiblesNodes);
  const { x: targetX, y: targetY, position: targetPosition } = getParameters(target, source, edgeId, visiblesNodes);

  return {
    sourceX,
    sourceY,
    sourcePosition,
    targetX,
    targetY,
    targetPosition,
  };
};

const getParameters: GetParameters = (nodeA, nodeB, edgeId, visiblesNodes) => {
  const centerA = getNodeCenter(nodeA, visiblesNodes);
  const centerB = getNodeCenter(nodeB, visiblesNodes);

  const horizontallDifference = Math.abs(centerA.x - centerB.x);
  const verticalDifference = Math.abs(centerA.y - centerB.y);

  let position: Position;
  if (horizontallDifference > verticalDifference) {
    position = centerA.x > centerB.x ? Position.Left : Position.Right;
  } else {
    position = centerA.y > centerB.y ? Position.Top : Position.Bottom;
  }

  const { x, y } = getHandleCoordinatesByPosition(nodeA, position, edgeId);

  return {
    x,
    y,
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

const getHandleCoordinatesByPosition: GetHandleCoordinatesByPosition = (node, handlePosition, edgeId) => {
  let handle: HandleElement | undefined = (node[internalsSymbol]?.handleBounds?.source ?? []).find(
    (handle) => handle.id?.split('--')[2] === edgeId
  );
  if (!handle) {
    handle = (node[internalsSymbol]?.handleBounds?.target ?? []).find((handle) => handle.id?.split('--')[2] === edgeId);
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
