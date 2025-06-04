/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { ProjectSettingTabContribution } from './ProjectSettingsView.types';

/**
 * Extension point for the Project Settings View.
 *
 * This extension point allows contributions to define custom tabs in the Project Settings View.
 * Each contribution can define how a specific tab should be rendered and behave.
 *
 * @since v2024.9.0
 */
export const projectSettingsTabExtensionPoint: DataExtensionPoint<Array<ProjectSettingTabContribution>> = {
  identifier: 'projectSettings#tabContribution',
  fallback: [],
};
