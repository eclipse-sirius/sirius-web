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

import { ApolloProvider } from '@apollo/client';
import { ApolloGraphQLProviderProps } from './ApolloGraphQLProvider.types';
import { useCreateApolloClient } from './useCreateApolloClient';

export const ApolloGraphQLProvider = ({ children, httpOrigin, wsOrigin }: ApolloGraphQLProviderProps) => {
  const apolloClient = useCreateApolloClient(httpOrigin, wsOrigin);
  return <ApolloProvider client={apolloClient}>{children}</ApolloProvider>;
};
