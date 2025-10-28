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

import { GQLOmniboxCommand, OmniboxMode, OmniboxProvider } from '@eclipse-sirius/sirius-components-omnibox';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ProjectBrowserOmniboxProps, ProjectBrowserOmniboxState } from './ProjectBrowserOmnibox.types';
import { useProjectsOmniboxCommands } from './useProjectsOmniboxCommands';
import { GQLGetProjectsOmniboxCommandsQueryVariables } from './useProjectsOmniboxCommands.types';

export const ProjectBrowserOmnibox = ({ children }: ProjectBrowserOmniboxProps) => {
  const [state, setState] = useState<ProjectBrowserOmniboxState>({
    open: false,
    commands: null,
  });

  const onOpen = () => setState((prevState) => ({ ...prevState, open: true }));
  const onClose = () => setState((prevState) => ({ ...prevState, open: false }));

  const { getProjectsOmniboxCommands, loading: commandsLoading, data: commandsData } = useProjectsOmniboxCommands();

  const navigate = useNavigate();

  const handleQuery = (query: string, _: OmniboxMode) => {
    const variables: GQLGetProjectsOmniboxCommandsQueryVariables = {
      query,
    };
    getProjectsOmniboxCommands({ variables });
  };

  useEffect(() => {
    if (!commandsLoading && commandsData) {
      setState((prevState) => ({
        ...prevState,
        commands: commandsData.viewer.projectsOmniboxCommands.edges.map((edge) => edge.node),
      }));
    }
  }, [commandsLoading, commandsData]);

  const handleCommandClick = (command: GQLOmniboxCommand, _: OmniboxMode) => {
    if (command.id === 'newProject') {
      navigate('/new/project');
      onClose();
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
