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

import React, { useState } from 'react';
import { GQLTool } from '../TreeItemPalette.types';
import {
  PaletteContextProviderProps,
  PaletteContextProviderState,
  PaletteContextValue,
  PaletteWithLastTool,
} from './PaletteContext.types';

const defaultValue: PaletteContextValue = {
  getLastToolInvoked: () => null,
  setLastToolInvoked: () => {},
};

export const PaletteContext = React.createContext<PaletteContextValue>(defaultValue);

export const PaletteContextProvider = ({ children }: PaletteContextProviderProps) => {
  const [state, setState] = useState<PaletteContextProviderState>({
    lastToolsInvoked: [],
  });

  const getLastToolInvoked = (paletteId: string): GQLTool | null => {
    return (
      state.lastToolsInvoked.find((toolSectionWithDefaultTool) => toolSectionWithDefaultTool.paletteId === paletteId)
        ?.lastTool || null
    );
  };

  const setLastToolInvoked = (paletteId: string, tool: GQLTool) => {
    setState((prevState) => {
      const lastToolsInvoked: PaletteWithLastTool[] = prevState.lastToolsInvoked;
      if (lastToolsInvoked.some((toolSectionWithLastTool) => toolSectionWithLastTool.paletteId === paletteId)) {
        lastToolsInvoked.splice(
          lastToolsInvoked.findIndex((toolSectionWithLastTool) => toolSectionWithLastTool.paletteId === paletteId),
          1
        );
      }
      lastToolsInvoked.push({ paletteId, lastTool: tool });
      return { ...prevState, lastToolsInvoked: lastToolsInvoked };
    });
  };

  return (
    <PaletteContext.Provider
      value={{
        getLastToolInvoked,
        setLastToolInvoked,
      }}>
      {children}
    </PaletteContext.Provider>
  );
};
