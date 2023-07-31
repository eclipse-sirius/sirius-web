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
  FullscreenContextProviderProps,
  FullscreenContextProviderState,
  FullscreenContextValue,
} from './Fullscreen.context.types';

export const FullscreenContext = React.createContext<FullscreenContextValue>({
  fullscreen: false,
  setFullscreen: () => {},
});

export const FullscreenContextProvider = ({ children }: FullscreenContextProviderProps) => {
  const [state, setState] = useState<FullscreenContextProviderState>({ fullscreen: false });

  const setFullscreen = useCallback((fullscreen: boolean) => {
    setState((prevState) => ({ ...prevState, fullscreen }));
  }, []);

  return (
    <FullscreenContext.Provider value={{ fullscreen: state.fullscreen, setFullscreen }}>
      {children}
    </FullscreenContext.Provider>
  );
};
