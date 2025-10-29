/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { WorkbenchViewComponentProps, WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { ForwardedRef, forwardRef, useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { SearchQueryInput } from './SearchQueryInput';
import { SearchResults } from './SearchResults';
import { SearchViewState } from './SearchView.types';
import { useResultAreaSize } from './useResultAreaSize';
import { useSearch } from './useSearch';
import { GQLSearchPayload, GQLSearchSuccessPayload } from './useSearch.types';
import { useSearchViewHandle } from './useSearchViewHandle';

const useSearchViewStyles = makeStyles()((theme) => ({
  view: {
    display: 'grid',
    gridTemplateRows: 'min-content min-content 1fr', // input, separator, results
    gridTemplateColumns: '1fr',
    overflow: 'auto',
  },
  separator: {
    borderTop: `1px solid ${theme.palette.divider}`,
  },
}));

const isSearchSuccessPayload = (payload: GQLSearchPayload): payload is GQLSearchSuccessPayload =>
  payload && payload.__typename === 'SearchSuccessPayload';

export const SearchView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ editingContextId, id }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const { classes } = useSearchViewStyles();

    useSearchViewHandle(id, ref);

    const [state, setState] = useState<SearchViewState>({
      query: null,
      result: null,
      resultsReceivedTimestamp: null,
    });

    const { launchSearch, loading, data } = useSearch();
    useEffect(() => {
      if (loading) {
        setState((prevState) => ({ ...prevState, result: null, resultsReceivedTimestamp: null }));
      } else if (isSearchSuccessPayload(data?.viewer?.editingContext?.search)) {
        const success: GQLSearchSuccessPayload = data.viewer.editingContext.search;
        setState((prevState) => ({ ...prevState, result: success.result, resultsReceivedTimestamp: Date.now() }));
      }
    }, [loading, data]);

    const { ref: resultAreaRef, width, height } = useResultAreaSize();

    return (
      <div className={classes.view} data-representation-kind="search-view" ref={resultAreaRef}>
        <SearchQueryInput
          onLaunchSearch={(newQuery) => {
            setState((prevState) => ({ ...prevState, query: newQuery }));
            launchSearch(editingContextId, newQuery);
          }}
        />
        <div className={classes.separator} />
        <SearchResults
          loading={loading}
          query={state.query}
          result={state.result}
          width={width}
          height={height}
          timestamp={state.resultsReceivedTimestamp}
        />
      </div>
    );
  }
);
