/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { Selection } from 'workbench/Workbench.types';
import { TreeItemType } from './TreeItem.types';
import { TreeItemContextMenuComponentProps } from './TreeItemContextMenuContribution.types';

export interface TreeItemContextMenuProps {
  menuAnchor: Element;
  item: TreeItemType;
  editingContextId: string;
  treeId: string;
  readOnly: boolean;
  treeItemMenuContributionComponents: ((props: TreeItemContextMenuComponentProps) => JSX.Element)[];
  depth: number;
  onExpand: (id: string, depth: number) => void;
  selection: Selection;
  setSelection: (selection: Selection) => void;
  enterEditingMode: () => void;
  onClose: () => void;
}

export interface GQLDeleteTreeItemData {
  deleteTreeItem: GQLDeleteTreeItemPayload;
}
export interface GQLDeleteTreeItemPayload {
  __typename: string;
}

export interface GQLDeleteTreeItemSuccessPayload extends GQLDeleteTreeItemPayload {
  id: string;
}

export interface GQLErrorPayload extends GQLDeleteTreeItemPayload {
  message: string;
}

export interface GQLDeleteTreeItemVariables {
  input: GQLDeleteTreeItemInput;
}

export interface GQLDeleteTreeItemInput {
  id: string;
  editingContextId: string;
  representationId: string;
  treeItemId: string;
}

export interface TreeItemContextMenuState {
  message: string | null;
}
