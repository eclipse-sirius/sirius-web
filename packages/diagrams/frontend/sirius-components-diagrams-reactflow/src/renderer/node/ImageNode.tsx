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

import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import { memo, useContext } from 'react';
import { Handle, NodeProps, NodeResizer, Position } from 'reactflow';
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDropNode } from '../dropNode/useDropNode';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { ImageNodeData } from './ImageNode.types';

const imageNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  faded: boolean
): React.CSSProperties => {
  const imageNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
  };
  if (selected) {
    imageNodeStyle.outline = `${theme.palette.primary.main} solid 1px`;
  }

  return imageNodeStyle;
};

export const ImageNode = memo(({ data, isConnectable, id, selected }: NodeProps<ImageNodeData>) => {
  const theme = useTheme();
  const { dropFeedbackStyleProvider } = useDropNode();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { onConnectionStartElementClick, newConnectionStyleProvider } = useConnector();

  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} shouldResize={() => !data.isBorderNode} />
      <img
        src={httpOrigin + data.imageURL}
        style={{
          ...imageNodeStyle(theme, data.style, selected, data.faded),
          ...newConnectionStyleProvider.getNodeStyle(data.descriptionId),
          ...dropFeedbackStyleProvider.getNodeStyle(id),
        }}
        data-testid={`Image - ${data?.targetObjectLabel}`}
      />
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
    </>
  );
});
