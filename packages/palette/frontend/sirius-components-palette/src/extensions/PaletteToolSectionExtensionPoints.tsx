/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { PaletteToolSectionContributionProps } from './PaletteToolSectionContribution.types';

/**
 * Extension point for palette tools.
 *
 * This extension point allows the addition of custom quick tools to the diagram palette.
 * Each contribution can define how a tool should be rendered and behave.
 *
 * @since v2025.12.0
 */
export const paletteToolSectionExtensionPoint: DataExtensionPoint<Array<PaletteToolSectionContributionProps>> = {
  identifier: 'diagramPalette#toolSection',
  fallback: [],
};
