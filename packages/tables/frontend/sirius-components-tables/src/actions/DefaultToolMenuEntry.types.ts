/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { GQLTableToolMenuEntry } from './useToolMenuEntries.types';

export interface DefaultToolMenuEntryProps {
  editingContextId: string;
  representationId: string;
  tableId: string;
  entry: GQLTableToolMenuEntry;
  readOnly: boolean;
}

export interface GQLInvokeToolMenuEntryData {
  invokeToolMenuEntry: GQLInvokeToolMenuEntryPayload;
}

export interface GQLInvokeToolMenuEntryVariables {
  input: GQLInvokeToolMenuEntryInput;
}

export interface GQLInvokeToolMenuEntryInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  menuEntryId: string;
}

export interface GQLInvokeToolMenuEntryPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLInvokeToolMenuEntryPayload {
  id: string;
}

export interface GQLErrorPayload extends GQLInvokeToolMenuEntryPayload {
  message: string;
}
