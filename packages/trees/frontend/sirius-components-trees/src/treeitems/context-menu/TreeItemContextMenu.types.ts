/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
import { GQLTreeItem } from '../../views/TreeView.types';

export interface TreeItemContextMenuProps {
  menuAnchor: Element;
  item: GQLTreeItem;
  editingContextId: string;
  treeId: string;
  readOnly: boolean;
  depth: number;
  expanded: string[];
  maxDepth: number;
  selectedTreeItemIds: string[];
  selectTreeItems: (selectedTreeItemIds: string[]) => void;
  onExpandedElementChange: (expanded: string[], maxDepth: number) => void;
  enterEditingMode: () => void;
  onClose: () => void;
}
