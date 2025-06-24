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
import { useCurrentProject } from '../views/edit-project/useCurrentProject';
import {
  ProjectCapabilitiesContextProviderProps,
  ProjectCapabilitiesContextValue,
} from './ProjectCapabilitiesContext.types';
import { useProjectCapabilities } from './useProjectCapabilities';
import { GQLProjectCapabilities } from './useProjectCapabilities.types';

const defaultValue: ProjectCapabilitiesContextValue = {
  canDownload: false,
};

export const ProjectCapabilitiesContext = React.createContext<ProjectCapabilitiesContextValue>(defaultValue);

export const ProjectCapabilitiesContextProvider = ({ children }: ProjectCapabilitiesContextProviderProps) => {
  const { project } = useCurrentProject();
  const { data } = useProjectCapabilities(project.id);
  const projectCapabilities: GQLProjectCapabilities | null = data?.viewer.project.capabilities || null;

  return (
    <ProjectCapabilitiesContext.Provider value={{ ...projectCapabilities }}>
      {children}
    </ProjectCapabilitiesContext.Provider>
  );
};
