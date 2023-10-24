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

import { makeStyles } from '@material-ui/core/styles';
import { useCallback } from 'react';
import { useViewport } from 'reactflow';
import { useLayout } from '../layout/useLayout';
import { DiagramPaletteProps } from './DiagramPalette.types';
import { DiagramPalettePortal } from './DiagramPalettePortal';
import { Palette } from './Palette';
import { useDiagramPalette } from './useDiagramPalette';

const useDiagramPaletteStyle = makeStyles((theme) => ({
  toolbar: {
    background: theme.palette.background.paper,
    border: '1px solid #d1dadb',
    boxShadow: '0px 2px 5px #002b3c40',
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
  },
}));

export const DiagramPalette = ({ targetObjectId }: DiagramPaletteProps) => {
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const { x: paletteX, y: paletteY, isOpened } = useDiagramPalette();
  const { setReferencePosition, resetReferencePosition } = useLayout();
  const classes = useDiagramPaletteStyle();

  const onToolApply = useCallback(() => {
    if (viewportZoom !== 0 && paletteX && paletteY) {
      const referencePosition = {
        x: (paletteX - viewportX) / viewportZoom,
        y: (paletteY - viewportY) / viewportZoom,
      };
      setReferencePosition(referencePosition, '');
    }
  }, [paletteX, paletteY, viewportX, viewportY, viewportZoom]);

  const onToolApplyError = () => {
    resetReferencePosition();
  };

  return isOpened && paletteX && paletteY ? (
    <DiagramPalettePortal>
      <div className={classes.toolbar} style={{ position: 'absolute', left: paletteX, top: paletteY }}>
        <Palette
          diagramElementId={targetObjectId}
          onDirectEditClick={() => {}}
          isDiagramElementPalette={false}
          onToolApply={onToolApply}
          onToolApplyError={onToolApplyError}
        />
      </div>
    </DiagramPalettePortal>
  ) : null;
};
