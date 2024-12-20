/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import { MRT_ColumnOrderState } from 'material-react-table';

export interface UseTableColumnOrderingValue {
  columnOrder: MRT_ColumnOrderState;
  setColumnOrder: (
    columnOrder: MRT_ColumnOrderState | ((prevState: MRT_ColumnOrderState) => MRT_ColumnOrderState)
  ) => void;
}

export interface GQLReorderColumnsInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  reorderedColumnIds: string[];
}

export interface GQLReorderColumnsVariables {
  input: GQLReorderColumnsInput;
}

export interface GQLReorderColumnsData {
  reorderTableColumns: GQLReorderTableColumnsPayload;
}

export type GQLReorderTableColumnsPayload = GQLErrorPayload | GQLSuccessPayload;
