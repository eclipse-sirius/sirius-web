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

import { useCallback, useContext, useEffect } from 'react';
import { XYPosition, useKeyPress, useStoreApi } from 'reactflow';
import { DiagramPaletteContext } from './DiagramPaletteContext';
import { DiagramPaletteContextValue } from './DiagramPaletteContext.types';
import { UseDiagramPaletteValue } from './useDiagramPalette.types';

const computePalettePosition = (event: MouseEvent | React.MouseEvent, bounds?: DOMRect): XYPosition => {
  return {
    x: event.clientX - (bounds?.left ?? 0),
    y: event.clientY - (bounds?.top ?? 0),
  };
};

export const useDiagramPalette = (): UseDiagramPaletteValue => {
  const { x, y, isOpened, hideDiagramPalette, showDiagramPalette, getLastToolInvoked, setLastToolInvoked } =
    useContext<DiagramPaletteContextValue>(DiagramPaletteContext);
  const { domNode } = useStoreApi().getState();
  const element = domNode?.getBoundingClientRect();

  const onDiagramBackgroundClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      const palettePosition = computePalettePosition(event, element);
      showDiagramPalette(palettePosition.x, palettePosition.y);
    },
    [element]
  );

  const escapePressed = useKeyPress('Escape');
  useEffect(() => {
    if (escapePressed) {
      hideDiagramPalette();
    }
  }, [escapePressed]);

  return {
    x,
    y,
    isOpened,
    hideDiagramPalette,
    showDiagramPalette,
    onDiagramBackgroundClick,
    getLastToolInvoked,
    setLastToolInvoked,
  };
};
