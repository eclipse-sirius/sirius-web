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
import { useStoreApi } from 'reactflow';
import { EdgePaletteContext } from './EdgePaletteContext';
import { EdgePaletteContextValue } from './EdgePaletteContext.types';
import { UseEdgePaletteValue } from './useEdgePalette.types';

const computePalettePosition = (event: MouseEvent | React.MouseEvent, bounds: DOMRect | undefined) => {
  return {
    x: event.clientX - (bounds?.left ?? 0),
    y: event.clientY - (bounds?.top ?? 0),
  };
};

export const useEdgePalette = (): UseEdgePaletteValue => {
  const { x, y, isOpened, hideEdgePalette, showEdgePalette } = useContext<EdgePaletteContextValue>(EdgePaletteContext);
  const { domNode } = useStoreApi().getState();
  const element = domNode?.getBoundingClientRect();

  const onEdgeClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      const palettePosition = computePalettePosition(event, element);
      showEdgePalette(palettePosition.x, palettePosition.y);
    },
    [element]
  );

  return {
    x,
    y,
    isOpened,
    hideEdgePalette,
    showEdgePalette,
    onEdgeClick,
  };
};
