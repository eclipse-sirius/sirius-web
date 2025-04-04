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
import { GQLTable } from '../table/TableContent.types';
import { GQLColumnFilter } from '../columns/useTableColumnFiltering.types';
import { GQLColumnSort } from '../columns/useTableColumnSorting.types';

export interface UseTableSubscriptionValue {
  loading: boolean;
  table: GQLTable | null;
  complete: boolean;
}

export interface UseTableSubscriptionState {
  id: string;
  table: GQLTable | null;
  complete: boolean;
}

export interface GQLTableEventInput {
  id: string;
  representationId: string;
  editingContextId: string;
}

export interface GQLTableEventVariables {
  input: GQLTableEventInput;
}

export interface GQLTableEventData {
  tableEvent: GQLTableEventPayload;
}

export interface GQLTableEventPayload {
  __typename: string;
}

export interface GQLTableRefreshedEventPayload extends GQLTableEventPayload {
  id: string;
  table: GQLTable;
}

export interface GQLTableGlobalFilterValuePayload extends GQLTableEventPayload {
  id: string;
  globalFilterValue: string;
}

export interface GQLTableColumnFilterPayload extends GQLTableEventPayload {
  id: string;
  columnFilters: GQLColumnFilter[];
}

export interface GQLTableColumnSortPayload extends GQLTableEventPayload {
  id: string;
  columnSort: GQLColumnSort[];
}
