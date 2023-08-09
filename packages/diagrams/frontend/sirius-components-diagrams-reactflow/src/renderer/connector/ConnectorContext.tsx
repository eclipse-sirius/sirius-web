/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { Connection } from 'reactflow';
import {
  ConnectorContextProviderProps,
  ConnectorContextProviderState,
  ConnectorContextValue,
} from './ConnectorContext.types';

const defaultValue: ConnectorContextValue = {
  connection: null,
  setConnection: () => {},
  resetConnection: () => {},
};

export const ConnectorContext = React.createContext<ConnectorContextValue>(defaultValue);

export const ConnectorContextProvider = ({ children }: ConnectorContextProviderProps) => {
  const [state, setState] = useState<ConnectorContextProviderState>({
    connection: null,
  });

  const setConnection = (connection: Connection) => setState((prevState) => ({ ...prevState, connection }));

  const resetConnection = () => {
    setState((prevState) => ({ ...prevState, connection: null }));
  };

  return (
    <ConnectorContext.Provider value={{ connection: state.connection, setConnection, resetConnection }}>
      {children}
    </ConnectorContext.Provider>
  );
};
