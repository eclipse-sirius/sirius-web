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
import React, { memo } from 'react';
import { NodeProps, NodeResizer } from 'reactflow';
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDrop } from '../drop/useDrop';
import { useDropNode } from '../dropNode/useDropNode';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { RectangularNodeData } from './RectangularNode.types';
import { ConnectionHandles } from './handles/ConnectionHandles';
import { ConnectionSourceHandles } from './handles/NewConnectionSourceHandles';
import { ConnectionTargetHandles } from './handles/NewConnectionTargetHandle';
import { useConnectionHandles } from './handles/useConnectionHandles';
const rectangularNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  faded: boolean
): React.CSSProperties => {
  const rectangularNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    backgroundColor: getCSSColor(String(style.backgroundColor), theme),
  };
  if (selected) {
    rectangularNodeStyle.outline = `${theme.palette.primary.main} solid 1px`;
  }

  return rectangularNodeStyle;
};

export const RectangularNode = memo(({ data, id, selected }: NodeProps<RectangularNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { dropFeedbackStyleProvider } = useDropNode();
  const { newConnectionStyleProvider } = useConnector();

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  useConnectionHandles(id, data.connectionHandles);
  //console.log('Coucou Node');
  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} shouldResize={() => !data.isBorderNode} />
      <div
        style={{
          ...rectangularNodeStyle(theme, data.style, selected, data.faded),
          ...dropFeedbackStyleProvider.getNodeStyle(id),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`Rectangle - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
        {selected ? <ConnectionSourceHandles nodeId={id} /> : null}
        <ConnectionTargetHandles nodeId={id} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
      </div>
    </>
  );
});
