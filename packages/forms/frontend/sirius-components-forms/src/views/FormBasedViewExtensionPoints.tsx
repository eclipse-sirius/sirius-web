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
import { FormConverter } from './FormBasedView.types';

/**
 * Extension point for form converters in form-based workbench views.
 *
 * This extension point allows the addition of custom form converters to form-based workbench views.
 * Each contribution can define how a form should be transformed.
 *
 * @since v2024.7.0
 */
export const formBasedViewFormConverterExtensionPoint: DataExtensionPoint<FormConverter[]> = {
  identifier: 'formBasedView#formConverter',
  fallback: [],
};
