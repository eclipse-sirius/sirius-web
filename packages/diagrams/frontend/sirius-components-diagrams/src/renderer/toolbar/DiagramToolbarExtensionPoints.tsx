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
import { DiagramToolbarActionProps } from './DiagramToolbar.types';

/**
 * Extension point for diagram toolbar actions.
 *
 * This extension point allows the addition of custom actions to the diagram toolbar.
 * Each contribution can define how an action should be rendered or behave.
 *
 * @since v2024.7.0
 */
export const diagramToolbarActionExtensionPoint: ComponentExtensionPoint<DiagramToolbarActionProps> = {
  identifier: 'diagramToolbar#action',
  FallbackComponent: () => null,
};
