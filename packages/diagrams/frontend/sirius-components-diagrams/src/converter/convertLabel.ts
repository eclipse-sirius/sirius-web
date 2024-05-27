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
import { convertLineStyle } from './convertDiagram';

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
    contentStyle: {
      ...convertContentStyle(labelStyle),
    },
    iconURL: labelStyle.iconURL,
    overflowStrategy: gqlInsideLabel.overflowStrategy,
    headerSeparatorStyle: {
      width: '100%',
    },
    headerPosition: undefined,
  };

  const alignement = AlignmentMap[gqlInsideLabel.insideLabelLocation];
  if (alignement && alignement.isPrimaryVerticalAlignment) {
    if (alignement.primaryAlignment === 'TOP') {
      if (insideLabel.isHeader) {
        insideLabel.headerPosition = 'TOP';
      }
      if (insideLabel.displayHeaderSeparator && hasVisibleChild) {
        insideLabel.headerSeparatorStyle.borderBottom = borderStyle;
      }
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-start' };
    }
    if (alignement.primaryAlignment === 'BOTTOM') {
      if (insideLabel.isHeader) {
        insideLabel.headerPosition = 'BOTTOM';
      }
      if (insideLabel.displayHeaderSeparator && hasVisibleChild) {
        insideLabel.headerSeparatorStyle.borderTop = borderStyle;
      }
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-end' };
    }
    if (alignement.primaryAlignment === 'MIDDLE') {
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'center' };
    }
    data.style = { ...data.style, alignItems: 'stretch' };
    if (alignement.secondaryAlignment === 'LEFT') {
      insideLabel.style = { ...insideLabel.style, marginRight: 'auto' };
    }
    if (alignement.secondaryAlignment === 'RIGHT') {
      insideLabel.style = { ...insideLabel.style, marginLeft: 'auto' };
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
      contentStyle: {
        ...convertContentStyle(labelStyle),
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

export const convertContentStyle = (gqlLabelStyle: GQLLabelStyle): React.CSSProperties => {
  const style: React.CSSProperties = {};

  style.background = gqlLabelStyle.background;
  style.borderColor = gqlLabelStyle.borderColor;
  style.borderRadius = gqlLabelStyle.borderRadius;
  style.borderWidth = gqlLabelStyle.borderSize;
  style.borderStyle = convertLineStyle(gqlLabelStyle.borderStyle);

  return style;
};
