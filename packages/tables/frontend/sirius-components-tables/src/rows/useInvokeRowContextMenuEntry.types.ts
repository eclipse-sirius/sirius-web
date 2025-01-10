/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseInvokeRowContextMenuEntryMutationValue {
  invokeRowContextMenuEntry: (menuEntryId: string) => void;
}

export interface GQLInvokeRowContextMenuEntryInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  rowId: string;
  menuEntryId: string;
}

export interface GQLInvokeRowContextMenuEntryVariables {
  input: GQLInvokeRowContextMenuEntryInput;
}

export interface GQLInvokeRowContextMenuEntryData {
  invokeRowContextMenuEntry: GQLInvokeRowContextMenuEntryPayload;
}

export type GQLInvokeRowContextMenuEntryPayload = GQLErrorPayload | GQLSuccessPayload;
