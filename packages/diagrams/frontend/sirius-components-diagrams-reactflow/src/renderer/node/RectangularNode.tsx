/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import React, { memo, useContext } from 'react';
import { NodeProps, NodeResizer } from 'reactflow';
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDrop } from '../drop/useDrop';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { ConnectionHandles } from '../handles/ConnectionHandles';
import { ConnectionTargetHandle } from '../handles/ConnectionTargetHandle';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { NodeContext } from './NodeContext';
import { NodeContextValue } from './NodeContext.types';
import { RectangularNodeData } from './RectangularNode.types';

const rectangularNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const rectangularNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    backgroundColor: getCSSColor(String(style.backgroundColor), theme),
  };
  if (selected || hovered) {
    rectangularNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }
  return rectangularNodeStyle;
};

const resizeHandleStyle = (theme: Theme): React.CSSProperties => {
  return {
    width: theme.spacing(0.75),
    height: theme.spacing(0.75),
  };
};

export const RectangularNode = memo(({ data, id, selected }: NodeProps<RectangularNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { newConnectionStyleProvider } = useConnector();
  const { style: dropFeedbackStyle } = useDropNodeStyle(id);
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  useRefreshConnectionHandles(id, data.connectionHandles);
  return (
    <>
      {data.nodeDescription?.userResizable && (
        <NodeResizer
          handleStyle={{ ...resizeHandleStyle(theme) }}
          color={theme.palette.selected}
          isVisible={selected}
          shouldResize={() => !data.isBorderNode}
          keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        />
      )}
      <div
        style={{
          ...rectangularNodeStyle(theme, data.style, selected, hoveredNode?.id === id, data.faded),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
          ...dropFeedbackStyle,
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`Rectangle - ${data?.insideLabel?.text}`}>
        {data.insideLabel ? (
          <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} transform="" />
        ) : null}
        {selected ? (
          <DiagramElementPalette diagramElementId={id} labelId={data.insideLabel ? data.insideLabel.id : null} />
        ) : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
      </div>
    </>
  );
});
