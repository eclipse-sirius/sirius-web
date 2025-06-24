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
import {
  ProjectsCapabilitiesContextProviderProps,
  ProjectsCapabilitiesContextValue,
} from './ProjectsCapabilitiesContext.types';
import { useProjectsCapabilities } from './useProjectsCapabilities';
import { GQLProjectsCapabilities } from './useProjectsCapabilities.types';

const defaultValue: ProjectsCapabilitiesContextValue = {
  canCreate: false,
  canUpload: false,
};

export const ProjectsCapabilitiesContext = React.createContext<ProjectsCapabilitiesContextValue>(defaultValue);

export const ProjectsCapabilitiesContextProvider = ({ children }: ProjectsCapabilitiesContextProviderProps) => {
  const { data } = useProjectsCapabilities();
  const projectsCapabilities: GQLProjectsCapabilities | null = data?.viewer.capabilities.projects || null;

  if (!projectsCapabilities) {
    return null;
  }

  return (
    <ProjectsCapabilitiesContext.Provider value={{ ...projectsCapabilities }}>
      {children}
    </ProjectsCapabilitiesContext.Provider>
  );
};
