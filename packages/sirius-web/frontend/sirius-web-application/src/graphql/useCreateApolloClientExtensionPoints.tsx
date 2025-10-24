/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { DataExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import {
  ApolloClientOptionsConfigurer,
  CacheOptionsConfigurer,
  HttpOptionsConfigurer,
  WebSocketOptionsConfigurer,
} from './useCreateApolloClient.types';

/**
 * Extension points for configuring Apollo Client options.
 *
 * These extension points allow contributions to customize the HTTP Apollo Client options.
 * Each extension point can have multiple contributions that will be applied in the order they are registered.
 *
 * @since v2024.3.0
 */
export const httpOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<HttpOptionsConfigurer>> = {
  identifier: 'apolloClient#httpOptionsConfigurers',
  fallback: [],
};

/**
 * Extension points for configuring WebSocket options for Apollo Client.
 *
 * These extension points allow contributions to customize the WebSocket Apollo Client options.
 * Each extension point can have multiple contributions that will be applied in the order they are registered.
 *
 * @since v2024.3.0
 */
export const webSocketOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<WebSocketOptionsConfigurer>> = {
  identifier: 'apolloClient#webSocketOptionsConfigurers',
  fallback: [],
};

/**
 * Extension points for configuring cache options for Apollo Client.
 *
 * These extension points allow contributions to customize the cache options for Apollo Client.
 * Each extension point can have multiple contributions that will be applied in the order they are registered.
 *
 * @since v2024.3.0
 */
export const cacheOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<CacheOptionsConfigurer>> = {
  identifier: 'apolloClient#cacheOptionsConfigurers',
  fallback: [],
};

/**
 * Extension points for configuring the overall Apollo Client options.
 *
 * These extension points allow contributions to customize the Apollo Client options.
 * Each extension point can have multiple contributions that will be applied in the order they are registered.
 *
 * @since v2024.3.0
 */
export const apolloClientOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<ApolloClientOptionsConfigurer>> = {
  identifier: 'apolloClient#apolloClientOptionsConfigurers',
  fallback: [],
};
