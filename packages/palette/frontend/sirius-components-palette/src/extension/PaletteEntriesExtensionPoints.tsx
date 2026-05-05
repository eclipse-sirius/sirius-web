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
import { PaletteEntriesContributionProps } from './PaletteEntriesContribution.types';

/**
 * Extension point for palette entries.
 *
 * This extension point allows the addition of tools or tool sections to the palette.
 *
 * @since v2026.5.0
 */
export const paletteEntriesExtensionPoint: DataExtensionPoint<Array<PaletteEntriesContributionProps>> = {
  identifier: 'palette#entries',
  fallback: [],
};
