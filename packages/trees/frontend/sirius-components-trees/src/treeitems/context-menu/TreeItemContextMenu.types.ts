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

export interface FetchTreeItemContextMenuEntry {
  urlToFetch: string;
  fetchKind: GQLTreeItemFetchKind;
}

export interface GQLInvokeSingleClickTreeItemContextMenuEntryData {
  invokeSingleClickTreeItemContextMenuEntry: GQLInvokeSingleClickTreeItemContextMenuEntryPayload;
}

export interface GQLInvokeSingleClickTreeItemContextMenuEntryVariables {
  input: GQLInvokeSingleClickTreeItemContextMenuEntryInput;
}

export interface GQLInvokeSingleClickTreeItemContextMenuEntryInput {
  id: string;
  editingContextId: string;
  representationId: string;
  treeItemId: string;
  menuEntryId: string;
}

export interface GQLInvokeSingleClickTreeItemContextMenuEntryPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLInvokeSingleClickTreeItemContextMenuEntryPayload {
  id: string;
}

export interface GQLErrorPayload extends GQLInvokeSingleClickTreeItemContextMenuEntryPayload {
  message: string;
}

export interface GQLGetFetchTreeItemContextMenuEntryDataQueryVariables {
  editingContextId: string;
  representationId: string;
  treeItemId: string;
  menuEntryId: string;
}

export interface GQLFetchTreeItemContextEntryDataData {
  viewer: GQLFetchTreeItemContextMenuEntryDataViewer;
}

export interface GQLFetchTreeItemContextMenuEntryDataViewer {
  editingContext: GQLFetchTreeItemContextMenuEntryDataEditingContext;
}

export interface GQLFetchTreeItemContextMenuEntryDataEditingContext {
  representation: GQLFetchTreeItemContextMenuEntryDataRepresentationMetadata;
}

export interface GQLFetchTreeItemContextMenuEntryDataRepresentationMetadata {
  description: GQLFetchTreeItemContextMenuEntryDataRepresentationDescription;
}

export interface GQLFetchTreeItemContextMenuEntryDataRepresentationDescription {
  fetchTreeItemContextMenuEntryData: GQLFetchTreeItemContextMenuEntryData;
}

export interface GQLFetchTreeItemContextMenuEntryData {
  urlToFetch: string;
  fetchKind: GQLTreeItemFetchKind;
}

export type GQLTreeItemFetchKind = 'DOWNLOAD' | 'OPEN';
