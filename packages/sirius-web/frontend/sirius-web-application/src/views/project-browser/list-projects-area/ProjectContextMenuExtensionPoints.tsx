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
import { ProjectContextMenuContainerProps, ProjectContextMenuEntryProps } from './ProjectActionButton.types';

const FallbackProjectContextMenuContainer = ({ children }: ProjectContextMenuContainerProps) => {
  return <div>{children}</div>;
};

/**
 * Extension point for the project context menu container.
 *
 * This extension point allows the contribution of a custom container for the project context menu.
 * A contribution can thus define how the menu should be structured and rendered. It can be used to
 * create a custom context for the entire menu.
 *
 * @since v2024.11.0
 */
export const projectContextMenuContainerExtensionPoint: ComponentExtensionPoint<ProjectContextMenuContainerProps> = {
  identifier: 'projectContextMenu#container',
  FallbackComponent: FallbackProjectContextMenuContainer,
};

/**
 * Extension point for the project context menu entry.
 *
 * This extension point allows the contribution of a custom entry in the project context menu.
 * A contribution can thus define how the entry should be rendered and behave.
 *
 * @since v2024.11.0
 */
export const projectContextMenuEntryExtensionPoint: ComponentExtensionPoint<ProjectContextMenuEntryProps> = {
  identifier: 'projectContextMenu#entry',
  FallbackComponent: () => null,
};
