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

import { ComponentExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { EditProjectNavbarMenuContainerProps, EditProjectNavbarMenuEntryProps } from './EditProjectNavbar.types';

const FallbackEditProjectNavbarMenuContainer = ({ children }: EditProjectNavbarMenuContainerProps) => {
  return children;
};

/**
 * Extension point for the edit project navbar menu container.
 *
 * This extension point allows the contribution of a custom container for the edit project navbar menu.
 * A contribution can thus define how the menu should be structured and rendered. It can be used to
 * create a custom context for the entire menu.
 *
 * @since v2024.11.0
 */
export const editProjectNavbarMenuContainerExtensionPoint: ComponentExtensionPoint<EditProjectNavbarMenuContainerProps> =
  {
    identifier: 'editProjectNavbarMenu#container',
    FallbackComponent: FallbackEditProjectNavbarMenuContainer,
  };

/**
 * Extension point for the edit project navbar menu entry.
 *
 * This extension point allows the contribution of a custom entry in the edit project navbar menu.
 * A contribution can thus define how the entry should be rendered and behave.
 *
 * @since v2024.11.0
 */
export const editProjectNavbarMenuEntryExtensionPoint: ComponentExtensionPoint<EditProjectNavbarMenuEntryProps> = {
  identifier: 'editProjectNavbarMenu#entry',
  FallbackComponent: () => null,
};
