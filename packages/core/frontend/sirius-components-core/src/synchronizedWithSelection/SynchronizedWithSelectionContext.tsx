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
  SynchronizeWithSelectionContextProviderState,
  SynchronizedWithSelectionContextProviderProps,
  SynchronizedWithSelectionContextValue,
} from './SynchronizedWithSelectionContext.types';

const defaultValue: SynchronizedWithSelectionContextValue = {
  isSynchronized: true,
  toggleSynchronizeWithSelection: () => {},
};

export const SynchronizeWithSelectionContext = React.createContext<SynchronizedWithSelectionContextValue>(defaultValue);

export const SynchronizeWithSelectionContextProvider = ({
  children,
}: SynchronizedWithSelectionContextProviderProps) => {
  const [state, setState] = useState<SynchronizeWithSelectionContextProviderState>({
    isSynchronized: true,
  });

  const toggleSynchronizeWithSelection = useCallback(
    () => setState((prevState) => ({ ...prevState, isSynchronized: !prevState.isSynchronized })),
    []
  );

  return (
    <SynchronizeWithSelectionContext.Provider
      value={{
        isSynchronized: state.isSynchronized,
        toggleSynchronizeWithSelection: toggleSynchronizeWithSelection,
      }}>
      {children}
    </SynchronizeWithSelectionContext.Provider>
  );
};
