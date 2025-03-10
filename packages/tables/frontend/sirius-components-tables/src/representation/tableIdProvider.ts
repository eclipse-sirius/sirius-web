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

import { ColumnFilter } from '../table/TableContent.types';

export const tableIdProvider = (
  tableId: string,
  cursor: string | null,
  direction: 'PREV' | 'NEXT',
  size: number,
  globalFilter: string | null,
  columnFilters: ColumnFilter[] | null,
  expanded: string[]
) => {
  const globalFilterParam: string = globalFilter !== null ? `&globalFilter=${encodeURIComponent(globalFilter)}` : '';
  const columnFiltersParam: string =
    columnFilters !== null
      ? `&columnFilters=[${columnFilters
          .map((filter) => {
            return filter.id + ':' + JSON.stringify(filter.value);
          })
          .map(encodeURIComponent)
          .join(',')}]`
      : '';
  const expandIds: string = `&expandedIds=[${expanded.map(encodeURIComponent).join(',')}]`;
  return `${tableId}?cursor=${
    cursor ? encodeURIComponent(cursor) : cursor
  }&direction=${direction}&size=${size}${globalFilterParam}${columnFiltersParam}${expandIds}`;
};
