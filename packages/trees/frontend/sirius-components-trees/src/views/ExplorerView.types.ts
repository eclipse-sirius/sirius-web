/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

export interface GQLExplorerEventVariables {
  input: GQLExplorerEventInput;
}

export interface GQLExplorerEventInput {
  id: string;
  treeId: string;
  editingContextId: string;
  expanded: string[];
}

export interface GQLExplorerEventData {
  treeEvent: GQLTreeEventPayload;
}

export interface GQLTreeEventPayload {
  __typename: string;
}

export interface GQLTreeRefreshedEventPayload extends GQLTreeEventPayload {
  id: string;
  tree: GQLTree;
}

export interface GQLTree {
  id: string;
  children: GQLTreeItem[];
}

export interface GQLTreeItem {
  id: string;
  label: string;
  kind: string;
  imageURL: string;
  hasChildren: boolean;
  children: GQLTreeItem[];
  expanded: boolean;
  editable: boolean;
  deletable: boolean;
}

export interface GQLGetTreePathVariables {
  editingContextId: string;
  treeId: string;
  selectionEntryIds: string[];
}

export interface GQLGetTreePathData {
  viewer: GQLGetTreePathViewer;
}

export interface GQLGetTreePathViewer {
  editingContext: GQLGetTreePathEditingContext;
}

export interface GQLGetTreePathEditingContext {
  treePath: GQLTreePath;
}

export interface GQLTreePath {
  treeItemIdsToExpand: string[];
  maxDepth: number;
}

export interface GQLGetExpandAllTreePathVariables {
  editingContextId: string;
  treeId: string;
  treeItemId: string;
}

export interface GQLGetExpandAllTreePathData {
  viewer: GQLGetExpandAllTreePathViewer;
}

export interface GQLGetExpandAllTreePathViewer {
  editingContext: GQLGetExpandAllTreePathEditingContext;
}

export interface GQLGetExpandAllTreePathEditingContext {
  expandAllTreePath: GQLTreePath;
}
