/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
export interface GQLUndoVariables {
  input: GQLUndoRedoInput;
}

export interface GQLUndoRedoInput {
  id: string;
  editingContextId: string;
  mutationId: string;
}
export interface GQLUndoData {
  undo: GQLUndoRedoItemPayload;
}

export interface GQLRedoData {
  redo: GQLUndoRedoItemPayload;
}

export interface GQLUndoRedoItemPayload {
  __typename: string;
}

export interface GQLSuccessPayload {
  __typename: 'SuccessPayload';
}
