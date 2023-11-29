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

import React, { useState } from 'react';
import { XYPosition } from 'reactflow';
import {
  ConnectorContextProviderProps,
  ConnectorContextProviderState,
  ConnectorContextValue,
} from './ConnectorContext.types';
import { GQLNodeDescription } from './useConnector.types';

const defaultValue: ConnectorContextValue = {
  position: null,
  candidates: [],
  isNewConnection: false,
  isFrozen: false,
  setFrozen: () => {},
  setPosition: () => {},
  setCandidates: () => {},
  resetConnection: () => {},
  setIsNewConnection: () => {},
};

export const ConnectorContext = React.createContext<ConnectorContextValue>(defaultValue);

export const ConnectorContextProvider = ({ children }: ConnectorContextProviderProps) => {
  const [state, setState] = useState<ConnectorContextProviderState>({
    position: null,
    candidates: [],
    isNewConnection: false,
    isFrozen: false,
  });

  const setFrozen = (isFrozen: boolean) => {
    setState((prevState) => ({ ...prevState, isFrozen }));
  };

  const setPosition = (position: XYPosition) => {
    setState((prevState) => ({ ...prevState, position }));
  };

  const setCandidates = (candidates: GQLNodeDescription[]) => {
    setState((prevState) => ({ ...prevState, candidates }));
  };

  const setIsNewConnection = (isNewConnection: boolean) => {
    setState((prevState) => ({ ...prevState, isNewConnection }));
  };

  const resetConnection = () => {
    setState((prevState) => ({
      ...prevState,
      targetCandidates: [],
      isNewConnection: false,
      selectedNodeId: null,
    }));
  };

  return (
    <ConnectorContext.Provider
      value={{
        position: state.position,
        candidates: state.candidates,
        isNewConnection: state.isNewConnection,
        isFrozen: state.isFrozen,
        setFrozen,
        setPosition,
        setCandidates,
        resetConnection,
        setIsNewConnection,
      }}>
      {children}
    </ConnectorContext.Provider>
  );
};
