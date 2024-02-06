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

import { DataExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import {
  ApolloClientOptionsConfigurer,
  CacheOptionsConfigurer,
  HttpOptionsConfigurer,
  WebSocketOptionsConfigurer,
} from './useCreateApolloClient.types';

export const httpOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<HttpOptionsConfigurer>> = {
  identifier: 'apolloClient#httpOptionsConfigurers',
  fallback: [],
};

export const webSocketOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<WebSocketOptionsConfigurer>> = {
  identifier: 'apolloClient#webSocketOptionsConfigurers',
  fallback: [],
};

export const cacheOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<CacheOptionsConfigurer>> = {
  identifier: 'apolloClient#cacheOptionsConfigurers',
  fallback: [],
};

export const apolloClientOptionsConfigurersExtensionPoint: DataExtensionPoint<Array<ApolloClientOptionsConfigurer>> = {
  identifier: 'apolloClient#apolloClientOptionsConfigurers',
  fallback: [],
};
