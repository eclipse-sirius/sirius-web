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
import { memo, useMemo } from 'react';
import { NodeProps } from 'reactflow';
import { Label } from '../Label';
import { useConnectorNodeStyle } from '../connector/useConnectorNodeStyle';
import { useDrop } from '../drop/useDrop';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { ConnectionHandles } from '../handles/ConnectionHandles';
import { ConnectionTargetHandle } from '../handles/ConnectionTargetHandle';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { ListNodeData } from './ListNode.types';
import { Resizer } from './Resizer';

const listNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const listNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    background: getCSSColor(String(style.background), theme),
    borderTopColor: getCSSColor(String(style.borderTopColor), theme),
    borderBottomColor: getCSSColor(String(style.borderBottomColor), theme),
    borderLeftColor: getCSSColor(String(style.borderLeftColor), theme),
    borderRightColor: getCSSColor(String(style.borderRightColor), theme),
  };
  if (selected || hovered) {
    listNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return listNodeStyle;
};

export const ListNode = memo(({ data, id, selected, dragging }: NodeProps<ListNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { style: connectionFeedbackStyle } = useConnectorNodeStyle(id, data.nodeDescription.id);
  const { style: dropFeedbackStyle } = useDropNodeStyle(data.isDropNodeTarget, data.isDropNodeCandidate, dragging);
  const nodeStyle = useMemo(
    () => listNodeStyle(theme, data.style, selected, data.isHovered, data.faded),
    [data.style, selected, data.isHovered, data.faded]
  );

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  useRefreshConnectionHandles(id, data.connectionHandles);
  return (
    <>
      <Resizer data={data} selected={selected} />
      <div
        style={{
          ...nodeStyle,
          ...connectionFeedbackStyle,
          ...dropFeedbackStyle,
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`List - ${data?.insideLabel?.text}`}>
        {data.insideLabel ? <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} /> : null}
        {selected ? (
          <DiagramElementPalette diagramElementId={id} labelId={data.insideLabel ? data.insideLabel.id : null} />
        ) : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} nodeDescription={data.nodeDescription} isHovered={data.isHovered} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
      </div>
    </>
  );
});
