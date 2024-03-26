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
import { ReactFlowState, useStore, useViewport } from '@xyflow/react';
import { CSSProperties, useEffect, useRef } from 'react';
import { HelperLinesProps } from './HelperLines.types';

const canvasStyle: CSSProperties = {
  width: '100%',
  height: '100%',
  position: 'absolute',
  zIndex: 10,
  pointerEvents: 'none',
};

const storeSelector = (state: ReactFlowState) => ({
  width: state.width,
  height: state.height,
});

export const HelperLines = ({ horizontal, vertical }: HelperLinesProps) => {
  const { width, height } = useStore(storeSelector);
  const { x, y, zoom } = useViewport();
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const theme: Theme = useTheme();

  useEffect(() => {
    const canvas = canvasRef.current;
    const context2D = canvas?.getContext('2d');

    if (canvas && context2D) {
      const dpi = window.devicePixelRatio;
      canvas.width = width * dpi;
      canvas.height = height * dpi;

      context2D.scale(dpi, dpi);
      context2D.clearRect(0, 0, width, height);
      context2D.strokeStyle = theme.palette.primary.main;
      context2D.setLineDash([4, 2]);

      if (vertical) {
        context2D.moveTo(vertical * zoom + x, 0);
        context2D.lineTo(vertical * zoom + x, height);
        context2D.stroke();
      }

      if (horizontal) {
        context2D.moveTo(0, horizontal * zoom + y);
        context2D.lineTo(width, horizontal * zoom + y);
        context2D.stroke();
      }
    }
  }, [width, height, x, y, zoom, horizontal, vertical]);

  return <canvas ref={canvasRef} className="react-flow__canvas" style={canvasStyle} />;
};
