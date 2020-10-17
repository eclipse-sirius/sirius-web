/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import React from 'react';

import { GraphQLHttpClient } from './GraphQLHttpClient';
import { GraphQLWebSocketClient } from './GraphQLWebSocketClient';
import { httpOrigin, wsOrigin } from './URL';

const httpUrl = `${httpOrigin}/api/graphql`;
const wsUrl = `${wsOrigin}/subscriptions`;

export const graphQLHttpClient = new GraphQLHttpClient(httpUrl);
export const graphQLWebSocketClient = new GraphQLWebSocketClient(wsUrl);

const value = {
  graphQLWebSocketClient,
  graphQLHttpClient,
};

export const GraphQLClient = React.createContext(value);
