/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { GQLTree, GQLTreeItem, TreeFilter } from '@eclipse-sirius/sirius-components-trees';

export interface ExplorerViewState {
  synchronizedWithSelection: boolean;
  filterBar: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
  treeFilters: TreeFilter[];
  activeTreeDescriptionId: string | null;
  expanded: { [key: string]: string[] };
  maxDepth: { [key: string]: number };
  tree: GQLTree | null;
  singleTreeItemSelected: GQLTreeItem | null;
}
