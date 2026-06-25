/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
import Box from '@mui/material/Box';
import { useEffect, useState } from 'react';
import { NavigationBar } from '../../../navigationBar/NavigationBar';
import { useCurrentProject } from '../useCurrentProject';
import { EditProjectNavbarProps, EditProjectNavbarState } from './EditProjectNavbar.types';
import { ProjectTitle } from './ProjectTitle';
import { useProjectSubscription } from './useProjectSubscription';
import { GQLProjectEventPayload, GQLProjectRenamedEventPayload } from './useProjectSubscription.types';

const isProjectRenamedEventPayload = (payload: GQLProjectEventPayload): payload is GQLProjectRenamedEventPayload =>
  payload.__typename === 'ProjectRenamedEventPayload';

export const EditProjectNavbar = ({ workbenchHandle }: EditProjectNavbarProps) => {
  const { project } = useCurrentProject();
  const [state, setState] = useState<EditProjectNavbarState>({
    projectName: project.name,
  });

  useEffect(() => {
    setState((prevState) => ({
      ...prevState,
      projectName: project.name,
    }));
  }, [project]);

  const { payload } = useProjectSubscription(project.id);
  useEffect(() => {
    if (payload && isProjectRenamedEventPayload(payload)) {
      setState((prevState) => ({
        ...prevState,
        projectName: payload.newName,
      }));
    }
  }, [payload]);

  return (
    <NavigationBar>
      <Box
        sx={{
          display: 'grid',
          gridTemplateRows: '1fr',
          gridTemplateColumns: '1fr',
          alignItems: 'center',
        }}>
        <ProjectTitle name={state.projectName} workbenchHandle={workbenchHandle} key={project.id} />
      </Box>
    </NavigationBar>
  );
};
