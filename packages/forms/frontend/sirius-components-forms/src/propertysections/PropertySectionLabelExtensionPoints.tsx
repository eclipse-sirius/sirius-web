/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo and others.
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

import { ComponentExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { PropertySectionLabelDecoratorProps } from './PropertySectionLabel.types';

/**
 * Extension point for property section label decorators.
 *
 * This extension point allows the addition of custom decorators to property section labels.
 *
 * @since v2024.7.0
 */
export const propertySectionLabelDecoratorExtensionPoint: ComponentExtensionPoint<PropertySectionLabelDecoratorProps> =
  {
    identifier: 'propertySectionLabel#decorator',
    FallbackComponent: () => null,
  };
