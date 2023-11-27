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
  DiagramPaletteContextProviderProps,
  DiagramPaletteContextProviderState,
  DiagramPaletteContextValue,
  ToolSectionWithLastTool,
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

  const getLastToolInvoked = (toolSectionId: string): GQLTool | null => {
    return (
      state.lastToolsInvoked.find(
        (toolSectionWithDefaultTool) => toolSectionWithDefaultTool.toolSectionId === toolSectionId
      )?.lastTool || null
    );
  };

  const setLastToolInvoked = (toolSectionId: string, tool: GQLTool) => {
    const lastToolsInvoked: ToolSectionWithLastTool[] = state.lastToolsInvoked;
    if (lastToolsInvoked.some((toolSectionWithLastTool) => toolSectionWithLastTool.toolSectionId === toolSectionId)) {
      lastToolsInvoked.splice(
        lastToolsInvoked.findIndex(
          (toolSectionWithLastTool) => toolSectionWithLastTool.toolSectionId === toolSectionId
        ),
        1
      );
    }
    lastToolsInvoked.push({ toolSectionId: toolSectionId, lastTool: tool });
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
