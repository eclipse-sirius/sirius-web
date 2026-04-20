/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { WidgetContribution } from './WidgetContribution.types';

/**
 * Extension point for widget overrides.
 *
 * This extension point allows the addition of custom widget behavior to forms.
 * Each override can define how a widget should be rendered or behave.
 *
 * @since v2026.5.0
 */
export const widgetOverrideExtensionPoint: DataExtensionPoint<Array<WidgetContribution>> = {
  identifier: 'form#widgetOverride',
  fallback: [],
};
