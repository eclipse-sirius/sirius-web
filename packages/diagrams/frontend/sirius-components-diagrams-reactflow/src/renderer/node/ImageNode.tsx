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
import { NodeProps, NodeResizer } from 'reactflow';
import { BorderNodePositon } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { ConnectionHandles } from '../handles/ConnectionHandles';
import { ConnectionTargetHandle } from '../handles/ConnectionTargetHandle';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { ImageNodeData } from './ImageNode.types';
import { NodeContext } from './NodeContext';
import { NodeContextValue } from './NodeContext.types';

const imageNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean,
  rotation: string | undefined
): React.CSSProperties => {
  const imageNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    transform: rotation,
    ...style,
  };
  if (selected || hovered) {
    imageNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return imageNodeStyle;
};

const resizeHandleStyle = (theme: Theme): React.CSSProperties => {
  return {
    width: theme.spacing(0.75),
    height: theme.spacing(0.75),
  };
};

const computeBorderRotation = (data: ImageNodeData): string | undefined => {
  if (data?.isBorderNode && data.positionDependentRotation) {
    switch (data.borderNodePosition) {
      case BorderNodePositon.NORTH:
        return 'rotate(90deg)';
      case BorderNodePositon.EAST:
        return 'rotate(180deg)';
      case BorderNodePositon.SOUTH:
        return 'rotate(270deg)';
      default:
        return undefined;
    }
  }
  return undefined;
};

export const ImageNode = memo(({ data, id, selected }: NodeProps<ImageNodeData>) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const theme = useTheme();
  const { style: dropFeedbackStyle } = useDropNodeStyle(id);
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);
  const { newConnectionStyleProvider } = useConnector();
  const rotation = computeBorderRotation(data);

  useRefreshConnectionHandles(id, data.connectionHandles);
  return (
    <>
      <NodeResizer
        handleStyle={{ ...resizeHandleStyle(theme) }}
        color={theme.palette.selected}
        isVisible={selected && !data.isBorderNode}
        shouldResize={() => !data.isBorderNode}
        keepAspectRatio={data.nodeDescription?.keepAspectRatio}
      />
      <img
        src={httpOrigin + data.imageURL}
        style={{
          ...imageNodeStyle(theme, data.style, selected, hoveredNode?.id === id, data.faded, rotation),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
          ...dropFeedbackStyle,
        }}
        data-testid={`Image - ${data?.targetObjectLabel}`}
      />
      {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
      {selected ? <DiagramElementPalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
      {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
      <ConnectionTargetHandle nodeId={id} />
      <ConnectionHandles connectionHandles={data.connectionHandles} />
    </>
  );
});
