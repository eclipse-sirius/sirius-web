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
  DiagramPaletteContextProviderProps,
  DiagramPaletteContextProviderState,
  DiagramPaletteContextValue,
  PaletteWithLastTool,
} from './DiagramPaletteContext.types';
import { GQLTool } from './Palette.types';

const defaultValue: DiagramPaletteContextValue = {
  x: null,
  y: null,
  isOpened: false,
  hideDiagramPalette: () => {},
  showDiagramPalette: () => {},
  getLastToolInvoked: () => null,
  setLastToolInvoked: () => {},
};

export const DiagramPaletteContext = React.createContext<DiagramPaletteContextValue>(defaultValue);

export const DiagramPaletteContextProvider = ({ children }: DiagramPaletteContextProviderProps) => {
  const [state, setState] = useState<DiagramPaletteContextProviderState>({
    x: null,
    y: null,
    isOpened: false,
    lastToolsInvoked: [],
  });

  const showPalette = useCallback((x: number, y: number) => {
    setState((prevState) => ({ ...prevState, x, y, isOpened: true }));
  }, []);

  const hidePalette = useCallback(() => {
    if (state.isOpened) {
      setState((prevState) => ({ ...prevState, x: null, y: null, isOpened: false }));
    }
  }, [state.isOpened]);

  const getLastToolInvoked = (paletteId: string): GQLTool | null => {
    return (
      state.lastToolsInvoked.find((toolSectionWithDefaultTool) => toolSectionWithDefaultTool.paletteId === paletteId)
        ?.lastTool || null
    );
  };

  const setLastToolInvoked = (paletteId: string, tool: GQLTool) => {
    const lastToolsInvoked: PaletteWithLastTool[] = state.lastToolsInvoked;
    if (lastToolsInvoked.some((toolSectionWithLastTool) => toolSectionWithLastTool.paletteId === paletteId)) {
      lastToolsInvoked.splice(
        lastToolsInvoked.findIndex((toolSectionWithLastTool) => toolSectionWithLastTool.paletteId === paletteId),
        1
      );
    }
    lastToolsInvoked.push({ paletteId, lastTool: tool });
  };

  return (
    <DiagramPaletteContext.Provider
      value={{
        x: state.x,
        y: state.y,
        isOpened: state.isOpened,
        showDiagramPalette: showPalette,
        hideDiagramPalette: hidePalette,
        getLastToolInvoked,
        setLastToolInvoked,
      }}>
      {children}
    </DiagramPaletteContext.Provider>
  );
};
