/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { PaletteToolOverriddenContributionProps } from './PaletteToolOverrideContribution.types';

/**
 * Extension point for overriding tools retrieved from the server.
 *
 * This extension point allows the addition of custom tools to the diagram palette.
 * Each contribution can define how a tool should be rendered and behave.
 *
 * @since v2026.7.0
 */
export const paletteToolOverrideExtensionPoint: DataExtensionPoint<Array<PaletteToolOverriddenContributionProps>> = {
  identifier: 'palette#toolOverride',
  fallback: [],
};
