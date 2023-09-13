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
import React, { memo } from 'react';
import { Handle, NodeProps, NodeResizer, Position } from 'reactflow';
import { Label } from '../Label';
import { useDrop } from '../drop/useDrop';
import { NodePalette } from '../palette/NodePalette';
import { RectangularNodeData } from './RectangularNode.types';

const rectangularNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  faded: boolean
): React.CSSProperties => {
  const rectangularNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '8px',
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
  };

  if (selected) {
    rectangularNodeStyle.outline = `${theme.palette.primary.main} solid 1px`;
  }

  return rectangularNodeStyle;
};

export const RectangularNode = memo(({ data, isConnectable, id, selected }: NodeProps<RectangularNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} />
      <div
        style={rectangularNodeStyle(theme, data.style, selected, data.faded)}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`Rectangle - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
        {selected ? <NodePalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
        <Handle id={`handle--${id}--top`} type="source" position={Position.Top} isConnectable={isConnectable} />
        <Handle id={`handle--${id}--left`} type="source" position={Position.Left} isConnectable={isConnectable} />
        <Handle id={`handle--${id}--right`} type="source" position={Position.Right} isConnectable={isConnectable} />
        <Handle id={`handle--${id}--bottom`} type="source" position={Position.Bottom} isConnectable={isConnectable} />
      </div>
    </>
  );
});
