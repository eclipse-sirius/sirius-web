/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo and others.
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
import React from 'react';
import { Handle, Position } from 'reactflow';
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

const handleStyle = (position: Position, isVirtualHandle: boolean): React.CSSProperties => {
  const style: React.CSSProperties = {
    position: 'relative',
    transform: 'none',
    opacity: '0',
    pointerEvents: 'none',
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
  return style;
};

export const ConnectionHandles = ({ connectionHandles }: ConnectionHandlesProps) => {
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
                  style={handleStyle(connectionHandle.position, connectionHandle.hidden)}
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
};
