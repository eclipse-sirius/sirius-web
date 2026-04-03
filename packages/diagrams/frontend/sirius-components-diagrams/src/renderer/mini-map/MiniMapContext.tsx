/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import React, { useContext, useState } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { MiniMapContextProviderProps, MiniMapContextValue } from './MiniMapContext.types';

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
  const localStorageKey: string = 'sirius-single-diagram-mini-map-visibility';
  const { diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { diagramDescription } = useDiagramDescription();

  const getInitialMiniMapVisibility = (): boolean => {
    let minimapVisible: boolean = diagramDescription.minimapVisible;
    if (isLocalStorageAvailable()) {
      const storedPreference = localStorage.getItem(localStorageKey);
      if (storedPreference) {
        const localStorageValue: boolean | undefined = JSON.parse(storedPreference)[diagramId];
        if (localStorageValue !== undefined) {
          minimapVisible = localStorageValue;
        }
      }
    }
    return minimapVisible;
  };

  const [miniMap, setMiniMap] = useState<boolean>(getInitialMiniMapVisibility);

  const handleMiniMapVisibilityChange = (visible: boolean) => {
    if (isLocalStorageAvailable()) {
      const storedPreference = localStorage.getItem(localStorageKey);
      const preferences = storedPreference ? JSON.parse(storedPreference) : {};
      preferences[diagramId] = visible;
      localStorage.setItem(localStorageKey, JSON.stringify(preferences));
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
