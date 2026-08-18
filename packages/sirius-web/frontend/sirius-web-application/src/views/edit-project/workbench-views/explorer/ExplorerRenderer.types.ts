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
import { GQLTree, GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';

export interface ExplorerRendererState {
  filterBar: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
}

export interface ExplorerRenderedProps {
  editingContextId: string;
  readOnly: boolean;
  tree: GQLTree | null;
  target: HTMLDivElement | null;
  selectedTreeItem: GQLTreeItem;
  selectedTreeItemIds: string[];
  expanded: string[];
  maxDepth: number;
  onExpandedElementChange: (newExpandedIds: string[], newMaxDepth: number) => void;
  onTreeItemClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => void;
  selectTreeItems: (selectedTreeItemIds: string[]) => void;
}
