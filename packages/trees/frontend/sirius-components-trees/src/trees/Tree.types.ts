/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
  onExpandAll: (treeItem: GQLTreeItem) => void;
  readOnly: boolean;
  enableMultiSelection: boolean;
  textToHighlight: string | null;
  textToFilter: string | null;
  markedItemIds: string[];
  children?: React.ReactElement<any, any>;
  treeItemActionRender?: (props: TreeItemActionProps) => React.ReactNode;
}
