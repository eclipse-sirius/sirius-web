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
  hideDiagramElementPalette: () => {},
  showDiagramElementPalette: () => {},
};

export const DiagramElementPaletteContext = React.createContext<DiagramElementPaletteContextValue>(defaultValue);

export const DiagramElementPaletteContextProvider = ({ children }: DiagramElementPaletteContextProviderProps) => {
  const [state, setState] = useState<DiagramElementPaletteContextProviderState>({
    x: null,
    y: null,
    isOpened: false,
  });

  const showPalette = useCallback((x: number, y: number) => {
    setState((prevState) => ({ ...prevState, x, y, isOpened: true }));
  }, []);

  const hidePalette = useCallback(() => {
    setState((prevState) => ({ ...prevState, x: null, y: null, isOpened: false }));
  }, []);

  return (
    <DiagramElementPaletteContext.Provider
      value={{
        x: state.x,
        y: state.y,
        isOpened: state.isOpened,
        showDiagramElementPalette: showPalette,
        hideDiagramElementPalette: hidePalette,
      }}>
      {children}
    </DiagramElementPaletteContext.Provider>
  );
};
