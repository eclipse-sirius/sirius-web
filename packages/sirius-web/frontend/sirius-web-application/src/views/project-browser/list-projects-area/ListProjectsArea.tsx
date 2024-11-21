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

import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ListProjectsAreaProps, ListProjectsAreaState, NoProjectsFoundProps } from './ListProjectsArea.types';
import { ProjectsTable } from './ProjectsTable';
import { useProjectsAfter, useProjectsBefore } from './useProjects';

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
    pageSize: 5,
    projects: [],
    hasPrev: false,
    hasNext: false,
    startCursor: null,
    endCursor: null,
    onRefreshProjects: () => {},
    beforeRefreshProjects: false,
    afterRefreshProjects: false,
    beforeRefreshNeeded: false,
    afterRefreshNeeded: false,
    previousPageRequired: false,
    nextPageRequired: false,
  });

  const {
    data: afterData,
    loading: afterLoading,
    refreshProjects: afterRefreshProjects,
  } = useProjectsAfter(state.endCursor, state.pageSize);

  const {
    data: beforeData,
    loading: beforeLoading,
    refreshProjects: beforeRefreshProjects,
  } = useProjectsBefore(state.startCursor, state.pageSize);

  useEffect(() => {
    if (state.startCursor === null && state.endCursor === null && !afterLoading && afterData) {
      setState((prevState) => ({
        ...prevState,
        projects: afterData?.viewer.projects.edges.map((edge) => edge.node) ?? [],
        hasPrev: false,
        hasNext: afterData?.viewer.projects.pageInfo.hasNextPage ?? false,
        startCursor: afterData?.viewer.projects.pageInfo.startCursor,
        endCursor: afterData?.viewer.projects.pageInfo.endCursor,
        onRefreshProjects: afterRefreshProjects,
        beforeRefreshProjects: false,
        afterRefreshProjects: true,
        beforeRefreshNeeded: false,
        afterRefreshNeeded: false,
        previousPageRequired: false,
        nextPageRequired: false,
      }));
    } else if (state.previousPageRequired && !beforeLoading && beforeData) {
      setState((prevState) => ({
        ...prevState,
        projects: beforeData?.viewer.projects.edges.map((edge) => edge.node).reverse() ?? [],
        hasPrev: beforeData?.viewer.projects.pageInfo.hasPreviousPage ?? false,
        hasNext: true,
        startCursor: beforeData?.viewer.projects.pageInfo.endCursor,
        endCursor: beforeData?.viewer.projects.pageInfo.startCursor,
        onRefreshProjects: beforeRefreshProjects,
        beforeRefreshProjects: true,
        afterRefreshProjects: false,
        beforeRefreshNeeded: false,
        afterRefreshNeeded: false,
        previousPageRequired: false,
        nextPageRequired: false,
      }));
    } else if (state.nextPageRequired && !afterLoading && afterData) {
      setState((prevState) => ({
        ...prevState,
        projects: afterData?.viewer.projects.edges.map((edge) => edge.node) ?? [],
        hasPrev: true,
        hasNext: afterData?.viewer.projects.pageInfo.hasNextPage ?? false,
        startCursor: afterData?.viewer.projects.pageInfo.startCursor,
        endCursor: afterData?.viewer.projects.pageInfo.endCursor,
        onRefreshProjects: afterRefreshProjects,
        beforeRefreshProjects: false,
        afterRefreshProjects: true,
        beforeRefreshNeeded: false,
        afterRefreshNeeded: false,
        previousPageRequired: false,
        nextPageRequired: false,
      }));
    } else if (state.beforeRefreshNeeded && !beforeLoading && beforeData) {
      setState((prevState) => ({
        ...prevState,
        projects: beforeData?.viewer.projects.edges.map((edge) => edge.node).reverse() ?? [],
        hasPrev: beforeData?.viewer.projects.pageInfo.hasPreviousPage ?? false,
        hasNext: true,
        startCursor: beforeData?.viewer.projects.pageInfo.endCursor,
        endCursor: beforeData?.viewer.projects.pageInfo.startCursor,
        onRefreshProjects: beforeRefreshProjects,
        beforeRefreshProjects: true,
        afterRefreshProjects: false,
        beforeRefreshNeeded: false,
        afterRefreshNeeded: false,
        previousPageRequired: false,
        nextPageRequired: false,
      }));
    } else if (state.afterRefreshNeeded && !afterLoading && afterData) {
      setState((prevState) => ({
        ...prevState,
        projects: afterData?.viewer.projects.edges.map((edge) => edge.node) ?? [],
        hasPrev: true,
        hasNext: afterData?.viewer.projects.pageInfo.hasNextPage ?? false,
        startCursor: afterData?.viewer.projects.pageInfo.startCursor,
        endCursor: afterData?.viewer.projects.pageInfo.endCursor,
        onRefreshProjects: afterRefreshProjects,
        beforeRefreshProjects: false,
        afterRefreshProjects: true,
        beforeRefreshNeeded: false,
        afterRefreshNeeded: false,
        previousPageRequired: false,
        nextPageRequired: false,
      }));
    }
  }, [afterLoading, beforeLoading, afterData, beforeData, afterRefreshProjects, beforeRefreshProjects]);

  const onPreviousPage = () => {
    setState((prevState) => ({ ...prevState, previousPageRequired: true }));
  };

  const onNextPage = () => {
    setState((prevState) => ({ ...prevState, nextPageRequired: true }));
  };

  const onPageSizeChange = (pageSize: number) =>
    setState((prevState) => ({ ...prevState, pageSize, startCursor: null, endCursor: null }));

  const onRefreshProjects = () => {
    state.onRefreshProjects();
    if (state.beforeRefreshProjects) {
      setState((prevState) => ({ ...prevState, beforeRefreshNeeded: true }));
    } else if (state.afterRefreshProjects) {
      setState((prevState) => ({ ...prevState, afterRefreshNeeded: true }));
    }
  };

  let projectsComponent: JSX.Element | null;
  if (beforeLoading || afterLoading) {
    projectsComponent = null;
  } else if (state.projects.length === 0) {
    projectsComponent = <NoProjectsFound />;
  } else {
    projectsComponent = (
      <ProjectsTable
        projects={state.projects}
        hasPrev={state.hasPrev}
        hasNext={state.hasNext}
        onPrev={onPreviousPage}
        onNext={onNextPage}
        pageSize={state.pageSize}
        onChange={onRefreshProjects}
        onPageSizeChange={onPageSizeChange}
      />
    );
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
