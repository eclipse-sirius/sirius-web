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
import { ComponentExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { CreateProjectAreaCardProps } from './CreateProjectArea.types';

/**
 * Extension point for the Create Project Area Card.
 *
 * This extension point allows contributions to define a custom card for creating a project.
 * A contribution can thus define how the card should be rendered and behave.
 *
 * @since v2024.7.0
 */
export const createProjectAreaCardExtensionPoint: ComponentExtensionPoint<CreateProjectAreaCardProps> = {
  identifier: 'createProjectArea#card',
  FallbackComponent: () => null,
};
