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

import { TreeFilter, useTreeFilters } from '@eclipse-sirius/sirius-components-trees';
import React, { useContext, useEffect, useState } from 'react';
import {
  ExplorerFilterContextProviderProps,
  ExplorerFilterContextState,
  ExplorerFilterContextValue,
} from './ExplorerFilterContext.types';
import { ExplorerInteractionContext } from './ExplorerInteractionContext';
import { ExplorerInteractionContextValue } from './ExplorerInteractionContext.types';

const defaultValue: ExplorerFilterContextValue = {
  loading: false,
  treeFilters: [],
  setTreeFilters: () => {},
};

const convertGQLTreeFiltersToTreeFilters = (gqlTreeFilter): TreeFilter => {
  return {
    id: gqlTreeFilter.id,
    label: gqlTreeFilter.label,
    state: gqlTreeFilter.defaultState,
  };
};

export const ExplorerFilterContext = React.createContext<ExplorerFilterContextValue>(defaultValue);

export const ExplorerFilterContextProvider = ({
  editingContextId,
  initialTreeFilters,
  children,
}: ExplorerFilterContextProviderProps) => {
  const { activeTreeDescriptionId } = useContext<ExplorerInteractionContextValue>(ExplorerInteractionContext);

  const { loading, treeFilters } = useTreeFilters(editingContextId, activeTreeDescriptionId);

  const [state, setState] = useState<ExplorerFilterContextState>({
    treeFilters: initialTreeFilters,
  });

  useEffect(() => {
    if (!loading) {
      const retrievedFilters: TreeFilter[] = treeFilters.map(convertGQLTreeFiltersToTreeFilters);
      setState((prevState) => ({
        ...prevState,
        treeFilters: retrievedFilters.map((retrievedFilter) => {
          const existingFilter: TreeFilter = state.treeFilters.find((filter) => filter.id === retrievedFilter.id);
          if (existingFilter) {
            return {
              ...retrievedFilter,
              state: existingFilter.state,
            };
          } else {
            return retrievedFilter;
          }
        }),
      }));
    }
  }, [loading, treeFilters.map((treeFilter) => treeFilter.id).join()]);

  const setTreeFilters = (treeFilters: TreeFilter[]) => {
    setState((prevState) => {
      return { ...prevState, treeFilters };
    });
  };

  return (
    <ExplorerFilterContext.Provider
      value={{
        loading,
        setTreeFilters,
        treeFilters: state.treeFilters,
      }}>
      {children}
    </ExplorerFilterContext.Provider>
  );
};
