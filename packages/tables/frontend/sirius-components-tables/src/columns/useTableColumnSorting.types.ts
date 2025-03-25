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
import { MRT_SortingState } from 'material-react-table';

export type ColumnSortSetter = (
  columnSort: MRT_SortingState | ((prevState: MRT_SortingState) => MRT_SortingState)
) => void;

export interface UseTableColumnSortingValue {
  sorting: MRT_SortingState;
  setSorting: ColumnSortSetter | undefined;
}

export interface GQLChangeColumnSortInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  columnSort: GQLColumnSort[];
}

export interface GQLColumnSort {
  id: string;
  desc: boolean;
}

export interface GQLChangeColumnSortVariables {
  input: GQLChangeColumnSortInput;
}

export interface GQLChangeColumnSortData {
  changeColumnSort: GQLChangeColumnSortPayload;
}

export type GQLChangeColumnSortPayload = GQLErrorPayload | GQLSuccessPayload;
