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

import { useCallback, useContext } from 'react';
import { XYPosition, useStoreApi } from 'reactflow';
import { DiagramElementPaletteContext } from './DiagramElementPaletteContext';
import { DiagramElementPaletteContextValue } from './DiagramElementPaletteContext.types';
import { UseEdgePaletteValue } from './useDiagramElementPalette.types';

const computePalettePosition = (event: MouseEvent | React.MouseEvent, bounds: DOMRect | undefined): XYPosition => {
  return {
    x: event.clientX - (bounds?.left ?? 0),
    y: event.clientY - (bounds?.top ?? 0),
  };
};

export const useDiagramElementPalette = (): UseEdgePaletteValue => {
  const { x, y, isOpened, hideDiagramElementPalette, showDiagramElementPalette } =
    useContext<DiagramElementPaletteContextValue>(DiagramElementPaletteContext);
  const { domNode } = useStoreApi().getState();
  const element = domNode?.getBoundingClientRect();

  const onDiagramElementClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      const palettePosition = computePalettePosition(event, element);
      showDiagramElementPalette(palettePosition.x, palettePosition.y);
    },
    [element]
  );

  return {
    x,
    y,
    isOpened,
    hideDiagramElementPalette,
    showDiagramElementPalette,
    onDiagramElementClick,
  };
};
