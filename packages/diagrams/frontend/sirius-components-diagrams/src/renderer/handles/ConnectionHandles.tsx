/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo and others.
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
import { Handle, Position, XYPosition } from '@xyflow/react';
import React, { memo } from 'react';
import { ConnectionHandlesProps } from './ConnectionHandles.types';

const borderHandlesStyle = (position: Position): React.CSSProperties => {
  const style: React.CSSProperties = {
    display: 'flex',
    position: 'absolute',
    justifyContent: 'space-evenly',
    pointerEvents: 'none',
  };
  switch (position) {
    case Position.Left:
      style.height = '100%';
      style.left = '0';
      style.top = '0';
      style.flexDirection = 'column';
      break;
    case Position.Right:
      style.height = '100%';
      style.right = '0';
      style.top = '0';
      style.flexDirection = 'column';
      break;
    case Position.Top:
      style.width = '100%';
      style.left = '0';
      style.top = '0';
      style.flexDirection = 'row';
      break;
    case Position.Bottom:
      style.width = '100%';
      style.left = '0';
      style.bottom = '0';
      style.flexDirection = 'row';
      break;
  }
  return style;
};

const handleStyle = (
  position: Position,
  isVirtualHandle: boolean,
  isHidden: boolean,
  XYPosition: XYPosition | null
): React.CSSProperties => {
  if (XYPosition) {
    const style: React.CSSProperties = {
      position: 'absolute',
      transform: 'none',
      pointerEvents: 'none',
      backgroundColor: 'white',
      border: '1px solid black',
    };
    switch (position) {
      case Position.Left:
      case Position.Top:
        style.top = XYPosition.y;
        style.left = XYPosition.x;
        break;
      case Position.Right:
      case Position.Bottom:
        style.top = XYPosition.y;
        style.left = XYPosition.x;
        break;
    }
    if (isVirtualHandle) {
      style.position = 'absolute';
      style.display = 'none';
    }
    if (isHidden) {
      style.opacity = 0;
    }
    return style;
  } else {
    const style: React.CSSProperties = {
      position: 'relative',
      transform: 'none',
      pointerEvents: 'none',
      backgroundColor: 'black',
      border: 'black',
    };
    switch (position) {
      case Position.Left:
      case Position.Right:
        style.top = 'auto';
        break;
      case Position.Top:
      case Position.Bottom:
        style.left = 'auto';
        break;
    }
    if (isVirtualHandle) {
      style.position = 'absolute';
      style.display = 'none';
    }
    if (isHidden) {
      style.opacity = 0;
    }
    return style;
  }
};

export const ConnectionHandles = memo(({ connectionHandles }: ConnectionHandlesProps) => {
  return (
    <>
      {Object.values(Position).map((position) => {
        const currentSideHandles = connectionHandles
          .filter((connectionHandle) => connectionHandle.position === position)
          .sort((a, b) => a.index - b.index);
        return (
          <div style={borderHandlesStyle(position)} key={position}>
            {currentSideHandles.map((connectionHandle) => {
              return (
                <Handle
                  id={connectionHandle.id ?? ''}
                  style={handleStyle(
                    connectionHandle.position,
                    connectionHandle.isVirtualHandle,
                    connectionHandle.isHidden,
                    connectionHandle.XYPosition
                  )}
                  type={connectionHandle.type}
                  position={connectionHandle.position}
                  key={connectionHandle.id}
                  isConnectable={true}
                />
              );
            })}
          </div>
        );
      })}
    </>
  );
});
