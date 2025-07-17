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

import React from 'react';
import { useParams } from 'react-router-dom';
import { ProjectContextProviderParams, ProjectContextProviderProps, ProjectContextValue } from './ProjectContext.types';
import { useProject } from './useProject';

const PROJECT_ID_SEPARATOR = '@';

const defaultValue: ProjectContextValue = {
  project: {
    id: '',
    name: '',
    natures: [],
    capabilities: {
      canDownload: false,
      canRename: false,
      canDelete: false,
      canEdit: false,
      settings: {
        canView: false,
      },
    },
    currentEditingContext: null,
  },
  name: null,
};

export const ProjectContext = React.createContext<ProjectContextValue>(defaultValue);

export const ProjectContextProvider = ({ children }: ProjectContextProviderProps) => {
  const { projectId: rawProjectId } = useParams<ProjectContextProviderParams>();

  const separatorIndex = rawProjectId?.indexOf(PROJECT_ID_SEPARATOR) ?? -1;
  const projectId: string = separatorIndex !== -1 ? rawProjectId?.substring(0, separatorIndex) : rawProjectId;
  const name: string | null =
    separatorIndex !== -1 ? rawProjectId?.substring(separatorIndex + 1, rawProjectId.length) : null;

  const { data, loading } = useProject(projectId, name);

  if (loading || !data) {
    return null;
  }

  return <ProjectContext.Provider value={{ project: data.viewer.project, name }}> {children} </ProjectContext.Provider>;
};
