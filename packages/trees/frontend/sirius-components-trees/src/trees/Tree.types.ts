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
import { TreeItemActionProps } from '../treeitems/TreeItemAction.types';
import { GQLTree, GQLTreeItem } from '../views/TreeView.types';

export interface TreeProps {
  editingContextId: string;
  tree: GQLTree;
  expanded: string[];
  maxDepth: number;
  readOnly: boolean;
  textToHighlight: string | null;
  textToFilter: string | null;
  markedItemIds: string[];
  selectedTreeItemIds: string[];
  children?: React.ReactElement<any, any>;
  onExpandedElementChange: (expanded: string[], maxDepth: number) => void;
  onTreeItemClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, item: GQLTreeItem, selected: boolean) => void;
  onDragStart: React.DragEventHandler<HTMLDivElement>;
  treeItemActionRender?: (props: TreeItemActionProps) => React.ReactNode;
}
