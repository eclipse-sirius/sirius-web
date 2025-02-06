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
import { ActionsStateContextValue } from './ActionsStateContext.types';

const defaultValue: ActionsStateContextValue = {
  setIsLoading: () => [],
  isLoading: false,
};

export const ActionsStateContext = React.createContext<ActionsStateContextValue>(defaultValue);

export const ActionsStateContextProvider = ({ children }) => {
  const [state, setState] = useState<ActionsStateContextValue>(defaultValue);

  const setIsLoading: ActionsStateContextValue['setIsLoading'] = (isLoading) =>
    setState((prevState) => ({ ...prevState, isLoading }));

  return (
    <ActionsStateContext.Provider
      value={{
        setIsLoading,
        isLoading: state.isLoading,
      }}>
      {children}
    </ActionsStateContext.Provider>
  );
};
