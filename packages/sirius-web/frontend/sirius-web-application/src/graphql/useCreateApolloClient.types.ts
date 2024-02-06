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

import { ApolloClientOptions, HttpOptions, InMemoryCacheConfig, NormalizedCacheObject } from '@apollo/client';
import { WebSocketLink } from '@apollo/client/link/ws';

export type HttpOptionsConfigurer = (options: HttpOptions) => HttpOptions;

export type WebSocketOptionsConfigurer = (options: WebSocketLink.Configuration) => WebSocketLink.Configuration;

export type CacheOptionsConfigurer = (options: InMemoryCacheConfig) => InMemoryCacheConfig;

export type ApolloClientOptionsConfigurer = (
  options: ApolloClientOptions<NormalizedCacheObject>
) => ApolloClientOptions<NormalizedCacheObject>;
