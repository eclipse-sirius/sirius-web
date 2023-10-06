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
import { Label } from '../Label';
import { useConnector } from '../connector/useConnector';
import { useDropNode } from '../dropNode/useDropNode';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { ImageNodeData } from './ImageNode.types';
import { ConnectionHandles } from './handles/ConnectionHandles';
import { ConnectionSourceHandles } from './handles/NewConnectionSourceHandles';
import { ConnectionTargetHandles } from './handles/NewConnectionTargetHandle';
import { useConnectionHandles } from './handles/useConnectionHandles';

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

export const ImageNode = memo(({ data, id, selected }: NodeProps<ImageNodeData>) => {
  const theme = useTheme();
  const { dropFeedbackStyleProvider } = useDropNode();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { newConnectionStyleProvider } = useConnector();

  useConnectionHandles(id, data.connectionHandles);

  return (
    <>
      <NodeResizer color={theme.palette.primary.main} isVisible={selected} shouldResize={() => !data.isBorderNode} />
      <img
        src={httpOrigin + data.imageURL}
        style={{
          ...imageNodeStyle(theme, data.style, selected, data.faded),
          ...dropFeedbackStyleProvider.getNodeStyle(id),
          ...newConnectionStyleProvider.getNodeStyle(id, data.descriptionId),
        }}
        data-testid={`Image - ${data?.targetObjectLabel}`}
      />
      {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
      {selected ? <DiagramElementPalette diagramElementId={id} labelId={data.label ? data.label.id : null} /> : null}
      {selected ? <ConnectionSourceHandles nodeId={id} /> : null}
      <ConnectionTargetHandles nodeId={id} />
      <ConnectionHandles connectionHandles={data.connectionHandles} />
    </>
  );
});
