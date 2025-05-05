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
import { ColumnFilter, ColumnSort } from '../table/TableContent.types';

export interface UseTableConfigurationValue {
  globalFilter: string | null;
  columnFilters: ColumnFilter[] | null;
  columnSort: ColumnSort[] | null;
  defaultPageSize: number | null;
}

export interface GQLGetTableConfigurationVariables {
  editingContextId: string;
  representationId: string;
  tableId: string;
}

export interface GQLGetTableConfigurationData {
  viewer: GQLGetTableConfigurationViewer;
}

export interface GQLGetTableConfigurationViewer {
  editingContext: GQLGetTableConfigurationEditingContext;
}

export interface GQLGetTableConfigurationEditingContext {
  representation: GQLGetTableConfigurationRepresentation;
}

export interface GQLGetTableConfigurationRepresentation {
  configuration: GQLGetTableConfigurationConfiguration;
}

export interface GQLGetTableConfigurationConfiguration {
  globalFilter: string;
  columnFilters: GQLColumnFilter[];
  columnSort: GQLColumnSort[];
  defaultPageSize: number;
}

export interface GQLColumnFilter {
  id: string;
  value: unknown;
}

export interface GQLColumnSort {
  id: string;
  desc: boolean;
}
