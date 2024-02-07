/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import {
  ApolloClient,
  ApolloClientOptions,
  DefaultOptions,
  HttpLink,
  HttpOptions,
  InMemoryCache,
  InMemoryCacheConfig,
  NormalizedCacheObject,
  split,
} from '@apollo/client';
import { FragmentRegistryAPI, createFragmentRegistry } from '@apollo/client/cache';
import { WebSocketLink } from '@apollo/client/link/ws';
import { getMainDefinition } from '@apollo/client/utilities';
import { useData } from '@eclipse-sirius/sirius-components-core';
import { useMemo } from 'react';
import {
  ProjectFragment,
  ViewerProjectsFragment,
} from '../views/project-browser/list-projects-area/useProjects.fragments';
import {
  apolloClientOptionsConfigurersExtensionPoint,
  cacheOptionsConfigurersExtensionPoint,
  httpOptionsConfigurersExtensionPoint,
  webSocketOptionsConfigurersExtensionPoint,
} from './useCreateApolloClientExtensionPoints';

export const useCreateApolloClient = (httpOrigin: string, wsOrigin: string): ApolloClient<NormalizedCacheObject> => {
  let httpOptions: HttpOptions = {
    uri: `${httpOrigin}/api/graphql`,
  };
  const { data: httpOptionsConfigurers } = useData(httpOptionsConfigurersExtensionPoint);
  httpOptionsConfigurers.forEach((configurer) => {
    httpOptions = configurer(httpOptions);
  });

  let webSocketOptions: WebSocketLink.Configuration = {
    uri: `${wsOrigin}/subscriptions`,
    options: {
      reconnect: true,
      lazy: true,
    },
  };
  const { data: webSocketOptionsConfigurers } = useData(webSocketOptionsConfigurersExtensionPoint);
  webSocketOptionsConfigurers.forEach((configurer) => {
    webSocketOptions = configurer(webSocketOptions);
  });

  let fragmentRegistry: FragmentRegistryAPI = createFragmentRegistry();
  fragmentRegistry.register(ViewerProjectsFragment, ProjectFragment);

  let cacheOptions: InMemoryCacheConfig = {
    fragments: fragmentRegistry,
  };
  const { data: cacheOptionsConfigurers } = useData(cacheOptionsConfigurersExtensionPoint);
  cacheOptionsConfigurers.forEach((configurer) => {
    cacheOptions = configurer(cacheOptions);
  });

  const httpLink = new HttpLink(httpOptions);
  const wsLink = new WebSocketLink(webSocketOptions);

  const link = split(
    ({ query }) => {
      const definition = getMainDefinition(query);
      return definition.kind === 'OperationDefinition' && definition.operation === 'subscription';
    },
    wsLink,
    httpLink
  );

  const cache = new InMemoryCache(cacheOptions);

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

  let apolloClientOptions: ApolloClientOptions<NormalizedCacheObject> = {
    link,
    cache,
    connectToDevTools: true,
    defaultOptions,
  };
  const { data: apolloClientOptionsConfigurers } = useData(apolloClientOptionsConfigurersExtensionPoint);
  apolloClientOptionsConfigurers.forEach((configurer) => {
    apolloClientOptions = configurer(apolloClientOptions);
  });

  const apolloClient = useMemo(() => new ApolloClient(apolloClientOptions), [apolloClientOptions]);
  return apolloClient;
};
