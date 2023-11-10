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
import { Connection, XYPosition } from 'reactflow';
import {
  ConnectorContextProviderProps,
  ConnectorContextProviderState,
  ConnectorContextValue,
} from './ConnectorContext.types';
import { GQLNodeDescription } from './useConnector.types';

const defaultValue: ConnectorContextValue = {
  connection: null,
  position: null,
  candidates: [],
  isNewConnection: false,
  setConnection: () => {},
  setPosition: () => {},
  setCandidates: () => {},
  resetConnection: () => {},
  setIsNewConnection: () => {},
};

export const ConnectorContext = React.createContext<ConnectorContextValue>(defaultValue);

export const ConnectorContextProvider = ({ children }: ConnectorContextProviderProps) => {
  const [state, setState] = useState<ConnectorContextProviderState>({
    connection: null,
    position: null,
    candidates: [],
    isNewConnection: false,
  });

  const setConnection = (connection: Connection) => {
    setState((prevState) => ({ ...prevState, connection }));
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
      connection: null,
      targetCandidates: [],
      isNewConnection: false,
    }));
  };

  return (
    <ConnectorContext.Provider
      value={{
        connection: state.connection,
        position: state.position,
        candidates: state.candidates,
        isNewConnection: state.isNewConnection,
        setConnection,
        setPosition,
        setCandidates,
        resetConnection,
        setIsNewConnection,
      }}>
      {children}
    </ConnectorContext.Provider>
  );
};
