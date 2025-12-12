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

import { GQLTree, GQLTreeItem } from '../views/TreeView.types';

export interface UseTreeSelectionValue {
  treeItemClick: (
    event: React.MouseEvent<HTMLDivElement, MouseEvent>,
    tree: GQLTree,
    item: GQLTreeItem,
    selectedElements: string[],
    allowMultiSelection: boolean
  ) => TreeItemClickResult;
}

export interface TreeItemClickResult {
  selectedTreeItemIds: string[];
  singleTreeItemSelected: GQLTreeItem | null;
}

export interface ComputeSelectedElementsInRangeResult {
  selectedElements: string[];
  isSelecting: boolean;
}
