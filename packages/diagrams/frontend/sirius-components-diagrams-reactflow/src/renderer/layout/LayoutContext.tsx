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
  LayoutContextContextProviderProps,
  LayoutContextContextProviderState,
  LayoutContextContextValue,
  ReferencePosition,
} from './LayoutContext.types';

const defaultValue: LayoutContextContextValue = {
  referencePosition: null,
  setReferencePosition: () => {},
  resetReferencePosition: () => {},
};

export const LayoutContext = React.createContext<LayoutContextContextValue>(defaultValue);

export const LayoutContextContextProvider = ({ children }: LayoutContextContextProviderProps) => {
  const [state, setState] = useState<LayoutContextContextProviderState>({
    referencePosition: null,
  });

  const setReferencePosition = useCallback((referencePosition: ReferencePosition) => {
    setState((prevState) => ({ ...prevState, referencePosition }));
  }, []);

  const resetReferencePosition = useCallback(() => {
    setState((prevState) => ({ ...prevState, referencePosition: null }));
  }, []);

  return (
    <LayoutContext.Provider
      value={{
        referencePosition: state.referencePosition,
        setReferencePosition,
        resetReferencePosition,
      }}>
      {children}
    </LayoutContext.Provider>
  );
};
