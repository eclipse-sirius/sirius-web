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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';

export interface UseInvokeSingleClickContextMenuEntryValue {
  invokeSingleClickContextMenuEntry: (
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
 * @since v2026.3.0
 */
export interface UseInvokeSingleClickContextMenuEntryState {
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
  messages: GQLMessage[];
}
