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
import { useEffect, useState } from 'react';
import { UseTreeFilteringState, UseTreeFilteringValue } from './useTreeFiltering.types';

const convertGQLTreeFiltersToTreeFilters = (gqlTreeFilter): TreeFilter => {
  return {
    id: gqlTreeFilter.id,
    label: gqlTreeFilter.label,
    state: gqlTreeFilter.defaultState,
  };
};

export const useTreeFiltering = (
  editingContextId: string,
  activeTreeDescriptionId: string | null,
  configuredTreeFilters: TreeFilter[]
): UseTreeFilteringValue => {
  const { loading, treeFilters } = useTreeFilters(editingContextId, activeTreeDescriptionId);

  const [state, setState] = useState<UseTreeFilteringState>({
    treeFilters: configuredTreeFilters,
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

  return {
    loading,
    treeFilters: state.treeFilters,
    setTreeFilters,
  };
};
