/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { Connection, ReactFlowState, useStore, XYPosition } from '@xyflow/react';
import React, { useState } from 'react';
import {
  ConnectorContextProviderProps,
  ConnectorContextProviderState,
  ConnectorContextValue,
} from './ConnectorContext.types';
import { GQLNodeDescription } from './useConnector.types';

const defaultValue: ConnectorContextValue = {
  connection: null,
  position: { x: 0, y: 0 },
  candidates: [],
  isConnectionInProgress: false,
  setConnection: () => {},
  setPosition: () => {},
  setCandidates: () => {},
  resetConnection: () => {},
};

export const ConnectorContext = React.createContext<ConnectorContextValue>(defaultValue);

const isConnectionInProgressSelector = (state: ReactFlowState) =>
  state.connection.inProgress && !!state.connection.fromHandle.id?.startsWith('creation');

export const ConnectorContextProvider = ({ children }: ConnectorContextProviderProps) => {
  const isConnectionInProgress = useStore((state) => isConnectionInProgressSelector(state));

  const [state, setState] = useState<ConnectorContextProviderState>({
    connection: null,
    position: { x: 0, y: 0 },
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

  const resetConnection = () => {
    setState((prevState) => ({
      ...prevState,
      connection: null,
      targetCandidates: [],
      isNewConnection: false,
      selectedNodeId: null,
    }));
  };

  return (
    <ConnectorContext.Provider
      value={{
        connection: state.connection,
        position: state.position,
        candidates: state.candidates,
        isConnectionInProgress,
        setConnection,
        setPosition,
        setCandidates,
        resetConnection,
      }}>
      {children}
    </ConnectorContext.Provider>
  );
};
