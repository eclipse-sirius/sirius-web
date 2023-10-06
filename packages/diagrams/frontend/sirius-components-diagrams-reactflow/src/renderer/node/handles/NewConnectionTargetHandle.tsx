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
import { ConnectionTargetHandlesProps } from './NewConnectionTargetHandle.types';
const targetHandleNodeStyle = (): React.CSSProperties => {
  return {
    position: 'absolute',
    width: '100%',
    height: '100%',
    top: 0,
    left: 0,
    transform: 'none',
    borderRadius: 0,
    border: 'none',
    opacity: 0,
    pointerEvents: 'all',
    cursor: 'crosshair',
  };
};
const connectionNodeIdSelector = (state) => state.connectionNodeId;
export const ConnectionTargetHandles = ({ nodeId }: ConnectionTargetHandlesProps) => {
  const connectionNodeId = useStore(connectionNodeIdSelector);
  const isConnecting = !!connectionNodeId;
  return (
    <>
      {isConnecting ? (
        <Handle
          id={`handle--${nodeId}--target`}
          type="target"
          position={Position.Top}
          style={targetHandleNodeStyle()}
          isConnectableEnd={true}
          isConnectableStart={false}
        />
      ) : null}
    </>
  );
};
