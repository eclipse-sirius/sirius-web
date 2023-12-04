/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { GQLLabelStyle, GQLOutsideLabel } from '../graphql/subscription/labelFragment.types';
import { OutsideLabel, OutsideLabels } from '../renderer/DiagramRenderer.types';

export const convertOutsideLabels = (gqlOutsideLabels: GQLOutsideLabel[]): OutsideLabels => {
  const outsideLabels: OutsideLabels = {};
  const reducer = (allOutsideLabels: OutsideLabels, gqlOutsideLabel: GQLOutsideLabel): OutsideLabels => {
    const {
      id,
      text,
      style: labelStyle,
      style: { iconURL },
    } = gqlOutsideLabel;
    const outsideLabel: OutsideLabel = {
      id,
      text,
      iconURL,
      style: {
        ...convertLabelStyle(labelStyle),
      },
    };

    allOutsideLabels[gqlOutsideLabel.outsideLabelLocation] = outsideLabel;

    return allOutsideLabels;
  };

  return gqlOutsideLabels.reduce<OutsideLabels>(reducer, outsideLabels);
};

export const convertLabelStyle = (gqlLabelStyle: GQLLabelStyle): React.CSSProperties => {
  const style: React.CSSProperties = {};

  if (gqlLabelStyle.bold) {
    style.fontWeight = 'bold';
  }
  if (gqlLabelStyle.italic) {
    style.fontStyle = 'italic';
  }
  if (gqlLabelStyle.fontSize) {
    style.fontSize = gqlLabelStyle.fontSize;
  }
  if (gqlLabelStyle.color) {
    style.color = gqlLabelStyle.color;
  }

  let decoration: string = '';
  if (gqlLabelStyle.strikeThrough) {
    decoration = decoration + 'line-through';
  }
  if (gqlLabelStyle.underline) {
    const separator: string = decoration.length > 0 ? ' ' : '';
    decoration = decoration + separator + 'underline';
  }
  if (decoration.length > 0) {
    style.textDecoration = decoration;
  }

  return style;
};
