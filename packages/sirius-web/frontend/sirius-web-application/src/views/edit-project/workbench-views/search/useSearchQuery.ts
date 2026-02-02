/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { useEffect, useState } from 'react';
import { SearchQuery, UseSearchQueryValue } from './useSearchQuery.types';

export const EMPTY_QUERY: SearchQuery = {
  text: '',
  matchCase: false,
  matchWholeWord: false,
  useRegularExpression: false,
  searchInAttributes: false,
  searchInLibraries: false,
};

export const useSearchQuery = (editingContextId: string, initialQuery: SearchQuery | null): UseSearchQueryValue => {
  const sessionStorageKey: string = `search_view_query#${editingContextId}`;
  const defaultValue: SearchQuery = getDefaultValue(initialQuery, sessionStorageKey);
  const [searchQuery, setSearchQuery] = useState<SearchQuery>(defaultValue);

  useEffect(() => {
    if (isSessionStorageAvailable()) {
      window.sessionStorage.setItem(sessionStorageKey, searchQuery ? JSON.stringify(searchQuery) : '');
    }
  }, [searchQuery]);

  const onSearchQueryChange = (newQuery: SearchQuery) => {
    setSearchQuery(newQuery);
  };

  return {
    searchQuery,
    onSearchQueryChange,
  };
};

const getDefaultValue = (initialQuery: SearchQuery | null, sessionStorageKey: string): SearchQuery => {
  let result: SearchQuery = EMPTY_QUERY;
  if (initialQuery) {
    result = initialQuery;
  } else if (isSessionStorageAvailable()) {
    const rawItem = window.sessionStorage.getItem(sessionStorageKey);
    if (rawItem) {
      try {
        const storedQuery = JSON.parse(rawItem) as SearchQuery;
        result = { ...EMPTY_QUERY, ...storedQuery };
      } catch (error) {
        console.error('Error parsing stored search query from session storage:', error);
      }
    }
  }
  return result;
};

const isSessionStorageAvailable = (): boolean => {
  let isSessionStorageAvailable: boolean = false;

  if (
    window.sessionStorage &&
    window.sessionStorage.setItem &&
    window.sessionStorage.getItem &&
    window.sessionStorage.removeItem
  ) {
    try {
      window.sessionStorage.setItem('session_storage_availability', 'value');
      window.sessionStorage.getItem('session_storage_availability');
      window.sessionStorage.removeItem('session_storage_availability');
      isSessionStorageAvailable = true;
    } catch (error) {
      isSessionStorageAvailable = false;
    }
  }

  return isSessionStorageAvailable;
};
