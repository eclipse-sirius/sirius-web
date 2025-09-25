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

import { useContext } from 'react';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { UseConnectorContextValue } from './useConnectorContext.types';

export const useConnectorContext = (): UseConnectorContextValue => {
  const { setConnection, connection, setToolCandidates, toolCandidates, setPosition, position } =
    useContext<ConnectorContextValue>(ConnectorContext);

  return {
    connection,
    position,
    toolCandidates,
    setConnection,
    setPosition,
    setToolCandidates,
  };
};
