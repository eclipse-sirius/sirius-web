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

import { CSSProperties, memo } from 'react';
import { Handle, NodeProps, Position } from 'reactflow';
import { RectangularNodeData } from './RectangularNode.types';

const rectangularNodeStyle = (style: Partial<React.CSSProperties>): React.CSSProperties => {
  return {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: '20px',
    ...style,
  };
};

const labelStyle = (style: Partial<React.CSSProperties>): CSSProperties => {
  return {
    ...style,
  };
};

export const RectangularNode = memo(({ data, isConnectable }: NodeProps<RectangularNodeData>) => {
  return (
    <div style={rectangularNodeStyle(data.style)}>
      <div style={labelStyle(data.label.style)}>{data.label.text}</div>
      <Handle type="source" position={Position.Left} isConnectable={isConnectable} />
      <Handle type="target" position={Position.Right} isConnectable={isConnectable} />
    </div>
  );
});
