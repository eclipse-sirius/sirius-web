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

import { Theme, useTheme } from '@material-ui/core/styles';
import { memo, useContext } from 'react';
import { NodeProps } from 'reactflow';
import { Label } from '../Label';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { IconLabelNodeData } from './IconsLabelNode.types';
import { NodeContext } from './NodeContext';
import { NodeContextValue } from './NodeContext.types';

const iconlabelStyle = (
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

export const IconLabelNode = memo(({ data, id, selected }: NodeProps<IconLabelNodeData>) => {
  const theme = useTheme();
  const { style: dropFeedbackStyle } = useDropNodeStyle(id);
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);
  return (
    <div style={{ paddingLeft: '8px', paddingRight: '8px' }}>
      <div
        style={{
          ...iconlabelStyle(data.style, theme, selected, hoveredNode?.id === id, data.faded),
          ...dropFeedbackStyle,
        }}
        data-testid={`IconLabel - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={data?.label?.id ?? null} /> : null}
      </div>
    </div>
  );
});
