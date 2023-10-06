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

import { useContext } from 'react';
import { Connection, OnConnect } from 'reactflow';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { UseConnectorValue } from './useConnector.types';

export const useConnector = (): UseConnectorValue => {
  const { connection, setConnection, resetConnection } = useContext<ConnectorContextValue>(ConnectorContext);

  const onConnect: OnConnect = (connection: Connection) => setConnection(connection);
  const onConnectorContextualMenuClose = () => resetConnection();

  return {
    onConnect,
    onConnectorContextualMenuClose,
    connection,
  };
};
