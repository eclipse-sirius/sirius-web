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

import { getCSSColor, ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { makeStyles, Theme, useTheme } from '@material-ui/core/styles';
import { memo, useContext } from 'react';
import { DiagramDirectEditInput } from './direct-edit/DiagramDirectEditInput';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { LabelProps } from './Label.types';

const useStyle = makeStyles(() => ({
  iconContainer: {
    position: 'relative',
    width: '16px',
    height: '16px',
  },
  icon: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
}));

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
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { currentlyEditedLabelId, editingKey, resetDirectEdit } = useDiagramDirectEdit();
  const classes = useStyle();

  const handleClose = () => {
    resetDirectEdit();
    const diagramElement = document.querySelector(`[data-id="${diagramElementId}"]`);
    if (diagramElement instanceof HTMLElement) {
      diagramElement.focus();
    }
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
      style={labelStyle(theme, label.style, faded, transform, !!label.iconURL)}
      className="nopan">
      {label.iconURL.length > 0 ? (
        <div className={classes.iconContainer}>
          {label.iconURL.map((icon, index) => (
            <img
              height="16"
              width="16"
              key={index}
              alt={label.text}
              src={httpOrigin + icon}
              className={classes.icon}
              style={{ zIndex: index }}
            />
          ))}
        </div>
      ) : (
        ''
      )}
      {label.text}
    </div>
  );
});
