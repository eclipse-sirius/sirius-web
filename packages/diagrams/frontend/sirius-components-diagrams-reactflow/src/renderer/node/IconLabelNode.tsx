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
import { memo } from 'react';
import { NodeProps } from 'reactflow';
import { Label } from '../Label';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { IconLabelNodeData } from './IconsLabelNode.types';

const iconlabelStyle = (
  style: React.CSSProperties,
  theme: Theme,
  selected: boolean,
  faded: boolean
): React.CSSProperties => {
  const iconLabelNodeStyle: React.CSSProperties = {
    opacity: faded ? '0.4' : '',
    ...style,
  };

  if (selected) {
    iconLabelNodeStyle.outline = `${theme.palette.primary.main} solid 1px`;
  }

  return iconLabelNodeStyle;
};

export const IconLabelNode = memo(({ data, id, selected }: NodeProps<IconLabelNodeData>) => {
  const theme = useTheme();
  return (
    <div style={{ paddingLeft: '8px', paddingRight: '8px' }}>
      <div
        style={iconlabelStyle(data.style, theme, selected, data.faded)}
        data-testid={`IconLabel - ${data?.label?.text}`}>
        {data.label ? <Label diagramElementId={id} label={data.label} faded={data.faded} transform="" /> : null}
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={data?.label?.id ?? null} /> : null}
      </div>
    </div>
  );
});
