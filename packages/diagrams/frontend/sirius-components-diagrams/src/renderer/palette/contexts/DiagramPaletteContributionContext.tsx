/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import React from 'react';
import {
  DiagramPaletteContributionContextProviderProps,
  DiagramPaletteContributionContextValue,
} from './DiagramPaletteContributionContext.types';

const defaultValue: DiagramPaletteContributionContextValue = {
  paletteId: '',
  x: null,
  y: null,
  diagramElementIds: [],
  hideDiagramPalette: () => {},
  setLastToolInvokedId: () => {},
};

export const DiagramPaletteContributionContext =
  React.createContext<DiagramPaletteContributionContextValue>(defaultValue);

export const DiagramPaletteContributionContextProvider = ({
  children,
  paletteId,
  diagramElementIds,
  hideDiagramPalette,
  setLastToolInvokedId,
  x,
  y,
}: DiagramPaletteContributionContextProviderProps) => {
  return (
    <DiagramPaletteContributionContext.Provider
      value={{
        paletteId,
        x,
        y,
        diagramElementIds,
        hideDiagramPalette,
        setLastToolInvokedId,
      }}>
      {children}
    </DiagramPaletteContributionContext.Provider>
  );
};
