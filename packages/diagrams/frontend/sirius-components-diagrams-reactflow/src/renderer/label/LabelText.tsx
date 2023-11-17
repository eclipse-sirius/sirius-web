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
import React from 'react';
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { LabelContext } from './LabelContext';
import { Label } from '../DiagramRenderer.types';

const labelStyle = (theme: Theme, style: React.CSSProperties): React.CSSProperties => {
  return {
    pointerEvents: 'all',
    ...style,
    color: style.color ? getCSSColor(String(style.color), theme) : undefined,
  };
};

export const LabelText = () => {
  const theme: Theme = useTheme();
  const label = React.useContext<Label | undefined>(LabelContext);
  if (!label) {
    return <div />;
  }
  return <div style={labelStyle(theme, label.style)}>{label.text}</div>;
};
