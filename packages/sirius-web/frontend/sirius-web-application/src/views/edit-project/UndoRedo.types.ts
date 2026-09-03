/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

export interface GQLUndoVariables {
  input: GQLUndoInput;
}

export interface GQLRedoVariables {
  input: GQLRedoInput;
}

export interface GQLUndoInput {
  id: string;
  editingContextId: string;
  inputId: string;
}

export interface GQLRedoInput {
  id: string;
  editingContextId: string;
  inputId: string;
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
  id: string;
  messages: GQLMessage[];
}

export interface GQLErrorPayload {
  __typename: 'ErrorPayload';
  id: string;
  messages: GQLMessage[];
}
