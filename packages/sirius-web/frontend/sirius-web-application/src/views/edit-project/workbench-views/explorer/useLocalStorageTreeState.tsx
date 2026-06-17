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

import { useState } from 'react';
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

export const useLocalStorageTreeState = (
  editingContextId: string,
  initialActiveTreeDescriptionId: string,
  explorerDescriptions: TreeDescriptionMetadata[]
): UseTreeStateLocalStorageValue => {
  const localStorageKey: string = `explorer_${editingContextId}`;

  const persistState = (state: UseTreeStateLocalStorageState): void => {
    if (isLocalStorageAvailable()) {
      localStorage.setItem(localStorageKey, JSON.stringify(state));
    }
  };

  const getInitialValue = (initialState: UseTreeStateLocalStorageState): UseTreeStateLocalStorageState => {
    if (isLocalStorageAvailable()) {
      const storedValues = localStorage.getItem(localStorageKey);
      if (storedValues) {
        return JSON.parse(storedValues);
      } else {
        persistState(initialState);
        return initialState;
      }
    }
    return initialState;
  };

  const [state, setState] = useState<UseTreeStateLocalStorageState>(
    getInitialValue({
      activeTreeDescriptionId: initialActiveTreeDescriptionId,
      expanded: Object.fromEntries(explorerDescriptions.map((explorerDescription) => [explorerDescription.id, []])),
      maxDepth: Object.fromEntries(explorerDescriptions.map((explorerDescription) => [explorerDescription.id, 1])),
    })
  );

  const setActiveDescriptionId = (activeTreeDescriptionId: string) => {
    setState((prevState) => {
      // The description available can change if for example we add a document in a studio
      const hasEntry = !!prevState.expanded[activeTreeDescriptionId];

      let newState = { ...prevState, activeTreeDescriptionId };
      if (!prevState.expanded[activeTreeDescriptionId]) {
        newState = {
          ...prevState,
          activeTreeDescriptionId,
          expanded: hasEntry ? prevState.expanded : { ...prevState.expanded, [activeTreeDescriptionId]: [] },
          maxDepth: hasEntry ? prevState.maxDepth : { ...prevState.maxDepth, [activeTreeDescriptionId]: 1 },
        };
      }
      persistState(newState);
      return newState;
    });
  };

  const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number) => {
    setState((prevState) => {
      const newState = {
        ...prevState,
        expanded: {
          ...prevState.expanded,
          [state.activeTreeDescriptionId]: newExpandedIds,
        },
        maxDepth: {
          ...prevState.maxDepth,
          [state.activeTreeDescriptionId]: Math.max(newMaxDepth, prevState.maxDepth[state.activeTreeDescriptionId]),
        },
      };
      persistState(newState);
      return newState;
    });
  };

  return {
    activeTreeDescriptionId: state.activeTreeDescriptionId,
    expanded: state.expanded[state.activeTreeDescriptionId],
    maxDepth: state.maxDepth[state.activeTreeDescriptionId],
    setActiveDescriptionId,
    onExpandedElementChange,
  };
};
