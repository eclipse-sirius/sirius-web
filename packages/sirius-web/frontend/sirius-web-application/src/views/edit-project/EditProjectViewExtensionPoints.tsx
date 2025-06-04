/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { ProjectReadOnlyPredicate } from './EditProjectView.types';

/**
 * Extension point for the read-only predicate in the Edit Project View.
 *
 * This extension point allows contributions to define whether the Edit Project View should be read-only.
 * A contribution can thus define a custom logic to determine if the view is read-only.
 *
 * @since v2024.9.0
 */
export const editProjectViewReadOnlyPredicateExtensionPoint: DataExtensionPoint<ProjectReadOnlyPredicate> = {
  identifier: 'editProjectView#readOnlyPredicate',
  fallback: () => false,
};
