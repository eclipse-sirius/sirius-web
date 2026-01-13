/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { Theme, useTheme } from '@mui/material/styles';
import { NodeResizeControl, NodeResizer } from '@xyflow/react';
import { ResizeControlVariant } from '@xyflow/system';
import { memo, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { ResizerProps } from './Resizer.types';

const resizeLineStyle = (theme: Theme): React.CSSProperties => {
  return { borderWidth: theme.spacing(0.15), borderColor: theme.palette.selected, zIndex: 1 };
};

const resizeControlLineStyle = (theme: Theme): React.CSSProperties => {
  return { borderColor: 'transparent', borderWidth: theme.spacing(0.25), zIndex: 2 };
};

const resizeHandleStyle = (theme: Theme, isLastNodeSelected: boolean, isResizable: boolean): React.CSSProperties => {
  const style: React.CSSProperties = {
    width: theme.spacing(1),
    height: theme.spacing(1),
    borderRadius: '100%',
    zIndex: 3,
    backgroundColor: isLastNodeSelected ? theme.palette.selected : '#FFFFFF',
    border: '1px solid black',
  };
  return isResizable ? style : { ...style, pointerEvents: 'none' };
};

export const Resizer = memo(({ data, selected }: ResizerProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const theme = useTheme();
  if (readOnly || data.isBorderNode) {
    return null;
  }

  let nodeResizeControl: JSX.Element | null = null;
  if (data.isListChild) {
    nodeResizeControl = (
      <NodeResizeControl
        variant={ResizeControlVariant.Line}
        position={'bottom'}
        style={{ ...resizeControlLineStyle(theme) }}
        minHeight={data.minComputedHeight ?? undefined}
      />
    );
  } else if (data.nodeDescription?.userResizable === 'BOTH') {
    nodeResizeControl = (
      <NodeResizer
        handleStyle={{ ...resizeHandleStyle(theme, data.isLastNodeSelected, true) }}
        lineStyle={{ ...resizeLineStyle(theme) }}
        isVisible={selected}
        keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        minWidth={data.minComputedWidth ?? undefined}
        minHeight={data.minComputedHeight ?? undefined}
      />
    );
  } else if (data.nodeDescription?.userResizable === 'HORIZONTAL' && selected) {
    nodeResizeControl = (
      <>
        <NodeResizeControl
          variant={ResizeControlVariant.Line}
          position={'left'}
          style={{ ...resizeControlLineStyle(theme) }}
          keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        />
        <NodeResizeControl
          variant={ResizeControlVariant.Line}
          position={'right'}
          style={{ ...resizeControlLineStyle(theme) }}
          keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        />
        <NodeResizer
          handleStyle={{ ...resizeHandleStyle(theme, data.isLastNodeSelected, false) }}
          lineStyle={{ visibility: 'hidden' }}
          isVisible={selected}
          shouldResize={() => false}
        />
      </>
    );
  } else if (data.nodeDescription?.userResizable === 'VERTICAL' && selected) {
    nodeResizeControl = (
      <>
        <NodeResizeControl
          variant={ResizeControlVariant.Line}
          position={'top'}
          style={{ ...resizeControlLineStyle(theme) }}
          keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        />
        <NodeResizeControl
          variant={ResizeControlVariant.Line}
          position={'bottom'}
          style={{ ...resizeControlLineStyle(theme) }}
          keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        />
        <NodeResizer
          handleStyle={{ ...resizeHandleStyle(theme, data.isLastNodeSelected, false) }}
          lineStyle={{ visibility: 'hidden' }}
          isVisible={selected}
          shouldResize={() => false}
        />
      </>
    );
  } else if (data.nodeDescription?.userResizable === 'NONE' && selected) {
    nodeResizeControl = (
      <NodeResizer
        handleStyle={{ ...resizeHandleStyle(theme, data.isLastNodeSelected, false) }}
        lineStyle={{ visibility: 'hidden' }}
        isVisible={selected}
        shouldResize={() => false}
      />
    );
  }

  return nodeResizeControl;
});
