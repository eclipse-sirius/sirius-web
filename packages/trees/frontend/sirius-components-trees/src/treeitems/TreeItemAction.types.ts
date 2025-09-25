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
import { GQLTreeItem } from '../views/TreeView.types';

export interface TreeItemActionProps {
  editingContextId: string;
  treeId: string;
  item: GQLTreeItem;
  depth: number;
  expanded: string[];
  selectedTreeItemIds: string[];
  maxDepth: number;
  onExpandedElementChange: (expanded: string[], maxDepth: number) => void;
  onEnterEditingMode: () => void;
  readOnly: boolean;
  isHovered: boolean;
}

export interface TreeItemActionState {
  showContextMenu: boolean;
  menuAnchor: Element | null;
}
