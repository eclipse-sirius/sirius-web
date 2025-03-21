/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import React, { useCallback, useState } from 'react';
import {
  DiagramElementPaletteContextProviderProps,
  DiagramElementPaletteContextProviderState,
  DiagramElementPaletteContextValue,
} from './DiagramElementPaletteContext.types';

const defaultValue: DiagramElementPaletteContextValue = {
  x: null,
  y: null,
  isOpened: false,
  paletteTargetElementId: null,
  hideDiagramElementPalette: () => {},
  showDiagramElementPalette: () => {},
};

export const DiagramElementPaletteContext = React.createContext<DiagramElementPaletteContextValue>(defaultValue);

export const DiagramElementPaletteContextProvider = ({ children }: DiagramElementPaletteContextProviderProps) => {
  const [state, setState] = useState<DiagramElementPaletteContextProviderState>({
    x: null,
    y: null,
    isOpened: false,
    elementId: null,
  });

  const showPalette = useCallback((x: number, y: number, elementId: string) => {
    setState((prevState) => ({ ...prevState, x, y, isOpened: true, elementId: elementId }));
  }, []);

  const hidePalette = useCallback(() => {
    if (state.isOpened) {
      setState((prevState) => ({ ...prevState, x: null, y: null, isOpened: false, elementId: null }));
    }
  }, [state.isOpened]);

  return (
    <DiagramElementPaletteContext.Provider
      value={{
        x: state.x,
        y: state.y,
        isOpened: state.isOpened,
        paletteTargetElementId: state.elementId,
        showDiagramElementPalette: showPalette,
        hideDiagramElementPalette: hidePalette,
      }}>
      {children}
    </DiagramElementPaletteContext.Provider>
  );
};
