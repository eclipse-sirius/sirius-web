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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import {
  ConnectionCreationHandles,
  ConnectionHandles,
  ConnectionTargetHandle,
  Label,
  Resizer,
  useConnectionLineNodeStyle,
  useConnectorNodeStyle,
  useDrop,
  useDropNodeStyle,
  useRefreshConnectionHandles,
} from '@eclipse-sirius/sirius-components-diagrams';
import { Theme, useTheme } from '@mui/material/styles';
import { Node, NodeProps } from '@xyflow/react';
import React, { memo } from 'react';
import { EllipseNodeData, NodeComponentsMap } from './EllipseNode.types';

const ellipseNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
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
    background: getCSSColor(String(style.background), theme),
  };

  if (selected || hovered) {
    ellipseNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return ellipseNodeStyle;
};

export const EllipseNode: NodeComponentsMap['ellipseNode'] = memo(
  ({ data, id, selected, dragging }: NodeProps<Node<EllipseNodeData>>) => {
    const theme = useTheme();
    const { onDrop, onDragOver } = useDrop();
    const { style: connectionFeedbackStyle } = useConnectorNodeStyle(id, data.nodeDescription.id);
    const { style: dropFeedbackStyle } = useDropNodeStyle(data.isDropNodeTarget, data.isDropNodeCandidate, dragging);
    const { style: connectionLineActiveNodeStlye } = useConnectionLineNodeStyle(data.connectionLinePositionOnNode);

    const handleOnDrop = (event: React.DragEvent) => {
      onDrop(event, id);
    };

    useRefreshConnectionHandles(id, data.connectionHandles);

    return (
      <>
        <Resizer data={data} selected={!!selected} />
        <div
          style={{
            ...ellipseNodeStyle(theme, data.style, !!selected, data.isHovered, data.faded),
            ...connectionFeedbackStyle,
            ...dropFeedbackStyle,
            ...connectionLineActiveNodeStlye,
          }}
          data-svg="rect"
          onDragOver={onDragOver}
          onDrop={handleOnDrop}
          data-testid={`Ellipse - ${data?.insideLabel?.text}`}>
          {data.insideLabel ? <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} /> : null}
          {!!selected ? <ConnectionCreationHandles nodeId={id} /> : null}
          <ConnectionTargetHandle nodeId={id} nodeDescription={data.nodeDescription} isHovered={data.isHovered} />
          <ConnectionHandles connectionHandles={data.connectionHandles} />
        </div>
      </>
    );
  }
);
