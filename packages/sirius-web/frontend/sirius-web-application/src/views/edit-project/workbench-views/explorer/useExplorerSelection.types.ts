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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLTree, GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';

export interface UseExplorerSelectionValue {
  selectedTreeItemIds: string[];
  singleTreeItemSelected: GQLTreeItem | null;
  setSelectedTreeItemIds: (selectedTreeItemIds: string[]) => void;
  onRevealSelection: () => void;
  onTreeItemClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => void;
  applySelection: (selection: Selection) => void;
}

export interface UseExplorerSelectionState {
  selectedTreeItemIds: string[];
  singleTreeItemSelected: GQLTreeItem | null;
}
