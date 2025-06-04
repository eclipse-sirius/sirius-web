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
import { ProjectFilterCustomizer } from './useProjectsExtensionPoints.types';

/**
 * Extension point for customizing the project filter.
 *
 * This extension point allows the contribution of custom filter when the list of projects will
 * be requested to the backend.
 * A contribution can thus define how the projects should be filtered based on custom criteria.
 *
 * @since v2025.4.0
 */
export const projectFilterCustomizersExtensionPoint: DataExtensionPoint<Array<ProjectFilterCustomizer>> = {
  identifier: 'useProjects#projectFilterCustomizers',
  fallback: [],
};
