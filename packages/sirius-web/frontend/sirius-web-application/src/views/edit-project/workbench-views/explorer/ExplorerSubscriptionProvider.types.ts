/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { GQLTree, GQLTreeItem, TreeFilter } from '@eclipse-sirius/sirius-components-trees';
import { ForwardedRef } from 'react';
import { TreeDescriptionMetadata } from './TreeDescriptionsMenu.types';

export interface ExplorerSubscriptionProviderProps {
  id: string;
  explorerRef: ForwardedRef<WorkbenchViewHandle>;
  editingContextId: string;
  explorerDescriptions: TreeDescriptionMetadata[];
  initialActiveTreeDescriptionId: string | null;
  initialFilters: TreeFilter[];
  readOnly: boolean;
}

export interface ExplorerSubscriptionProviderState {
  filterBar: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
  treeFilters: TreeFilter[];
  activeTreeDescriptionId: string | null;
  expanded: { [key: string]: string[] };
  maxDepth: { [key: string]: number };
  tree: GQLTree | null;
  selectedTreeItemIds: string[];
  selectionPivotTreeItemId: string | null;
  singleTreeItemSelected: GQLTreeItem | null;
}
