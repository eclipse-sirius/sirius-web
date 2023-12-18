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

import { ServerContext, ServerContextValue, getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import React, { memo, useContext } from 'react';
import { NodeProps, NodeResizer } from 'reactflow';
import { BorderNodePosition, NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDrop } from '../drop/useDrop';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { ConnectionHandles } from '../handles/ConnectionHandles';
import { ConnectionTargetHandle } from '../handles/ConnectionTargetHandle';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { FlexDirection } from './DefaultNode.types';
import { NodeContext } from './NodeContext';
import { NodeContextValue } from './NodeContext.types';

const defaultNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean,
  rotation: string | undefined,
  imageURL: string | undefined,
  flexDirection: FlexDirection | undefined,
  justifyContent: string | undefined,
  alignItems: string | undefined
): React.CSSProperties => {
  const freeFormNodeStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection,
    justifyContent,
    alignItems,
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    transform: rotation,
    ...style,
    backgroundColor: getCSSColor(String(style.backgroundColor), theme),
    borderTopColor: getCSSColor(String(style.borderTopColor), theme),
    borderBottomColor: getCSSColor(String(style.borderBottomColor), theme),
    borderLeftColor: getCSSColor(String(style.borderLeftColor), theme),
    borderRightColor: getCSSColor(String(style.borderRightColor), theme),
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

const computeBorderRotation = (data: NodeData): string | undefined => {
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

const outsideBottomLabelAreaStyle = (): React.CSSProperties => {
  return {
    display: 'grid',
    gridTemplateColumns: `1fr 1fr 1fr`,
    gridTemplateRows: `min-content`,
    gridTemplateAreas: `
    'BOTTOM_BEGIN   BOTTOM_MIDDLE   BOTTOM_END'
    `,
  };
};

export const DefaultNode = memo(({ id, data, selected }: NodeProps<NodeData>) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);

  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { newConnectionStyleProvider } = useConnector();
  const { style: dropFeedbackStyle } = useDropNodeStyle(id);

  const rotation = computeBorderRotation(data);
  let imageURL: string | undefined = undefined;
  if (data.imageURL) {
    imageURL = httpOrigin + data.imageURL;
  }

  const handleOnDrop = (event: React.DragEvent) => onDrop(event, id);

  useRefreshConnectionHandles(id, data.connectionHandles);

  const outsideBottomlabels: JSX.Element[] = [];
  if (data.outsideLabels.BOTTOM_BEGIN) {
    const outsideLabel = data.outsideLabels.BOTTOM_BEGIN;
    outsideBottomlabels.push(
      <div style={{ gridArea: 'BOTTOM_BEGIN' }} key={outsideLabel.id}>
        <Label diagramElementId={id} label={outsideLabel} faded={data.faded} transform="" />
      </div>
    );
  }
  if (data.outsideLabels.BOTTOM_MIDDLE) {
    const outsideLabel = data.outsideLabels.BOTTOM_MIDDLE;
    outsideBottomlabels.push(
      <div style={{ gridArea: 'BOTTOM_MIDDLE' }} key={outsideLabel.id}>
        <Label diagramElementId={id} label={outsideLabel} faded={data.faded} transform="" />
      </div>
    );
  }
  if (data.outsideLabels.BOTTOM_END) {
    const outsideLabel = data.outsideLabels.BOTTOM_END;
    outsideBottomlabels.push(
      <div style={{ gridArea: 'BOTTOM_END' }} key={outsideLabel.id}>
        <Label diagramElementId={id} label={outsideLabel} faded={data.faded} transform="" />
      </div>
    );
  }

  let direction: FlexDirection | undefined;
  let nodeJustifyContent: string | undefined;
  let labelJustifyContent: string | undefined;
  let nodeAlignItems: string | undefined;
  if (data.insideLabel && data.insideLabelLocation) {
    if (data.insideLabelLocation.startsWith('TOP')) {
      direction = 'column';
      nodeJustifyContent = 'flex-start';
    }

    if (data.insideLabelLocation.endsWith('CENTER')) {
      nodeAlignItems = 'stretch';
      labelJustifyContent = 'center';
    }
  }

  return (
    <>
      {data.nodeDescription.userResizable && (
        <NodeResizer
          color={theme.palette.primary.main}
          isVisible={selected && !data.isBorderNode}
          shouldResize={() => !data.isBorderNode}
          keepAspectRatio={data.nodeDescription.keepAspectRatio}
        />
      )}
      <div
        style={{
          ...defaultNodeStyle(
            theme,
            data.style,
            selected,
            hoveredNode?.id === id,
            data.faded,
            rotation,
            imageURL,
            direction,
            nodeJustifyContent,
            nodeAlignItems
          ),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
          ...dropFeedbackStyle,
        }}
        onDragOver={onDragOver}
        onDrop={handleOnDrop}
        data-testid={`Default - ${data.targetObjectLabel}`}>
        {data.insideLabel ? (
          <div style={{ justifyContent: labelJustifyContent }}>
            <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} transform="" />
          </div>
        ) : null}
        {selected ? (
          <DiagramElementPalette diagramElementId={id} labelId={data.insideLabel ? data.insideLabel.id : null} />
        ) : null}
        {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
        <ConnectionTargetHandle nodeId={id} />
        <ConnectionHandles connectionHandles={data.connectionHandles} />
      </div>
      <div style={{ ...outsideBottomLabelAreaStyle() }}>{outsideBottomlabels}</div>
    </>
  );
});
