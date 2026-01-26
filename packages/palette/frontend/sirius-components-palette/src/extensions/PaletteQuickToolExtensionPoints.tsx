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

import { ComponentExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { PaletteQuickToolComponentProps } from './PaletteQuickToolContribution.types';

/**
 * Extension point for palette quick tools.
 *
 * This extension point allows the addition of custom quick tools to the diagram palette.
 * Each contribution can define how a quick tool should be rendered and behave.
 *
 * @since v2026.7.0
 */
export const paletteQuickToolExtensionPoint: ComponentExtensionPoint<PaletteQuickToolComponentProps> = {
  identifier: 'palette#quickTool',
  FallbackComponent: () => null,
};
