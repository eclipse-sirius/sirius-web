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
import React, { memo } from 'react';
import { Handle, Position, ReactFlowState, useStore } from 'reactflow';
import { useConnector } from '../connector/useConnector';
import { ConnectionTargetHandleProps } from './ConnectionTargetHandle.types';
const targetHandleNodeStyle: React.CSSProperties = {
  position: 'absolute',
  top: 0,
  left: 0,
  width: '100%',
  height: '100%',
  transform: 'none',
  borderRadius: 0,
  border: 'none',
  opacity: 0,
  pointerEvents: 'all',
  cursor: 'crosshair',
};
const connectionNodeIdSelector = (state: ReactFlowState) => !!state.connectionNodeId;
export const ConnectionTargetHandle = memo(({ nodeId }: ConnectionTargetHandleProps) => {
  const isConnecting = useStore(connectionNodeIdSelector);
  const { isNewConnection, setPosition, setFrozen } = useConnector();

  const handleOnMouseUp = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (isNewConnection) {
      event.stopPropagation();
      setFrozen(true);
      if ('clientX' in event && 'clientY' in event) {
        setPosition({ x: event.clientX || 0, y: event.clientY });
      } else if ('touches' in event) {
        const touchEvent = event as TouchEvent;
        setPosition({ x: touchEvent.touches[0]?.clientX || 0, y: touchEvent.touches[0]?.clientY || 0 });
      }
    }
  };

  if (isConnecting) {
    return (
      <Handle
        id={`handle--${nodeId}--target`}
        type="target"
        position={Position.Top}
        style={targetHandleNodeStyle}
        isConnectableEnd={true}
        isConnectableStart={false}
        onMouseUp={handleOnMouseUp}
      />
    );
  } else {
    return null;
  }
});
