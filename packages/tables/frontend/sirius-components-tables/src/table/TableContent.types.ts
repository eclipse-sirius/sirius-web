/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { GQLColumnFilter } from '../columns/useTableColumnFiltering.types';
import { GQLColumnSort } from '../columns/useTableColumnSorting.types';
import { RowFilter } from '../rows/filters/RowFiltersMenu.types';

export interface TableContentProps {
  editingContextId: string;
  representationId: string;
  table: GQLTable;
  readOnly: boolean;
  onPaginationChange: (cursor: string | null, direction: 'PREV' | 'NEXT', size: number | null) => void;
  onGlobalFilterChange: (globalFilter: string) => void;
  onColumnFiltersChange: (columnFilters: ColumnFilter[]) => void;
  onExpandedElementChange: (rowId: string) => void;
  onRowFiltersChange: (activeRowFilterIds: string[]) => void;
  onSortingChange: (columnSort: ColumnSort[]) => void;
  enableColumnVisibility: boolean;
  enableColumnResizing: boolean;
  enableColumnFilters: boolean;
  enableRowSizing: boolean;
  enableGlobalFilter: boolean;
  enablePagination: boolean;
  enableColumnOrdering: boolean;
  enableSelectionSynchronization: boolean;
  pageSize: number;
  expandedRowIds: string[];
  rowFilters: RowFilter[] | null;
  activeRowFilterIds: string[];
  enableSorting: boolean;
}

export interface TablePaginationState {
  cursor: string | null;
  direction: 'PREV' | 'NEXT';
  size: number;
}

export interface GQLTableEventSubscription {
  tableEvent: GQLTableEventPayload;
}

export interface GQLTableEventPayload {
  __typename: string;
}

export interface GQLTableRefreshedEventPayload extends GQLTableEventPayload {
  id: string;
  table: GQLTable;
}

export interface GQLTable {
  id: string;
  targetObjectId: string;
  stripeRow: boolean;
  enableSubRows: boolean;
  pageSizeOptions: number[];
  defaultPageSize: number;
  globalFilter: string | null;
  columns: GQLColumn[];
  lines: GQLLine[];
  paginationData: GQLPaginationData;
  columnFilters: GQLColumnFilter[];
  columnSort: GQLColumnSort[];
}

export interface GQLColumn {
  id: string;
  headerLabel: string;
  headerIconURLs: string[];
  headerIndexLabel: string;
  width: number;
  isResizable: boolean;
  hidden: boolean;
  filterVariant:
    | 'autocomplete'
    | 'checkbox'
    | 'date'
    | 'date-range'
    | 'datetime'
    | 'datetime-range'
    | 'multi-select'
    | 'range'
    | 'range-slider'
    | 'select'
    | 'text'
    | 'time'
    | 'time-range'
    | undefined;
  isSortable: boolean;
}

export interface GQLLine {
  id: string;
  targetObjectId: string;
  targetObjectKind: string;
  cells: GQLCell[];
  headerLabel: string;
  headerIconURLs: string[];
  headerIndexLabel: string;
  height: number;
  isResizable: boolean;
  depthLevel: number;
}

export interface GQLPaginationData {
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  totalRowCount: number;
}

export interface GQLCell {
  __typename: string;
  id: string;
  columnId: string;
  targetObjectId: string;
  targetObjectKind: string;
}

export interface GQLTextfieldCell extends GQLCell {
  stringValue: string;
}

export interface GQLTextareaCell extends GQLCell {
  stringValue: string;
}

export interface GQLIconLabelCell extends GQLCell {
  label: string;
  iconURLs: string[];
}

export interface GQLSelectCell extends GQLCell {
  value: string;
  options: GQLSelectCellOption[];
}

export interface GQLMultiSelectCell extends GQLCell {
  values: string[];
  options: GQLSelectCellOption[];
}

export interface GQLSelectCellOption {
  id: string;
  label: string;
}

export interface ColumnFilter {
  id: string;
  value: unknown;
}

export interface ColumnSort {
  id: string;
  desc: boolean;
}
