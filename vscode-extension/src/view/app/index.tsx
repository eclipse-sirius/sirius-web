/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import { ApolloClient, ApolloProvider, DefaultOptions, HttpLink, InMemoryCache, split } from '@apollo/client';
import { WebSocketLink } from '@apollo/client/link/ws';
import { getMainDefinition } from '@apollo/client/utilities';
import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import React from 'react';
import ReactDOM from 'react-dom';
import { App } from './App';
import './index.css';
import { ToastProvider } from './toast/ToastProvider';

declare global {
  interface Window {
    acquireVsCodeApi(): any;
    serverAddress: string;
    username: string;
    password: string;
    editingContextId: string;
    representationId: string;
    representationLabel: string;
    representationKind: string;
  }
}

// Theia needs 127.0.0.1 instead of localhost
const value = { httpOrigin: window.serverAddress };

const httpLink = new HttpLink({
  uri: `${window.serverAddress}/api/graphql`,
  credentials: 'include',
});

const protocol = window.serverAddress.startsWith('https') ? 'wss' : 'ws';
const address = window.serverAddress.substring(window.serverAddress.indexOf('://'), window.serverAddress.length);
const subscriptionURL = `${protocol}${address}/subscriptions`;

const wsLink = new WebSocketLink({
  uri: subscriptionURL,
  options: {
    reconnect: true,
    lazy: true,
  },
});

const splitLink = split(
  ({ query }) => {
    const definition = getMainDefinition(query);
    return definition.kind === 'OperationDefinition' && definition.operation === 'subscription';
  },
  wsLink,
  httpLink
);

const defaultOptions: DefaultOptions = {
  watchQuery: {
    fetchPolicy: 'no-cache',
  },
  query: {
    fetchPolicy: 'no-cache',
  },
  mutate: {
    fetchPolicy: 'no-cache',
  },
};

const ApolloGraphQLClient = new ApolloClient({
  link: splitLink,
  cache: new InMemoryCache({ addTypename: true }),
  defaultOptions,
});

ReactDOM.render(
  <ServerContext.Provider value={value}>
    <ApolloProvider client={ApolloGraphQLClient}>
      <ToastProvider>
        <App
          serverAddress={window.serverAddress}
          username={window.username}
          password={window.password}
          editingContextId={window.editingContextId}
          representationId={window.representationId}
          representationLabel={window.representationLabel}
          representationKind={window.representationKind}
        />
      </ToastProvider>
    </ApolloProvider>
  </ServerContext.Provider>,
  document.getElementById('root')
);
