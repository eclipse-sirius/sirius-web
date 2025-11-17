/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  OmniboxCommand,
  OmniboxMode,
  OmniboxProvider,
  toOmniboxCommand,
} from '@eclipse-sirius/sirius-components-omnibox';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ProjectBrowserOmniboxProps, ProjectBrowserOmniboxState } from './ProjectBrowserOmnibox.types';
import { useProjectsOmniboxCommands } from './useProjectsOmniboxCommands';
import { GQLGetProjectsOmniboxCommandsQueryVariables } from './useProjectsOmniboxCommands.types';
import { useProjectsOmniboxSearch } from './useProjectsOmniboxSearch';
import { GQLGetProjectsOmniboxSearchResultsQueryVariables } from './useProjectsOmniboxSearch.types';

export const ProjectBrowserOmnibox = ({ children }: ProjectBrowserOmniboxProps) => {
  const [state, setState] = useState<ProjectBrowserOmniboxState>({
    open: false,
    commands: null,
  });

  const onOpen = () => setState((prevState) => ({ ...prevState, open: true }));
  const onClose = () => setState((prevState) => ({ ...prevState, open: false }));

  const { getProjectsOmniboxCommands, loading: commandsLoading, data: commandsData } = useProjectsOmniboxCommands();
  const { getProjectsOmniboxSearchResults, loading: searchLoading, data: searchData } = useProjectsOmniboxSearch();

  const navigate = useNavigate();

  const handleQuery = (query: string, mode: OmniboxMode) => {
    if (mode === 'Search') {
      const variables: GQLGetProjectsOmniboxSearchResultsQueryVariables = {
        query,
      };
      getProjectsOmniboxSearchResults({ variables });
    } else if (mode === 'Command') {
      const variables: GQLGetProjectsOmniboxCommandsQueryVariables = {
        query,
      };
      getProjectsOmniboxCommands({ variables });
    }
  };

  useEffect(() => {
    if (!commandsLoading && commandsData) {
      setState((prevState) => ({
        ...prevState,
        commands: commandsData.viewer.projectsOmniboxCommands.edges.map((edge) => toOmniboxCommand(edge.node)),
      }));
    }
  }, [commandsLoading, commandsData]);

  useEffect(() => {
    if (!searchLoading && searchData) {
      setState((prevState) => ({
        ...prevState,
        commands: searchData.viewer.projectsOmniboxSearch.edges.map((edge) => toOmniboxCommand(edge.node)),
      }));
    }
  }, [searchLoading, searchData]);

  const { addErrorMessage } = useMultiToast();

  const handleCommandClick = (command: OmniboxCommand, mode: OmniboxMode) => {
    if (mode === 'Command') {
      if (command.id === 'newProject') {
        navigate('/new/project');
        onClose();
      } else if (command.id === 'search') {
        setState((prevState) => ({
          ...prevState,
          commands: null,
        }));
      }
    } else if (mode === 'Search') {
      const splitCommandId = command.id.split('#');
      if (splitCommandId.length == 2) {
        const rawProjectId = splitCommandId[0];
        const objectId = splitCommandId[1];
        navigate(`/projects/${rawProjectId}/edit?selection=${objectId}`);
      } else {
        addErrorMessage('Unsupported search result id ' + command.id);
      }
    }
  };

  return (
    <OmniboxProvider
      open={state.open}
      onOpen={onOpen}
      onClose={onClose}
      loading={false}
      commands={state.commands}
      onQuery={handleQuery}
      onCommandClick={handleCommandClick}>
      {children}
    </OmniboxProvider>
  );
};
