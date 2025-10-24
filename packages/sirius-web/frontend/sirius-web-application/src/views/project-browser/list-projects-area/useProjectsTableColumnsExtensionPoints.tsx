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
import { ProjectsTableColumnCustomizer } from './useProjectsTableColumnsExtensionPoints.types';

/**
 * Extension point for customizing the columns of the projects table.
 *
 * This extension point allows contributions to customize the columns displayed in the projects table.
 * Each contribution can define how a specific column should be rendered and behave.
 *
 * @since v2025.4.0
 */
export const projectsTableColumnCustomizersExtensionPoint: DataExtensionPoint<Array<ProjectsTableColumnCustomizer>> = {
  identifier: 'useProjectsTableColumn#projectsTableColumnCustomizers',
  fallback: [],
};
