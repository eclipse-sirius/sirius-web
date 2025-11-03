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

import { useContext } from 'react';
import { DiagramPaletteContext } from './contexts/DiagramPaletteContext';
import { DiagramPaletteContextValue } from './contexts/DiagramPaletteContext.types';
import { UseDiagramPaletteValue } from './useDiagramPalette.types';

export const useDiagramPalette = (): UseDiagramPaletteValue => {
  const {
    x,
    y,
    isOpened,
    diagramElementIds,
    hideDiagramPalette,
    showDiagramPalette,
    getLastToolInvoked,
    setLastToolInvoked,
  } = useContext<DiagramPaletteContextValue>(DiagramPaletteContext);

  return {
    x,
    y,
    isOpened,
    diagramElementIds,
    hideDiagramPalette,
    showDiagramPalette,
    getLastToolInvoked,
    setLastToolInvoked,
  };
};
