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
import { OmniboxCommandOverrideContribution } from './OmniboxExtensionPoints.types';

/**
 * Extension point for omnibox command override contributions.
 *
 * This extension point allows a contribution to replace the rendering of an omnibox command.
 * Each contribution can define how a command should be rendered and can thus trigger the execution
 * of frontend behavior on top of backend behavior (like opening a dialog from the omnibox).
 *
 * @since v2025.2.0
 */
export const omniboxCommandOverrideContributionExtensionPoint: DataExtensionPoint<
  Array<OmniboxCommandOverrideContribution>
> = {
  identifier: 'omnibox#commandOverrideContribution',
  fallback: [],
};
