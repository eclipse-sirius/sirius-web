/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { Connection, XYPosition } from '@xyflow/react';
import React, { useState } from 'react';
import {
  ConnectorContextProviderProps,
  ConnectorContextProviderState,
  ConnectorContextValue,
} from './ConnectorContext.types';
import { GQLConnectorTool } from './useConnector.types';

const defaultValue: ConnectorContextValue = {
  connection: null,
  position: null,
  toolCandidates: [],
  setConnection: () => {},
  setPosition: () => {},
  setToolCandidates: () => {},
};

export const ConnectorContext = React.createContext<ConnectorContextValue>(defaultValue);

export const ConnectorContextProvider = ({ children }: ConnectorContextProviderProps) => {
  const [state, setState] = useState<ConnectorContextProviderState>({
    connection: null,
    position: null,
    toolCandidates: [],
  });

  const setConnection = (connection: Connection | null) => {
    setState((prevState) => ({ ...prevState, connection }));
  };

  const setPosition = (position: XYPosition) => {
    setState((prevState) => ({ ...prevState, position }));
  };

  const setToolCandidates = (candidates: GQLConnectorTool[]) => {
    setState((prevState) => ({ ...prevState, toolCandidates: candidates }));
  };

  return (
    <ConnectorContext.Provider
      value={{
        connection: state.connection,
        position: state.position,
        toolCandidates: state.toolCandidates,
        setConnection,
        setPosition,
        setToolCandidates,
      }}>
      {children}
    </ConnectorContext.Provider>
  );
};
