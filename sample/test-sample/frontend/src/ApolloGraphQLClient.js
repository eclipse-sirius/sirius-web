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
import { ApolloClient, HttpLink, InMemoryCache, split } from '@apollo/client';
import { WebSocketLink } from '@apollo/client/link/ws';
import { getMainDefinition } from '@apollo/client/utilities';
import { httpOrigin, wsOrigin } from '@eclipse-sirius/sirius-components';

const httpLink = new HttpLink({
  uri: `${httpOrigin}/api/graphql`
});

const wsLink = new WebSocketLink({
  uri: `${wsOrigin}/subscriptions`,
  options: {
    reconnect: true,
    lazy: true
  }
});

const splitLink = split(
  ({ query }) => {
    const definition = getMainDefinition(query);
    return definition.kind === 'OperationDefinition' && definition.operation === 'subscription';
  },
  wsLink,
  httpLink
);

export const ApolloGraphQLClient = new ApolloClient({
  link: splitLink,
  cache: new InMemoryCache()
});
