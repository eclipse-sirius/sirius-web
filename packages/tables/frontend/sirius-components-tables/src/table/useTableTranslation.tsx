/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { MRT_Localization } from 'material-react-table';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

/**
 * Hook providing translation for Material React Table.
 *
 * @author tgiraudet
 */
export const useTableTranslation = (): Partial<MRT_Localization> => {
  const { t } = useTranslation('sirius-components-tables', { keyPrefix: 'useTableTranslation' });

  return useMemo(
    () => ({
      actions: t('actions'),
      and: t('and'),
      cancel: t('cancel'),
      changeFilterMode: t('changeFilterMode'),
      changeSearchMode: t('changeSearchMode'),
      clearFilter: t('clearFilter'),
      clearSearch: t('clearSearch'),
      clearSelection: t('clearSelection'),
      clearSort: t('clearSort'),
      clickToCopy: t('clickToCopy'),
      collapse: t('collapse'),
      collapseAll: t('collapseAll'),
      columnActions: t('columnActions'),
      copiedToClipboard: t('copiedToClipboard'),
      copy: t('copy'),
      dropToGroupBy: t('dropToGroupBy'),
      edit: t('edit'),
      expand: t('expand'),
      expandAll: t('expandAll'),
      filterArrIncludes: t('filterArrIncludes'),
      filterArrIncludesAll: t('filterArrIncludesAll'),
      filterArrIncludesSome: t('filterArrIncludesSome'),
      filterBetween: t('filterBetween'),
      filterBetweenInclusive: t('filterBetweenInclusive'),
      filterByColumn: t('filterByColumn'),
      filterContains: t('filterContains'),
      filterEmpty: t('filterEmpty'),
      filterEndsWith: t('filterEndsWith'),
      filterEquals: t('filterEquals'),
      filterEqualsString: t('filterEqualsString'),
      filterFuzzy: t('filterFuzzy'),
      filterGreaterThan: t('filterGreaterThan'),
      filterGreaterThanOrEqualTo: t('filterGreaterThanOrEqualTo'),
      filterIncludesString: t('filterIncludesString'),
      filterIncludesStringSensitive: t('filterIncludesStringSensitive'),
      filteringByColumn: t('filteringByColumn'),
      filterInNumberRange: t('filterInNumberRange'),
      filterLessThan: t('filterLessThan'),
      filterLessThanOrEqualTo: t('filterLessThanOrEqualTo'),
      filterMode: t('filterMode'),
      filterNotEmpty: t('filterNotEmpty'),
      filterNotEquals: t('filterNotEquals'),
      filterStartsWith: t('filterStartsWith'),
      filterWeakEquals: t('filterWeakEquals'),
      goToFirstPage: t('goToFirstPage'),
      goToLastPage: t('goToLastPage'),
      goToNextPage: t('goToNextPage'),
      goToPreviousPage: t('goToPreviousPage'),
      grab: t('grab'),
      groupByColumn: t('groupByColumn'),
      groupedBy: t('groupedBy'),
      hideAll: t('hideAll'),
      hideColumn: t('hideColumn'),
      max: t('max'),
      min: t('min'),
      move: t('move'),
      noRecordsToDisplay: t('noRecordsToDisplay'),
      noResultsFound: t('noResultsFound'),
      of: t('of'),
      or: t('or'),
      pin: t('pin'),
      pinToLeft: t('pinToLeft'),
      pinToRight: t('pinToRight'),
      resetColumnSize: t('resetColumnSize'),
      resetOrder: t('resetOrder'),
      rowActions: t('rowActions'),
      rowNumber: t('rowNumber'),
      rowNumbers: t('rowNumbers'),
      rowsPerPage: t('rowsPerPage'),
      save: t('save'),
      search: t('search'),
      select: t('select'),
      selectedCountOfRowCountRowsSelected: t('selectedCountOfRowCountRowsSelected'),
      showAll: t('showAll'),
      showAllColumns: t('showAllColumns'),
      showHideColumns: t('showHideColumns'),
      showHideFilters: t('showHideFilters'),
      showHideSearch: t('showHideSearch'),
      sortByColumnAsc: t('sortByColumnAsc'),
      sortByColumnDesc: t('sortByColumnDesc'),
      sortedByColumnAsc: t('sortedByColumnAsc'),
      sortedByColumnDesc: t('sortedByColumnDesc'),
      thenBy: t('thenBy'),
      toggleDensity: t('toggleDensity'),
      toggleFullScreen: t('toggleFullScreen'),
      toggleSelectAll: t('toggleSelectAll'),
      toggleSelectRow: t('toggleSelectRow'),
      toggleVisibility: t('toggleVisibility'),
      ungroupByColumn: t('ungroupByColumn'),
      unpin: t('unpin'),
      unpinAll: t('unpinAll'),
    }),
    [t]
  );
};
