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

import { useEffect, useState } from 'react';
import { WorkbenchOmniboxProps, WorkbenchOmniboxState } from './WorkbenchOmnibox.types';
import {
  OmniboxMode,
  GQLOmniboxCommand,
  OmniboxProvider,
  useWorkbenchOmniboxCommands,
  GQLGetWorkbenchOmniboxCommandsQueryVariables,
  useExecuteWorkbenchOmniboxCommand,
  useWorkbenchOmniboxSearch,
  GQLGetWorkbenchOmniboxSearchResultsQueryVariables,
} from '@eclipse-sirius/sirius-components-omnibox';
import { useSelection, Selection } from '@eclipse-sirius/sirius-components-core';

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
        commands: commandsData.viewer.workbenchOmniboxCommands.edges.map((edge) => edge.node),
      }));
    }
  }, [commandsLoading, commandsData]);

  useEffect(() => {
    if (!searchLoading && searchData) {
      setState((prevState) => ({
        ...prevState,
        commands: searchData.viewer.workbenchOmniboxSearch.edges.map((edge) => edge.node),
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

  const handleCommandClick = (command: GQLOmniboxCommand, mode: OmniboxMode) => {
    if (mode === 'Search') {
      const newSelection: Selection = { entries: [{ id: command.id }] };
      setSelection(newSelection);
      workbenchHandle.applySelection(newSelection);
      onClose();
    } else if (mode === 'Command') {
      if (command.id === 'search') {
        setState((prevState) => ({
          ...prevState,
          commands: null,
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
