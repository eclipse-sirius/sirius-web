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
  EdgePaletteContextProviderProps,
  EdgePaletteContextProviderState,
  EdgePaletteContextValue,
} from './EdgePaletteContext.types';

const defaultValue: EdgePaletteContextValue = {
  x: null,
  y: null,
  isOpened: false,
  hideEdgePalette: () => {},
  showEdgePalette: () => {},
};

export const EdgePaletteContext = React.createContext<EdgePaletteContextValue>(defaultValue);

export const EdgePaletteContextProvider = ({ children }: EdgePaletteContextProviderProps) => {
  const [state, setState] = useState<EdgePaletteContextProviderState>({
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
    <EdgePaletteContext.Provider
      value={{
        x: state.x,
        y: state.y,
        isOpened: state.isOpened,
        showEdgePalette: showPalette,
        hideEdgePalette: hidePalette,
      }}>
      {children}
    </EdgePaletteContext.Provider>
  );
};
