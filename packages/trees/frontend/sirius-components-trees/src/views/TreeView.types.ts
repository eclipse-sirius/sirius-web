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

import { GQLStyledString, WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import { TreeConverter } from './TreeConverter.types';

export interface TreeViewComponentProps extends WorkbenchViewComponentProps {
  treeId: string;
  enableMultiSelection: boolean;
  synchronizedWithSelection: boolean;
  textToHighlight: string | null;
  textToFilter: string | null;
  markedItemIds?: string[];
  converter?: TreeConverter;
}

export interface GQLTreeEventVariables {
  input: GQLTreeEventInput;
}

export interface GQLTreeEventInput {
  id: string;
  treeId: string;
  editingContextId: string;
  expanded: string[];
}

export interface GQLTreeEventData {
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
