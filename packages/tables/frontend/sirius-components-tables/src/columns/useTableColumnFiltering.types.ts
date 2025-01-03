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
import { MRT_ColumnFiltersState } from 'material-react-table';

export type ColumnFilterSetter = (
  columnFilters: MRT_ColumnFiltersState | ((prevState: MRT_ColumnFiltersState) => MRT_ColumnFiltersState)
) => void;

export interface UseTableColumnFilteringValue {
  columnFilters: MRT_ColumnFiltersState;
  setColumnFilters: ColumnFilterSetter | undefined;
}

export interface GQLChangeColumnFilterInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  columnFilters: GQLColumnFilter[];
}

export interface GQLColumnFilter {
  id: string;
  value: unknown;
}

export interface GQLChangeColumnFilterVariables {
  input: GQLChangeColumnFilterInput;
}

export interface GQLChangeColumnFilterData {
  changeColumnFilter: GQLChangeColumnFilterPayload;
}

export type GQLChangeColumnFilterPayload = GQLErrorPayload | GQLSuccessPayload;
