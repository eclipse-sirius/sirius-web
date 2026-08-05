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
import { Selection, WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { GQLTree, GQLTreeItem, TreeFilter } from '@eclipse-sirius/sirius-components-trees';
import { ForwardedRef } from 'react';

export interface ExplorerSelectionContextValue {
  selectedTreeItemIds: string[];
  singleTreeItemSelected: GQLTreeItem | null;
  setSelectedTreeItemIds: (selectedTreeItemIds: string[]) => void;
  onRevealSelection: () => void;
  onTreeItemClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => void;
  applySelection: (selection: Selection) => void;
}

export interface ExplorerSelectionContextState {
  selectedTreeItemIds: string[];
  singleTreeItemSelected: GQLTreeItem | null;
}

export interface ExplorerSelectionContextProviderProps {
  id: string;
  activeTreeDescriptionId: string;
  treeFilters: TreeFilter[];
  editingContextId: string;
  refHandle: ForwardedRef<WorkbenchViewHandle>;
  expanded: string[];
  onExpandedElementChange: (newExpandedIds: string[], newMaxDepth: number) => void;
  children: React.ReactNode;
}
