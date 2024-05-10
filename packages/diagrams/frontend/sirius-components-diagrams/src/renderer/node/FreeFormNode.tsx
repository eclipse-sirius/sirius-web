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

import { ServerContext, ServerContextValue, getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import React, { memo, useContext } from 'react';
import { NodeProps } from 'reactflow';
import { BorderNodePosition } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { useConnectorNodeStyle } from '../connector/useConnectorNodeStyle';
import { useDrop } from '../drop/useDrop';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { ConnectionHandles } from '../handles/ConnectionHandles';
import { ConnectionTargetHandle } from '../handles/ConnectionTargetHandle';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { FreeFormNodeData } from './FreeFormNode.types';
import { Resizer } from './Resizer';

const freeFormNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean,
  rotation: string | undefined,
  imageURL: string | undefined
): React.CSSProperties => {
  const freeFormNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    transform: rotation,
    ...style,
    background: getCSSColor(style.backgroundColor, theme),
  };
  if (selected || hovered) {
    freeFormNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }
  if (imageURL) {
    freeFormNodeStyle.backgroundImage = `url(${imageURL})`;
    freeFormNodeStyle.backgroundRepeat = 'no-repeat';
    freeFormNodeStyle.backgroundSize = '100% 100%';
  }
  return freeFormNodeStyle;
};

const computeBorderRotation = (data: FreeFormNodeData): string | undefined => {
  if (data?.isBorderNode && data.positionDependentRotation) {
    switch (data.borderNodePosition) {
      case BorderNodePosition.NORTH:
        return 'rotate(90deg)';
      case BorderNodePosition.EAST:
        return 'rotate(180deg)';
      case BorderNodePosition.SOUTH:
        return 'rotate(270deg)';
      default:
        return undefined;
    }
  }
  return undefined;
};

export const FreeFormNode = memo(({ data, id, selected, dragging }: NodeProps<FreeFormNodeData>) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { style: connectionFeedbackStyle } = useConnectorNodeStyle(id, data.nodeDescription.id);
  const { style: dropFeedbackStyle } = useDropNodeStyle(data.isDropNodeTarget, data.isDropNodeCandidate, dragging);

  const rotation = computeBorderRotation(data);
  let imageURL: string | undefined = undefined;
  if (data.imageURL) {
    imageURL = httpOrigin + data.imageURL;
  }

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  useRefreshConnectionHandles(id, data.connectionHandles);

  const getLabelId = (data: FreeFormNodeData): string | null => {
    let labelId: string | null = null;
    if (data.insideLabel) {
      labelId = data.insideLabel.id;
    } else if (data.outsideLabels.BOTTOM_MIDDLE) {
      labelId = data.outsideLabels.BOTTOM_MIDDLE.id;
    }
    return labelId;
  };

  return (
    <>
      <Resizer data={data} selected={selected} />
      <div
        style={{
          ...freeFormNodeStyle(theme, data.style, selected, data.isHovered, data.faded, rotation, imageURL),
          ...connectionFeedbackStyle,
          ...dropFeedbackStyle,
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`FreeForm - ${data?.targetObjectLabel}`}>
        {data.insideLabel && <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} transform="" />}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={getLabelId(data)} /> : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} nodeDescription={data.nodeDescription} isHovered={data.isHovered} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
      </div>
      {data.outsideLabels.BOTTOM_MIDDLE && (
        <Label diagramElementId={id} label={data.outsideLabels.BOTTOM_MIDDLE} faded={data.faded} transform="" />
      )}
    </>
  );
});
