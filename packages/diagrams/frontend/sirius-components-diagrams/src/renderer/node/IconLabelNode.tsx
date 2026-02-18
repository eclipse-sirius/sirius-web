/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { Theme, useTheme } from '@mui/material/styles';
import { Node, NodeProps } from '@xyflow/react';
import { memo, useMemo } from 'react';
import { Label } from '../Label';
import { useConnectionLineNodeStyle } from '../connector/useConnectionLineNodeStyle';
import { useConnectorNodeStyle } from '../connector/useConnectorNodeStyle';
import { useDrop } from '../drop/useDrop';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { ConnectionHandles } from '../handles/ConnectionHandles';
import { ConnectionTargetHandle } from '../handles/ConnectionTargetHandle';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { IconLabelNodeData } from './IconLabelNode.types';
import { NodeComponentsMap } from './NodeTypes';

const iconLabelStyle = (
  style: React.CSSProperties,
  theme: Theme,
  selected: boolean,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const iconLabelNodeStyle: React.CSSProperties = {
    opacity: faded ? '0.4' : '',
    ...style,
  };

  if (selected || hovered) {
    iconLabelNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return iconLabelNodeStyle;
};

export const IconLabelNode: NodeComponentsMap['iconLabelNode'] = memo(
  ({ data, id, selected, dragging }: NodeProps<Node<IconLabelNodeData>>) => {
    const theme = useTheme();
    const { onDrop, onDragOver } = useDrop();
    const { style: connectionFeedbackStyle } = useConnectorNodeStyle(id, data.nodeDescription.id);
    const { style: dropFeedbackStyle } = useDropNodeStyle(
      data.isDropNodeTarget,
      data.isDragNodeSource,
      data.isDropNodeCandidate,
      dragging
    );
    const { style: connectionLineActiveNodeStyle } = useConnectionLineNodeStyle(data.connectionLinePositionOnNode);
    const nodeStyle = useMemo(
      () => iconLabelStyle(data.style, theme, !!selected, data.isHovered, data.faded),
      [data.style, selected, data.isHovered, data.faded]
    );

    useRefreshConnectionHandles(id, data.connectionHandles);

    const handleOnDrop = (event: React.DragEvent) => {
      onDrop(event, id);
    };

    return (
      <div
        style={{
          ...nodeStyle,
          ...connectionFeedbackStyle,
          ...dropFeedbackStyle,
          ...connectionLineActiveNodeStyle,
        }}
        data-svg="rect"
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`IconLabel - ${data?.insideLabel?.text}`}>
        {data.insideLabel ? <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} /> : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} nodeDescription={data.nodeDescription} isHovered={data.isHovered} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
      </div>
    );
  }
);
