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
import { NodeProps, NodeResizer } from 'reactflow';
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDrop } from '../drop/useDrop';
import { useDropNode } from '../dropNode/useDropNode';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { ListNodeData } from './ListNode.types';
import { ConnectionHandles } from './handles/ConnectionHandles';
import { ConnectionSourceHandles } from './handles/NewConnectionSourceHandles';
import { ConnectionTargetHandles } from './handles/NewConnectionTargetHandle';
import { useConnectionHandles } from './handles/useConnectionHandles';

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

export const ListNode = memo(({ data, id, selected }: NodeProps<ListNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { newConnectionStyleProvider } = useConnector();
  const { dropFeedbackStyleProvider } = useDropNode();

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  useConnectionHandles(id, data.connectionHandles);

  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} shouldResize={() => !data.isBorderNode} />
      <div
        style={{
          ...listNodeStyle(theme, data.style, selected, data.faded),
          ...dropFeedbackStyleProvider.getNodeStyle(id),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`List - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
        {selected ? <ConnectionSourceHandles nodeId={id} /> : null}
        <ConnectionTargetHandles nodeId={id} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
      </div>
    </>
  );
});
