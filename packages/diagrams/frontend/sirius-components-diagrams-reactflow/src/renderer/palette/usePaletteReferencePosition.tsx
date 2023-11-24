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
import { useViewport } from 'reactflow';
import { UsePaletteReferencePositionValue } from './usePaletteReferencePosition.types';
import { useDiagramElementPalette } from './useDiagramElementPalette';
import { useDiagramPalette } from './useDiagramPalette';

export const usePaletteReferencePosition = (): UsePaletteReferencePositionValue => {
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const { x: paletteElementX, y: paletteElementY, isOpened: isPaletteElementOpened } = useDiagramElementPalette();
  const { x: paletteX, y: paletteY, isOpened: isPaletteOpened } = useDiagramPalette();

  let x: number | null = null;
  let y: number | null = null;

  if (viewportZoom !== 0 && isPaletteOpened && paletteX && paletteY) {
    x = (paletteX - viewportX) / viewportZoom;
    y = (paletteY - viewportY) / viewportZoom;
  }

  if (viewportZoom !== 0 && isPaletteElementOpened && paletteElementX && paletteElementY) {
    x = (paletteElementX - viewportX) / viewportZoom;
    y = (paletteElementY - viewportY) / viewportZoom;
  }

  return {
    x,
    y,
  };
};
