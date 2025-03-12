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

import { RowFilter } from './RowFiltersMenu.types';

export interface UseTableRowFilterValue {
  rowFilters: RowFilter[];
  activeRowFilterIds: string[];
}

export interface UseTableRowFilterState {
  rowFilters: RowFilter[];
  activeRowFilterIds: string[];
}

export interface GQLGetAllTableRowFiltersVariables {
  editingContextId: string;
  representationId: string;
  tableId: string;
}

export interface GQLGetAllTableRowFiltersData {
  viewer: GQLGetAllTableRowFiltersViewer;
}

export interface GQLGetAllTableRowFiltersViewer {
  editingContext: GQLGetAllTableRowFiltersEditingContext;
}

export interface GQLGetAllTableRowFiltersEditingContext {
  representation: GQLGetAllTableRowFiltersRepresentation;
}

export interface GQLGetAllTableRowFiltersRepresentation {
  description: GQLTableRowDescription;
}

export interface GQLTableRowDescription {
  id: string;
  rowFilters: GQLTableRowFilter[];
}

export interface GQLTableRowFilter {
  id: string;
  label: string;
  defaultState: boolean;
}
