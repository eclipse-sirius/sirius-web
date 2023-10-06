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

export const getEdgeParameters: GetEdgeParameters = (source, target) => {
  const { x: sourceX, y: sourceY, position: sourcePosition } = getParameters(source, target);
  const { x: targetX, y: targetY, position: targetPosition } = getParameters(target, source);

  return {
    sourceX,
    sourceY,
    sourcePosition,
    targetX,
    targetY,
    targetPosition,
  };
};

const getParameters: GetParameters = (nodeA, nodeB) => {
  const centerA = getNodeCenter(nodeA);
  const centerB = getNodeCenter(nodeB);

  const horizontallDifference = Math.abs(centerA.x - centerB.x);
  const verticalDifference = Math.abs(centerA.y - centerB.y);

  let position: Position;
  if (horizontallDifference > verticalDifference) {
    position = centerA.x > centerB.x ? Position.Left : Position.Right;
  } else {
    position = centerA.y > centerB.y ? Position.Top : Position.Bottom;
  }

  const { x, y } = getHandleCoordinatesByPosition(nodeA, position);

  return {
    x,
    y,
    position,
  };
};

const getNodeCenter: GetNodeCenter = (node) => {
  return {
    x: node.positionAbsolute?.x ?? 0 + (node.width ?? 0) / 2,
    y: node.positionAbsolute?.y ?? 0 + (node.height ?? 0) / 2,
  };
};

const getHandleCoordinatesByPosition: GetHandleCoordinatesByPosition = (node, handlePosition) => {
  const handle: HandleElement | undefined = (node[internalsSymbol]?.handleBounds?.source ?? []).find(
    (handle) => handle.position === handlePosition
  );

  if (handle) {
    let offsetX = handle.width / 2;
    let offsetY = handle.height / 2;

    switch (handlePosition) {
      case Position.Left:
        offsetX = 0;
        break;
      case Position.Right:
        offsetX = handle.width;
        break;
      case Position.Top:
        offsetY = 0;
        break;
      case Position.Bottom:
        offsetY = handle.height;
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
