/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { Theme, useTheme } from '@mui/material/styles';
import { useViewport } from '@xyflow/react';
import { memo, useContext, useMemo, useRef } from 'react';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramContextValue } from '../contexts/DiagramContext.types';
import { EdgeLabel, InsideLabel, LabelOverflowStrategy, OutsideLabel } from './DiagramRenderer.types';
import { DiagramDirectEditInput } from './direct-edit/DiagramDirectEditInput';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { LabelProps } from './Label.types';

const getOverflowStrategy = (label: EdgeLabel | InsideLabel | OutsideLabel): LabelOverflowStrategy | undefined => {
  if ('overflowStrategy' in label) {
    return label.overflowStrategy;
  }
  return undefined;
};

const isDisplayTopHeaderSeparator = (label: EdgeLabel | InsideLabel | OutsideLabel): boolean => {
  if ('displayHeaderSeparator' in label) {
    return label.displayHeaderSeparator && label.headerPosition === 'BOTTOM';
  }
  return false;
};

const isDisplayBottomHeaderSeparator = (label: EdgeLabel | InsideLabel | OutsideLabel): boolean => {
  if ('displayHeaderSeparator' in label) {
    return label.displayHeaderSeparator && label.headerPosition === 'TOP';
  }
  return false;
};

const getHeaderSeparatorStyle = (label: EdgeLabel | InsideLabel | OutsideLabel): React.CSSProperties | undefined => {
  if ('headerSeparatorStyle' in label) {
    return label.headerSeparatorStyle;
  }
  return undefined;
};

const labelStyle = (theme: Theme, style: React.CSSProperties, faded: Boolean): React.CSSProperties => {
  return {
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
};

const labelContentStyle = (
  theme: Theme,
  label: EdgeLabel | InsideLabel | OutsideLabel,
  highlighted: boolean
): React.CSSProperties => {
  const labelContentStyle: React.CSSProperties = {
    display: 'flex',
    alignItems: 'center',
  };
  switch (getOverflowStrategy(label)) {
    case 'WRAP':
    case 'ELLIPSIS':
      labelContentStyle.overflow = 'hidden';
      break;
    case 'NONE':
      labelContentStyle.whiteSpace = 'pre';
      break;
    default:
      break;
  }
  const style = {
    ...labelContentStyle,
    ...label.contentStyle,
    background: label.contentStyle.background ? getCSSColor(String(label.contentStyle.background), theme) : undefined,
    borderColor: label.contentStyle.borderColor
      ? getCSSColor(String(label.contentStyle.borderColor), theme)
      : undefined,
  };
  if (highlighted) {
    style.borderWidth = `1px`;
    style.borderColor = theme.palette.selected;
    style.borderStyle = 'dashed';
  }
  return style;
};

const labelOverflowStyle = (label: EdgeLabel | InsideLabel | OutsideLabel): React.CSSProperties => {
  const style: React.CSSProperties = {};
  switch (getOverflowStrategy(label)) {
    case 'WRAP':
      style.overflow = 'hidden';
      style.overflowWrap = 'break-word';
      break;
    case 'ELLIPSIS':
      style.whiteSpace = 'nowrap';
      style.overflow = 'hidden';
      style.textOverflow = 'ellipsis';
      break;
    case 'NONE':
    default:
      break;
  }
  return style;
};

const minWidth: number = 150;

const directEditInputWidth = (element: HTMLDivElement | null): number => {
  let result = minWidth;
  if (element) {
    result = element.getBoundingClientRect().width;
  }
  return Math.max(minWidth, result);
};

export const Label = memo(({ diagramElementId, label, faded, highlighted }: LabelProps) => {
  const theme: Theme = useTheme();
  const { currentlyEditedLabelId, editingKey, resetDirectEdit } = useDiagramDirectEdit();
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const labelElementRef = useRef<HTMLDivElement>(null);
  const { zoom } = useViewport();

  const handleClose = () => {
    resetDirectEdit();
    const diagramElement = document.querySelector(`[data-id="${diagramElementId}"]`);
    if (diagramElement instanceof HTMLElement) {
      diagramElement.focus();
    }
  };

  const customIconStyle = useMemo(() => {
    return { marginRight: theme.spacing(1) };
  }, []);

  const content: JSX.Element =
    label.id === currentlyEditedLabelId && !readOnly ? (
      <DiagramDirectEditInput
        editingKey={editingKey}
        width={Math.max(minWidth, directEditInputWidth(labelElementRef.current) / zoom)}
        onClose={handleClose}
        labelId={label.id}
      />
    ) : (
      <div
        ref={labelElementRef}
        data-id={`${label.id}-content`}
        data-testid={`Label content - ${label.text}`}
        style={labelContentStyle(theme, label, !!highlighted)}>
        <IconOverlay iconURL={label.iconURL} alt={label.text} customIconStyle={customIconStyle} />
        <div style={labelOverflowStyle(label)} data-svg="text">
          {label.text}
        </div>
      </div>
    );
  return (
    <>
      {isDisplayTopHeaderSeparator(label) && (
        <div
          data-svg="rect"
          data-testid={`Label top separator - ${label.text}`}
          style={getHeaderSeparatorStyle(label)}
        />
      )}
      <div
        data-id={label.id}
        data-testid={`Label - ${label.text}`}
        style={labelStyle(theme, label.style, faded)}
        className="nopan">
        {content}
      </div>
      {isDisplayBottomHeaderSeparator(label) && (
        <div
          data-svg="rect"
          data-testid={`Label bottom separator - ${label.text}`}
          style={getHeaderSeparatorStyle(label)}
        />
      )}
    </>
  );
});
