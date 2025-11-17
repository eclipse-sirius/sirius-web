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

import { IconOverlay, Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import {
  GQLGetWorkbenchOmniboxCommandsQueryVariables,
  GQLGetWorkbenchOmniboxSearchResultsQueryVariables,
  OmniboxCommand,
  OmniboxHandle,
  OmniboxMode,
  OmniboxProvider,
  useExecuteWorkbenchOmniboxCommand,
  useWorkbenchOmniboxCommands,
  useWorkbenchOmniboxSearch,
} from '@eclipse-sirius/sirius-components-omnibox';
import SearchIcon from '@mui/icons-material/Search';
import { useEffect, useRef, useState } from 'react';
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
      const commands: OmniboxCommand[] = commandsData.viewer.workbenchOmniboxCommands.edges.map((edge) => ({
        id: edge.node.id,
        label: edge.node.label,
        description: edge.node.description,
        icon: <IconOverlay iconURLs={edge.node.iconURLs} alt={edge.node.label} />,
      }));
      setState((prevState) => ({
        ...prevState,
        commands,
      }));
    }
  }, [commandsLoading, commandsData]);

  useEffect(() => {
    if (!searchLoading && searchData) {
      const commands: OmniboxCommand[] = searchData.viewer.workbenchOmniboxSearch.edges.map((edge) => ({
        id: edge.node.id,
        label: edge.node.label,
        description: edge.node.description,
        icon: <IconOverlay iconURLs={edge.node.iconURLs} alt={edge.node.label} />,
      }));
      setState((prevState) => ({
        ...prevState,
        commands,
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

  const omniboxHandle = useRef<OmniboxHandle | null>(null);

  const handleCommandClick = (mode: OmniboxMode, command: OmniboxCommand) => {
    if (mode === 'Search') {
      if (command.id.startsWith('search:')) {
        // User selected a previous search command
        const lastSearch = command.id.replaceAll('search:', '');
        const variables: GQLGetWorkbenchOmniboxSearchResultsQueryVariables = {
          editingContextId,
          selectedObjectIds,
          query: lastSearch,
        };

        if (omniboxHandle.current) {
          omniboxHandle.current.updateInputValue(lastSearch);
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
        const previousSearchCommands: OmniboxCommand[] = getPreviousSearches().map((value) => {
          return {
            id: `search:${value}`,
            label: `Search '${value}'`,
            icon: <SearchIcon />,
            description: `Previous search '${value}'`,
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
      onCommandClick={handleCommandClick}
      ref={omniboxHandle}>
      {children}
    </OmniboxProvider>
  );
};
