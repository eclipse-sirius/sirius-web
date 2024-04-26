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

import { getCSSColor, IconOverlay } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import { memo, useContext } from 'react';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramContextValue } from '../contexts/DiagramContext.types';
import { DiagramDirectEditInput } from './direct-edit/DiagramDirectEditInput';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { LabelProps } from './Label.types';
import { EdgeLabel, InsideLabel, OutsideLabel } from './DiagramRenderer.types';

const isInsideLabel = (label: EdgeLabel | InsideLabel | OutsideLabel): label is InsideLabel =>
  (label as InsideLabel).overflowStrategy !== undefined;

const labelStyle = (
  theme: Theme,
  style: React.CSSProperties,
  faded: Boolean,
  transform: string
): React.CSSProperties => {
  return {
    transform,
    opacity: faded ? '0.4' : '',
    pointerEvents: 'all',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
    whiteSpace: 'pre-line',
    ...style,
    color: style.color ? getCSSColor(String(style.color), theme) : undefined,
  };
};

const labelContentStyle = (label: EdgeLabel | InsideLabel | OutsideLabel): React.CSSProperties => {
  const labelContentStyle: React.CSSProperties = {
    display: 'flex',
    alignItems: 'center',
  };
  if (isInsideLabel(label)) {
    labelContentStyle.overflow = 'hidden';
  }
  return labelContentStyle;
};

const labelOverflowStyle = (label: EdgeLabel | InsideLabel | OutsideLabel): React.CSSProperties => {
  const style: React.CSSProperties = {};
  if (isInsideLabel(label)) {
    switch (label.overflowStrategy) {
      case 'WRAP':
        style.overflow = 'hidden';
        style.overflowWrap = 'break-word';
        break;
      case 'ELLIPSIS':
        style.whiteSpace = 'nowrap';
        style.overflow = 'hidden';
        style.textOverflow = 'ellipsis';
        break;
      case 'NONE':
      default:
        break;
    }
  }
  return style;
};

export const Label = memo(({ diagramElementId, label, faded, transform }: LabelProps) => {
  const theme: Theme = useTheme();
  const { currentlyEditedLabelId, editingKey, resetDirectEdit } = useDiagramDirectEdit();
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const handleClose = () => {
    resetDirectEdit();
    const diagramElement = document.querySelector(`[data-id="${diagramElementId}"]`);
    if (diagramElement instanceof HTMLElement) {
      diagramElement.focus();
    }
  };

  const content: JSX.Element =
    label.id === currentlyEditedLabelId && !readOnly ? (
      <DiagramDirectEditInput editingKey={editingKey} onClose={handleClose} labelId={label.id} />
    ) : (
      <div data-id={`${label.id}-content`} style={labelContentStyle(label)}>
        <IconOverlay iconURL={label.iconURL} alt={label.text} customIconStyle={{ marginRight: theme.spacing(1) }} />
        <div style={labelOverflowStyle(label)}>{label.text}</div>
      </div>
    );
  return (
    <div
      data-id={label.id}
      data-testid={`Label - ${label.text}`}
      style={labelStyle(theme, label.style, faded, transform)}
      className="nopan">
      {content}
    </div>
  );
});
