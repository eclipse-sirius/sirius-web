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
import {
  GQLLabelStyle,
  GQLOutsideLabel,
  GQLInsideLabel,
  GQLLabelTextAlign,
} from '../graphql/subscription/labelFragment.types';
import { OutsideLabels, InsideLabel, NodeData } from '../renderer/DiagramRenderer.types';
import { AlignmentMap } from './convertDiagram.types';

export const convertInsideLabel = (
  gqlInsideLabel: GQLInsideLabel | undefined,
  data: NodeData,
  borderStyle: string,
  hasVisibleChild: boolean = true
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
      textAlign: convertLabelTextAlign(gqlInsideLabel.textAlign),
      ...convertLabelStyle(labelStyle),
    },
    iconURL: labelStyle.iconURL,
    overflowStrategy: gqlInsideLabel.overflowStrategy,
  };

  const alignement = AlignmentMap[gqlInsideLabel.insideLabelLocation];
  if (alignement && alignement.isPrimaryVerticalAlignment) {
    if (alignement.primaryAlignment === 'TOP') {
      if (insideLabel.displayHeaderSeparator && hasVisibleChild) {
        insideLabel.style.borderBottom = borderStyle;
      }
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-start' };
    }
    if (alignement.primaryAlignment === 'BOTTOM') {
      if (insideLabel.displayHeaderSeparator && hasVisibleChild) {
        insideLabel.style.borderTop = borderStyle;
      }
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-end' };
    }
    if (alignement.primaryAlignment === 'MIDDLE') {
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'center' };
    }
    if (alignement.secondaryAlignment === 'CENTER') {
      data.style = { ...data.style, alignItems: 'center' };
    }
    if (alignement.secondaryAlignment === 'LEFT') {
      data.style = { ...data.style, alignItems: 'flex-start' };
    }
    if (alignement.secondaryAlignment === 'RIGHT') {
      data.style = { ...data.style, alignItems: 'flex-end' };
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
      overflowStrategy,
    } = gqlOutsideLabel;
    allOutsideLabels[gqlOutsideLabel.outsideLabelLocation] = {
      id,
      text,
      iconURL,
      style: {
        justifyContent: 'center',
        textAlign: convertLabelTextAlign(gqlOutsideLabel.textAlign),
        ...convertLabelStyle(labelStyle),
      },
      overflowStrategy,
    };

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

const convertLabelTextAlign = (textAlign: GQLLabelTextAlign): 'left' | 'right' | 'center' | 'justify' => {
  switch (textAlign) {
    case 'JUSTIFY':
      return 'justify';
    case 'LEFT':
      return 'left';
    case 'RIGHT':
      return 'right';
    case 'CENTER':
    default:
      return 'center';
  }
};
