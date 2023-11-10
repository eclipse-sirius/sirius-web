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
import React from 'react';
import { Handle, Position, useStore } from 'reactflow';
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

export const ConnectionTargetHandle = ({ nodeId }: ConnectionTargetHandleProps) => {
  const connectionNodeId = useStore((state) => state.connectionNodeId);
  const isConnecting = !!connectionNodeId;
  if (isConnecting) {
    return (
      <Handle
        id={`handle--${nodeId}--target`}
        type="target"
        position={Position.Top}
        style={targetHandleNodeStyle}
        isConnectableEnd={true}
        isConnectableStart={false}
      />
    );
  } else {
    return null;
  }
};
