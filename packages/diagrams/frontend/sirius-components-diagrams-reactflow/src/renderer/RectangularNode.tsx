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

import { memo } from 'react';
import { Handle, NodeProps, NodeResizer, Position } from 'reactflow';
import { RectangularNodeData } from './RectangularNode.types';

import { Palette } from './Palette';

const rectangularNodeStyle = (style: React.CSSProperties, selected: boolean): React.CSSProperties => {
  const rectangularNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '8px',
    width: '100%',
    height: '100%',
    ...style,
  };

  if (selected) {
    rectangularNodeStyle.outline = `var(--blue-lagoon) solid 1px`;
  }

  return rectangularNodeStyle;
};

const labelStyle = (style: React.CSSProperties): React.CSSProperties => {
  return {
    ...style,
  };
};

export const RectangularNode = memo(({ data, isConnectable, id, selected }: NodeProps<RectangularNodeData>) => {
  return (
    <>
      <NodeResizer color="var(--blue-lagoon)" isVisible={selected} />
      <div style={rectangularNodeStyle(data.style, selected)}>
        <div data-id={data.label.id} style={labelStyle(data.label.style)}>
          {data.label.text}
        </div>
        {selected ? <Palette diagramElementId={id} /> : null}
        <Handle type="source" position={Position.Left} isConnectable={isConnectable} />
        <Handle type="target" position={Position.Right} isConnectable={isConnectable} />
      </div>
    </>
  );
});
