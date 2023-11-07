/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { ApolloClient, DefaultOptions, HttpLink, InMemoryCache, split } from '@apollo/client';
import { WebSocketLink } from '@apollo/client/link/ws';
import { getMainDefinition } from '@apollo/client/utilities';

const httpLink = (httpOrigin: string) => {
  return new HttpLink({
    uri: `${httpOrigin}/api/graphql`,
  });
};

const wsLink = (wsOrigin: string) => {
  return new WebSocketLink({
    uri: `${wsOrigin}/subscriptions`,
    options: {
      reconnect: true,
      lazy: true,
    },
  });
};

const splitLink = (httpOrigin: string, wsOrigin: string) => {
  return split(
    ({ query }) => {
      const definition = getMainDefinition(query);
      return definition.kind === 'OperationDefinition' && definition.operation === 'subscription';
    },
    wsLink(wsOrigin),
    httpLink(httpOrigin)
  );
};

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

export const createApolloGraphQLClient = (httpOrigin: string, wsOrigin: string) => {
  return new ApolloClient({
    link: splitLink(httpOrigin, wsOrigin),
    cache: new InMemoryCache(),
    connectToDevTools: true,
    defaultOptions,
  });
};
