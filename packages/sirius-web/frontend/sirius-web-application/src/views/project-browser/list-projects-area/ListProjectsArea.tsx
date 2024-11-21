/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ListProjectsAreaProps, ListProjectsAreaState, NoProjectsFoundProps } from './ListProjectsArea.types';
import { ProjectsTable } from './ProjectsTable';
import { useProjects } from './useProjects';

const useListProjectsAreaStyles = makeStyles()((theme) => ({
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
  const { classes } = useListProjectsAreaStyles();

  const [state, setState] = useState<ListProjectsAreaState>({
    pageSize: 20,
    startCursor: null,
    endCursor: null,
  });

  const { data, loading, refreshProjects } = useProjects(state.startCursor, state.endCursor, state.pageSize);

  const onPreviousPage = () => {
    setState((prevState) => ({
      ...prevState,
      startCursor: null,
      endCursor: data.viewer.projects.pageInfo.startCursor ?? null,
    }));
  };

  const onNextPage = () => {
    setState((prevState) => ({
      ...prevState,
      startCursor: data.viewer.projects.pageInfo.endCursor ?? null,
      endCursor: null,
    }));
  };

  const onPageSizeChange = (pageSize: number) =>
    setState((prevState) => ({
      ...prevState,
      pageSize,
      startCursor: null,
      endCursor: null,
    }));

  const onRefreshProjects = () => {
    refreshProjects();
  };

  let projectsComponent: JSX.Element | null;
  if (loading) {
    projectsComponent = null;
  } else if (data) {
    if (data.viewer.projects.edges.length === 0) {
      projectsComponent = <NoProjectsFound />;
    } else {
      const hasPrev = data.viewer.projects.pageInfo.hasPreviousPage;
      const hasNext = data.viewer.projects.pageInfo.hasNextPage;
      projectsComponent = (
        <ProjectsTable
          projects={data.viewer.projects.edges.map((edge) => edge.node)}
          hasPrev={hasPrev}
          hasNext={hasNext}
          onPrev={onPreviousPage}
          onNext={onNextPage}
          pageSize={state.pageSize}
          onChange={onRefreshProjects}
          onPageSizeChange={onPageSizeChange}
        />
      );
    }
  }
  return (
    <div className={classes.listProjectsArea}>
      <div className={classes.header}>
        <Typography variant="h4">Existing Projects</Typography>
      </div>
      <div>{projectsComponent}</div>
    </div>
  );
};

const useNoProjectsFoundStyles = makeStyles()(() => ({
  noProjectsFound: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
}));

const NoProjectsFound = ({}: NoProjectsFoundProps) => {
  const { classes } = useNoProjectsFoundStyles();
  return (
    <div className={classes.noProjectsFound}>
      <Typography variant="h4" align="center">
        No projects found, start by creating one
      </Typography>
    </div>
  );
};
