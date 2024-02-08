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
import { GQLLabelStyle, GQLOutsideLabel, GQLInsideLabel } from '../graphql/subscription/labelFragment.types';
import { OutsideLabel, OutsideLabels, InsideLabel, NodeData } from '../renderer/DiagramRenderer.types';
import { AlignmentMap } from './convertDiagram.types';

export const convertInsideLabel = (
  gqlInsideLabel: GQLInsideLabel | undefined,
  data: NodeData,
  borderStyle: string
): InsideLabel | null => {
  if (!gqlInsideLabel) {
    return null;
  }
  const labelStyle = gqlInsideLabel.style;
  const insideLabel: InsideLabel = {
    id: gqlInsideLabel.id,
    text: gqlInsideLabel.text,
    isHeader: gqlInsideLabel.isHeader,
    displayHeaderSeparator: gqlInsideLabel.displayHeaderSeparator,
    style: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '8px 16px',
      textAlign: 'center',
      ...convertLabelStyle(labelStyle),
    },
    iconURL: labelStyle.iconURL,
  };

  const alignement = AlignmentMap[gqlInsideLabel.insideLabelLocation];
  if (alignement.isPrimaryVerticalAlignment) {
    if (alignement.primaryAlignment === 'TOP') {
      if (insideLabel.displayHeaderSeparator) {
        insideLabel.style.borderBottom = borderStyle;
      }
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-start' };
    }
    if (alignement.secondaryAlignment === 'CENTER') {
      data.style = { ...data.style, alignItems: 'stretch' };
      insideLabel.style = { ...insideLabel.style, justifyContent: 'center' };
    }
  }
  return insideLabel;
};

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
        justifyContent: 'center',
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
