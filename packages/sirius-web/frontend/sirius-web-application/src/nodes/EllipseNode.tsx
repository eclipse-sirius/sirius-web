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
import {
  ConnectionCreationHandles,
  ConnectionTargetHandle,
  DiagramElementPalette,
  Label,
  useConnector,
  useDrop,
  useDropNode,
} from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { Theme, useTheme } from '@material-ui/core/styles';
import React, { memo } from 'react';
import { Handle, NodeProps, NodeResizer, Position } from 'reactflow';
import { EllipseNodeData } from './EllipseNode.types';

const ellipseNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  faded: boolean
): React.CSSProperties => {
  const ellipseNodeStyle: React.CSSProperties = {
    display: 'flex',
    padding: '8px',
    width: '100%',
    height: '100%',
    borderRadius: '50%',
    border: 'black solid 1px',
    opacity: faded ? '0.4' : '',
    ...style,
    backgroundColor: getCSSColor(String(style.backgroundColor), theme),
  };

  if (selected) {
    ellipseNodeStyle.outline = `${theme.palette.primary.main} solid 1px`;
  }

  return ellipseNodeStyle;
};

export const EllipseNode = memo(({ data, isConnectable, id, selected }: NodeProps<EllipseNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { onConnectionStartElementClick, newConnectionStyleProvider } = useConnector();
  const { dropFeedbackStyleProvider } = useDropNode();

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  return (
    <>
      <NodeResizer
        color={theme.palette.primary.main}
        isVisible={selected}
        shouldResize={() => !data.isBorderNode}
        keepAspectRatio={data.nodeDescription?.keepAspectRatio}
      />
      <div
        style={{
          ...ellipseNodeStyle(theme, data.style, selected, data.faded),
          ...newConnectionStyleProvider.getNodeStyle(data.descriptionId),
          ...dropFeedbackStyleProvider.getNodeStyle(id),
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`Ellipse - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} />
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
