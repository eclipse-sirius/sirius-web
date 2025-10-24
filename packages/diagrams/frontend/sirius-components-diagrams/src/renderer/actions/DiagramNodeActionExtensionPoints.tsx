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
import { DiagramNodeActionOverrideContribution } from './DiagramNodeActionExtensionPoints.types';

/**
 * Extension point for diagram node action override contributions.
 *
 * This extension point allows a contribution to replace the rendering of a diagram node action.
 * Each contribution can define how a node action should be rendered and can thus trigger the execution
 * of frontend behavior on top of backend behavior (like opening a dialog from the node action).
 *
 * @since v2025.6.0
 */
export const diagramNodeActionOverrideContributionExtensionPoint: DataExtensionPoint<
  DiagramNodeActionOverrideContribution[]
> = {
  identifier: 'diagramNodeAction#overrideContribution',
  fallback: [],
};
