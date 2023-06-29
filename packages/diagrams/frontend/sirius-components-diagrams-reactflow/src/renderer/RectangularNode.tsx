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

import React from 'react';
import { Label } from './Label';
import { Palette } from './Palette';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';

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

export const RectangularNode = memo(({ data, isConnectable, id, selected }: NodeProps<RectangularNodeData>) => {
  const { setCurrentlyEditedLabelId } = useDiagramDirectEdit();

  const handleDirectEditClick = () => {
    if (data.label) {
      setCurrentlyEditedLabelId('palette', data.label.id, null);
    }
  };

  return (
    <>
      <NodeResizer color="var(--blue-lagoon)" isVisible={selected} />
      <div style={rectangularNodeStyle(data.style, selected)}>
        <Label label={data.label} />
        {selected ? <Palette diagramElementId={id} onDirectEditClick={handleDirectEditClick} /> : null}
        <Handle type="source" position={Position.Left} isConnectable={isConnectable} />
        <Handle type="target" position={Position.Right} isConnectable={isConnectable} />
      </div>
    </>
  );
});
