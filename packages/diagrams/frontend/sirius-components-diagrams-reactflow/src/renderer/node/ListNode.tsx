/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import { memo } from 'react';
import { Handle, NodeProps, NodeResizer, Position } from 'reactflow';
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDrop } from '../drop/useDrop';
import { useDropNode } from '../dropNode/useDropNode';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { ListNodeData } from './ListNode.types';

const listNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  faded: boolean
): React.CSSProperties => {
  const listNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    backgroundColor: getCSSColor(String(style.backgroundColor), theme),
    borderColor: getCSSColor(String(style.borderColor), theme),
  };
  if (selected) {
    listNodeStyle.outline = `${theme.palette.primary.main} solid 1px`;
  }

  return listNodeStyle;
};

export const ListNode = memo(({ data, isConnectable, id, selected }: NodeProps<ListNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { onConnectionStartElementClick, newConnectionStyleProvider } = useConnector();
  const { dropFeedbackStyleProvider } = useDropNode();

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} shouldResize={() => !data.isBorderNode} />
      <div
        style={{
          ...listNodeStyle(theme, data.style, selected, data.faded),
          ...newConnectionStyleProvider.getNodeStyle(data.descriptionId),
          ...dropFeedbackStyleProvider.getNodeStyle(id),
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`List - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <Handle
          id={`handle--${id}--top`}
          type="source"
          position={Position.Top}
          isConnectable={isConnectable}
          style={newConnectionStyleProvider.getHandleStyle(data.descriptionId)}
          onMouseDown={onConnectionStartElementClick}
        />
        <Handle
          id={`handle--${id}--left`}
          type="source"
          position={Position.Left}
          isConnectable={isConnectable}
          style={newConnectionStyleProvider.getHandleStyle(data.descriptionId)}
          onMouseDown={onConnectionStartElementClick}
        />
        <Handle
          id={`handle--${id}--right`}
          type="source"
          position={Position.Right}
          isConnectable={isConnectable}
          style={newConnectionStyleProvider.getHandleStyle(data.descriptionId)}
          onMouseDown={onConnectionStartElementClick}
        />
        <Handle
          id={`handle--${id}--bottom`}
          type="source"
          position={Position.Bottom}
          isConnectable={isConnectable}
          style={newConnectionStyleProvider.getHandleStyle(data.descriptionId)}
          onMouseDown={onConnectionStartElementClick}
        />
      </div>
    </>
  );
});
