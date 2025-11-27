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
import { useState } from 'react';
import { UseMiniMapValue } from './useMiniMap.types';

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

export const useMiniMap = (): UseMiniMapValue => {
  const localStorageKey: string = 'sirius-diagram-mini-map-show';

  const getInitialMiniMapShow = (): boolean => {
    if (!isLocalStorageAvailable()) {
      return true;
    }
    const storedPreference = localStorage.getItem(localStorageKey);
    return storedPreference ? JSON.parse(storedPreference) : true;
  };

  const [showMiniMap, setShowMiniMap] = useState<boolean>(getInitialMiniMapShow);

  const handleShowMiniMapChange = (show: boolean) => {
    if (isLocalStorageAvailable()) {
      localStorage.setItem(localStorageKey, JSON.stringify(show));
    }
    setShowMiniMap(show);
  };

  return { showMiniMap, setShowMiniMap: handleShowMiniMapChange };
};
