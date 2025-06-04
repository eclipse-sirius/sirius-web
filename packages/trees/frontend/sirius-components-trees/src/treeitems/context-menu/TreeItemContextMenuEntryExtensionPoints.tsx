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

import { ComponentExtensionPoint, DataExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from './TreeItemContextMenuEntry.types';
import { TreeItemContextMenuOverrideContribution } from './TreeItemContextMenuEntryExtensionPoints.types';

/**
 * Extension point for tree item context menu entries.
 *
 * This extension point allows the addition of custom context menu entries to tree items.
 * Each contribution can define how a context menu entry should be rendered or behave.
 *
 * @since v2024.9.0
 */
export const treeItemContextMenuEntryExtensionPoint: ComponentExtensionPoint<TreeItemContextMenuComponentProps> = {
  identifier: 'treeItem#contextMenuEntry',
  FallbackComponent: () => null,
};

/**
 * Extension point for overriding tree item context menu entries retrieved from the server.
 *
 * This extension point allows the modification of the react component used to render a tree item
 * context menu entry retrieved from the server.
 *
 * @since v2025.4.0
 */
export const treeItemContextMenuEntryOverrideExtensionPoint: DataExtensionPoint<
  Array<TreeItemContextMenuOverrideContribution>
> = {
  identifier: 'treeItem#contextMenuEntryOverride',
  fallback: [],
};
