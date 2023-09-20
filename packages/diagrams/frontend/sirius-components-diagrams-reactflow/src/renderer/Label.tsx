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
import { memo, useContext } from 'react';
import { LabelProps } from './Label.types';
import { DiagramDirectEditInput } from './direct-edit/DiagramDirectEditInput';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';

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
    ...style,
    color: style.color ? getCSSColor(String(style.color), theme) : undefined,
  };
};

export const Label = memo(({ diagramElementId, label, faded, transform }: LabelProps) => {
  const theme: Theme = useTheme();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { currentlyEditedLabelId, editingKey, setCurrentlyEditedLabelId, resetDirectEdit } = useDiagramDirectEdit();

  const handleClose = () => {
    resetDirectEdit();
    const diagramElement = document.querySelector(`[data-id="${diagramElementId}"]`);
    if (diagramElement instanceof HTMLElement) {
      diagramElement.focus();
    }
  };

  const handleDoubleClick = () => {
    setCurrentlyEditedLabelId('doubleClick', label.id, null);
  };

  if (label.id === currentlyEditedLabelId) {
    return (
      <DiagramDirectEditInput editingKey={editingKey} onClose={handleClose} labelId={label.id} transform={transform} />
    );
  }
  return (
    <div
      data-id={label.id}
      data-testid={`Label - ${label.text}`}
      onDoubleClick={handleDoubleClick}
      style={labelStyle(theme, label.style, faded, transform)}
      className="nopan">
      {label.iconURL ? <img src={httpOrigin + label.iconURL} /> : ''}
      {label.text}
    </div>
  );
});
