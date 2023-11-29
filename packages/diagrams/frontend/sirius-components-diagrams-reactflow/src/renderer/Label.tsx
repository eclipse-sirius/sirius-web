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

import { getCSSColor, IconOverlay } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import { memo } from 'react';
import { DiagramDirectEditInput } from './direct-edit/DiagramDirectEditInput';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { LabelProps } from './Label.types';

const labelStyle = (
  theme: Theme,
  style: React.CSSProperties,
  faded: Boolean,
  transform: string,
  hasIcon: boolean
): React.CSSProperties => {
  const labelStyle: React.CSSProperties = {
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

  if (hasIcon) {
    labelStyle.gap = '8px';
  }

  return labelStyle;
};

export const Label = memo(({ diagramElementId, label, faded, transform }: LabelProps) => {
  const theme: Theme = useTheme();
  const { currentlyEditedLabelId, editingKey, resetDirectEdit } = useDiagramDirectEdit();

  const handleClose = () => {
    resetDirectEdit();
    const diagramElement = document.querySelector(`[data-id="${diagramElementId}"]`);
    if (diagramElement instanceof HTMLElement) {
      diagramElement.focus();
    }
  };

  const content: JSX.Element =
    label.id === currentlyEditedLabelId ? (
      <DiagramDirectEditInput editingKey={editingKey} onClose={handleClose} labelId={label.id} transform={transform} />
    ) : (
      <>
        <IconOverlay iconURL={label.iconURL} alt={label.text} />
        {label.text}
      </>
    );
  return (
    <div
      data-id={label.id}
      data-testid={`Label - ${label.text}`}
      style={labelStyle(theme, label.style, faded, transform, !!label.iconURL)}
      className="nopan">
      {content}
    </div>
  );
});
