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
import { Label } from './Label';
import React from 'react';
import { DiagramLabelProps } from './DiagramLabel.types';
import { LabelIcon } from './LabelIcon';
import { LabelText } from './LabelText';

const labelStyle = (
  faded: Boolean,
  hasIcon: boolean,
  borderStyle: React.CSSProperties | undefined
): React.CSSProperties => {
  const labelStyle: React.CSSProperties = {
    opacity: faded ? '0.4' : '',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: borderStyle?.justifyContent ?? 'flex-start',
    whiteSpace: 'pre-line',
    padding: '8px 16px',
    textAlign: 'center',
    borderBottom: borderStyle?.borderBottom,
  };

  if (hasIcon) {
    labelStyle.gap = '8px';
  }

  return labelStyle;
};

export const DiagramLabel = ({ diagramElementId, label, faded }: DiagramLabelProps) => {
  return (
    <Label diagramElementId={diagramElementId} label={label}>
      <div style={labelStyle(faded, !!label.iconURL, label.borderStyle)}>
        <LabelIcon />
        <LabelText />
      </div>
    </Label>
  );
};
