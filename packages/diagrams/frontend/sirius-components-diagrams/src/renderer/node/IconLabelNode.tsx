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

import { Theme, useTheme } from '@material-ui/core/styles';
import { memo, useMemo } from 'react';
import { NodeProps } from 'reactflow';
import { Label } from '../Label';
import { useDrop } from '../drop/useDrop';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { IconLabelNodeData } from './IconsLabelNode.types';

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

export const IconLabelNode = memo(({ data, id, selected, dragging }: NodeProps<IconLabelNodeData>) => {
  const theme = useTheme();
  const { onDrop, onDragOver } = useDrop();
  const { style: dropFeedbackStyle } = useDropNodeStyle(data.isDropNodeTarget, data.isDropNodeCandidate, dragging);
  const nodeStyle = useMemo(
    () => iconlabelStyle(data.style, theme, selected, data.isHovered, data.faded),
    [data.style, selected, data.isHovered, data.faded]
  );

  const handleOnDrop = (event: React.DragEvent) => {
    onDrop(event, id);
  };

  return (
    <div
      style={{
        ...nodeStyle,
        ...dropFeedbackStyle,
      }}
      onDragOver={onDragOver}
      onDrop={handleOnDrop}
      data-testid={`IconLabel - ${data?.insideLabel?.text}`}>
      {data.insideLabel ? <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} /> : null}
      {selected ? <DiagramElementPalette diagramElementId={id} labelId={data?.insideLabel?.id ?? null} /> : null}
    </div>
  );
});
