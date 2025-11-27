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
import React, { useState } from 'react';
import { MiniMapContextValue, MiniMapContextProviderProps } from './MiniMapContext.types';

const defaultContextValue: MiniMapContextValue = {
  isMiniMapVisible: false,
  setMiniMapVisibility: () => {},
};

export const MiniMapContext = React.createContext<MiniMapContextValue>(defaultContextValue);

const isLocalStorageAvailable = (): boolean => {
  const test = 'localStorageTest';
  try {
    localStorage.setItem(test, test);
    localStorage.removeItem(test);
    return true;
  } catch (_) {
    return false;
  }
};

export const MiniMapContextProvider = ({ children }: MiniMapContextProviderProps) => {
  const localStorageKey: string = 'sirius-diagram-mini-map-visibility';

  const getInitialMiniMapVisibility = (): boolean => {
    if (!isLocalStorageAvailable()) {
      return true;
    }
    const storedPreference = localStorage.getItem(localStorageKey);
    return storedPreference ? JSON.parse(storedPreference) : true;
  };

  const [miniMap, setMiniMap] = useState<boolean>(getInitialMiniMapVisibility);

  const handleMiniMapVisibilityChange = (visible: boolean) => {
    if (isLocalStorageAvailable()) {
      localStorage.setItem(localStorageKey, JSON.stringify(visible));
    }
    setMiniMap(visible);
  };

  return (
    <MiniMapContext.Provider
      value={{
        isMiniMapVisible: miniMap,
        setMiniMapVisibility: handleMiniMapVisibilityChange,
      }}>
      {children}
    </MiniMapContext.Provider>
  );
};
