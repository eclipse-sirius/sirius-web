/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useState } from 'react';
import { ListProjectsAreaProps, ListProjectsAreaState, NoProjectsFoundProps } from './ListProjectsArea.types';
import { ProjectsTable } from './ProjectsTable';
import { useProjects } from './useProjects';
import { GQLProject } from './useProjects.types';

const useListProjectsAreaStyles = makeStyles((theme) => ({
  listProjectsArea: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(5),
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
}));

export const ListProjectsArea = ({}: ListProjectsAreaProps) => {
  const [state, setState] = useState<ListProjectsAreaState>({
    page: 0,
    limit: 20,
  });

  const onPageChange = (page: number) => setState((prevState) => ({ ...prevState, page }));

  const { data, refreshProjects } = useProjects(state.page, state.limit);
  const projects: GQLProject[] = data?.viewer.projects.edges.map((edge) => edge.node) ?? [];
  const count: number = data?.viewer.projects.pageInfo.count ?? 0;

  const classes = useListProjectsAreaStyles();
  return (
    <div className={classes.listProjectsArea}>
      <div className={classes.header}>
        <Typography variant="h4">Existing Projects</Typography>
      </div>
      <div>
        {projects.length === 0 ? (
          <NoProjectsFound />
        ) : (
          <ProjectsTable
            projects={projects}
            page={state.page}
            limit={state.limit}
            count={count}
            onChange={() => refreshProjects()}
            onPageChange={onPageChange}
          />
        )}
      </div>
    </div>
  );
};

const useNoProjectsFoundStyles = makeStyles(() => ({
  noProjectsFound: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
}));

const NoProjectsFound = ({}: NoProjectsFoundProps) => {
  const classes = useNoProjectsFoundStyles();
  return (
    <div className={classes.noProjectsFound}>
      <Typography variant="h4" align="center">
        No projects found, start by creating one
      </Typography>
    </div>
  );
};
