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
import { Theme, useTheme } from '@material-ui/core/styles';
import { memo, useContext } from 'react';
import { NodeResizer, NodeResizeControl, ResizeControlVariant } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { ResizerProps } from './Resizer.types';

const resizeLineStyle = (theme: Theme): React.CSSProperties => {
  return { borderWidth: theme.spacing(0.15) };
};

const resizeControlLineStyle = (theme: Theme): React.CSSProperties => {
  return { borderColor: 'transparent', borderWidth: theme.spacing(0.25) };
};

const resizeHandleStyle = (theme: Theme): React.CSSProperties => {
  return {
    width: theme.spacing(1),
    height: theme.spacing(1),
    borderRadius: '100%',
  };
};

export const Resizer = memo(({ data, selected }: ResizerProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const theme = useTheme();

  if (data.nodeDescription?.userResizable && !readOnly) {
    if (data.isListChild) {
      return (
        <NodeResizeControl
          variant={ResizeControlVariant.Line}
          position={'bottom'}
          shouldResize={() => !data.isBorderNode}
          style={{ ...resizeControlLineStyle(theme) }}
        />
      );
    } else {
      return (
        <NodeResizer
          handleStyle={{ ...resizeHandleStyle(theme) }}
          lineStyle={{ ...resizeLineStyle(theme) }}
          color={theme.palette.selected}
          isVisible={selected}
          shouldResize={() => !data.isBorderNode}
          keepAspectRatio={data.nodeDescription?.keepAspectRatio}
        />
      );
    }
  } else {
    return null;
  }
});
