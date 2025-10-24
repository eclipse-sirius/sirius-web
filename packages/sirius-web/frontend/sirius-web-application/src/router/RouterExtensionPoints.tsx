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
import { RouteProps } from 'react-router-dom';

/**
 * Extension point for defining routes in the application.
 *
 * This extension point allows contributions to define custom routes that will be added to the application's router.
 * Each contribution can define how a specific route should be rendered and behave.
 *
 * @since v2024.3.0
 */
export const routerExtensionPoint: DataExtensionPoint<Array<RouteProps>> = {
  identifier: 'router#routes',
  fallback: [],
};
