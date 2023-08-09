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
import { Label } from './Label';
import { RectangularNodeData } from './RectangularNode.types';
import { NodePalette } from './palette/NodePalette';

const rectangularNodeStyle = (
  style: React.CSSProperties,
  theme: Theme,
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

  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} />
      <div style={rectangularNodeStyle(data.style, theme, selected, data.faded)}>
        {data.label ? <Label label={data.label} faded={data.faded} /> : null}
        {selected ? <NodePalette diagramElementId={id} labelId={data.label?.id ?? null} /> : null}
        <Handle id="source" type="source" position={Position.Left} isConnectable={isConnectable} />
        <Handle id="target" type="target" position={Position.Right} isConnectable={isConnectable} />
      </div>
    </>
  );
});
