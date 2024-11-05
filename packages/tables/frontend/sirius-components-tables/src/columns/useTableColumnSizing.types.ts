/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import { MRT_ColumnSizingState } from 'material-react-table';

export interface UseTableColumnSizingValue {
  columnSizing: MRT_ColumnSizingState;
  setColumnSizing: (
    columnSizing: MRT_ColumnSizingState | ((prevState: MRT_ColumnSizingState) => MRT_ColumnSizingState)
  ) => void;
}

export interface GQLResizeColumnInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  columnId: string;
  width: number;
}

export interface GQLResizeColumnVariables {
  input: GQLResizeColumnInput;
}

export interface GQLResizeColumnData {
  resizeTableColumn: GQLResizeTableColumnPayload;
}

export type GQLResizeTableColumnPayload = GQLErrorPayload | GQLSuccessPayload;
