/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import {
  GQLGetWorkbenchOmniboxCommandsQueryVariables,
  GQLGetWorkbenchOmniboxSearchResultsQueryVariables,
  OmniboxCommand,
  OmniboxMode,
  OmniboxProvider,
  toOmniboxCommand,
  useExecuteWorkbenchOmniboxCommand,
  useWorkbenchOmniboxCommands,
  useWorkbenchOmniboxSearch,
} from '@eclipse-sirius/sirius-components-omnibox';
import SearchIcon from '@mui/icons-material/Search';
import { RefObject, useEffect, useState } from 'react';
import { WorkbenchOmniboxProps, WorkbenchOmniboxState } from './WorkbenchOmnibox.types';

const isLocalStorageAvailable = (): boolean => {
  let available = false;
  if (window.localStorage) {
    try {
      window.localStorage.setItem('local_storage_availability', 'value');
      window.localStorage.getItem('local_storage_availability');
      window.localStorage.removeItem('local_storage_availability');
      available = true;
    } catch {
      available = false;
    }
  }
  return available;
};

const localStorageKey = 'sirius_web_search_history';

const getPreviousSearches = (): string[] => {
  if (!isLocalStorageAvailable()) return [];
  try {
    const data = window.localStorage.getItem(localStorageKey);
    return data ? JSON.parse(data) : [];
  } catch {
    return [];
  }
};

const saveNewSearchQuery = (newSearch: string) => {
  if (isLocalStorageAvailable() && newSearch && newSearch.length > 0) {
    const previousSearches = getPreviousSearches().filter((value) => value !== newSearch);

    const newPreviousSearch =
      previousSearches.length < 10
        ? [newSearch, ...previousSearches]
        : [newSearch, ...previousSearches.slice(0, previousSearches.length - 1)];

    window.localStorage.setItem(localStorageKey, JSON.stringify(newPreviousSearch));
  }
};

export const WorkbenchOmnibox = ({ editingContextId, workbenchHandle, children }: WorkbenchOmniboxProps) => {
  const [state, setState] = useState<WorkbenchOmniboxState>({
    open: false,
    commands: null,
  });

  const onOpen = () => setState((prevState) => ({ ...prevState, open: true }));
  const onClose = () => setState((prevState) => ({ ...prevState, open: false }));

  const { getWorkbenchOmniboxCommands, loading: commandsLoading, data: commandsData } = useWorkbenchOmniboxCommands();
  const { getWorkbenchOmniboxSearchResults, loading: searchLoading, data: searchData } = useWorkbenchOmniboxSearch();

  const { selection, setSelection } = useSelection();
  const selectedObjectIds: string[] = selection.entries.map((entry) => entry.id);

  const handleQuery = (query: string, mode: OmniboxMode) => {
    if (mode === 'Search') {
      const variables: GQLGetWorkbenchOmniboxSearchResultsQueryVariables = {
        editingContextId,
        selectedObjectIds,
        query,
      };
      saveNewSearchQuery(query);
      getWorkbenchOmniboxSearchResults({ variables });
    } else if (mode === 'Command') {
      const variables: GQLGetWorkbenchOmniboxCommandsQueryVariables = {
        editingContextId,
        selectedObjectIds,
        query,
      };
      getWorkbenchOmniboxCommands({ variables });
    }
  };

  useEffect(() => {
    if (!commandsLoading && commandsData) {
      setState((prevState) => ({
        ...prevState,
        commands: commandsData.viewer.workbenchOmniboxCommands.edges.map((edge) => toOmniboxCommand(edge.node)),
      }));
    }
  }, [commandsLoading, commandsData]);

  useEffect(() => {
    if (!searchLoading && searchData) {
      setState((prevState) => ({
        ...prevState,
        commands: searchData.viewer.workbenchOmniboxSearch.edges.map((edge) => toOmniboxCommand(edge.node)),
      }));
    }
  }, [searchLoading, searchData]);

  const {
    executeWorkbenchOmniboxCommand,
    loading: executeCommandLoading,
    data: executeCommandData,
  } = useExecuteWorkbenchOmniboxCommand();

  useEffect(() => {
    if (!executeCommandLoading && executeCommandData) {
      onClose();
    }
  }, [executeCommandLoading, executeCommandData]);

  const handleCommandClick = (
    command: OmniboxCommand,
    mode: OmniboxMode,
    inputElement: RefObject<HTMLInputElement>
  ) => {
    if (mode === 'Search') {
      if (command.id.startsWith('search:')) {
        // User selected a previous search command
        const lastSearch = command.id.replaceAll('search:', '');
        const variables: GQLGetWorkbenchOmniboxSearchResultsQueryVariables = {
          editingContextId,
          selectedObjectIds,
          query: lastSearch,
        };
        if (inputElement?.current) {
          inputElement.current.value = lastSearch;
          inputElement.current.focus();
        }
        getWorkbenchOmniboxSearchResults({ variables });
      } else {
        // User selected a search result
        const newSelection: Selection = { entries: [{ id: command.id }] };
        setSelection(newSelection);
        workbenchHandle.applySelection(newSelection);
        onClose();
      }
    } else if (mode === 'Command') {
      if (command.id === 'search') {
        // Switch to search mode. Display previous search commands
        const previousSearchCommands = getPreviousSearches().map((value) => {
          return {
            id: `search:${value}`,
            label: `Search '${value}'`,
            iconComponent: <SearchIcon />,
            description: `Previous search '${value}'`,
            __typename: 'GQLOmniboxCommandWithComponent',
          };
        });

        setState((prevState) => ({
          ...prevState,
          commands: previousSearchCommands,
        }));
      } else {
        executeWorkbenchOmniboxCommand(editingContextId, selectedObjectIds, command.id);
      }
    }
  };

  return (
    <OmniboxProvider
      open={state.open}
      onOpen={onOpen}
      onClose={onClose}
      loading={commandsLoading || searchLoading}
      commands={state.commands}
      onQuery={handleQuery}
      onCommandClick={handleCommandClick}>
      {children}
    </OmniboxProvider>
  );
};
