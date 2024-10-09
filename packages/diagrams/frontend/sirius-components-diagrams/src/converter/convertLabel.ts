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
import { GQLLabelLayoutData } from '../graphql/subscription/diagramFragment.types';

export const convertInsideLabel = (
  gqlInsideLabel: GQLInsideLabel | undefined,
  data: NodeData,
  borderStyle: string,
  hasVisibleChild: boolean = true,
  padding: string = '8px 16px'
): InsideLabel | null => {
  if (!gqlInsideLabel) {
    return null;
  }
  const labelStyle = gqlInsideLabel.style;
  const insideLabel: InsideLabel = {
    id: gqlInsideLabel.id,
    text: gqlInsideLabel.text,
    isHeader: gqlInsideLabel.isHeader,
    displayHeaderSeparator: false,
    style: {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'center',
      padding,
      textAlign: convertLabelTextAlign(gqlInsideLabel.textAlign),
      maxWidth: '100%',
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
      if (
        (hasVisibleChild && gqlInsideLabel.headerSeparatorDisplayMode === 'IF_CHILDREN') ||
        gqlInsideLabel.headerSeparatorDisplayMode === 'ALWAYS'
      ) {
        insideLabel.headerSeparatorStyle.borderBottom = borderStyle;
        insideLabel.displayHeaderSeparator = true;
      }
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-start' };
    }
    if (alignement.primaryAlignment === 'BOTTOM') {
      if (insideLabel.isHeader) {
        insideLabel.headerPosition = 'BOTTOM';
      }
      if (
        (hasVisibleChild && gqlInsideLabel.headerSeparatorDisplayMode === 'IF_CHILDREN') ||
        gqlInsideLabel.headerSeparatorDisplayMode === 'ALWAYS'
      ) {
        insideLabel.headerSeparatorStyle.borderTop = borderStyle;
        insideLabel.displayHeaderSeparator = true;
      }
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-end' };
    }
    if (alignement.primaryAlignment === 'MIDDLE') {
      data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'center' };
    }
    data.style = { ...data.style, alignItems: 'stretch' };
    if (alignement.secondaryAlignment === 'LEFT' || alignement.secondaryAlignment === 'CENTER') {
      insideLabel.style = { ...insideLabel.style, marginRight: 'auto' };
    }
    if (alignement.secondaryAlignment === 'RIGHT' || alignement.secondaryAlignment === 'CENTER') {
      insideLabel.style = { ...insideLabel.style, marginLeft: 'auto' };
    }
  }

  if (labelStyle.maxWidth) {
    insideLabel.style = {
      ...insideLabel.style,
      maxWidth: gqlInsideLabel.overflowStrategy === 'NONE' ? undefined : labelStyle.maxWidth,
    };
  }
  return insideLabel;
};

export const convertOutsideLabels = (
  gqlOutsideLabels: GQLOutsideLabel[],
  gqlLabelLayoutData: GQLLabelLayoutData[]
): OutsideLabels => {
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
        maxWidth: gqlOutsideLabel.overflowStrategy === 'NONE' ? undefined : labelStyle.maxWidth,
        ...convertLabelStyle(labelStyle),
      },
      contentStyle: {
        ...convertContentStyle(labelStyle),
      },
      overflowStrategy,
      position: gqlLabelLayoutData.find((labelLayoutData) => labelLayoutData.id === id)?.position ?? { x: 0, y: 0 },
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
