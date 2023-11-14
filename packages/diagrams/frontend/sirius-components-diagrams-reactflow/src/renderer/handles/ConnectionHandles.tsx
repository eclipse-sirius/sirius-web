/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { Theme, useTheme } from '@material-ui/core/styles';
import React from 'react';
import { Handle, Position, ReactFlowState, useStore } from 'reactflow';
import { ConnectionHandle, ConnectionHandlesProps } from './ConnectionHandles.types';

const borderHandlesStyle = (position: Position): React.CSSProperties => {
  const style: React.CSSProperties = {
    display: 'flex',
    position: 'absolute',
    justifyContent: 'space-evenly',
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

const handleStyle = (theme: Theme, position: Position, isEdgeSelected: boolean): React.CSSProperties => {
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
  if (isEdgeSelected) {
    style.opacity = 1;
    style.outline = `${theme.palette.primary.main} solid 1px`;
  }
  return style;
};

const edgesSelectedSelector = (state: ReactFlowState) =>
  Array.from(state.edges.values()).filter((edge) => edge.selected);

export const ConnectionHandles = ({ connectionHandles }: ConnectionHandlesProps) => {
  const theme = useTheme();
  const edgesSelected = useStore(edgesSelectedSelector);
  const handlesSelected: string[] = edgesSelected.flatMap((edge) => {
    if (edge.sourceHandle && edge.targetHandle) {
      return [edge.sourceHandle, edge.targetHandle];
    } else {
      return [];
    }
  });

  const isHandleSelected = (connectionHandle: ConnectionHandle) => {
    return !!handlesSelected.find((selectedHandle) => selectedHandle === connectionHandle.id);
  };

  return (
    <>
      {Object.values(Position).map((position) => {
        return (
          <div style={borderHandlesStyle(position)} key={position}>
            {connectionHandles
              .filter((connectionHandle) => connectionHandle.position === position)
              .map((connectionHandle) => {
                return (
                  <Handle
                    id={connectionHandle.id ?? ''}
                    style={handleStyle(theme, connectionHandle.position, isHandleSelected(connectionHandle))}
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
