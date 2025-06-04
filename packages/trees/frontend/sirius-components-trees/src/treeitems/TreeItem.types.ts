/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { TreeItemActionProps } from './TreeItemAction.types';

export interface TreeItemProps {
  editingContextId: string;
  treeId: string;
  item: GQLTreeItem;
  itemIndex: number;
  depth: number;
  expanded: string[];
  maxDepth: number;
  readOnly: boolean;
  textToHighlight: string | null;
  textToFilter: string | null;
  marked: boolean;
  selected: boolean;
  onExpandedElementChange: (expanded: string[], maxDepth: number) => void;
  onTreeItemClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, item: GQLTreeItem) => void;
  onDragStart: React.DragEventHandler<HTMLDivElement>;
  treeItemActionRender?: (props: TreeItemActionProps) => React.ReactNode;
}

export interface TreeItemState {
  editingMode: boolean;
  editingKey: string | null;
  partHovered: PartHovered | null;
}

export type PartHovered = 'before' | 'item';
