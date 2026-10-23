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
import { TreeDescriptionMetadata } from './TreeDescriptionsMenu.types';
import { UseTreeStateLocalStorageState, UseTreeStateLocalStorageValue } from './useLocalStorageTreeState.types';

const isLocalStorageAvailable = (): boolean => {
  const test = 'localStorageTest';
  try {
    localStorage.setItem(test, test);
    localStorage.removeItem(test);
    return true;
  } catch (_) {
    return false;
  }
};

const getStoredState = (localStorageKey: string): UseTreeStateLocalStorageState | null => {
  if (!isLocalStorageAvailable()) {
    return null;
  }

  try {
    const storedValues = localStorage.getItem(localStorageKey);
    if (storedValues) {
      const storedState = JSON.parse(storedValues) as UseTreeStateLocalStorageState;
      if (
        typeof storedState.activeTreeDescriptionId === 'string' &&
        typeof storedState.expanded === 'object' &&
        storedState.expanded !== null &&
        typeof storedState.maxDepth === 'object' &&
        storedState.maxDepth !== null
      ) {
        return storedState;
      }
    }
  } catch (_) {
    return null;
  }
  return null;
};

export const useLocalStorageTreeState = (
  editingContextId: string,
  configuredActiveTreeDescriptionId: string | null,
  explorerDescriptions: TreeDescriptionMetadata[],
  descriptionsLoading: boolean
): UseTreeStateLocalStorageValue => {
  const localStorageKey: string = `sirius-tree-interaction-state-${editingContextId}`;
  const descriptionIdsKey = explorerDescriptions.map((description) => description.id).join('\u0000');
  const stateKey = `${localStorageKey}:${descriptionIdsKey}`;
  const [state, setState] = useState<UseTreeStateLocalStorageState | null>(null);

  const persistState = (newState: UseTreeStateLocalStorageState): void => {
    if (isLocalStorageAvailable()) {
      const { activeTreeDescriptionId, expanded, maxDepth } = newState;
      localStorage.setItem(localStorageKey, JSON.stringify({ activeTreeDescriptionId, expanded, maxDepth }));
    }
  };

  useEffect(() => {
    if (descriptionsLoading || explorerDescriptions.length === 0) {
      setState(null);
      return;
    }

    const descriptionIds = explorerDescriptions.map((description) => description.id);
    const initialExpanded = Object.fromEntries(descriptionIds.map((id) => [id, []]));
    const initialMaxDepth = Object.fromEntries(descriptionIds.map((id) => [id, 1]));
    const storedState = getStoredState(localStorageKey);
    let activeTreeDescriptionId = descriptionIds[0];
    if (storedState && descriptionIds.includes(storedState.activeTreeDescriptionId)) {
      activeTreeDescriptionId = storedState.activeTreeDescriptionId;
    } else if (configuredActiveTreeDescriptionId && descriptionIds.includes(configuredActiveTreeDescriptionId)) {
      activeTreeDescriptionId = configuredActiveTreeDescriptionId;
    }

    const expanded = {
      ...initialExpanded,
      ...(storedState?.expanded ?? {}),
    };
    const maxDepth = {
      ...initialMaxDepth,
      ...(storedState?.maxDepth ?? {}),
    };
    const initializedState: UseTreeStateLocalStorageState = {
      loadedStateKey: stateKey,
      activeTreeDescriptionId,
      expanded,
      maxDepth,
    };
    setState(initializedState);
    persistState(initializedState);
  }, [descriptionsLoading, explorerDescriptions, configuredActiveTreeDescriptionId, localStorageKey, stateKey]);

  const setActiveDescriptionId = (activeTreeDescriptionId: string): void => {
    setState((previousState) => {
      if (!previousState || !explorerDescriptions.some((description) => description.id === activeTreeDescriptionId)) {
        return previousState;
      }

      const newState: UseTreeStateLocalStorageState = {
        ...previousState,
        activeTreeDescriptionId,
        expanded: {
          ...previousState.expanded,
          [activeTreeDescriptionId]: previousState.expanded[activeTreeDescriptionId] ?? [],
        },
        maxDepth: {
          ...previousState.maxDepth,
          [activeTreeDescriptionId]: previousState.maxDepth[activeTreeDescriptionId] ?? 1,
        },
      };
      persistState(newState);
      return newState;
    });
  };

  const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number): void => {
    setState((previousState) => {
      if (!previousState) {
        return previousState;
      }

      const activeTreeDescriptionId = previousState.activeTreeDescriptionId;
      const newState: UseTreeStateLocalStorageState = {
        ...previousState,
        expanded: {
          ...previousState.expanded,
          [activeTreeDescriptionId]: newExpandedIds,
        },
        maxDepth: {
          ...previousState.maxDepth,
          [activeTreeDescriptionId]: Math.max(newMaxDepth, previousState.maxDepth[activeTreeDescriptionId] ?? 1),
        },
      };
      persistState(newState);
      return newState;
    });
  };

  const ready = state !== null && state.loadedStateKey === stateKey && !descriptionsLoading;

  return {
    activeTreeDescriptionId: ready ? state.activeTreeDescriptionId : null,
    expanded: ready ? state.expanded[state.activeTreeDescriptionId] ?? [] : [],
    maxDepth: ready ? state.maxDepth[state.activeTreeDescriptionId] ?? 1 : 1,
    ready,
    setActiveDescriptionId,
    onExpandedElementChange,
  };
};
