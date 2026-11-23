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
import { UseLocalStorageTableStateState, UseLocalStorageTableStateValue } from './useLocalStorageTableState.types';

const getStoredState = (localStorageKey: string) => {
  try {
    const storedValue = localStorage.getItem(localStorageKey);
    if (storedValue !== null) {
      const storedState: unknown = JSON.parse(storedValue);
      if (typeof storedState === 'object' && storedState !== null && !Array.isArray(storedState)) {
        const candidate = storedState as Record<string, unknown>;
        if (
          Array.isArray(candidate.expanded) &&
          candidate.expanded.every((id: unknown) => typeof id === 'string') &&
          typeof candidate.expandAll === 'boolean'
        ) {
          return { expanded: [...new Set(candidate.expanded as string[])], expandAll: candidate.expandAll };
        }
      }
    }
  } catch (_) {
    return null;
  }
  return null;
};

export const useLocalStorageTableState = (
  editingContextId: string,
  representationId: string
): UseLocalStorageTableStateValue => {
  const localStorageKey = `sirius-table-interaction-state-${editingContextId}-${representationId}`;
  const [state, setState] = useState<UseLocalStorageTableStateState | null>(null);

  useEffect(() => {
    const storedState = getStoredState(localStorageKey);
    setState({
      loadedStateKey: localStorageKey,
      expanded: storedState?.expanded ?? [],
      expandAll: storedState?.expandAll ?? false,
    });
  }, [localStorageKey]);

  useEffect(() => {
    if (state !== null && state.loadedStateKey === localStorageKey) {
      try {
        const { expanded, expandAll } = state;
        localStorage.setItem(localStorageKey, JSON.stringify({ expanded, expandAll }));
      } catch (_) {
        // Keep the expansion state in memory when local storage is unavailable.
      }
    }
  }, [state, localStorageKey]);

  const onExpandedElementChange = (rowId: string, expandableRowIds: string[]): void => {
    setState((previousState) => {
      if (previousState === null || previousState.loadedStateKey !== localStorageKey) {
        return previousState;
      }
      if (previousState.expandAll) {
        return { ...previousState, expandAll: false, expanded: expandableRowIds.filter((id) => id !== rowId) };
      }
      const expanded = previousState.expanded.includes(rowId)
        ? previousState.expanded.filter((id) => id !== rowId)
        : [...previousState.expanded, rowId];
      return { ...previousState, expanded };
    });
  };

  const onExpandAllChange = (): void => {
    setState((previousState) => {
      if (previousState === null || previousState.loadedStateKey !== localStorageKey) {
        return previousState;
      }
      return {
        ...previousState,
        expandAll: !previousState.expandAll,
        expanded: previousState.expandAll ? [] : previousState.expanded,
      };
    });
  };

  const ready = state !== null && state.loadedStateKey === localStorageKey;
  return {
    expanded: ready ? state.expanded : [],
    expandAll: ready ? state.expandAll : false,
    onExpandedElementChange,
    onExpandAllChange,
  };
};
