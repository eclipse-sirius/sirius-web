/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { GQLTool } from '../Palette.types';
import {
  PaletteContextProviderProps,
  PaletteContextProviderState,
  PaletteContextValue,
  PaletteWithLastTool,
} from './PaletteContext.types';

const defaultValue: PaletteContextValue = {
  x: null,
  y: null,
  isOpened: false,
  representationElementIds: [],
  hidePalette: () => {},
  showPalette: () => {},
  getLastToolInvoked: () => null,
  setLastToolInvoked: () => {},
};

export const PaletteContext = React.createContext<PaletteContextValue>(defaultValue);

export const PaletteContextProvider = ({ children }: PaletteContextProviderProps) => {
  const [state, setState] = useState<PaletteContextProviderState>({
    x: null,
    y: null,
    isOpened: false,
    representationElementIds: [],
    lastToolsInvoked: [],
  });

  const showPalette = useCallback((x: number, y: number, representationElementIds: string[]) => {
    setState((prevState) => ({ ...prevState, x, y, isOpened: true, representationElementIds }));
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
    <PaletteContext.Provider
      value={{
        x: state.x,
        y: state.y,
        isOpened: state.isOpened,
        representationElementIds: state.representationElementIds,
        showPalette,
        hidePalette,
        getLastToolInvoked,
        setLastToolInvoked,
      }}>
      {children}
    </PaletteContext.Provider>
  );
};
