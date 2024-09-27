/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export interface TableProps {
  editingContextId: string;
  representationId: string;
  table: GQLTable;
  readOnly: boolean;
}

export type SortOrder = 'asc' | 'desc';
export interface TableState {
  order: SortOrder;
  orderBy: string;
  page: number;
  rowsPerPage: number;
  selectedRow: string | null;
  globalFilter: string;
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
  columns: GQLColumn[];
  lines: GQLLine[];
}

export interface GQLColumn {
  id: string;
  label: string;
}
export interface ExportData {
  [key: string]: string;
}

export interface GQLLine {
  id: string;
  targetObjectId: string;
  cells: GQLCell[];
}

export interface GQLCell {
  __typename: string;
  id: string;
  columnId: string;
}
export interface GQLTextfieldCell extends GQLCell {
  stringValue: string;
}

export interface GQLCheckboxCell extends GQLCell {
  booleanValue: boolean;
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
