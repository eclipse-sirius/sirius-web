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

import { GQLStyledString } from '@eclipse-sirius/sirius-components-core';
import { TreeItemActionProps } from '../treeitems/TreeItemAction.types';

export interface TreeViewProps {
  editingContextId: string;
  readOnly: boolean;
  tree: GQLTree;
  textToHighlight: string | null;
  textToFilter: string | null;
  markedItemIds?: string[];
  treeItemActionRender?: (props: TreeItemActionProps) => React.ReactNode;
  onExpandedElementChange: (expanded: string[], maxDepth: number) => void;
  expanded: string[];
  maxDepth: number;
  onTreeItemClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, item: GQLTreeItem) => void;
  selectTreeItems: (selectedTreeItemIds: string[]) => void;
  selectedTreeItemIds: string[];
  'data-testid'?: string;
}

export interface TreeConverter {
  convert(editingContextId: string, tree: GQLTree): GQLTree;
}

export interface GQLTree {
  id: string;
  children: GQLTreeItem[];
}

export interface GQLTreeItem {
  id: string;
  label: GQLStyledString;
  kind: string;
  iconURL: [string];
  hasChildren: boolean;
  children: GQLTreeItem[];
  expanded: boolean;
  editable: boolean;
  deletable: boolean;
  selectable: boolean;
}
