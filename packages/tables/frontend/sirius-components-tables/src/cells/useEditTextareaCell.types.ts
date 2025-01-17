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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseEditTextareaCellValue {
  editTextareaCell: (newValue: string) => void;
  loading: boolean;
}

export interface GQLEditTextareaCellMutationData {
  editTextareaCell: GQLEditTextareaCellPayload;
}

export interface GQLEditTextareaCellMutationVariables {
  input: GQLEditTextareaCellInput;
}

export interface GQLEditTextareaCellInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  cellId: string;
  newValue: string;
}

export type GQLEditTextareaCellPayload = GQLErrorPayload | GQLSuccessPayload;
