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
import { Handle, Position } from '@xyflow/react';
import React, { memo } from 'react';
import { useConnectorContext } from '../connector/useConnectorContext';
import { ConnectionTargetHandleProps } from './ConnectionTargetHandle.types';
import { useRefreshTargetHandles } from './useRefreshTargetHandles';

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
  zIndex: 999,
};

const targetTempHandleStyle: React.CSSProperties = {
  opacity: 0,
};

export const ConnectionTargetHandle = memo(({ nodeId }: ConnectionTargetHandleProps) => {
  const { connection } = useConnectorContext();

  useRefreshTargetHandles(nodeId, !!connection);

  return (
    <>
      {!!connection ? (
        <>
          <Handle
            id={`handle--${nodeId}--temp--top`}
            type="target"
            position={Position.Top}
            style={targetTempHandleStyle}
            isConnectableEnd={false}
            isConnectableStart={false}
          />
          <Handle
            id={`handle--${nodeId}--temp--bottom`}
            type="target"
            position={Position.Bottom}
            style={targetTempHandleStyle}
            isConnectableEnd={false}
            isConnectableStart={false}
          />
          <Handle
            id={`handle--${nodeId}--temp--left`}
            type="target"
            position={Position.Left}
            style={targetTempHandleStyle}
            isConnectableEnd={false}
            isConnectableStart={false}
          />
          <Handle
            id={`handle--${nodeId}--temp--right`}
            type="target"
            position={Position.Right}
            style={targetTempHandleStyle}
            isConnectableEnd={false}
            isConnectableStart={false}
          />
          <Handle
            id={`handle--${nodeId}--target`}
            type="target"
            position={Position.Top}
            style={targetHandleNodeStyle}
            isConnectableEnd={true}
            isConnectableStart={false}
          />
        </>
      ) : null}
    </>
  );
});
