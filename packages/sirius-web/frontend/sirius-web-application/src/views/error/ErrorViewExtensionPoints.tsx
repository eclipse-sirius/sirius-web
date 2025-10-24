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
import { ErrorMessageProvider } from './ErrorView.types';

/**
 * Extension point for error message providers.
 *
 * This extension point allows contributions to provide custom error messages for the error view.
 * Each contribution can define how a specific error message should be generated and displayed.
 *
 * @since v2024.9.0
 */
export const errorMessageProvidersExtensionPoint: DataExtensionPoint<Array<ErrorMessageProvider>> = {
  identifier: 'error#messageProviders',
  fallback: [],
};
