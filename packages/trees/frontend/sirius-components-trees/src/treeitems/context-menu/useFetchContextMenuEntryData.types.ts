/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

export interface UseFetchContextMenuEntryDataValue {
  getFetchContextMenuEntryData: (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntryId: string
  ) => void;
  loading: boolean;
  fetchData: GQLFetchTreeItemContextMenuEntryData | null;
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
