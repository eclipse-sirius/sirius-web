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
import { PaletteAppearanceSectionContributionProps } from './PaletteAppearanceSectionContribution.types';

/**
 * Extension point for palette appearance section.
 *
 * This extension point allows the addition of appearance section to the diagram palette.
 * Each contribution can then be used to contribute appearance section to a custom node.
 *
 * @since v2025.10.0
 */
export const paletteAppearanceSectionExtensionPoint: DataExtensionPoint<
  Array<PaletteAppearanceSectionContributionProps>
> = {
  identifier: 'diagramPalette#appearanceSection',
  fallback: [],
};
