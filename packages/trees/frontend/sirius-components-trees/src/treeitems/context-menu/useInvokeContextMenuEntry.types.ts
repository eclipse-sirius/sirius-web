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
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';

export interface UseInvokeContextMenuEntryValue {
  invokeContextMenuEntry: (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntry: GQLTreeItemContextMenuEntry,
    onClick: () => void
  ) => void;
}

/**
 * The state used for the execution of a context menu entry on a tree item.
 *
 * @since v2026.1.0
 */
export interface UseInvokeContextMenuEntryState {
  /**
   * The entry that is currently being executed.
   *
   * This entry is kept in the state to be able to give it to the impact analysis dialog
   * when the user clicks on the confirmation button.
   */
  currentEntry: GQLTreeItemContextMenuEntry | null;

  /**
   * The behavior to execute when the entry is invoked.
   *
   * This behavior can be kept in the state in order to be able to execute it later,
   * for example when the user clicks on the confirmation button of the impact analysis dialog.
   */
  onEntryExecution: () => void;

  /**
   * The behavior to execute once the entry has been invoked.
   *
   * This behavior can be kept in the state in order to be able to execute it later,
   * for example when the fetch data associated to the entry have been retrieved.
   */
  onClick: () => void;
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
