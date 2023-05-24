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

import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import { memo, useContext } from 'react';
import { Handle, NodeProps, Position } from 'reactflow';
import { ImageNodeData } from './ImageNode.types';

const imageNodeStyle = (style: Partial<React.CSSProperties>): React.CSSProperties => {
  return { ...style };
};

export const ImageNode = memo(({ data, isConnectable }: NodeProps<ImageNodeData>) => {
  const { httpOrigin } = useContext(ServerContext);
  return (
    <>
      <img src={httpOrigin + data.imageURL} style={imageNodeStyle(data.style)} />
      <Handle type="source" position={Position.Left} isConnectable={isConnectable} />
      <Handle type="target" position={Position.Right} isConnectable={isConnectable} />
    </>
  );
});
