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
import { GQLTreeItem } from '../../views/TreeView.types';
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';

export interface TreeItemContextMenuComponentProps {
  editingContextId: string;
  treeId: string;
  item: GQLTreeItem;
  entry: GQLTreeItemContextMenuEntry | null;
  readOnly: boolean;
  expandItem: () => void;
  onExpandedElementChange: (expanded: string[], maxDepth: number) => void;
  onClose: () => void;
  key: string;
  expanded: string[];
  maxDepth: number;
}
